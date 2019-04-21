'use strict';

myapp.controller('LoginController', function($rootScope, $scope, $state, $routeParams, $location, AuthSharedService, growlService) {
	// Status
	$scope.login = 1;
	$scope.register = 0;
	$scope.forgot = 0;

	$scope.rememberMe = false;
	$scope.loginFunc = function() {
		$rootScope.authenticationError = false;
		AuthSharedService.login(this.username, this.password, this.rememberMe);
	}
}).controller('HomeController', function($scope, HomeService) {
	$scope.technos = HomeService.getTechno();
}).controller('ApiDocController', function($scope) {
	// init form
	$scope.isLoading = false;
	$scope.url = $scope.swaggerUrl = 'v2/api-docs';
	// error management
	$scope.myErrorHandler = function(data, status) {
		console.log('failed to load swagger: ' + status + '   ' + data);
	};

	$scope.infos = false;
}).controller('TokensController', function($scope, UsersService, TokensService, $q) {

	var browsers = ["Firefox", 'Chrome', 'Trident']

	$q.all([UsersService.getAll().$promise, TokensService.getAll().$promise]).then(function(data) {
		var users = data[0];
		var tokens = data[1];

		tokens.forEach(function(token) {
			users.forEach(function(user) {
				if (token.userLogin === user.username) {
					token.fullName = user.fullName;
					browsers.forEach(function(browser) {
						if (token.userAgent.indexOf(browser) > -1) {
							token.browser = browser;
						}
					});
				}
			});
		});

		$scope.tokens = tokens;
	});

}).controller('LogoutController', function(AuthSharedService) {
	AuthSharedService.logout();
}).controller('ErrorController', function($scope, $state) {
	$scope.code = $state.params.code;

	switch ($scope.code) {
	case "403":
		$scope.message = "Oops! you have come to unauthorised page."
		break;
	case "404":
		$scope.message = "Page not found."
		break;
	default:
		$scope.code = 500;
		$scope.message = "Oops! unexpected error"
	}

});