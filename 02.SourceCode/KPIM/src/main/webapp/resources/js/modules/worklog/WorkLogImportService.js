'use strict';

myapp.service('WorkLogImportService', ['$http', function ($http) {
	this.imports = function imports(workLogImportRequest) {
    	return $http({
    		method: 'POST',
    		url: 'worklogs/worklogs-imports',
    		data: workLogImportRequest,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);