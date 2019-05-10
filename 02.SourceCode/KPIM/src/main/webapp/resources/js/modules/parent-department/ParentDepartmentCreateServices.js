'use strict';

myapp.service('ParentDepartmentCreateService',['$http', function ($http) {
	
    this.create = function create(request){
    	return $http({
    		method: 'POST',
    		url: 'parent-departments/parent-department-create',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);