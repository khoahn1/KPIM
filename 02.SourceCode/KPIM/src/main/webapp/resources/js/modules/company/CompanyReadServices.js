'use strict';

myapp.service('CompanyReadService',['$http', function ($http) {
	this.getAll = function getAll(CompanyReadRequest){
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