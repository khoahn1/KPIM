'use strict';

myapp.service('CompanyUpdateService',['$http', function ($http) {
	
    this.init = function init(request){
        return $http({
    		method: 'GET',
    		params: request,
    		url: 'companies/company-update',
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'companies/company-update',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);