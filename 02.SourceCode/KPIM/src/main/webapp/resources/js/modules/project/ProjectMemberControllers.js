/**
 * 
 */
'use strict';

myapp.controller('ProjectMemberController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
    $sessionStorage, $ngConfirm, $routeParams, $log, $uibModal, ngTableParams, ProjectMemberService) {
	
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
		projectMemberSearchRequest : {
			projectId : $localStorage.prevPageData.selectedProject.id
		}
	}
	
	var selectedOrders = [];
	$scope.memberReadRequest = {};
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
				
				$scope.memberReadRequest.paginationRequest.pageNumber = page;
				$scope.memberReadRequest.paginationRequest.pageSize = pageSize;
				$scope.memberReadRequest.sortRequest =[];
				if (sort && sort.length > 0) {
                    for (var i = 0; i < sort.length; i++) {
                        var sortElement = sort[i];
                        $scope.memberReadRequest.sortRequest.push({
                            fieldName: sortElement['field'],
                            order: sortElement['dir']
                        });
                    };
                } else {
                	$scope.memberReadRequest.sortRequest = angular.copy($scope.initData.sortRequest);
                }
				
				ProjectMemberService.getMember($scope.memberReadRequest).then(
				    function success(response) {
				    	options.success(response.data);
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
        columns: $scope.memberColumns,
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
		$scope.projectDetailRequest.projectMemberSearchRequest = angular.copy($scope.memberSearchForm);
        $scope.mainGridOptions.dataSource.read();
	}
	
	function onExpand(e) {
        if ($("#filterText").val() == "") {
            $(e.node).find("li").show();
        }
    }
	
	$scope.init = function() {
        $scope.memberReadRequest = angular.copy($scope.initData);
        $scope.memberSearchForm = angular.copy($scope.initData.projectMemberSearchRequest);
	}
	
	$scope.init();
});