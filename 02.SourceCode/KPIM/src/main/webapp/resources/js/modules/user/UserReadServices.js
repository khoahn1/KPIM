'use strict';

myapp.service('UserReadService',['$http', function ($http) {
    this.getAll = function getAll(userReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'users/user-read',
    		data: userReadRequest,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteUsers = function deleteUsers(userReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'users/user-delete',
    		data: userReadRequest,
    		headers: 'Accept:application/json'
    	});
    },
    this.updateAuthority = function updateAuthority(authority){
        return $http({
    		method: 'POST',
    		url: 'users/user-update/authority',
    		data: authority,
    		headers: 'Accept:application/json'
    	});
    },
    this.exportExcelData = function exportExcelData(){
    	return $http({
    		method: 'GET',
    		url: 'users/user-export/excel',
    		headers: 'Accept:application/json'
    	});
    }
    this.exportPdfData = function exportPdfData(){
    	return $http({
    		method: 'GET',
    		url: 'users/user-export/pdf',
    		headers: 'Accept:application/json'
    	});
    }
}]);
