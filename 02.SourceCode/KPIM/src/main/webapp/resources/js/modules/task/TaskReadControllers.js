'use strict';

myapp.controller('TaskReadController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
    $sessionStorage, $ngConfirm, $routeParams, $log, $uibModal, ngTableParams, TaskReadService) {

    $scope.initData = {
        stateName: 'tasks',
        paginationRequest: {
            pageNumber: 1,
            pageSize: 10
        },
        sortRequest: [{
            fieldName: "createdDate",
            order: "desc"
        }],
        taskSearchRequest: {
            taskname: '',
            email: ''
        },
        taskDeleteRequests: []
    }
    
    $scope.rolesOptions = {
		dataSource: $localStorage.roles,
        dataTextField: "roleName",
        dataValueField: "id"
    };    
    
    $scope.taskReadRequest = {};
    $scope.taskAuthorityUpdateResquest = {};
    $scope.privileges = {};

    // Init grid
    $scope.selectedRow = [];
    var selectedOrders = [];
    var idField = "id";
    $scope.exportFlag = false;

    $scope.dataSource = new kendo.data.DataSource({
        transport: {
            read: function(options) {
                var pageSize = options.data.pageSize;
                var page = options.data.page;
                var skip = options.data.skip;
                var take = options.data.take;
                var sort = options.data.sort;
                var filter = options.data.filter;
                var sortParam;

                $scope.taskReadRequest.paginationRequest.pageNumber = page;
                $scope.taskReadRequest.paginationRequest.pageSize = pageSize;
                $scope.taskReadRequest.sortRequest = [];
                if (sort && sort.length > 0) {
                    for (var i = 0; i < sort.length; i++) {
                        var sortElement = sort[i];
                        $scope.taskReadRequest.sortRequest.push({
                            fieldName: sortElement['field'],
                            order: sortElement['dir']
                        });
                    };
                } else {
                    $scope.taskReadRequest.sortRequest = angular.copy($scope.initData.sortRequest);
                }
                TaskReadService.getAll($scope.taskReadRequest).then(function success(response) {
                    options.success(response.data.tasks);
                });
            }
        },
        schema: {
            data: 'content',
            total: 'totalElements',
            model: {
                id: "id"
            }
        },
        pageSize: $scope.initData.paginationRequest.pageSize,
        serverPaging: true,
        serverSorting: true,
        sort: $scope.initData.sortRequest
    });

    $scope.columns = [{
        selectable: true,
        width: 50
    }, {
        field: "id",
        title: "Id",
        hidden: true,
        width: 100
    }, {
        field: "avatar",
        title: "Avatar",
        hidden: false,
        template: '<img class="lv-img-sm" src="#= avatar #"/>',
        width: 100
    }, {
        field: "taskname",
        title: "Taskname",
        hidden: false,
        width: 150
    }, {
        field: "fullName",
        title: "Full Name",
        hidden: false,
        width: 150
    }, {
        field: "email",
        title: "Email",
        hidden: false,
        width: 150
    }, {
        field: "address",
        title: "Address",
        hidden: false,
        width: 150
    }, {
        field: "phone",
        title: "Phone",
        hidden: false,
        width: 150
    }, {
        field: "language",
        title: "Language",
        hidden: true,
        template: '<span>#= language != null ? language.languageName : "" #</span>',
        width: 150
    }, {
        field: "password",
        title: "Password",
        hidden: true,
        width: 150
    }, {
        field: "birthday",
        title: "Birthday",
        hidden: true,
        width: 150
    }, {
        field: "status",
        title: "Status",
        hidden: true,
        width: 150
    }, {
        field: "gender",
        title: "Gender",
        hidden: true,
        template: '<span>#= gender != null ? gender.genderName : "" #</span>',
        width: 150
    }, {
        field: "maritalStatus",
        title: "Marital Status",
        hidden: true,
        template: '<span>#= maritalStatus != null ? maritalStatus.maritalStatusName : "" #</span>',
        width: 150
    }, {
        field: "role.roleName",
        title: "Role",
        hidden: false,
        width: 150
    }, {
        field: "createdDate",
        title: "Created Date",
        hidden: true,
        width: 150
    }, {
        field: "updatedDate",
        title: "Updated Date",
        hidden: true,
        width: 150
    }, {
        field: "createdBy",
        title: "Created By",
        hidden: true,
        width: 150
    }, {
        field: "updatedBy",
        title: "Updated By",
        hidden: true,
        width: 150
    }];

    $scope.mainGridOptions = {
        toolbar: kendo.template($("#template-toolbar").html()),
        dataSource: $scope.dataSource,
        change: onChange,
        sortable: true,
        reorderable: true,
        resizable: true,
        columnMenu: true,
        pageable: {
            pageSizes: [10, 20, 50, 100],
            refresh: true,
            numeric: false
        },
        columns: $scope.columns,
        detailInit: detailInit,
        detailExpand: function(e) {
            var grid = e.sender;
            var rows = grid.element.find(".k-master-row").not(e.masterRow);

            rows.each(function(e) {
                grid.collapseRow(this);
            });
        },
        dataBound: onDataBound
    }

    function onDataBound(e) {
        var grid = e.sender;
        var items = grid.items();
        var itemsToSelect = [];
        items.each(function(idx, row) {
            var dataItem = grid.dataItem(row);
            if (selectedOrders[dataItem[idField]]) {
                itemsToSelect.push(row);
            }
        });
        e.sender.select(itemsToSelect);
    }

    function onChange(e, arg) {
        var selected = $.map(this.select(), function(item) {
            return $(item).text();
        });
        if (selected.length > 0) {
            angular.element('#btnDelete').show();
        } else {
            angular.element('#btnDelete').hide();
        };
        var grid = e.sender;
        var items = grid.items();
        items.each(function(idx, row) {
            var idValue = grid.dataItem(row).get(idField);
            if (row.className.indexOf("k-state-selected") >= 0) {
                selectedOrders[idValue] = true;
            } else if (selectedOrders[idValue]) {
                delete selectedOrders[idValue];
            }
        });
        var rows = e.sender.select();
        $scope.selectedRow = [];
        rows.each(function(e) {
            var selectedData = grid.dataItem(this);
            $scope.selectedRow.push(selectedData);
        })
    }

    function detailInit(e) {
        var items = JSON.stringify(this.dataItem(e.masterRow).items);
        var detailRow = e.detailRow;
        detailRow.find(".authority." + this.dataItem(e.masterRow).taskname).kendoTreeView({
            dataSource: new kendo.data.HierarchicalDataSource({
                data: JSON.parse(items),
            }),
            dataTextField: "text",
            checkboxes: {
                checkChildren: true
            },
            loadOnDemand: false,
            expand: onExpand
        });
    }

    // callback for ng-click 'editTask':
    $scope.editTask = function(dataItem) {
        if (dataItem) {
            var storageData = {
                historyRequest: $scope.taskReadRequest,
                selectedTask: dataItem,
                stateName: $scope.initData.stateName
            }
            $localStorage.prevPageData = storageData;
            $state.go('tasks.task-update');
        } else {
            $ngConfirm({
                title: 'ERROR',
                content: '<div class="text-center">Please select task in table!</div>',
                icon: 'fa fa-exclamation-triangle',
                theme: 'modern',
                type: 'red',
                buttons: {
                    ok: {
                        text: 'OK',
                        btnClass: 'btn btn-raised btn-danger',
                        keys: ['enter']
                    }
                }
            });
        }
    };

    // callback for ng-click 'create':
    $scope.createTask = function() {
        $state.go('tasks.task-create');
    }

    // callback for ng-click 'import':
    $scope.importTask = function() {
        $state.go('tasks.task-import');
    }

    // callback for ng-click 'exportTask':
    var allImportExportTaskSize = $rootScope.allImportExportTask.length;
    $scope.exportExcel = function() {
        $rootScope.showImportExportBoard = true;
        $rootScope.isMinImportExportBoard = false;
        var id_ = allImportExportTaskSize++;
        $rootScope.allImportExportTask.push({
        	indexTask: id_,
            id: "Tasks_DataExport_Excel_" + id_,
            isExport: true,
            url: null,
            readyDownload: false,
            status: "Processing",
            fileName: "File is downloading ..."
        });
        TaskReadService.exportExcelData().then(function success(response) {
            var dataUrl = response.data.fileDownloadUri;
            var dataFileName = response.data.fileName;
            var recordExport = $rootScope.allImportExportTask.find(x => x.id === "Tasks_DataExport_Excel_" + id_);
            recordExport.readyDownload = true;
            recordExport.url = dataUrl;
            recordExport.fileName = dataFileName;
            recordExport.status = "Completed";
            var importExportBoard = {
                allImportExportTask: $rootScope.allImportExportTask,
                showImportExportBoard: $rootScope.showImportExportBoard,
                isMinImportExportBoard: $rootScope.isMinImportExportBoard
            }
            $localStorage.importExportBoard = importExportBoard;
        })
    }

    // callback for ng-click 'deleteTask':
    $scope.deleteTask = function(task) {
    	$scope.taskReadRequest.taskDeleteRequests = [];
        $scope.taskReadRequest.taskDeleteRequests.push(task);
        $ngConfirm({
            title: 'Delete Task',
            icon: 'fa fa-exclamation-triangle',
            theme: 'modern',
            escapeKey: true, // close the modal when escape is pressed.
            content: 'Are you sure you want to delete this item?',
            type: 'red',
            buttons: {
                confirm: {
                    text: 'OK',
                    btnClass: 'btn btn-raised btn-success',
                    keys: ['enter'],
                    action: function() {
                        TaskReadService.deleteTasks($scope.taskReadRequest).then(function success(response) {
                        	angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Task",
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
    };

    // callback for ng-click 'deleteTask':
    $scope.deleteTasks = function() {
    	$scope.taskReadRequest.taskDeleteRequests = angular.copy($scope.selectedRow);
        $ngConfirm({
            title: 'Delete Task',
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
                        TaskReadService.deleteTasks($scope.taskReadRequest).then(function success(response) {
                            angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Tasks",
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
    };

    $scope.uploadFile = function() {
        var file = $scope.myFile;
        var data = new FormData();
        data.append('file', file);
        TaskReadService.upload(data).then(function success(response) {
            console.log("upload success");
        });
    };

    $scope.updateAuthorities = function(dataItem) {
        var checkedNodes = [];
        var treeView = $(".authority." + dataItem.taskname).data("kendoTreeView");
        getCheckedNodes(treeView.dataSource.view(), checkedNodes);
        $scope.taskAuthorityUpdateResquest = angular.copy(dataItem);
        $scope.taskAuthorityUpdateResquest.items = checkedNodes;
        TaskReadService.updateAuthority($scope.taskAuthorityUpdateResquest).then(function success(response) {
        	$scope.notification.show({
                title: "Authorities update",
                message: response.data
            }, "success");
        });
    }

    function getCheckedNodes(nodes, checkedNodes) {
        var node;
        for (var i = 0; i < nodes.length; i++) {
            node = nodes[i];
            if (node.operation) {
                checkedNodes.push(node);
            }
            if (node.hasChildren) {
                getCheckedNodes(node.children.view(), checkedNodes);
            }
        }
    }

    function onExpand(e) {
        if ($("#filterText").val() == "") {
            $(e.node).find("li").show();
        }
    }

    // callback for ng-click 'createNewTask':
    $scope.search = function() {
        $scope.taskReadRequest.taskSearchRequest = angular.copy($scope.taskSearchForm);
        $scope.mainGridOptions.dataSource.read();
    }

    $scope.init = function() {
        $scope.taskReadRequest = angular.copy($scope.initData);
        $scope.taskSearchForm = angular.copy($scope.initData.taskSearchRequest);
    }

    $rootScope.$on("reload-task", function(event, opt) {
        $scope.mainGridOptions.dataSource.read();
        $scope.notification.show({
            title: opt.title,
            message: opt.message
        }, opt.notificationType);
    });

    $scope.init();
});