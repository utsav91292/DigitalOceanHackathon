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
	$scope.userType="hotel";
	var input = document.getElementById('pac-input');
	var autocomplete = new google.maps.places.Autocomplete(input);

	autocomplete.addListener('place_changed', function() {
		$scope.selectedPlace = autocomplete.getPlace();
	});

	$scope.submit = function() {
		console.log($scope.firstName);
		console.log($scope.selectedPlace.geometry.location.lat());
		console.log($scope.selectedPlace.geometry.location.lng());
		console.log($scope.userType);
	};
});

routerApp.controller('ngoController', function($scope, $http) {

});

routerApp.controller('HotelController',
		function($scope, $http) {

			var map, infoWindow;
			var markers = [];

			// map config
			var mapOptions = {
				center : new google.maps.LatLng(50, 2),
				zoom : 4,
				mapTypeId : google.maps.MapTypeId.ROADMAP,
				scrollwheel : false
			};
			map = new google.maps.Map(document.getElementById("googleMap"),
					mapOptions);

		});