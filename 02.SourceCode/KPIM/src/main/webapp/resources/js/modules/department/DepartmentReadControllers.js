'use strict';

myapp.controller('DepartmentReadController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
				$sessionStorage, $ngConfirm, $routeParams, $log, $uibModal, ngTableParams, DepartmentReadService) {

	$scope.initData = {
		stateName: 'departments',
		paginationRequest: {
			pageNumber: 1,
			pageSize: 10
		},
		sortRequests: [{
			fieldName: "createdDate",
			order: "desc"
		}],
		departmentSearchRequest: {
			departmentCode: '',
			departmentName: ''
		},
		departmentDeleteRequests: []
	}
	
    // Init grid
	$scope.selectedRow = [];
    var selectedOrders = [];
    var idField = "id";
    
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

				$scope.departmentReadRequest.paginationRequest.pageNumber = page;
				$scope.departmentReadRequest.paginationRequest.pageSize = pageSize;
				$scope.departmentReadRequest.sortRequests = [];
				if (sort && sort.length > 0) {
					for (var i = 0; i < sort.length; i++) {
						var sortElement = sort[i];
						$scope.departmentReadRequest.sortRequests.push({
							fieldName: sortElement['field'],
							order: sortElement['dir']
						});
					}
					;
				} else {
					$scope.departmentReadRequest.sortRequests = angular.copy($scope.initData.sortRequests);
				}
				DepartmentReadService.getAll($scope.departmentReadRequest).then(function success(response) {
					options.success(response.data.departments);
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
		sort: $scope.initData.sortRequests
	});
	
	$scope.columns = [{
        selectable: true,
        width: 50
    }, {
        field: "id",
        title: "Id",
        hidden: false,
        width: 100
    }, {
        field: "departmentCode",
        title: "Department Code",
        hidden: false,
        width: 200
    }, {
    	field: "departmentName",
    	title: "Department Name",
    	hidden: false,
    	width: 200
    }, {
    	field: "description",
    	title: "Description",
    	hidden: false,
    	width: 200
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
        template: '<a ng-click="updateDepartment(dataItem)" class="btn bgm-blue waves-effect m-r-5"><i class="zmdi zmdi-edit"></i> Update</a>' +
        	'<a ng-click="deleteDepartment(dataItem)" class="btn bgm-red waves-effect"><i class="zmdi zmdi-delete"></i> Delete</a>'
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


	// callback for ng-click 'create':
	$scope.createDepartment = function() {
		$state.go('departments.department-create');
	}
	
	// callback for ng-click 'editdepartment':
    $scope.updateDepartment = function(dataItem) {
        if (dataItem) {
            var storageData = {
                historyRequest: $scope.departmentReadRequest,
                selectedDepartment: dataItem,
                stateName: $scope.initData.stateName
            }
            $localStorage.prevPageData = storageData;
            $state.go('departments.department-update');
        } else {
            $ngConfirm({
                title: 'ERROR',
                content: '<div class="text-center">Please select department in table!</div>',
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
    
 // callback for ng-click 'deleteDepartment':
    $scope.deleteDepartment = function(department) {
        $scope.departmentReadRequest.departmentDeleteRequests = [];
        $scope.departmentReadRequest.departmentDeleteRequests.push(department);
        $ngConfirm({
            title: 'Delete Department',
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
                        DepartmentReadService.deleteDepartments($scope.departmentReadRequest).then(function success(response) {
                        	angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Department",
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

    // callback for ng-click 'deleteDepartment':
    $scope.deleteDepartments = function() {
    	$scope.departmentReadRequest.departmentDeleteRequests = angular.copy($scope.selectedRow);
        $ngConfirm({
            title: 'Delete Department',
            icon: 'fa fa-exclamation-triangle',
            theme: 'modern',
            escapeKey: true,
            content: 'Are you sure you want to delete this item?',
            type: 'red',
            buttons: {
                confirm: {
                    text: 'OK',
                    btnClass: 'btn btn-raised btn-success',
                    keys: ['enter'],
                    action: function() {
                        DepartmentReadService.deleteDepartments($scope.departmentReadRequest).then(function success(response) {
                            angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Departments",
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

	$rootScope.$on("reload-department", function(event, opt) {
		$scope.mainGridOptions.dataSource.read();
		$scope.notification.show({
			title: opt.title,
			message: opt.message
		}, opt.notificationType);
	});

	// callback for ng-click 'createNewDepartment':
	$scope.search = function() {
		$scope.departmentReadRequest.departmentSearchRequest = angular.copy($scope.departmentSearchForm);
		$scope.mainGridOptions.dataSource.read();
	}

	$scope.init = function() {
		$scope.departmentReadRequest = angular.copy($scope.initData);
		$scope.departmentSearchForm = angular.copy($scope.initData.departmentSearchRequest);
	}

	$scope.init();
});