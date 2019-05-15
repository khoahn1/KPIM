'use strict';

myapp.service('CompanyReadService',['$http', function ($http) {
	this.getAll = function getAll(){
    	return $http({
    		method: 'GET',
    		url: 'companies/company-read',
    		headers: 'Accept:application/json'
    	});
    },
	this.findAll = function findAll(CompanyReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'companies/company-read',
    		data: CompanyReadRequest,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteCompanies = function deleteCompanies(CompanyReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'companies/company-delete',
    		data: CompanyReadRequest,
    		headers: 'Accept:application/json'
    	});
    }
}]);