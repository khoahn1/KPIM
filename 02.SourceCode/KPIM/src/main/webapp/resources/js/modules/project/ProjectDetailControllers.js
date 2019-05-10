/**
 * 
 */
'use strict';

myapp.controller('ProjectDetailController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
    $sessionStorage, $ngConfirm, $routeParams, $log, $uibModal, ngTableParams, ProjectDetailService) {
	
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
		memberSearchRequest : {
			username :''
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
				
				ProjectDetailService.getMember($scope.memberReadRequest)
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
	
	$scope.getProjectDetail = function (){
    	var projectDetailRequest = {
    		id : $localStorage.prevPageData.selectedProject.id
    	};
    	ProjectDetailService.show(projectDetailRequest)
        .then(function success(response) {
            $scope.project = response.data.projectDetailResponse;
            $scope.departments = response.data.departments;
        }, function error(response) {
            if (response.status == "404") {
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
                            , action: function () {
                                $scope.$apply(function () {
                                	$uibModalInstance.close();
                                	$rootScope.$broadcast("reload-project");
                                });
                            }
                        }
                    }
                });
            }
        });
    };
	
	$scope.search = function() {
		$scope.projectDetailRequest.memberSearchRequest = angular.copy($scope.memberSearchForm);
        $scope.mainGridOptions.dataSource.read();
	}
	
	// callback for ng-click 'editProject':
    $scope.editProject = function(dataItem) {
        var storageData = {
            historyRequest: $scope.projectDetailRequest,
            selectedProject: dataItem,
            stateName: $scope.initData.stateName
        }
        $localStorage.prevPageData = storageData;
        $state.go('projects.project-update');
    };
	
    $scope.deleteProject = function(project) {
    	$scope.projectDetailRequest.projectDeleteRequests = [];
    	$scope.projectDetailRequest.projectDeleteRequests.push(project);
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
                        ProjectDetailService.deleteProjects($scope.projectDetailRequest).then(function success(response) {
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
    
	function onExpand(e) {
        if ($("#filterText").val() == "") {
            $(e.node).find("li").show();
        }
    }
	
	$scope.init = function() {
        $scope.projectDetailRequest = angular.copy($scope.initData);
        $scope.memberSearchForm = angular.copy($scope.initData.memberSearchRequest);
        $scope.getProjectDetail();
	}
	
	$scope.init();
});