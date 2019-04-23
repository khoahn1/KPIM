'use strict';

myapp.controller('PhaseReadController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
				$sessionStorage, $ngConfirm, $routeParams, $log, $uibModal, ngTableParams, PhaseReadService) {

	$scope.initData = {
		stateName: 'phases',
		paginationRequest: {
			pageNumber: 1,
			pageSize: 10
		},
		sortRequests: [{
			fieldName: "createdDate",
			order: "desc"
		}],
		phaseSearchRequest: {
			phaseCode: '',
			phaseName: ''
		},
		phaseDeleteRequests: []
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

				$scope.phaseReadRequest.paginationRequest.pageNumber = page;
				$scope.phaseReadRequest.paginationRequest.pageSize = pageSize;
				$scope.phaseReadRequest.sortRequests = [];
				if (sort && sort.length > 0) {
					for (var i = 0; i < sort.length; i++) {
						var sortElement = sort[i];
						$scope.phaseReadRequest.sortRequests.push({
							fieldName: sortElement['field'],
							order: sortElement['dir']
						});
					}
					;
				} else {
					$scope.phaseReadRequest.sortRequests = angular.copy($scope.initData.sortRequests);
				}
				PhaseReadService.getAll($scope.phaseReadRequest).then(function success(response) {
					options.success(response.data.phases);
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
        field: "phaseCode",
        title: "Phase Code",
        hidden: false,
        width: 200
    }, {
    	field: "phaseName",
    	title: "Phase Name",
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
        template: '<a ng-click="updatePhase(dataItem)" class="btn bgm-blue waves-effect m-r-5"><i class="zmdi zmdi-edit"></i> Update</a>' +
        	'<a ng-click="deletePhase(dataItem)" class="btn bgm-red waves-effect"><i class="zmdi zmdi-delete"></i> Delete</a>'
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
	$scope.createPhase = function() {
		$state.go('phases.phase-create');
	}
	
	// callback for ng-click 'editphase':
    $scope.updatePhase = function(dataItem) {
        if (dataItem) {
            var storageData = {
                historyRequest: $scope.phaseReadRequest,
                selectedPhase: dataItem,
                stateName: $scope.initData.stateName
            }
            $localStorage.prevPageData = storageData;
            $state.go('phases.phase-update');
        } else {
            $ngConfirm({
                title: 'ERROR',
                content: '<div class="text-center">Please select phase in table!</div>',
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
    
 // callback for ng-click 'deletePhase':
    $scope.deletePhase = function(phase) {
        $scope.phaseReadRequest.phaseDeleteRequests = [];
        $scope.phaseReadRequest.phaseDeleteRequests.push(phase);
        $ngConfirm({
            title: 'Delete Phase',
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
                        PhaseReadService.deletePhases($scope.phaseReadRequest).then(function success(response) {
                        	angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Phase",
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

    // callback for ng-click 'deletePhase':
    $scope.deletePhases = function() {
    	$scope.phaseReadRequest.phaseDeleteRequests = angular.copy($scope.selectedRow);
        $ngConfirm({
            title: 'Delete Phase',
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
                        PhaseReadService.deletePhases($scope.phaseReadRequest).then(function success(response) {
                            angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Phases",
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

	$rootScope.$on("reload-phase", function(event, opt) {
		$scope.mainGridOptions.dataSource.read();
		$scope.notification.show({
			title: opt.title,
			message: opt.message
		}, opt.notificationType);
	});

	// callback for ng-click 'createNewPhase':
	$scope.search = function() {
		$scope.phaseReadRequest.phaseSearchRequest = angular.copy($scope.phaseSearchForm);
		$scope.mainGridOptions.dataSource.read();
	}

	$scope.init = function() {
		$scope.phaseReadRequest = angular.copy($scope.initData);
		$scope.phaseSearchForm = angular.copy($scope.initData.phaseSearchRequest);
	}

	$scope.init();
});