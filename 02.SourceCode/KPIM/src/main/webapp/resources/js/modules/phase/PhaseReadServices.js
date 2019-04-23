'use strict';

myapp.service('PhaseReadService',['$http', function ($http) {
	this.getAll = function getAll(request){
    	return $http({
    		method: 'POST',
    		url: 'phases/phase-read',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    },
    this.deletePhases = function deletePhases(request){
    	return $http({
    		method: 'POST',
    		url: 'phases/phase-delete',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);