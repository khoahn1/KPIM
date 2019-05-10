'use strict';

myapp.service('WorkLogReadService', ['$http', function ($http) {
    this.getAll = function getAll(workLogReadRequest) {
    	return $http({
    		method: 'POST',
    		url: 'worklogs/worklog-read',
    		data: workLogReadRequest,
    		headers: 'Accept:application/json'
    	});
	}
	this.changes = function changes(workLogChangeRequest) {
		return $http({
    		method: 'POST',
    		url: 'worklogs/worklog-changes',
    		data: workLogChangeRequest,
    		headers: 'Accept:application/json'
    	});
	}
	this.deletes = function deletes(workLogDeleteRequest) {
		return $http({
    		method: 'POST',
    		url: 'worklogs/worklog-deletes',
    		data: workLogDeleteRequest,
    		headers: 'Accept:application/json'
    	});
	}
}]);