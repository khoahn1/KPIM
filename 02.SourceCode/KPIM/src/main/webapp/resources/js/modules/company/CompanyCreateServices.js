'use strict';

myapp.service('CompanyCreateService',['$http', function ($http) {
    this.show = function show(){
        return $http({
    		method: 'GET',
    		url: 'companies/company-create',
    		headers: 'Accept:application/json'
    	});
    },
    this.saveCompany = function saveCompany(request){
    	return $http({
    		method: 'POST',
    		url: 'companies/company-create',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);