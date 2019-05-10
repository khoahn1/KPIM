'use strict';

myapp.service('ParentDepartmentUpdateService',['$http', function ($http) {
	
    this.init = function init(request){
        return $http({
    		method: 'GET',
    		params: request,
    		url: 'parent-departments/parent-department-update',
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'parent-departments/parent-department-update',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);