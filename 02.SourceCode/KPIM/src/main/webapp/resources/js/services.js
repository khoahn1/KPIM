'use strict';

myapp.service('Session', function() {
	this.create = function(data) {
        this.id = data.id;
        this.username = data.username;
        this.fullName = data.fullName;
        this.email = data.email;
        this.avatar = data.avatar;
        this.userRoles = [];
        angular.forEach(data.authorities, function (authority){
        	this.push(authority.operation.opsName);
        }, this.userRoles);
    };
    this.invalidate = function() {
        this.id = null;
        this.username = null;
        this.fullName = null;
        this.email = null;
        this.userRoles = null;
    };
    return this;
});

myapp.service('AuthSharedService', function($rootScope, $http, $resource,
    authService, Session) {
    return {
        login: function(userName, password, rememberMe) {
            var config = {
                ignoreAuthModule: 'ignoreAuthModule',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            };
            $http.post('authenticate', $.param({
                username: userName,
                password: password,
                rememberme: rememberMe
            }), config).then(function(response) {
                authService.loginConfirmed(response.data);
            }).catch(function(response) {
                $rootScope.authenticationError = true;
                Session.invalidate();
            });
        },
        getAccount: function() {
            $rootScope.loadingAccount = true;
            $http.get('security/account').then(function(response) {
                authService.loginConfirmed(response.data);
            });
        },
        isAuthorized: function(authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function(authorizedRole) {
                var authorized = (!!Session.username && Session.userRoles
                    .indexOf(authorizedRole) !== -1);
                if (authorized || authorizedRole == '*') {
                    isAuthorized = true;
                }
            });
            return isAuthorized;
        },
        logout: function() {
            $rootScope.authenticationError = false;
            $rootScope.authenticated = false;
            $rootScope.account = null;
            $http.get('logout');
            Session.invalidate();
            authService.loginCancelled();
        }
    };
});

myapp.service('HomeService', function($log, $resource) {
    return {
        getTechno: function() {
            var userResource = $resource('resources/json/techno.json', {}, {
                query: {
                    method: 'GET',
                    params: {},
                    isArray: true
                }
            });
            return userResource.query();
        }
    }
});

myapp.service('UsersService', function($log, $resource) {
    return {
        getAll: function() {
            var userResource = $resource('users', {}, {
                query: {
                    method: 'GET',
                    params: {},
                    isArray: true
                }
            });
            return userResource.query();
        }
    }
});

myapp.service('TokensService', function($log, $resource) {
    return {
        getAll: function() {
            var tokensResource = $resource('security/tokens', {}, {
                query: {
                    method: 'GET',
                    params: {},
                    isArray: true
                }
            });
            return tokensResource.query();
        }
    }
});
