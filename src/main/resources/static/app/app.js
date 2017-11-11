var routerApp = angular.module('routerApp', [ 'ui.router' ]);

routerApp.config(function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/hotel');

	$stateProvider

	// HOME STATES AND NESTED VIEWS ========================================

	.state('register', {
		url : '/register',
		templateUrl : './app/views/partial-registration.html',
		controller : 'RegistrationController'
	})

	.state('hotel', {
		url : '/hotel',
		templateUrl : './app/views/partial-hotel.html',
		controller : 'HotelController'
	})

	// ABOUT PAGE AND MULTIPLE NAMED VIEWS =================================
	.state('ngo', {
		url : '/ngo',
		views : {
			'' : {
				templateUrl : './app/views/partial-ngo.html'
			},
			'columnOne@about' : {
				template : 'Look I am a column!'
			},
			'columnTwo@about' : {
				templateUrl : './app/views/table-data.html',
				controller : 'ngoController'
			}
		}

	});

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

routerApp.controller('ngoController', function($scope, $http) {

});

routerApp
		.controller('HotelController',
				function($scope, $http, $rootScope) {

					var map, infoWindow;
					var markers = [];
					$scope.subscribers = [];

					// map config
					var mapOptions = {
						center : new google.maps.LatLng(50, 2),
						zoom : 4,
						mapTypeId : google.maps.MapTypeId.ROADMAP,
						scrollwheel : false
					};
					map = new google.maps.Map(document
							.getElementById("googleMap"), mapOptions);

					$http.get("/publishers").then(
							function(response) {
								var imagePath = 'https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png';
								$scope.publishers = response.data;
								var bounds = new google.maps.LatLngBounds();
								var infoWindow = new google.maps.InfoWindow();
								angular.forEach($scope.publishers, function(
										item, index) {
									$scope.createMarker(map,
											$scope.publishers[index], bounds, infoWindow, imagePath, true);
								});
								
								map.fitBounds(bounds);
							});
					
					$http.get("/subscribers").then(
							function(response) {
								var imagePath = 'https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png';
								$scope.subscribers = response.data;
								var bounds = new google.maps.LatLngBounds();
								var infoWindow = new google.maps.InfoWindow();
								angular.forEach($scope.subscribers, function(
										item, index) {
									
									$scope.createMarker(map,
											$scope.subscribers[index], bounds, infoWindow, imagePath, false);
								});
							});

					$scope.createMarker = function(map, markerData, bounds,
							infoWindow, imagePath, extendBounds) {
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
				});
