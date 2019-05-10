'use strict';

myapp.controller('TaskUpdateController', function ($rootScope, $scope, $state, Session, USER_ROLES, $localStorage, $sessionStorage, $ngConfirm, $location, $routeParams, $log, $uibModalInstance, TaskUpdateService) {
	
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
    	$scope.roles = $localStorage.roles;
        $scope.genders = $localStorage.genders;
    	$scope.languages = $localStorage.languages;
    	$scope.maritalStatuses = $localStorage.maritalStatuses;
    	
    	var taskUpdateRequest = {
    		id : $localStorage.prevPageData.selectedTask.id
    	};
    	TaskUpdateService.show(taskUpdateRequest)
        .then(function success(response) {
            $scope.task = response.data.taskDetailResponse;
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
                                	$rootScope.$broadcast("reload-task");
                                });
                            }
                        }
                    }
                });
            }
        });
    };
    
    // callback for ng-click 'updateTask':
    $scope.updateTask = function () {
        $scope.resetInvalidForm($scope.myForm);
		var formData = new FormData();
		$scope.taskUpdateRequest = {
			id : $scope.task.id,
			taskname : $scope.task.taskname,
			password : $scope.task.password,
			fullName : $scope.task.fullName,
			email : $scope.task.email,
			language : $scope.task.language,
			avatar : $scope.task.avatar,
			birthday : !isNullOrEmpty($scope.task.birthday) ? moment($scope.task.birthday).format('DD/MM/YYYY') : angular.element('#birthday').val(),
			status : $scope.task.status,
			gender : $scope.task.gender,
			maritalStatus : $scope.task.maritalStatus,
			address : $scope.task.address,
			phone : $scope.task.phone,
			role : $scope.task.role
		}
		convertObjFormdata(formData, $scope.taskUpdateRequest);
		if ($scope.avatarFile != undefined) {
			var avatarFile = $scope.avatarFile;
			formData.append('avatarFile', avatarFile);
		}
        TaskUpdateService.update(formData)
            .then(function success(response) {
            	$uibModalInstance.close();
            	$rootScope.$broadcast("reload-task", { title: "Update Task", message: response.data, notificationType: "success" });
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
