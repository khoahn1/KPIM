'use strict';

myapp.controller('WorkLogReadController', function($scope, WorkLogReadService, DialogService) {

    $scope.typeOfWorkList = []; 
    $scope.phaseList = [];
    $scope.unitList = [];
    $scope.productList = [];

    $scope.workLogReadRequest = {};
    $scope.selectedRow = [];
    $scope.selectedIds = [];

    // init data
    $scope.initData = {
        stateName: 'worklogs',
        paginationRequest: {
            pageNumber: 1,
            pageSize: 10
        },
        sortRequest: [
            {
                fieldName: "logDate",
                order: "desc"
            },
            {
                fieldName: "createdDate",
                order: "desc"
            }
        ],
        worklogSearchRequest: {
            id: ''
        }
    }

    // default value for dropdown
    $scope.defaultValue = {};
    $scope.defaultValue = {
        unit: {
            id: '',
            unitName: ''
        },
        typeOfWork: {
            id: '',
            typeName: ''
        },
        user: {
        },
        task: {
            product: {
                id: '',
                productName: '',
                component: {
                    id: '',
                    componentName: ''
                },
            },
            phase: {
                id: '',
                phaseCode: '',
                unit: {
                    id: '',
                    unitName: ''
                }
            },
            user: {
            }
        }
    }

    $scope.logDateEditor = function (container, options) {
        $('<input required name="' + options.field + '"/>')
            .appendTo(container)
            .kendoDatePicker({
                animation: false,
                format: "dd/MM/yyyy",
                parseFormat: "dd/MM/yyyy",
                value: kendo.toString(new Date(options.model.logDate), 'dd/MM/yyyy')
            })
            .bind("focus", function() {
                $(this).data("kendoDatePicker").open();
            });
            $('<span class="k-invalid-msg" data-for="'+options.field+'"></span>').appendTo(container);
    }

    $scope.dropDownListEditor = function (container, options, dataSource, dataTextField, dataValueField) {
        $('<input required name="' + options.field + '"/>')
            .appendTo(container)
            .kendoDropDownList({
                autoBind: true,
                animation: false,
                dataTextField: dataTextField,
                dataValueField: dataValueField,
                dataSource: {
                    data: dataSource
                }
            }).bind('change', function () {
                // Event on change phase => change unit.
                if (options.field === 'task.phase') {
                    let grid = $("#workLogGrid").data("kendoGrid");
                    let rowEdited = grid.dataSource.getByUid(options.model.uid);
                    let dropdown = $(this).data('kendoDropDownList');
                    let phaseSelected = $scope.phaseList.find(phase => phase.id == dropdown.value());
                    rowEdited.set('unit', phaseSelected.unit ? phaseSelected.unit : $scope.unitList[0]);
                }

            });
        $('<span class="k-invalid-msg" data-for="'+options.field+'"></span>').appendTo(container);
    }

    $scope.workLogGridOptions = {
        toolbar: kendo.template($("#template-toolbar").html()),
        dataSource: new kendo.data.DataSource({
            transport: {
                read: function(options) {
                    if (options.data && !angular.equals({}, options.data)) {
                        var pageSize = options.data.pageSize;
                        var page = options.data.page;
                        var skip = options.data.skip;
                        var take = options.data.take;
                        var sort = options.data.sort;
                        var filter = options.data.filter;
                        var sortParam;
        
                        $scope.workLogReadRequest.paginationRequest.pageNumber = page;
                        $scope.workLogReadRequest.paginationRequest.pageSize = pageSize;
                        $scope.workLogReadRequest.sortRequest = [];
                        if (sort && sort.length > 0) {
                            for (var i = 0; i < sort.length; i++) {
                                var sortElement = sort[i];
                                $scope.workLogReadRequest.sortRequest.push({
                                    fieldName: sortElement['field'],
                                    order: sortElement['dir']
                                });
                            };
                        } else {
                            $scope.workLogReadRequest.sortRequest = angular.copy($scope.initData.sortRequest);
                        }
                    } else {
                        $scope.workLogReadRequest = angular.copy($scope.initData);
                    }
                    WorkLogReadService.getAll($scope.workLogReadRequest).then(function success(response) {
                        // set worklog data for grid
                        options.success(response.data.worklogs);
                        // set phase data for dropdown
                        $scope.phaseList = response.data.phases;
                        // set typeofwork data for dropdown
                        $scope.typeOfWorkList = response.data.typeOfWorks;
                        // set unit data for dropdown
                        $scope.unitList = response.data.units;
                        // set product data for dropdown
                        $scope.productList = response.data.products;
                    });
                }
            },
            pageSize: $scope.initData.paginationRequest.pageSize,
            serverPaging: true,
            serverSorting: true,
            sort: $scope.initData.sortRequest,
            schema: {
                data: 'content',
                total: 'totalElements',
                model: { 
                    id: "id",
                    fields: {
                        logDate: {
                            defaultValue: kendo.toString(new Date(), 'dd/MM/yyyy'),
                            validation: {
                                required: true
                            }
                        },
                        task: {
                            defaultValue: $scope.defaultValue.task
                        },
                        typeOfWork: {
                            defaultValue: $scope.defaultValue.typeOfWork,
                            validation: {
                                required: true
                            }
                        },
                        unit: {
                            defaultValue: $scope.defaultValue.unit,
                            validation: {
                                required: true
                            }
                        },
                        planEffort: {
                            defaultValue: 0,
                            validation: {
                                required: true,
                                min: 0
                            },
                            type: "number"
                        },
                        actualEffort: {
                            defaultValue: 0,
                            validation: {
                                required: true,
                                min: 0
                            },
                            type: "number"
                        },
                        quantity: {
                            type: "number"
                        }
                    }
                }
            },
        }),
        pageable: {
            pageSizes: [10, 20, 50, 100],
            refresh: true,
            numeric: false
        },
        editable: true,
        columns: [
            {
                selectable: true,
                width: 50
            },
            {
                field:"logDate",
                title: "Date",
                width: 130,
                template: "#=kendo.toString(logDate, 'dd/MM/yyyy')#",
                editor: $scope.logDateEditor    
            },
            {
                field: "task.product",
                title: "Products",
                width: 200,
                template: "#=task.product.productName#",
                editor: function(container, options) {
                    $scope.dropDownListEditor(container, options, $scope.productList, 'productName', 'id');
                } 
            },
            {
                field: "task.phase",
                title: "Phases",
                width: 100,
                template: "#=task.phase.phaseCode#",
                editor: function(container, options) {
                    $scope.dropDownListEditor(container, options, $scope.phaseList, 'phaseCode', 'id');
                }
            },
            {
                field: "anken",
                title: "Anken/RedmineTicket",
                width: 100
            },
            {
                field: "screen",
                title: "Screen/Module",
                width: 100
            },
            {
                field: "description",
                title: "Task description",
                width: 200
            },
            {
                field: "typeOfWork",
                title: "Type of work",
                width: 100,
                template: "#=typeOfWork.typeName#",
                editor: function(container, options) {
                    $scope.dropDownListEditor(container, options, $scope.typeOfWorkList, 'typeName', 'id');
                }
            },
            {
                title: "Effort (h)",
                columns: [
                    {
                        field:"planEffort",
                        title: "Plan",
                        width: 70
                    },
                    {
                        field: "actualEffort",
                        title: "Actual",
                        width: 70
                    }
                ]
            },
            {
                field: "quantity",
                title: "Quantity",
                width: 80,
            },
            {
                field: "unit",
                title: "Unit",
                width: 90,
                template: "#=unit.unitName#",
                editor: function(container, options) {
                    $scope.dropDownListEditor(container, options, $scope.unitList, 'unitName', 'id');
                }
            },
            {
                field: "issue",
                title: "Issue",
                width: 200
            },
            {
                field: "notes",
                title: "Notes",
                width: 200
            }
        ],
        edit: function(e) {
            let dropdown = e.container.find('[data-role=dropdownlist]').data('kendoDropDownList');
            if (dropdown) {
                dropdown.open();
            } else {
                let input = e.container.find('.k-input');
                if (input) {
                    setTimeout(()=> input.select(), 25);
                }
            }
        },
        change: function(e, arg) {
            let grid = e.sender;
            $scope.selectedIds = [];
            grid.select().each(function(e) {
                let selectedData = grid.dataItem(this);
                $scope.selectedIds.push(selectedData.id);
                
            });
            if ($scope.selectedIds.length > 0) {
                angular.element('#btnDelete').show();
            } else {
                angular.element('#btnDelete').hide();
            }
        },
        save: function(e) {
            let field = Object.keys(e.values)[0];
            let newVal = e.values[field];
            let oldModel = e.sender.dataSource._pristineForModel(e.model);

            if (oldModel) {
                let fieldChilds = field.split('.');
                let oldVal;
                for (let i = 0; i < fieldChilds.length; i++) {
                    if (oldVal === undefined) {
                        oldVal = oldModel[fieldChilds[i]];
                    } else {
                        oldVal = oldVal[fieldChilds[i]];
                    }
                }
                if (oldVal != undefined && newVal != undefined) {
                    delete newVal.parent;
                    delete newVal.uid;
                    if ((oldVal.id && newVal.id && oldVal.id === newVal.id) || oldVal === newVal) {
                        delete e.model.dirtyFields[field];
                        if (Object.keys(e.model.dirtyFields).length === 0) {
                            e.model.dirty = false;
                        }
                    }
                }
            }

            let changeRecords = $scope.getChangeRecords();
            if (changeRecords && changeRecords.length > 0) {
                angular.element('#btnSaveChanges').show();
                angular.element('#btnCancelChanges').show();
            } else {
                angular.element('#btnSaveChanges').hide();
                angular.element('#btnCancelChanges').hide();
            }
        },
        saveChanges: function(e) {
            let changeRecords = $scope.getChangeRecords();
            if (changeRecords && changeRecords.length > 0) {
                changeRecords.forEach(item => {
                    item.logDate = kendo.toString(item.logDate, 'dd/MM/yyyy');
                });
                let changesRequest = {
                    worklogs: changeRecords
                }
                WorkLogReadService.changes(changesRequest).then(
                    function success(response) {
                        if (response.data.success) {
                            $scope.workLogGridOptions.dataSource.read();
                            angular.element('#btnSaveChanges').hide();
                            angular.element('#btnCancelChanges').hide();
                            $scope.notification.show({
                                title: "Change worklogs",
                                message: response.data.message
                            }, "success");
                        } else {
                            $scope.notification.show({
                                title: "Change worklogs",
                                message: response.data.message
                            }, "error");
                        }
                    },
                    function error(response) {
                        if (response.status === 400) {
                            if (angular.isArray(response.data)) {
                                let validationErrors = [];
                                response.data.forEach(item => {
                                    if (!validationErrors[item.index]){
                                        validationErrors[item.index] = [];
                                    }
                                    validationErrors[item.index].push(item.message);
                                });
                                let validationErrorsTemplate = kendo.template($("#validationErrors").html())(validationErrors);
                                DialogService.error({
                                    title: 'ERROR',
                                    content: validationErrorsTemplate
                                });
                            }
                        }
                    }
                );
            }
        },
        dataBound: function(e) {
            let grid = e.sender;
            grid.table.on('keydown', function(e) {
                if (e.keyCode === kendo.keys.TAB && $($(e.target).closest('.k-edit-cell'))[0]) {
                    e.preventDefault();
                    let currentNumberOfItems = grid.dataSource.view().length;
                    let row = $(e.target).closest('tr').index();
                    let col = grid.cellIndex($(e.target).closest('td'));
                    
                    let dataItem = grid.dataItem($(e.target).closest('tr'));
                    let dropdown = $(e.target).find('[data-role=dropdownlist]').data('kendoDropDownList');
                    if (dropdown) {
                        var field = dropdown.element.attr('name');
                        var value = dropdown.dataSource.data().find(item => item.id === dropdown.value());
                    } else {
                        var field = $(e.target).attr('name');
                        var value = $(e.target).val();
                    }
                    if (field) {
                        dataItem.set(field, value);
                    }

                    if (row >= 0 && row < currentNumberOfItems && col >= 0 && col <= grid.columns.length) {
                        let nextCellRow;
                        let nextCellCol = col + 1;
                        if (e.shiftKey) {
                            if (nextCellCol === 2) {
                                nextCellRow = row - 1;
                                nextCellCol = grid.columns.length;
                            } else {
                                nextCellRow = row;
                                nextCellCol = col - 1;
                            }
                        } else {
                            if (nextCellCol > grid.columns.length) {
                                nextCellRow = row + 1;
                                nextCellCol = 1;
                            } else {
                                nextCellRow = row;
                            }
                        }

                        if (nextCellRow >= currentNumberOfItems || nextCellRow < 0) {
                            return;
                        }

                        // wait for cell to close and Grid to rebind when changes have been made
                        setTimeout(function() {
                            let cell = grid.tbody.find("tr:eq(" + nextCellRow + ") td:eq(" + nextCellCol + ")");
                            grid.editCell(cell);
                        });
                    } 
                }
            });
        }
    }

    $scope.createWorkLog = function() {
        let grid = $("#workLogGrid").data("kendoGrid");
        grid.addRow();
        angular.element('#btnSaveChanges').show();
        angular.element('#btnCancelChanges').show();
    }

    $scope.saveChanges = function() {
        $scope.workLogGridOptions.saveChanges();
    }

    $scope.cancelChanges = function() {
        let grid = $("#workLogGrid").data("kendoGrid");
        grid.cancelChanges();
        angular.element('#btnSaveChanges').hide();
        angular.element('#btnCancelChanges').hide();
    }

    $scope.deleteWorkLogs = function() {
        DialogService.confirm({
            title: 'Delete WorkLogs',
            content: 'Are you sure you want to delete this items?'
        }).then(
            function(response) {
                if (response) {
                    let deletesRequest = {
                        ids: angular.copy($scope.selectedIds)
                    };
                    WorkLogReadService.deletes(deletesRequest).then(
                        function success(response) {
                            if (response.data.success) {
                                $scope.workLogGridOptions.dataSource.read();
                                angular.element('#btnSaveChanges').hide();
                                angular.element('#btnCancelChanges').hide();
                                $scope.notification.show({
                                    title: "Delete WorkLogs",
                                    message: response.data.message
                                }, "success");
                            } else {
                                $scope.notification.show({
                                    title: "Delete WorkLogs",
                                    message: response.data.message
                                }, "error");
                            }
                        }
                    )
                }
            }
        )
    }

    $scope.importWorkLogs = function() {

    }

    $scope.exportWorkLogs = function() {

    }

    $scope.getChangeRecords = function() {
        let grid = $("#workLogGrid").data("kendoGrid");
        let changeDatas = grid.dataSource.data().filter(item => {
            return item.dirty || item.isNew();
        });
        return changeDatas;
    }

    $scope.getUnitObjectByUnitName = function(unitList, unitName) {
        if (angular.isArray(unitList)) {
            return unitList.find(unit => unit.unitName === unitName);
        }
    }

    $scope.init = function() {
        $scope.workLogReadRequest = angular.copy($scope.initData);
    }

    $scope.init();
});