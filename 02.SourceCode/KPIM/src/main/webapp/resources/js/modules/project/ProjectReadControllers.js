/**
 * 
 */
'use strict';

myapp.controller('ProjectReadController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
    $sessionStorage, $ngConfirm, $routeParams, $log, $uibModal, ngTableParams, ProjectReadService) {
	
	$scope.initData = {
		stateName :'projects',
		paginationRequest : {
			pageNumber :1,
			pageSize :10
		},
		sortRequest  : [{
			fieldName : "createdDate",
			order : "desc"
		}],
		projectSearchRequest : {
			projectCode :'',
			projectName:''
		}
	}
	var selectedOrders = [];
	$scope.projectReadRequest = {};
	var idField = "id";
	$scope.dataSource = new kendo.data.DataSource({
		transport : {
			read : function (options) {
				var pageSize = options.data.pageSize;
				var page = options.data.page;
				var skip = options.data.skip;
				var take = options.data.take;
				var sort = options.data.sort;
				var filter = options.data.filter;
				var sortParam;
				
				$scope.projectReadRequest.paginationRequest.pageNumber = page;
				$scope.projectReadRequest.paginationRequest.pageSize = pageSize;
				$scope.projectReadRequest.sortRequest =[];
				if (sort && sort.length > 0) {
                    for (var i = 0; i < sort.length; i++) {
                        var sortElement = sort[i];
                        $scope.projectReadRequest.sortRequest.push({
                            fieldName: sortElement['field'],
                            order: sortElement['dir']
                        });
                    };
                } else {
                	$scope.projectReadRequest.sortRequest = angular.copy($scope.initData.sortRequest);
                }
				
				ProjectReadService.getAll($scope.projectReadRequest)
				.then(
				    function success(response) {
				    	options.success(response.data.projects);
				    }
				);
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
	})
	
	$scope.memberColumns = [{
        selectable: true,
        width: 50
    }, {
        field: "username",
        title: "Username",
        hidden: false,
        width: 150
    }, {
        field: "email",
        title: "Email",
        hidden: false,
        width: 150
    }, {
        field: "role.roleName",
        title: "Role",
        hidden: false,
        width: 150
    }];
	
	$scope.columns = [{
        selectable: true,
        width: 50
    }, {
        field: "id",
        title: "Id",
        hidden: true,
        width: 100
    },{
        field: "projectCode",
        title: "Project Code",
        hidden: false,
        width: 150
    }, {
        field: "projectName",
        title: "Project Name",
        hidden: false,
        width: 150
    }, {
        field: "status",
        title: "Status",
        hidden: false,
        width: 150
    } ,{
        field: "department.departmentName",
        title: "Department",
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
    }, {
        title: "Action",
        hidden: false,
        width: 250,
        template: '<a ng-click="viewProject(dataItem)" class="btn btn-icon-text waves-effect btn-primary"><i class="zmdi zmdi-info"></i>View</a>'
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
	
	function onChange(e) {
		var selected = $.map(this.select(), function(item){
		    return $(item).text();	
		});
		
		if (selected.length > 0) {
			angular.element('#btnDelete').show();
		} else {
			angular.element('#btnDelete').hide();
		}
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
	
	$scope.search = function() {
		$scope.projectReadRequest.projectSearchRequest = angular.copy($scope.projectSearchForm);
        $scope.mainGridOptions.dataSource.read();
	}
	
	// callback for ng-click 'editProject':
    $scope.viewProject = function(dataItem) {
        if (dataItem) {
            var storageData = {
                historyRequest: $scope.projectReadRequest,
                selectedProject: dataItem,
                stateName: $scope.initData.stateName
            }
            $localStorage.prevPageData = storageData;
            $state.go('projects.project-detail');
        } else {
            $ngConfirm({
                title: 'ERROR',
                content: '<div class="text-center">Please select project in table!</div>',
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
	
    $scope.deleteProjects = function() {
    	
    	$scope.projectReadRequest.projectDeleteRequests = angular.copy($scope.selectedRow);
    	$ngConfirm({
            title: 'Delete Project',
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
                        ProjectReadService.deleteProjects($scope.projectReadRequest).then(function success(response) {
                            angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Projects",
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
    }
    
    $scope.deleteProject = function(project) {
    	$scope.projectReadRequest.projectDeleteRequests = [];
    	$scope.projectReadRequest.projectDeleteRequests.push(project);
    	$ngConfirm({
            title: 'Delete Project',
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
                        ProjectReadService.deleteProjects($scope.projectReadRequest).then(function success(response) {
                            angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Projects",
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
    }
    
	function detailInit(e) {
		var users = JSON.stringify(this.dataItem(e.masterRow).users);
        var detailRow = e.detailRow;
        detailRow.find(".member." + this.dataItem(e.masterRow).id).kendoGrid({
            dataSource: new kendo.data.HierarchicalDataSource({
                data: JSON.parse(users),
            }),
            columns: $scope.memberColumns
        });
    }
	
	function onExpand(e) {
        if ($("#filterText").val() == "") {
            $(e.node).find("li").show();
        }
    }
	
	$rootScope.$on("reload-project", function(event, opt) {
        $scope.mainGridOptions.dataSource.read();
        $scope.notification.show({
            title: opt.title,
            message: opt.message
        }, opt.notificationType);
    });
	
	$scope.createNewProject = function() {
        $state.go('projects.project-create');
    }
	
    $scope.importProject = function() {
        $state.go('projects.project-import');
    }
    
    var allImportExportTaskSize = $rootScope.allImportExportTask.length;
    $scope.exportExcel = function (valExtendsFile) {
    	
    	var idFile;
    	
    	idFile = "";
    	
    	if (valExtendsFile === "Excel") {
    		idFile = "Projects_DataExport_Excel_";
    	} else if (valExtendsFile === "Pdf") {
    		idFile = "Projects_DataExport_Pdf_";
    	}
    	
    	$rootScope.showImportExportBoard = true;
        $rootScope.isMinImportExportBoard = false;
        var id_ = allImportExportTaskSize++;
        $rootScope.allImportExportTask.push({
        	indexTask: id_,
            id: idFile + id_,
            isExport: true,
            url: null,
            readyDownload: false,
            status: "Processing",
            fileName: "File is downloading ..."
        });
        
        ProjectReadService.exportExcel(valExtendsFile).then(function success(response){
        	var dataUrl = response.data.fileDownloadUri;
        	var dataFileName = response.data.fileName;
        	var recordExport = $rootScope.allImportExportTask.find(x => x.id === idFile + id_);
        	
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
	
	$scope.init = function() {
        $scope.projectReadRequest = angular.copy($scope.initData);
        $scope.projectSearchForm = angular.copy($scope.initData.projectSearchRequest);
	}
	
	$scope.init();
});