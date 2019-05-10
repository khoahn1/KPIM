'use strict';

myapp.service('TaskReadService',['$http', function ($http) {
    this.getAll = function getAll(taskReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'tasks/task-read',
    		data: taskReadRequest,
    		headers: 'Accept:application/json'
    	});
    },
    this.deleteTasks = function deleteTasks(taskReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'tasks/task-delete',
    		data: taskReadRequest,
    		headers: 'Accept:application/json'
    	});
    },
    this.updateAuthority = function updateAuthority(authority){
        return $http({
    		method: 'POST',
    		url: 'tasks/task-update/authority',
    		data: authority,
    		headers: 'Accept:application/json'
    	});
    },
    this.exportExcelData = function exportExcelData(){
    	return $http({
    		method: 'GET',
    		url: 'tasks/task-export/excel',
    		headers: 'Accept:application/json'
    	});
    }
    this.exportPdfData = function exportPdfData(){
    	return $http({
    		method: 'GET',
    		url: 'tasks/task-export/pdf',
    		headers: 'Accept:application/json'
    	});
    }
}]);
