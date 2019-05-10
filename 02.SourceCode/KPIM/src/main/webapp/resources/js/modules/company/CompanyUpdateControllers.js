'use strict';

myapp.controller('CompanyUpdateController', function($rootScope, $scope, $state, Session, USER_ROLES, $localStorage,
				$sessionStorage, $ngConfirm, $routeParams, $log, $uibModalInstance, ngTableParams, CompanyUpdateService) {
	
	$scope.initForm = function() {
		var companyUpdateRequest = {
    		id : $localStorage.prevPageData.selectedCompany.id
    	};
		CompanyUpdateService.init(companyUpdateRequest).then(function success(response) {
			$scope.company = response.data;
			$scope.treeViewOptions = {
				dataSource: new kendo.data.HierarchicalDataSource({
					data: response.data.items
				}),
				dataTextField: "text",
				checkboxes: {
					checkChildren: true
				},
				loadOnDemand: false,
				expand: onExpand
			};
		});
	};
	
    $scope.updateCompany = function () {
        $scope.resetInvalidForm($scope.myForm);
        
        /*var checkedNodes = [];
        var treeView = $(".authority").data("kendoTreeView");
        getCheckedNodes(treeView.dataSource.view(), checkedNodes);*/
        
		$scope.companyUpdateRequest = {
			id			: $scope.company.id,
			companyCode : $scope.company.companyCode,
			companyName : $scope.company.companyName,
			taxCode 	: $scope.company.taxCode,
			address		: $scope.company.address,
			province	: $scope.company.province,
			district	: $scope.company.district,
			phone		: $scope.company.phone,
			status		: $scope.company.status
		}
        CompanyUpdateService.update($scope.companyUpdateRequest)
            .then(function success(response) {
            	$uibModalInstance.close();
            	$rootScope.$broadcast("reload-company", { title: "Update company", message: response.data, notificationType: "success" });
            }, function error(response) {
                if (response.status == "409") {
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
                            }
                        }
                    });
                } else if (response.status == "400") {
                    angular.forEach(response.data, function (value, key) {
                        var field = value.field;
                        var code = value.code;
                        var message = value.message;
                        $scope.myForm[field].$invalid = true;
                        $scope.myForm[field].$error = {
                        [code]: true
                            , 'message': message
                        }
                    });
                }
            });
    };

    function onExpand(e) {
        if ($("#filterText").val() == "") {
            $(e.node).find("li").show();
        }
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
						keys: ['enter'],
						action: function() {
							$scope.$apply(function() {
								$uibModalInstance.dismiss('cancel');
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
		} else {
			$uibModalInstance.dismiss('cancel');
		}
	};

	$scope.resetInvalidForm = function(form) {
		angular.forEach(form, function(value, key) {
			if (!key.startsWith('$')) {
				var field = key;
				$scope.myForm[key].$invalid = false;
			}
			;
		});
	};

	$scope.init = function() {
		$scope.initForm();
	};

	$scope.init();
});