'use strict';

myapp.service('ProjectImportService',['$http', function ($http) {
 	this.upload = function upload(request){
    	return $http({
    		method: 'POST',
    		url: 'projects/project-import',
    		data: request,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);
