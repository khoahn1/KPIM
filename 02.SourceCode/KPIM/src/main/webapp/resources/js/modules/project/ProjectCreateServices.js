'use strict';

myapp.service('ProjectCreateService',['$http', function ($http) {
    this.show = function show(){
        return $http({
    		method: 'GET',
    		url: 'projects/project-create',
    		headers: 'Accept:application/json'
    	});
    },
    this.saveProject = function saveProject(request){
    	return $http({
    		method: 'POST',
    		url: 'projects/project-create',
    		data: request,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);