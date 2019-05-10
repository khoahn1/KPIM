'use strict';

myapp.service('ProjectDetailService',['$http', function ($http) {
	this.show = function show(project){
        return $http({
    		method: 'GET',
    		url: 'projects/project-detail',
    		params: user,
    		headers: 'Accept:application/json'
    	});
    },
    this.getMember = function getMember(memberReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'projects/project-detail/member-read',
    		data: memberReadRequest,
    		headers: 'Accept:application/json'
    	});
    }
    this.deleteProjects = function deleteProjects(projectDetailRequest){
    	return $http({
    		method: 'POST',
    		url: 'projects/project-delete',
    		data: projectDetailRequest,
    		headers: 'Accept:application/json'
    	});
    }
}]);
