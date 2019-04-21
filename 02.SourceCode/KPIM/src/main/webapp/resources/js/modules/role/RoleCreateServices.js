'use strict';

myapp.service('RoleCreateService',['$http', function ($http) {
	
    this.init = function init(){
        return $http({
    		method: 'GET',
    		url: 'roles/role-create',
    		headers: 'Accept:application/json'
    	});
    },
    this.create = function create(request){
    	return $http({
    		method: 'POST',
    		url: 'roles/role-create',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);