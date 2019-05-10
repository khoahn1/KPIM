'use strict';

myapp.service('ProjectUpdateService',['$http', function ($http) {
	
    this.show = function show(user){
        return $http({
    		method: 'GET',
    		url: 'projects/project-update',
    		params: user,
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'projects/project-update',
    		data: request,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);