'use strict';

myapp.service('ParentDepartmentReadService',['$http', function ($http) {
	this.getAll = function getAll(){
    	return $http({
    		method: 'GET',
    		url: 'parent-departments/parent-department-read',
    		headers: 'Accept:application/json'
    	});
    },
    this.findAll = function findAll(request){
    	return $http({
    		method: 'POST',
    		url: 'parent-departments/parent-department-read',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteParentDepartments = function deleteParentDepartments(request){
    	return $http({
    		method: 'POST',
    		url: 'parent-departments/parent-department-delete',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);