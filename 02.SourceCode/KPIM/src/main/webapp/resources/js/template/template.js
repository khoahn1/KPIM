myapp

	// =========================================================================
	// uiGridEditDatepicker
	// =========================================================================

.directive('uiGridEditDatepicker', ['$timeout', '$document', 'uiGridConstants', 'uiGridEditConstants', function($timeout, $document, uiGridConstants, uiGridEditConstants) {
    return {
        template: function(element, attrs) {
            var html = 
				'	<div class="form-group" data-ng-controller="DatepickerDemoCtrl">'
				+ '		<div class="fg-line">'
				+ '			<div class="date-picker input-group dp-blue" ng-class="{ \'is-opened\': opened == true }">'
				+ '				<div class="fg-line" ng-class="{ \'fg-toggled\': opened == true }">'
				+ '					<input ng-click="open($event, \'opened\')" type="text" class="form-control" uib-datepicker-popup="{{format}}"'
				+ '						show-weeks="false" is-open="opened" min-date="minDate" datepicker-options="dateOptions" close-text="Close"'
				+ '						ng-model="' + attrs.rowField + '"/>'
				+ '				</div>'
				+ '			</div>'
				+ '		</div>'
				+ '	</div>'
            return html;
        },
        require: ['?^uiGrid', '?^uiGridRenderContainer'],
        scope: true,
        compile: function() {
            return {
                pre: function($scope, $elm, $attrs) {

                },
                post: function($scope, $elm, $attrs, controllers) {
                    var setCorrectPosition = function() {
                        var gridElement = $('.ui-grid-viewport');
                        var gridPosition = {
                            width: gridElement.outerWidth(),
                            height: gridElement.outerHeight(),
                            offset: gridElement.offset()
                        };

                        var cellElement = $($elm);
                        var cellPosition = {
                            width: cellElement.outerWidth(),
                            height: cellElement.outerHeight(),
                            offset: cellElement.offset()
                        };

                        var datepickerElement = $('ul', cellElement);
                        var datepickerPosition = {
                            width: datepickerElement.outerWidth(),
                            height: datepickerElement.outerHeight()
                        };
                        var newOffsetValues = {};

                        var isFreeOnRight = (gridPosition.width - (cellPosition.offset.left - gridPosition.offset.left) - cellPosition.width) > datepickerPosition.width;
                        if (isFreeOnRight) {
                            newOffsetValues.left = cellPosition.offset.left + cellPosition.width;
                        } else {
                            newOffsetValues.left = cellPosition.offset.left - datepickerPosition.width;
                        }

                        var freePixelsOnBottom = gridPosition.height - (cellPosition.offset.top - gridPosition.offset.top) - cellPosition.height;
                        var freePixelsOnTop = gridPosition.height - freePixelsOnBottom - cellPosition.height;
                        var requiredPixels = (datepickerPosition.height - cellPosition.height) / 2;
                        if (freePixelsOnBottom >= requiredPixels && freePixelsOnTop >= requiredPixels) {
                            newOffsetValues.top = cellPosition.offset.top - requiredPixels + 10;
                        } else if (freePixelsOnBottom >= requiredPixels && freePixelsOnTop < requiredPixels) {
                            newOffsetValues.top = cellPosition.offset.top - freePixelsOnTop + 10;
                        } else {
                            newOffsetValues.top = gridPosition.height - datepickerPosition.height + gridPosition.offset.top - 20;
                        }

                        datepickerElement.offset(newOffsetValues);
                        datepickerElement.css("visibility", "visible");
                    };

                    $timeout(function() {
                        setCorrectPosition();
                    }, 0);

                    $scope.isOpen = true;

                    var uiGridCtrl = controllers[0];
                    var renderContainerCtrl = controllers[1];

                    var onWindowClick = function (evt) {
                        var classNamed = angular.element(evt.target).attr('class');
                        var inDatepicker = (classNamed.indexOf('datepicker-calendar') > -1);
                        if (!inDatepicker && evt.target.nodeName !== "INPUT") {
                            $scope.stopEdit(evt);
                        }
                    };

                    var onCellClick = function (evt) {
                        console.log('click')
                        angular.element(document.querySelectorAll('.ui-grid-cell-contents')).off('click', onCellClick);
                        $scope.stopEdit(evt);
                    };

                    $scope.changeDate = function (evt) {
                        $scope.stopEdit(evt);
                    };

                    $scope.$on(uiGridEditConstants.events.BEGIN_CELL_EDIT, function () {
                        if (uiGridCtrl.grid.api.cellNav) {
                            uiGridCtrl.grid.api.cellNav.on.navigate($scope, function (newRowCol, oldRowCol) {
                                $scope.stopEdit();
                            });
                        } else {
                            angular.element(document.querySelectorAll('.ui-grid-cell-contents')).on('click', onCellClick);
                        }
                        angular.element(window).on('click', onWindowClick);

                    });

                    $scope.$on('$destroy', function () {
                        angular.element(window).off('click', onWindowClick);
                    });

                    $scope.stopEdit = function(evt) {
                        $scope.$emit(uiGridEditConstants.events.END_CELL_EDIT);
                    };

                    $elm.on('keydown', function(evt) {
                        switch (evt.keyCode) {
                            case uiGridConstants.keymap.ESC:
                                evt.stopPropagation();
                                $scope.$emit(uiGridEditConstants.events.CANCEL_CELL_EDIT);
                                break;
                        }
                        if (uiGridCtrl && uiGridCtrl.grid.api.cellNav) {
                            evt.uiGridTargetRenderContainerId = renderContainerCtrl.containerId;
                            if (uiGridCtrl.cellNav.handleKeyDown(evt) !== null) {
                                $scope.stopEdit(evt);
                            }
                        } else {
                            switch (evt.keyCode) {
                                case uiGridConstants.keymap.ENTER:
                                case uiGridConstants.keymap.TAB:
                                    evt.stopPropagation();
                                    evt.preventDefault();
                                    $scope.stopEdit(evt);
                                    break;
                            }
                        }
                        return true;
                    });
                }
            };
        }
    };
}])

    // =========================================================================
    // LAYOUT
    // =========================================================================
    
    .directive('changeLayout', function(){
        
        return {
            restrict: 'A',
            scope: {
                changeLayout: '='
            },
            
            link: function(scope, element, attr) {
                
                //Default State
                if(scope.changeLayout === '1') {
                    element.prop('checked', true);
                }
                
                //Change State
                element.on('change', function(){
                    if(element.is(':checked')) {
                        localStorage.setItem('ma-layout-status', 1);
                        scope.$apply(function(){
                            scope.changeLayout = '1';
                        })
                    }
                    else {
                        localStorage.setItem('ma-layout-status', 0);
                        scope.$apply(function(){
                            scope.changeLayout = '0';
                        })
                    }
                })
            }
        }
    })



    // =========================================================================
    // MAINMENU COLLAPSE
    // =========================================================================

    .directive('toggleSidebar', function(){

        return {
            restrict: 'A',
            scope: {
                modelLeft: '=',
                modelRight: '='
            },
            
            link: function(scope, element, attr) {
                element.on('click', function(){
 
                    if (element.data('target') === 'mainmenu') {
                        if (scope.modelLeft === false) {
                            scope.$apply(function(){
                                scope.modelLeft = true;
                            })
                        }
                        else {
                            scope.$apply(function(){
                                scope.modelLeft = false;
                            })
                        }
                    }
                    
                    if (element.data('target') === 'chat') {
                        if (scope.modelRight === false) {
                            scope.$apply(function(){
                                scope.modelRight = true;
                            })
                        }
                        else {
                            scope.$apply(function(){
                                scope.modelRight = false;
                            })
                        }
                        
                    }
                })
            }
        }
    
    })
    

    
    // =========================================================================
    // SUBMENU TOGGLE
    // =========================================================================

    .directive('toggleSubmenu', function(){

        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                element.click(function(){
                    element.next().slideToggle(200);
                    element.parent().toggleClass('toggled');
                });
            }
        }
    })


    // =========================================================================
    // STOP PROPAGATION
    // =========================================================================
    
    .directive('stopPropagate', function(){
        return {
            restrict: 'C',
            link: function(scope, element) {
                element.on('click', function(event){
                    event.stopPropagation();
                });
            }
        }
    })

    .directive('aPrevent', function(){
        return {
            restrict: 'C',
            link: function(scope, element) {
                element.on('click', function(event){
                    event.preventDefault();
                });
            }
        }
    })


    // =========================================================================
    // PRINT
    // =========================================================================
    
    .directive('print', function(){
        return {
            restrict: 'A',
            link: function(scope, element){
                element.click(function(){
                    window.print();
                })   
            }
        }
    })

   