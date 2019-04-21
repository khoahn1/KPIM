'use strict';

myapp.controller('UserUpdateController', function ($rootScope, $scope, $state, Session, USER_ROLES, $localStorage, $sessionStorage, $ngConfirm, $location, $routeParams, $log, $uibModalInstance, UserUpdateService) {
	$scope.userUpdateRequest = {};
	
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
    	var userUpdateRequest = {
    		id : $localStorage.prevPageData.selectedUser.id
    	};
    	UserUpdateService.show(userUpdateRequest)
        .then(function success(response) {
            $scope.user = response.data.userDetailResponse;
            $scope.roles = response.data.roles;
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
                                	$rootScope.$broadcast("reload-user");
                                });
                            }
                        }
                    }
                });
            }
        });
    };
    
    // callback for ng-click 'updateUser':
    $scope.updateUser = function () {
        $scope.resetInvalidForm($scope.myForm);
		var formData = new FormData();
		$scope.userUpdateRequest = {
			id : $scope.user.id,
			username : $scope.user.username,
			password : $scope.user.password,
			fullName : $scope.user.fullName,
			email : $scope.user.email,
			language : $scope.user.language,
			avatar : $scope.user.avatar,
			birthday : !isNullOrEmpty($scope.user.birthday) ? moment($scope.user.birthday).format('YYYY/MM/DD') : angular.element('#birthday').val(),
			status : $scope.user.status,
			gender : $scope.user.gender,
			maritalStatus : $scope.user.maritalStatus,
			address : $scope.user.address,
			phone : $scope.user.phone,
			role : $scope.user.role
		}
		convertObjFormdata(formData, $scope.userUpdateRequest);
		if ($scope.avatarFile != undefined) {
			var avatarFile = $scope.avatarFile;
			formData.append('avatarFile', avatarFile);
		}
        UserUpdateService.update(formData)
            .then(function success(response) {
            	$uibModalInstance.close();
            	$rootScope.$broadcast("reload-user", { title: "Update User", message: response.data, notificationType: "success" });
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
