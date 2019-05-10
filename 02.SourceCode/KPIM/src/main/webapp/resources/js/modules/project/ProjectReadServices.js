'use strict';

myapp.service('ProjectReadService',['$http', function ($http) {
    this.getAll = function getAll(projectReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'projects/project-read',
    		data: projectReadRequest,
    		headers: 'Accept:application/json'
    	});
    }
    
    this.deleteProjects = function deleteProjects(projectReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'projects/project-delete',
    		data: projectReadRequest,
    		headers: 'Accept:application/json'
    	});
    }
    this.exportExcel = function exportExcel(valExtendsFile){
    	
    	if (valExtendsFile === "Excel") {
    		return $http({
        		method: 'GET',
        		url: 'projects/project-export/excel',
        		headers: 'Accept:application/json'
        	});
    	} else if (valExtendsFile === "Pdf") {
    		return $http({
        		method: 'GET',
        		url: 'projects/project-export/pdf',
        		headers: 'Accept:application/json'
        	});
    	}
    }
}]);
