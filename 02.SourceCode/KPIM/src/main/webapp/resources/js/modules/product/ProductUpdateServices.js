'use strict';

myapp.service('ProductUpdateService',['$http', function ($http) {
	
    this.init = function init(request){
        return $http({
    		method: 'GET',
    		params: request,
    		url: 'products/product-update',
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'products/product-update',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);