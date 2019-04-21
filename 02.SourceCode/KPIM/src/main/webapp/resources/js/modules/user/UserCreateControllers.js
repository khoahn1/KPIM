'use strict';

myapp.controller('UserCreateController', function ($rootScope, $scope, $state, Session, USER_ROLES, $localStorage, $sessionStorage, $ngConfirm, $location, $routeParams, $log, $uibModalInstance, UserCreateService) {
	$scope.user = {};
	$scope.roles = {};
	$scope.userCreateRequest = {};
    
    $scope.initForm = function (){
		UserCreateService.show()
        .then(function success(response) {
            $scope.roles = response.data.roles;
        });
    };

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

    $scope.createNewUser = function () {
        $scope.resetInvalidForm($scope.myForm);
		var formData = new FormData();
		$scope.userCreateRequest = {
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
		convertObjFormdata(formData, $scope.userCreateRequest);
		if ($scope.avatarFile != undefined) {
			var avatarFile = $scope.avatarFile;
			formData.append('avatarFile', avatarFile);
		}
        UserCreateService.saveUser(formData)
            .then(function success(response) {
            	$uibModalInstance.close();
            	$rootScope.$broadcast("reload-user", { title: "Create User", message: response.data, notificationType: "success" });
            }, function error(response) {
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
                } else if (response.status == "400") {
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
    };
    
    $scope.init = function() {
		$scope.initForm();
	};
	
	$scope.init();
});
