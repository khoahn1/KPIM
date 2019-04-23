'use strict';

myapp.service('UnitReadService',['$http', function ($http) {
	this.getAll = function getAll(request){
    	return $http({
    		method: 'POST',
    		url: 'units/unit-read',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteUnits = function deleteUnits(request){
    	return $http({
    		method: 'POST',
    		url: 'units/unit-delete',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);