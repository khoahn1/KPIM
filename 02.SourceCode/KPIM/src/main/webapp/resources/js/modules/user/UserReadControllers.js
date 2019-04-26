'use strict';

myapp.controller('UserReadController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
    $sessionStorage, $ngConfirm, $routeParams, $log, $uibModal, ngTableParams, UserReadService) {

    $scope.initData = {
        stateName: 'users',
        paginationRequest: {
            pageNumber: 1,
            pageSize: 10
        },
        sortRequest: [{
            fieldName: "createdDate",
            order: "desc"
        }],
        userSearchRequest: {
            username: '',
            email: ''
        },
        userDeleteRequests: []
    }
    
    $scope.rolesOptions = {
		dataSource: $localStorage.roles,
        dataTextField: "roleName",
        dataValueField: "id"
    };    
    
    $scope.userReadRequest = {};
    $scope.userAuthorityUpdateResquest = {};
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

                $scope.userReadRequest.paginationRequest.pageNumber = page;
                $scope.userReadRequest.paginationRequest.pageSize = pageSize;
                $scope.userReadRequest.sortRequest = [];
                if (sort && sort.length > 0) {
                    for (var i = 0; i < sort.length; i++) {
                        var sortElement = sort[i];
                        $scope.userReadRequest.sortRequest.push({
                            fieldName: sortElement['field'],
                            order: sortElement['dir']
                        });
                    };
                } else {
                    $scope.userReadRequest.sortRequest = angular.copy($scope.initData.sortRequest);
                }
                UserReadService.getAll($scope.userReadRequest).then(function success(response) {
                    options.success(response.data.users);
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
        field: "username",
        title: "Username",
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
        detailRow.find(".authority." + this.dataItem(e.masterRow).username).kendoTreeView({
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

    // callback for ng-click 'editUser':
    $scope.editUser = function(dataItem) {
        if (dataItem) {
            var storageData = {
                historyRequest: $scope.userReadRequest,
                selectedUser: dataItem,
                stateName: $scope.initData.stateName
            }
            $localStorage.prevPageData = storageData;
            $state.go('users.user-update');
        } else {
            $ngConfirm({
                title: 'ERROR',
                content: '<div class="text-center">Please select user in table!</div>',
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
    $scope.createNewUser = function() {
        $state.go('users.user-create');
    }

    // callback for ng-click 'import':
    $scope.importUser = function() {
        $state.go('users.user-import');
    }

    // callback for ng-click 'exportUser':
    var allImportExportTaskSize = $rootScope.allImportExportTask.length;
    $scope.exportExcel = function() {
        $rootScope.showImportExportBoard = true;
        $rootScope.isMinImportExportBoard = false;
        var id_ = allImportExportTaskSize++;
        $rootScope.allImportExportTask.push({
        	indexTask: id_,
            id: "Users_DataExport_Excel_" + id_,
            isExport: true,
            url: null,
            readyDownload: false,
            status: "Processing",
            fileName: "File is downloading ..."
        });
        UserReadService.exportExcelData().then(function success(response) {
            var dataUrl = response.data.fileDownloadUri;
            var dataFileName = response.data.fileName;
            var recordExport = $rootScope.allImportExportTask.find(x => x.id === "Users_DataExport_Excel_" + id_);
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

    // callback for ng-click 'exportUser':
    $scope.exportPdf = function() {

        $rootScope.showImportExportBoard = true;
        $rootScope.isMinImportExportBoard = false;
        var id_ = allImportExportTaskSize++;
        $rootScope.allImportExportTask.push({
        	indexTask: id_,
            id: "Users_DataExport_Pdf_" + id_,
            isExport: true,
            url: null,
            readyDownload: false,
            status: "Processing",
            fileName: "File is downloading ..."
        });
        UserReadService.exportPdfData().then(function success(response) {
            var dataUrl = response.data.fileDownloadUri;
            var dataFileName = response.data.fileName;
            var recordExport = $rootScope.allImportExportTask.find(x => x.id === "Users_DataExport_Pdf_" + id_);
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

    // callback for ng-click 'deleteUser':
    $scope.deleteUser = function(user) {
    	$scope.userReadRequest.userDeleteRequests = [];
        $scope.userReadRequest.userDeleteRequests.push(user);
        $ngConfirm({
            title: 'Delete User',
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
                        UserReadService.deleteUsers($scope.userReadRequest).then(function success(response) {
                        	angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete User",
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

    // callback for ng-click 'deleteUser':
    $scope.deleteUsers = function() {
    	$scope.userReadRequest.userDeleteRequests = angular.copy($scope.selectedRow);
        $ngConfirm({
            title: 'Delete User',
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
                        UserReadService.deleteUsers($scope.userReadRequest).then(function success(response) {
                            angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Users",
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
        UserReadService.upload(data).then(function success(response) {
            console.log("upload success");
        });
    };

    $scope.updateAuthorities = function(dataItem) {
        var checkedNodes = [];
        var treeView = $(".authority." + dataItem.username).data("kendoTreeView");
        getCheckedNodes(treeView.dataSource.view(), checkedNodes);
        $scope.userAuthorityUpdateResquest = angular.copy(dataItem);
        $scope.userAuthorityUpdateResquest.items = checkedNodes;
        UserReadService.updateAuthority($scope.userAuthorityUpdateResquest).then(function success(response) {
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

    // callback for ng-click 'createNewUser':
    $scope.search = function() {
        $scope.userReadRequest.userSearchRequest = angular.copy($scope.userSearchForm);
        $scope.mainGridOptions.dataSource.read();
    }

    $scope.init = function() {
        $scope.userReadRequest = angular.copy($scope.initData);
        $scope.userSearchForm = angular.copy($scope.initData.userSearchRequest);
    }

    $rootScope.$on("reload-user", function(event, opt) {
        $scope.mainGridOptions.dataSource.read();
        $scope.notification.show({
            title: opt.title,
            message: opt.message
        }, opt.notificationType);
    });

    $scope.init();
});