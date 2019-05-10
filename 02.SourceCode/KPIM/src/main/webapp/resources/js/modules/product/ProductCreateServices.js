'use strict';

myapp.service('ProductCreateService',['$http', function ($http) {
    this.show = function show(){
        return $http({
    		method: 'GET',
    		url: 'products/product-create',
    		headers: 'Accept:application/json'
    	});
    },
    this.saveProduct = function saveProduct(request){
    	return $http({
    		method: 'POST',
    		url: 'products/product-create',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);