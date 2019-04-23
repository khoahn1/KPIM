'use strict';

myapp.service('PhaseCreateService',['$http', function ($http) {
	
    this.create = function create(request){
    	return $http({
    		method: 'POST',
    		url: 'phases/phase-create',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);