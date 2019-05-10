'use strict';

myapp.service('ProjectInfoService',['$http', function ($http) {
	this.show = function show(project){
        return $http({
    		method: 'GET',
    		url: 'projects/project-detail/info',
    		params: project,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteProjects = function deleteProjects(projectDetailRequest){
    	return $http({
    		method: 'POST',
    		url: 'projects/project-delete',
    		data: projectDetailRequest,
    		headers: 'Accept:application/json'
    	});
    }
}]);
