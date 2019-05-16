'use strict';

myapp.controller('WorkLogImportController', function($rootScope, $scope, $localStorage, $uibModalInstance, WorkLogImportService, DialogService) {

    $scope.errorMessages = [];
	
    $scope.uploadFile = function() {
        if ($scope.file != undefined) {
            $scope.resetInvalidForm($scope.myForm);
            $rootScope.showImportExportBoard = true;
            $rootScope.isMinImportExportBoard = false;
            
            let data = new FormData();
            data.append('file', $scope.file);
            
            let allImportExportTaskSize = $rootScope.allImportExportTask.length;
            let id_ = allImportExportTaskSize++;
            let recordExport = {
                indexTask: id_,
                id: "WorkLogs_DataImport_" + id_,
                isImport: true,
                readyUpload: false,
                status: "Processing",
                fileName: $scope.file.name,
                errorMessages: []
            }
            $rootScope.allImportExportTask.push(recordExport);
		
            $uibModalInstance.close();
            WorkLogImportService.imports(data).then(function success(response) {
                recordExport.readyUpload = true;
                recordExport.status = "Completed";
                $rootScope.$broadcast("reload-worklog", { title: "Import worklogs", message: response.data, notificationType: "success" });
            }, function error(response) {
                if (response.status === 403) {
                    DialogService.error({
                        title: 'ERROR',
                        content: '<div class="text-center">' + response.data + '</div>'
                    });
                } else if (response.status === 400) {
                    recordExport.readyUpload = true;
                    recordExport.status = "Error";
                    
                    angular.forEach(response.data, function(value, key) {
                        if (value.code == "FileNotNull") {
                            let field = value.field;
                            let code = value.code;
                            let message = value.message;
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
                
                let importExportBoard = {
                    allImportExportTask: $rootScope.allImportExportTask,
                    showImportExportBoard: $rootScope.showImportExportBoard,
                    isMinImportExportBoard: $rootScope.isMinImportExportBoard
                }
                $localStorage.importExportBoard = importExportBoard;
            });
        } else {
            DialogService.error({
                title: 'ERROR',
                content: '<div class="text-center">Please select file to import</div>'
            })
        }
    };

    // callback for ng-click 'cancel':
    $scope.cancel = function() {
        if ($scope.myForm.$dirty) {
            DialogService.confirm({
                title: 'WARNING',
                content: '<div class="text-center">Do you sure you want to exit?</div>'
            }).then(function() {
                $scope.$apply(function() {
                    $uibModalInstance.dismiss('cancel');
                });
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