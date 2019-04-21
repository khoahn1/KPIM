'use strict';

myapp.service('UserImportService',['$http', function ($http) {
 	this.upload = function upload(request){
    	return $http({
    		method: 'POST',
    		url: 'users/user-import',
    		data: request,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);
