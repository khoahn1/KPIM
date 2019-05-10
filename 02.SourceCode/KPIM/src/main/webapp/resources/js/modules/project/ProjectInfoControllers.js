/**
 * 
 */
'use strict';

myapp.controller('ProjectInfoController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
    $sessionStorage, $ngConfirm, $routeParams, $log, $uibModal, ngTableParams, ProjectInfoService) {
	
	$scope.getProjectDetail = function (){
    	var projectDetailRequest = {
    		id : $localStorage.prevPageData.selectedProject.id
    	};
    	ProjectInfoService.show(projectDetailRequest)
        .then(function success(response) {
            $scope.project = response.data;
        }, function error(response) {
        });
    };
    
	// callback for ng-click 'editProject':
    $scope.editProject = function(dataItem) {
        var storageData = {
            historyRequest: $scope.projectDetailRequest,
            selectedProject: dataItem,
            stateName: $scope.initData.stateName
        }
        $localStorage.prevPageData = storageData;
        $state.go('projects.project-update');
    };
	
    $scope.deleteProject = function(project) {
    	$scope.projectDetailRequest.projectDeleteRequests = [];
    	$scope.projectDetailRequest.projectDeleteRequests.push(project);
    	$ngConfirm({
            title: 'Delete Project',
            icon: 'fa fa-exclamation-triangle',
            theme: 'modern',
            escapeKey: true // close the modal when escape is pressed.
                ,
            content: 'Are you sure you want to delete this item?',
            type: 'red',
            buttons: {
                confirm: {
                    text: 'OK',
                    btnClass: 'btn btn-raised btn-success',
                    keys: ['enter'],
                    action: function() {
                        ProjectDetailService.deleteProjects($scope.projectDetailRequest).then(function success(response) {
                            angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Projects",
                                message: response.data
                            }, "success");
                        });
                    }
                },
                cancel: {
                    text: 'CANCEL',
                    btnClass: 'btn btn-raised btn-danger',
                    keys: ['esc']
                }
            }
        });
    }
    
	$scope.init = function() {
        $scope.getProjectDetail();
	}
	
	$scope.init();
});