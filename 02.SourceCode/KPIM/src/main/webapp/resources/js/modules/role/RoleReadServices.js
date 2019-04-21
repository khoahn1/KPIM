'use strict';

myapp.service('RoleReadService',['$http', function ($http) {
	this.getAll = function getAll(roleReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'roles/role-read',
    		data: roleReadRequest,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteRoles = function deleteRoles(roleReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'roles/role-delete',
    		data: roleReadRequest,
    		headers: 'Accept:application/json'
    	});
    }
}]);