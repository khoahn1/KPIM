'use strict';

myapp.service('ProductReadService',['$http', function ($http) {
	this.getAll = function getAll(ProductReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'companies/product-read',
    		data: ProductReadRequest,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteProducts = function deleteProducts(ProductReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'companies/product-delete',
    		data: ProductReadRequest,
    		headers: 'Accept:application/json'
    	});
    }
}]);