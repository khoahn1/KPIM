'use strict';

myapp.service('PhaseUpdateService',['$http', function ($http) {
	
    this.init = function init(request){
        return $http({
    		method: 'GET',
    		params: request,
    		url: 'phases/phase-update',
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'phases/phase-update',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);