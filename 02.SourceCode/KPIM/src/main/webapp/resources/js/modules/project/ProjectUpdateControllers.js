'use strict';

myapp.controller('ProjectUpdateController', 
		function ($rootScope, $scope, $state, 
				Session, USER_ROLES, $localStorage, 
				$sessionStorage, $ngConfirm, $location, 
				$routeParams, $log, $uibModalInstance, ProjectUpdateService) {
	$scope.projectUpdateRequest = {};
	
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
    $scope.initForm = function (){
    	var projectUpdateRequest = {
    		id : $localStorage.prevPageData.selectedProject.id
    	};
    	ProjectUpdateService.show(projectUpdateRequest)
        .then(function success(response) {
            $scope.project = response.data.projectDetailResponse;
            $scope.departments = response.data.departments;
        }, function error(response) {
            if (response.status == "404") {
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
                            , action: function () {
                                $scope.$apply(function () {
                                	$uibModalInstance.close();
                                	$rootScope.$broadcast("reload-project");
                                });
                            }
                        }
                    }
                });
            }
        });
    };
    
    // callback for ng-click 'updateProject':
    $scope.updateProject = function () {
        $scope.resetInvalidForm($scope.myForm);
		var formData = new FormData();
		$scope.projectUpdateRequest = {
			id : $scope.project.id,
			projectCode : $scope.project.projectCode,
			projectName : $scope.project.projectName,
			status : $scope.project.status,
			department : $scope.project.department
		}
		convertObjFormdata(formData, $scope.projectUpdateRequest);
        ProjectUpdateService.update(formData)
            .then(function success(response) {
            	$uibModalInstance.close();
            	$rootScope.$broadcast("reload-project", { title: "Update Project", message: response.data, notificationType: "success" });
            }, function error(response) {
                if (response.status == "400") {
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
            });
    };

    $scope.resetInvalidForm = function (form) {
        angular.forEach(form, function (value, key) {
            if (!key.startsWith('$')) {
                var field = key;
                $scope.myForm[key].$invalid = false;
            };
        });
    }
    
    $scope.init = function() {
		$scope.initForm();
	};
	
	$scope.init();
});
