'use strict';

myapp.service('UserCreateService',['$http', function ($http) {
    this.show = function show(){
        return $http({
    		method: 'GET',
    		url: 'users/user-create',
    		headers: 'Accept:application/json'
    	});
    },
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