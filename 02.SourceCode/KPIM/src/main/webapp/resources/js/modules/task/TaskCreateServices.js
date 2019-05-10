'use strict';

myapp.service('TaskCreateService',['$http', function ($http) {
    this.saveTask = function saveTask(request){
    	return $http({
    		method: 'POST',
    		url: 'tasks/task-create',
    		data: request,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);