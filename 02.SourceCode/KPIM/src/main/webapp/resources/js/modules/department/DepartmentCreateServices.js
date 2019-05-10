'use strict';

myapp.service('DepartmentCreateService',['$http', function ($http) {
	
    this.create = function create(request){
    	return $http({
    		method: 'POST',
    		url: 'departments/department-create',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);