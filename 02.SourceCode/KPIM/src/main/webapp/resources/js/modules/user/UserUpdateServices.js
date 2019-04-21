'use strict';

myapp.service('UserUpdateService',['$http', function ($http) {
	
    this.show = function show(user){
        return $http({
    		method: 'GET',
    		url: 'users/user-update',
    		params: user,
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'users/user-update',
    		data: request,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);