'use strict';

myapp.service('TypeOfWorkUpdateService',['$http', function ($http) {
	
    this.init = function init(request){
        return $http({
    		method: 'GET',
    		params: request,
    		url: 'type-of-works/type-of-work-update',
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'type-of-works/type-of-work-update',
    		data: request,
    		headers: 'Accept:application/json'
    	});
    }
}]);