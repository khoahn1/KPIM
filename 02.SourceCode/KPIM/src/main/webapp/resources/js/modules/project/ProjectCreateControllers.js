/**
 * 
 */
'use strict';

myapp.controller('ProjectCreateController', 
		function ($rootScope, $scope, $state, Session, 
				USER_ROLES, $localStorage, $sessionStorage, 
				$ngConfirm, $location, $routeParams, 
				$log, $uibModalInstance, ProjectCreateService) {
	
	$scope.project = {};
	$scope.departments = {};
	$scope.projectCreateRequest = {};
	
	// callback for ng-click 'cancel':
    $scope.cancel = function () {
    	if ($scope.myForm.$dirty) {
    		$ngConfirm({
                title: 'WARNING'
                , content: '<div class="text-center">Do you sure you want to exit?</div>'
                , icon: 'fa fa-exclamation-triangle'
                , theme: 'modern'
                , type: 'red'
                , buttons: {
                    ok: {
                        text: 'OK'
                        , btnClass: 'btn btn-raised btn-success'
                        , keys: [
                        'enter'
                    ]
                        , action: function () {
                            $scope.$apply(function () {
                            	$uibModalInstance.dismiss('cancel');
                            });
                        }
                    }, cancel: {
                        text: 'CANCEL'
                            , btnClass: 'btn btn-raised btn-danger'
                            , keys: [
                                'esc'
                            ]
                    }
                }
            });
		} else {
			$uibModalInstance.dismiss('cancel');
		}
    };
	
    $scope.createNewProject = function () {
    	
    	$scope.resetInvalidForm($scope.myForm);
    	var formData = new FormData();
    	$scope.projectCreateRequest = {
    		projectCode : $scope.project.projectCode,
    		projectName : $scope.project.projectName,
    		status :$scope.project.status,
    		department : $scope.project.department
    	}
    	
    	convertObjFormdata(formData, $scope.projectCreateRequest);
    	ProjectCreateService.saveProject(formData)
    	.then(
    		function success(response) {
    			$uibModalInstance.close();
            	$rootScope.$broadcast("reload-project", 
            			{ title: "Create Project", message: response.data, notificationType: "success" });
    		},
    		function error(response) {
    			if (response.status == "409") {
    				$ngConfirm({
                        title: 'ERROR'
                        , content: '<div class="text-center">' + response.data + '</div>'
                        , icon: 'fa fa-exclamation-triangle'
                        , theme: 'modern'
                        , type: 'red'
                        , buttons: {
                            ok: {
                                text: 'OK'
                                , btnClass: 'btn btn-raised btn-danger'
                                , keys: [
                                'enter'
                            ]
                            }
                        }
                    });
    			} else if(response.status ="400") {
    				angular.forEach(response.data, function (value, key) {
                        var field = value.field;
                        var code = value.code;
                        var message = value.message;
                        $scope.myForm[field].$invalid = true;
                        $scope.myForm[field].$error = {
                        [code]: true
                            , 'message': message
                        }
                    });
    			}
    		}
    	);
    };
	
    $scope.resetInvalidForm = function (form) {
        angular.forEach(form, function (value, key) {
            if (!key.startsWith('$')) {
                var field = key;
                $scope.myForm[key].$invalid = false;
            };
        });
    };
    
	$scope.initForm = function() {
		ProjectCreateService.show()
		.then(function success(response) {
            $scope.departments = response.data.departments;
        });
	}
	
	
	$scope.init = function() {
		$scope.initForm();
	}
	
	$scope.init();
});