'use strict';

myapp.service('UnitCreateService',['$http', function ($http) {
	
    this.create = function create(request){
    	return $http({
    		method: 'POST',
    		url: 'units/unit-create',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);