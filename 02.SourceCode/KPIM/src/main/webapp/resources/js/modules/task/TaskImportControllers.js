'use strict';

myapp.controller('TaskImportController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
    $sessionStorage, $ngConfirm, $routeParams, $log, $uibModalInstance, ngTableParams, TaskImportService) {

    $scope.errorMessages = [];

	var allImportExportTaskSize = $rootScope.allImportExportTask.length;
    $scope.uploadFile = function() {
        $scope.resetInvalidForm($scope.myForm);
        
		$rootScope.showImportExportBoard = true;
		$rootScope.isMinImportExportBoard = false;
		
        var data = new FormData();
        if ($scope.file != undefined) {
            var file = $scope.file;
            data.append('file', file);
        };
        
		var id_ = allImportExportTaskSize++;
		$rootScope.allImportExportTask.push({
			indexTask: id_,
            id: "Tasks_DataImport_" + id_,
			isImport: true,
			readyUpload: false,
			status: "Processing",
			fileName: file.name,
			errorMessages: []
		});
		
		$uibModalInstance.close();
        TaskImportService.upload(data).then(function success(response) {
        	var recordExport = $rootScope.allImportExportTask.find(x => x.id === "Tasks_DataImport_" + id_);
			recordExport.readyUpload = true;
			recordExport.status = "Completed";
			$rootScope.$broadcast("reload-task");
        }, function error(response) {
            if (response.status == "409") {
                $ngConfirm({
                    title: 'ERROR',
                    content: '<div class="text-center">' + response.data + '</div>',
                    icon: 'fa fa-exclamation-triangle',
                    theme: 'modern',
                    type: 'red',
                    buttons: {
                        ok: {
                            text: 'OK',
                            btnClass: 'btn btn-raised btn-danger',
                            keys: [
                                'enter'
                            ]
                        }
                    }
                });
            } else if (response.status == "400") {
                var recordExport = $rootScope.allImportExportTask.find(x => x.id === "Tasks_DataImport_" + id_);
				recordExport.readyUpload = true;
				recordExport.status = "Error";
                
                angular.forEach(response.data, function(value, key) {
                    if (value.code == "FileNotNull") {
                        var field = value.field;
                        var code = value.code;
                        var message = value.message;
                        $scope.myForm[field].$invalid = true;
                        $scope.myForm[field].$error = {
                            [code]: true,
                            'message': message
                        }
                    } else {
                    	recordExport.errorMessages.push(value.message);
                    }
                });
            }
            
            var importExportBoard = {
				allImportExportTask: $rootScope.allImportExportTask,
				showImportExportBoard: $rootScope.showImportExportBoard,
				isMinImportExportBoard: $rootScope.isMinImportExportBoard
			}
			$localStorage.importExportBoard = importExportBoard;
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
                        keys: [
                            'enter'
                        ],
                        action: function() {
                            $scope.$apply(function() {
                                $uibModalInstance.dismiss('cancel');
                            });
                        }
                    },
                    cancel: {
                        text: 'CANCEL',
                        btnClass: 'btn btn-raised btn-danger',
                        keys: [
                            'esc'
                        ]
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
            };
        });
    };
});