'use strict';

myapp.service('TaskUpdateService',['$http', function ($http) {
	
    this.show = function show(task){
        return $http({
    		method: 'GET',
    		url: 'tasks/task-update',
    		params: task,
    		headers: 'Accept:application/json'
    	});
    },
    this.update = function update(request){
    	return $http({
    		method: 'POST',
    		url: 'tasks/task-update',
    		data: request,
    		transformRequest: angular.identity,
    		headers: {
				'Content-Type': undefined
			}
    	});
    }
}]);