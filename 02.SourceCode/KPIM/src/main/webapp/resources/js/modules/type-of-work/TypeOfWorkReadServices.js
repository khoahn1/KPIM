'use strict';

myapp.service('TypeOfWorkReadService',['$http', function ($http) {
	this.getAll = function getAll(request){
    	return $http({
    		method: 'POST',
    		url: 'type-of-works/type-of-work-read',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteTypeOfWorks = function deleteTypeOfWorks(request){
    	return $http({
    		method: 'POST',
    		url: 'type-of-works/type-of-work-delete',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);