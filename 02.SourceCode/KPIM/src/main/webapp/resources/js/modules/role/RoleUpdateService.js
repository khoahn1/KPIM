'use strict';

myapp.service('RoleUpdateService',['$http', function ($http) {
	
    this.init = function init(request){
        return $http({
    		method: 'GET',
    		params: request,
    		url: 'roles/role-update',
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'roles/role-update',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);