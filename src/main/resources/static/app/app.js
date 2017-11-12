var myLocationUrl;
var relevantLocationUrls;
var myId;
var map;
var myImageUrl;
var relevantImageUrl;

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

        .state('booking', {
            url : '/booking',
            templateUrl : './app/views/bookings.html',
            controller : 'BookingController'
        })
});

routerApp.controller('BookingController', function($scope, $http) {

    $scope.bookings = [];
    $scope.newBookingDescription = "";
    var subscriberBookings = [];
    var publisherBookings = [];

    $http.get("http://localhost:8080/bookings/subscriber/" +  myId).then(function(response) {
        subscriberBookings = response.data;
    });
    $http.get("http://localhost:8080/bookings/publisher/" +  myId).then(function(response) {
        publisherBookings = response.data;
    });

    $scope.isSubscriberBookingPage = function() {
        if(myLocationUrl.indexOf("subscriber") !== -1){
            $scope.bookings = subscriberBookings;
            return true;
        }
    };

    $scope.isPublisherBookingPage = function() {
        if(myLocationUrl.indexOf("publisher") !== -1) {
            $scope.bookings = publisherBookings;
            return true;
        }
    };

    $scope.lock = function(bookingId) {
        $http.put("http://localhost:8080/booking/lock/" + bookingId + "/subscriber/" + myId);
    };

    $scope.notify = function(bookingId) {
        $http.put("http://localhost:8080/booking/" + bookingId + "/publisher/" + myId);
    };
    
    $scope.deliver = function(bookingId) {
        $http.put("http://localhost:8080/booking/booking/"+ bookingId +"/deliver");
    };

    $scope.createNewBooking = function(description) {
        var booking = {
            description : description,
            publisherId : myId
        };

        $http.post("http://localhost:8080/booking/publisher/" +  myId, booking).then(function(response) {
        	$http.get("http://localhost:8080/bookings/subscriber/" +  myId).then(function(response) {
                subscriberBookings = response.data;
            });
            $http.get("http://localhost:8080/bookings/publisher/" +  myId).then(function(response) {
                publisherBookings = response.data;
            });
        });
    }
});

routerApp.controller('LoginController', function($scope, $state) {
	$scope.submit = function() {
        if($scope.userType == "publisher") {
            myLocationUrl = "http://localhost:8080/publisher/" + $scope.myId;
            relevantLocationUrls = "http://localhost:8080/subscribers";
            myImageUrl = 'https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png';
            relevantImageUrl = 'https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png';
        } else {
            myLocationUrl = "http://localhost:8080/subscriber/" + $scope.myId;
			relevantLocationUrls = "http://localhost:8080/publishers";
			myImageUrl = 'https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png';
            relevantImageUrl = 'https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png';
        }

        myId = $scope.myId;
        $state.go('mapview');
    };
});

routerApp.controller('RegistrationController', function($scope, $http) {
	$scope.userType = "hotel";
	$scope.email='';
	var input = document.getElementById('pac-input');
	var autocomplete = new google.maps.places.Autocomplete(input);

	autocomplete.addListener('place_changed', function() {
		$scope.selectedPlace = autocomplete.getPlace();
	});

	$scope.submit = function() {
		var user = {
			"transactions" : [],
			"name" : $scope.firstName,
			"email" : $scope.email,
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

routerApp.controller('GoogleMapController', function($scope, GoogleMapModelService, $state) {
	
	$scope.zoomToLocation = function(selectedLocation) {
		bounds = new google.maps.LatLngBounds(); 
		bounds.extend(new google.maps.LatLng(selectedLocation.latitude, selectedLocation.longitude));
		map.fitBounds(bounds);
	}
	if (myLocationUrl == undefined)
		$state.go('login');
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

    bounds = new google.maps.LatLngBounds();

    var mapOptions = {
        center : new google.maps.LatLng(50, 2),
        zoom : 4,
        mapTypeId : google.maps.MapTypeId.ROADMAP,
        scrollwheel : false
    };
    map = new google.maps.Map(document
        .getElementById("googleMap"), mapOptions);

    function loadMap() {
        loadMyLocation(myLocationUrl);
    }

    function loadMyLocation(myLocationUrl) {
        $http.get(myLocationUrl).then(
		function (response) {
			service.myLocation = response.data;
			createMarker(map, service.myLocation, bounds, infoWindow, myImageUrl, true);
			loadRelevantLocations(relevantLocationUrls);
			map.fitBounds(bounds);
		});
    }

    function loadRelevantLocations(relevantLocationUrl) {
        $http.get(relevantLocationUrl).then(
            function (response) {
                service.relevantLocations = response.data;
                angular.forEach(service.relevantLocations, function (item, index) {
                	var distance = getDistanceFromLatLonInKm(service.myLocation.latitude,service.myLocation.longitude,service.relevantLocations[index].latitude,service.relevantLocations[index].longitude);
                	service.relevantLocations[index].distance = distance.toFixed(2);
                    createMarker(map, service.relevantLocations[index], bounds, infoWindow, relevantImageUrl, false);
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
