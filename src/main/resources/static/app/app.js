var myLocationUrl;
var relevantLocationUrls;
var map;

var routerApp = angular.module('routerApp', [ 'ui.router' ]);

routerApp.config(function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/login');

	$stateProvider

	// HOME STATES AND NESTED VIEWS ========================================
	.state('login', {
		url: '/login',
		templateUrl: './app/views/login.html',
		controller: 'LoginController'
	})

	.state('register', {
		url : '/register',
		templateUrl : './app/views/partial-registration.html',
		controller : 'RegistrationController'
	})

	.state('mapview', {
		url : '/mapview',
		templateUrl : './app/views/google-map.html',
		controller : 'GoogleMapController'
	})
});

routerApp.controller('LoginController', function($scope, $state) {
	$scope.submit = function() {
        if($scope.userType == "publisher") {
            myLocationUrl = "http://localhost:8080/publisher/" + $scope.myId;
            relevantLocationUrls = "http://localhost:8080/subscribers";
        } else {
            myLocationUrl = "http://localhost:8080/subscriber/" + $scope.myId;
				relevantLocationUrls = "http://localhost:8080/publishers";
        }

        $state.go('mapview');
    };
});

routerApp.controller('RegistrationController', function($scope, $http) {
	$scope.userType = "hotel";
	var input = document.getElementById('pac-input');
	var autocomplete = new google.maps.places.Autocomplete(input);

	autocomplete.addListener('place_changed', function() {
		$scope.selectedPlace = autocomplete.getPlace();
	});

	$scope.submit = function() {
		var user = {
			"transactions" : [],
			"name" : $scope.firstName,
			"location" : $scope.selectedPlace.formatted_address,
			"latitude": $scope.selectedPlace.geometry.location.lat(),
			"longitude": $scope.selectedPlace.geometry.location.lng()
		};

		if($scope.userType == "subscriber") {
            $http.post("/subscriber", user);
        } else {
			$http.post("/publisher", user);
		}
	};
});

routerApp.controller('GoogleMapController', function($scope, GoogleMapModelService) {
	GoogleMapModelService.loadMap();

	$scope.getRelevantLocations = function() {
		return GoogleMapModelService.relevantLocations;
	}
});



routerApp.service('GoogleMapModelService', function($http) {
    var service = {
        myLocation : {},
        markers : [],
        relevantLocations : [],
        loadMap : loadMap
    };

    var infoWindow;
    var bounds;

    if(!infoWindow) {
        infoWindow = new google.maps.InfoWindow();
    }

    if(!bounds) {
        bounds = new google.maps.LatLngBounds();
    }

    if(!map) {
        var mapOptions = {
            center : new google.maps.LatLng(50, 2),
            zoom : 4,
            mapTypeId : google.maps.MapTypeId.ROADMAP,
            scrollwheel : false
        };
        map = new google.maps.Map(document
            .getElementById("googleMap"), mapOptions);
    }

    function loadMap() {
        loadMyLocation(myLocationUrl);
        loadRelevantLocations(relevantLocationUrls);
    }

    function loadMyLocation(myLocationUrl) {
        $http.get(myLocationUrl).then(
		function (response) {
			 var imagePath = 'https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png';
			service.myLocation = response.data;
			createMarker(map, service.myLocation, bounds, infoWindow, imagePath, false);
		});
    }

    function loadRelevantLocations(relevantLocationUrl) {
        $http.get(relevantLocationUrl).then(
            function (response) {
                var imagePath = 'https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png';
                service.relevantLocations = response.data;
                angular.forEach(service.relevantLocations, function (item, index) {
                    createMarker(map, service.relevantLocations[index], bounds, infoWindow, imagePath, false);
                });
            });
    }

    function createMarker(map, markerData, bounds, infoWindow, imagePath, extendBounds) {
        var location = {
            lat : markerData.latitude,
            lng : markerData.longitude
        };

        var image = {
            url : imagePath, scaledSize : new google.maps.Size(30, 30)
        };

        var marker = new google.maps.Marker({
            position : location,
            map : map,
            title : markerData.location,
            icon: image
        });

        if (extendBounds)
            bounds.extend(new google.maps.LatLng(markerData.latitude, markerData.longitude));
        marker.addListener('click', function() {
            map.setCenter(marker.getPosition());
            infoWindow.setContent(markerData.location);
            infoWindow.open(map, marker);
        });
    }

    return service;
});
