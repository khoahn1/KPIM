'use strict';

myapp.service('DepartmentReadService',['$http', function ($http) {
	this.getAll = function getAll(request){
    	return $http({
    		method: 'POST',
    		url: 'departments/department-read',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteDepartments = function deleteDepartments(request){
    	return $http({
    		method: 'POST',
    		url: 'departments/department-delete',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);