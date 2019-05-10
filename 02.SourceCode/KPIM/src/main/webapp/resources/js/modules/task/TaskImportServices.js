'use strict';

myapp.service('TaskImportService',['$http', function ($http) {
 	this.upload = function upload(request){
    	return $http({
    		method: 'POST',
    		url: 'tasks/task-import',
    		data: request,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);
