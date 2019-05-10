'use strict';

myapp.controller('ProductReadController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
		$sessionStorage, $ngConfirm, $routeParams, $log, $uibModal, ngTableParams, ProductReadService) {
	
	$scope.initData = {
			stateName: 'products',
			paginationRequest: {
				pageNumber: 1,
				pageSize: 10
			},
			sortRequest: [{
				fieldName: "createdDate",
				order: "desc"
			}],
			productSearchRequest: {
				id: '',
				productName: ''
			},
			productDeleteRequests: []
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

				$scope.productReadRequest.paginationRequest.pageNumber = page;
				$scope.productReadRequest.paginationRequest.pageSize = pageSize;
				$scope.productReadRequest.sortRequest = [];
				if (sort && sort.length > 0) {
					for (var i = 0; i < sort.length; i++) {
						var sortElement = sort[i];
						$scope.productReadRequest.sortRequest.push({
							fieldName: sortElement['field'],
							order: sortElement['dir']
						});
					}
					;
				} else {
					$scope.productReadRequest.sortRequest = angular.copy($scope.initData.sortRequest);
				}
				ProductReadService.getAll($scope.productReadRequest).then(function success(response) {
					options.success(response.data.products);
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
        hidden: false,
        width: 100
    }, {
        field: "productName",
        title: "Product Name",
        hidden: false,
        width: 150
    }, {
        field: "status",
        title: "Status",
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
        template: '<a ng-click="updateProduct(dataItem)" class="btn bgm-blue waves-effect m-r-5"><i class="zmdi zmdi-edit"></i> Update</a>' +
        	'<a ng-click="deleteProduct(dataItem)" class="btn bgm-red waves-effect"><i class="zmdi zmdi-delete"></i> Delete</a>'
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
	
    $rootScope.$on("reload-product", function(event, opt) {
		$scope.mainGridOptions.dataSource.read();
		$scope.notification.show({
			title: opt.title,
			message: opt.message
		}, opt.notificationType);
	});

    // callback for ng-click 'create':
    $scope.createNewProduct = function() {
        $state.go('products.product-create');
    }
    
    // callback for ng-click 'editProduct':
    $scope.updateProduct = function(dataItem) {
        if (dataItem) {
            var storageData = {
                historyRequest: $scope.productReadRequest,
                selectedProduct: dataItem,
                stateName: $scope.initData.stateName
            }
            $localStorage.prevPageData = storageData;
            $state.go('products.product-update');
        } else {
            $ngConfirm({
                title: 'ERROR',
                content: '<div class="text-center">Please select product in table!</div>',
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
    
 // callback for ng-click 'deleteProduct':
    $scope.deleteProduct = function(product) {
        $scope.productReadRequest.productDeleteRequests = [];
        $scope.productReadRequest.productDeleteRequests.push(product);
        $ngConfirm({
            title: 'Delete Product',
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
                        ProductReadService.deleteCompanies($scope.productReadRequest).then(function success(response) {
                        	angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Product",
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

    // callback for ng-click 'deleteProduct':
    $scope.deleteCompanies = function() {
    	$scope.productReadRequest.productDeleteRequests = angular.copy($scope.selectedRow);
        $ngConfirm({
            title: 'Delete Product',
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
                        ProductReadService.deleteCompanies($scope.productReadRequest).then(function success(response) {
                            angular.element('#btnDelete').hide();
                            $scope.mainGridOptions.dataSource.read();
                            var grid = $("#mainGrid").data("kendoGrid");
                            grid.clearSelection();
                            $scope.notification.show({
                                title: "Delete Products",
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

    
	// callback for ng-click 'createNewProduct':
	$scope.search = function() {
		$scope.productReadRequest.productSearchRequest = angular.copy($scope.productSearchForm);
		$scope.mainGridOptions.dataSource.read();
	}
    
	$scope.init = function() {
		$scope.productReadRequest = angular.copy($scope.initData);
		$scope.productSearchForm = angular.copy($scope.initData.productSearchRequest);
	}

	$scope.init();
});