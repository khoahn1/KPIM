'use strict';

var myapp = angular.module('myApp', ['ngResource', 'ngRoute', 'ngMessages', 'swaggerUi', 'http-auth-interceptor',
    'ngAnimate', 'ui.router', 'ui.bootstrap', 'angular-spinkit', 'cp.ngConfirm', 'ui.grid', 'ui.grid.selection', 'ui.grid.pagination', 'ui.grid.autoResize',
    'ui.grid.edit', 'ui.grid.selection', 'ui.grid.cellNav', 'ngStorage', 'oc.lazyLoad', 'nouislider', 'ngTable', 'kendo.directives', 'localytics.directives', 'ui.bootstrap.modal'
]);

myapp.constant('USER_ROLES', {
    all: '*',
    admin: 'admin',
    userRead: 'user-read',
	userCreate: 'user-create',
	userUpdate: 'user-update',
	userDelete: 'user-delete',
	userImport: 'user-import'
});

myapp.config(['$qProvider', function($qProvider) {
    'use strict';
    $qProvider.errorOnUnhandledRejections(false);
}]);

myapp.config(['$locationProvider', function($locationProvider) {
    $locationProvider.hashPrefix('');
}]);

myapp.config(['$compileProvider',
    function ($compileProvider) {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);
}]);

myapp.provider('modalState', ['$stateProvider', function($stateProvider) {
    var provider = this;

    this.$get = function() {
        return provider;
    }

    this.state = function(stateName, options) {
        var modalInstance;
        options["animation"] = true;
        options["size"] = 'slg';
        options["keyboard"] = false;
        options["backdrop"] = 'static';
        options.onEnter = onEnter;
        options.onExit = onExit;
        if (!options.resolve) options.resolve = [];

        var resolveKeys = angular.isArray(options.resolve) ? options.resolve : Object.keys(options.resolve);
        $stateProvider.state(stateName, omit(options, ['template', 'templateUrl', 'controller', 'controllerAs']));

        onEnter.$inject = ['$uibModal', '$state', '$timeout'].concat(resolveKeys);

        function onEnter($uibModal, $state, $timeout) {
            options.resolve = {};

            for (var i = onEnter.$inject.length - resolveKeys.length; i < onEnter.$inject.length; i++) {
                (function(key, val) {
                    options.resolve[key] = function() {
                        return val
                    }
                })(onEnter.$inject[i], arguments[i]);
            }

            $timeout(function() { // to let populate $stateParams
                modalInstance = $uibModal.open(options);
                modalInstance.result.finally(function() {
                    $timeout(function() { // to let populate $state.$current
                        if ($state.$current.name === stateName)
                            $state.go(options.parent || '^');
                    });
                });
            });
        }
        function onExit() {
            if (modalInstance)
            	modalInstance.dismiss('cancel');
        }
        return provider;
    }
}]);
function omit(object, forbidenKeys) {
    var prunedObject = {};
    for (var key in object)
        if (forbidenKeys.indexOf(key) === -1)
            prunedObject[key] = object[key];
    return prunedObject;
};

myapp.run(function($rootScope, $state, $location, $http, AuthSharedService, Session, USER_ROLES, $q, $timeout,
    growlService, $sce, $localStorage) {
    $rootScope.$on('$stateChangeStart', function(event, toState) {
        if (toState.name === "login" && $rootScope.authenticated) {
            event.preventDefault();
        } else if (toState.redirectTo && $rootScope.authenticated) {
            event.preventDefault();
            $state.go(toState.redirectTo, {}, {
                location: 'replace'
            })
            $state.reload();
        } else if (toState.access && toState.access.loginRequired && !$rootScope.authenticated) {
            event.preventDefault();
            $rootScope.$broadcast("event:auth-loginRequired", {});
        } else if (toState.access && !AuthSharedService.isAuthorized(toState.access.authorizedRoles)) {
            event.preventDefault();
            $rootScope.$broadcast("event:auth-forbidden", {});
        }
    });

    // Call when the the client is confirmed
    $rootScope.$on('event:auth-loginConfirmed', function(event, data) {
        console.log('login confirmed start ' + data);
        $rootScope.loadingAccount = false;
        var nextLocation = ($rootScope.requestedUrl ? $rootScope.requestedUrl : "/home");
        var delay = ($location.path() === "/loading" ? 1500 : 0);
        $timeout(function() {
            Session.create(data);
            $rootScope.account = Session;
            $rootScope.authenticated = true;
            $location.path(nextLocation).replace();
            $rootScope.allImportExportTask = $localStorage.importExportBoard.allImportExportTask;
        	$rootScope.showImportExportBoard = $localStorage.importExportBoard.showImportExportBoard;
        	$rootScope.isMinImportExportBoard = $localStorage.importExportBoard.isMinImportExportBoard;
        }, delay);

    });

    // Call when the 401 response is returned by the server
    $rootScope.$on('event:auth-loginRequired', function(event, data) {
        if ($rootScope.loadingAccount && data.status !== 401) {
            $rootScope.requestedUrl = $location.path()
            event.preventDefault();
            $state.go('loading');
        } else {
            Session.invalidate();
            $rootScope.authenticated = false;
            $rootScope.loadingAccount = false;
            event.preventDefault();
            $state.go('login');
        }
    });

    // Call when the 403 response is returned by the server
    $rootScope.$on('event:auth-forbidden', function(rejection) {
        $rootScope.$evalAsync(function() {
            event.preventDefault();
            $state.go('error.403');
        });
    });

    // Call when the user logs out
    $rootScope.$on('event:auth-loginCancelled', function() {
        event.preventDefault();
        $state.go('login');
    });

    // Get already authenticated user account
    AuthSharedService.getAccount();

});