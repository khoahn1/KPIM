'use strict';

myapp.service('TypeOfWorkCreateService',['$http', function ($http) {
	
    this.create = function create(request){
    	return $http({
    		method: 'POST',
    		url: 'type-of-works/type-of-work-create',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);