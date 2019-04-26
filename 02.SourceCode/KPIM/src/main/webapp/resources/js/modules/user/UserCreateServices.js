'use strict';

myapp.service('UserCreateService',['$http', function ($http) {
    this.saveUser = function saveUser(request){
    	return $http({
    		method: 'POST',
    		url: 'users/user-create',
    		data: request,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);