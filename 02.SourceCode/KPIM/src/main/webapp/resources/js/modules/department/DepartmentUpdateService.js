'use strict';

myapp.service('DepartmentUpdateService',['$http', function ($http) {
	
    this.init = function init(request){
        return $http({
    		method: 'GET',
    		params: request,
    		url: 'departments/department-update',
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'departments/department-update',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);