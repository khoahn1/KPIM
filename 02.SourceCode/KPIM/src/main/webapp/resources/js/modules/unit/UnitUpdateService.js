'use strict';

myapp.service('UnitUpdateService',['$http', function ($http) {
	
    this.init = function init(request){
        return $http({
    		method: 'GET',
    		params: request,
    		url: 'units/unit-update',
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'units/unit-update',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);