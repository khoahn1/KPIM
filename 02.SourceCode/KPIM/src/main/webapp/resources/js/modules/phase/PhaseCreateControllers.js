'use strict';

myapp.controller('PhaseCreateController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
				$sessionStorage, $ngConfirm, $routeParams, $log, $uibModalInstance, ngTableParams, PhaseCreateService) {
	
	$scope.phaseAuthorityCreateResquest = {};
	
	$scope.initForm = function() {
	};
	
    $scope.createPhase = function () {
        $scope.resetInvalidForm($scope.myForm);
        
        PhaseCreateService.create($scope.phase)
            .then(function success(response) {
            	$uibModalInstance.close();
            	$rootScope.$broadcast("reload-phase", { title: "Create Phase", message: response.data, notificationType: "success" });
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

	// callback for ng-click 'cancel':
	$scope.cancel = function() {
		if ($scope.myForm.$dirty) {
			$ngConfirm({
				title: 'WARNING',
				content: '<div class="text-center">Do you sure you want to exit?</div>',
				icon: 'fa fa-exclamation-triangle',
				theme: 'modern',
				type: 'red',
				buttons: {
					ok: {
						text: 'OK',
						btnClass: 'btn btn-raised btn-success',
						keys: ['enter'],
						action: function() {
							$scope.$apply(function() {
								$uibModalInstance.dismiss('cancel');
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
		} else {
			$uibModalInstance.dismiss('cancel');
		}
	};

	$scope.resetInvalidForm = function(form) {
		angular.forEach(form, function(value, key) {
			if (!key.startsWith('$')) {
				var field = key;
				$scope.myForm[key].$invalid = false;
			}
			;
		});
	};

	$scope.init = function() {
		$scope.initForm();
	};

	$scope.init();
});