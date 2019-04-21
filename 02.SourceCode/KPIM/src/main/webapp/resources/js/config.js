myapp.config(function($stateProvider, modalStateProvider, $urlRouterProvider, USER_ROLES) {
	$urlRouterProvider.otherwise('/error/404');
	modalStateProvider.state('users.user-update', {
		url: '/user-update',
		templateUrl: 'partials/modules/user/user-update.html',
		controller: 'UserUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.userUpdate]
		}
	}).state('users.user-create', {
		url: '/user-create',
		templateUrl: 'partials/modules/user/user-create.html',
		controller: 'UserCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.userCreate]
		}
	}).state('users.user-import', {
		url: '/user-import',
		templateUrl: 'partials/modules/user/user-import.html',
		controller: 'UserImportController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.userImport]
		}
	}).state('roles.role-create', {
		url: '/role-create',
		templateUrl: 'partials/modules/role/role-create.html',
		controller: 'RoleCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('roles.role-update', {
		url: '/role-update',
		templateUrl: 'partials/modules/role/role-update.html',
		controller: 'RoleUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('branches.branch-create', {
		url: '/branch-create',
		templateUrl: 'partials/modules/branch/branch-create.html',
		controller: 'BranchCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('branches.branch-update', {
		url: '/branch-update',
		templateUrl: 'partials/modules/branch/branch-update.html',
		controller: 'BranchUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('partners.customers.customer-update', {
		url: '/customer-update',
		templateUrl: 'partials/modules/customer/customer-update.html',
		controller: 'CustomerUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('partners.customers.customer-create', {
		url: '/customer-create',
		templateUrl: 'partials/modules/customer/customer-create.html',
		controller: 'CustomerCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('partners.customers.customer-import', {
		url: '/customer-import',
		templateUrl: 'partials/modules/customer/customer-import.html',
		controller: 'CustomerImportController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('partners.suppliers.supplier-create', {
		url: '/supplier-create',
		templateUrl: 'partials/modules/supplier/supplier-create.html',
		controller: 'SupplierCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('partners.suppliers.supplier-update', {
		url: '/supplier-update',
		templateUrl: 'partials/modules/supplier/supplier-update.html',
		controller: 'SupplierUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('partners.suppliers.supplier-import', {
		url: '/supplier-import',
		templateUrl: 'partials/modules/supplier/supplier-import.html',
		controller: 'SupplierImportController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	});
	
	$stateProvider.state("home", {
		url: '/home',
		templateUrl: "partials/views/home.html",
		controller: 'HomeController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state("profile", {
		url: '/profile',
		templateUrl: "partials/modules/profile/profile.html",
		controller: 'profileCtrl',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state("profile.profile-about", {
		url: '/profile-about',
		templateUrl: "partials/modules/profile/profile-about.html",
		controller: 'profileCtrl',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state("profile.profile-timeline", {
		url: '/profile-timeline',
		templateUrl: "partials/modules/profile/profile-timeline.html",
		controller: 'profileCtrl',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state("profile.profile-photos", {
		url: '/profile-photos',
		templateUrl: "partials/modules/profile/profile-photos.html",
		controller: 'profileCtrl',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state("profile.profile-connections", {
		url: '/profile-connections',
		templateUrl: "partials/modules/profile/profile-connections.html",
		controller: 'profileCtrl',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('branches', {
		url: '/branches',
		templateUrl: 'partials/modules/branch/branch-read.html',
		controller: 'BranchReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('roles', {
		url: '/roles',
		templateUrl: 'partials/modules/role/role-read.html',
		controller: 'RoleReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	})
	
	// ------------------------------
	// WIDGETS
	// ------------------------------

	.state('partners', {
		url: '/partners',
		templateUrl: 'partials/views/common-2.html'
	})

	.state('partners.customers', {
		url: '/customers',
		templateUrl: 'partials/modules/customer/customer-read.html',
		controller: 'CustomerReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	})

	.state('partners.suppliers', {
		url: '/suppliers',
		templateUrl: 'partials/modules/supplier/supplier-read.html',
		controller: 'SupplierReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	})
	
	.state('users', {
		url: '/users',
		templateUrl: 'partials/modules/user/user-read.html',
		controller: 'UserReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('apiDoc', {
		url: '/apiDoc',
		templateUrl: 'partials/apiDoc.html',
		controller: 'ApiDocController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('tokens', {
		url: '/tokens',
		templateUrl: 'partials/tokens.html',
		controller: 'TokensController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('login', {
		url: '/login',
		templateUrl: 'partials/login.html',
		controller: 'LoginController',
		access: {
			loginRequired: false,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('loading', {
		url: '/loading',
		templateUrl: 'partials/loading.html',
		access: {
			loginRequired: false,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state("logout", {
		url: '/logout',
		template: " ",
		controller: "LogoutController",
		access: {
			loginRequired: false,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state("error", {
		url: '/error/:code',
		templateUrl: "partials/error.html",
		controller: "ErrorController",
		access: {
			loginRequired: false,
			authorizedRoles: [USER_ROLES.all]
		}
	})

	// ------------------------------
	// HEADERS
	// ------------------------------
	.state('headers', {
		url: '/headers',
		templateUrl: 'partials/views/common-2.html'
	})

	.state('headers.textual-menu', {
		url: '/textual-menu',
		templateUrl: 'partials/views/textual-menu.html'
	})

	.state('headers.image-logo', {
		url: '/image-logo',
		templateUrl: 'partials/views/image-logo.html'
	})

	.state('headers.mainmenu-on-top', {
		url: '/mainmenu-on-top',
		templateUrl: 'partials/views/mainmenu-on-top.html'
	})

	// ------------------------------
	// TYPOGRAPHY
	// ------------------------------

	.state('typography', {
		url: '/typography',
		templateUrl: 'partials/modules/user/user-create.html'
	})

	// ------------------------------
	// WIDGETS
	// ------------------------------

	.state('widgets', {
		url: '/widgets',
		templateUrl: 'partials/views/common.html'
	})

	.state('widgets.widgets', {
		url: '/widgets',
		templateUrl: 'partials/views/widgets.html'
	})

	.state('widgets.widget-templates', {
		url: '/widget-templates',
		templateUrl: 'partials/views/widget-templates.html'
	})

	// ------------------------------
	// TABLES
	// ------------------------------

	.state('tables', {
		url: '/tables',
		templateUrl: 'partials/views/common.html'
	})

	.state('tables.tables', {
		url: '/tables',
		templateUrl: 'partials/views/tables.html'
	})

	.state('tables.data-table', {
		url: '/data-table',
		templateUrl: 'partials/views/data-table.html'
	})

	// ------------------------------
	// FORMS
	// ------------------------------
	.state('form', {
		url: '/form',
		templateUrl: 'partials/views/common.html'
	})

	.state('form.basic-form-elements', {
		url: '/basic-form-elements',
		templateUrl: 'partials/views/form-elements.html'
	})

	.state('form.form-components', {
		url: '/form-components',
		templateUrl: 'partials/views/form-components.html'
	})

	.state('form.form-examples', {
		url: '/form-examples',
		templateUrl: 'partials/views/form-examples.html'
	})

	.state('form.form-validations', {
		url: '/form-validations',
		templateUrl: 'partials/views/form-validations.html'
	})

	// ------------------------------
	// USER INTERFACE
	// ------------------------------

	.state('user-interface', {
		url: '/user-interface',
		templateUrl: 'partials/views/common.html'
	})

	.state('user-interface.ui-bootstrap', {
		url: '/ui-bootstrap',
		templateUrl: 'partials/views/ui-bootstrap.html'
	})

	.state('user-interface.colors', {
		url: '/colors',
		templateUrl: 'partials/views/colors.html'
	})

	.state('user-interface.animations', {
		url: '/animations',
		templateUrl: 'partials/views/animations.html'
	})

	.state('user-interface.box-shadow', {
		url: '/box-shadow',
		templateUrl: 'partials/views/box-shadow.html'
	})

	.state('user-interface.buttons', {
		url: '/buttons',
		templateUrl: 'partials/views/buttons.html'
	})

	.state('user-interface.icons', {
		url: '/icons',
		templateUrl: 'partials/views/icons.html'
	})

	.state('user-interface.alerts', {
		url: '/alerts',
		templateUrl: 'partials/views/alerts.html'
	})

	.state('user-interface.preloaders', {
		url: '/preloaders',
		templateUrl: 'partials/views/preloaders.html'
	})

	.state('user-interface.notifications-dialogs', {
		url: '/notifications-dialogs',
		templateUrl: 'partials/views/notification-dialog.html'
	})

	.state('user-interface.media', {
		url: '/media',
		templateUrl: 'partials/views/media.html'
	})

	.state('user-interface.other-components', {
		url: '/other-components',
		templateUrl: 'partials/views/other-components.html'
	})

	// ------------------------------
	// CHARTS
	// ------------------------------

	.state('charts', {
		url: '/charts',
		templateUrl: 'partials/views/common.html'
	})

	.state('charts.flot-charts', {
		url: '/flot-charts',
		templateUrl: 'partials/views/flot-charts.html'
	})

	.state('charts.other-charts', {
		url: '/other-charts',
		templateUrl: 'partials/views/other-charts.html'
	})

	// ------------------------------
	// CALENDAR
	// ------------------------------

	.state('calendar', {
		url: '/calendar',
		templateUrl: 'partials/views/calendar.html'
	})

	// ------------------------------
	// PHOTO GALLERY
	// ------------------------------

	.state('photo-gallery', {
		url: '/photo-gallery',
		templateUrl: 'partials/views/common.html'
	})

	// Default

	.state('photo-gallery.photos', {
		url: '/photos',
		templateUrl: 'partials/views/photos.html'
	})

	// Timeline

	.state('photo-gallery.timeline', {
		url: '/timeline',
		templateUrl: 'partials/views/photo-timeline.html'
	})

	// ------------------------------
	// GENERIC CLASSES
	// ------------------------------

	.state('generic-classes', {
		url: '/generic-classes',
		templateUrl: 'partials/views/generic-classes.html'
	})

	// ------------------------------
	// PAGES
	// ------------------------------

	.state('pages', {
		url: '/pages',
		templateUrl: 'partials/views/common.html'
	})

	// -------------------------------

	.state('pages.listview', {
		url: '/listview',
		templateUrl: 'partials/views/list-view.html'
	})

	.state('pages.messages', {
		url: '/messages',
		templateUrl: 'partials/views/messages.html'
	})

	.state('pages.pricing-table', {
		url: '/pricing-table',
		templateUrl: 'partials/views/pricing-table.html'
	})

	.state('pages.contacts', {
		url: '/contacts',
		templateUrl: 'partials/views/contacts.html'
	})

	.state('pages.invoice', {
		url: '/invoice',
		templateUrl: 'partials/views/invoice.html'
	})

	.state('pages.wall', {
		url: '/wall',
		templateUrl: 'partials/views/wall.html'
	})

	// ------------------------------
	// BREADCRUMB DEMO
	// ------------------------------
	.state('breadcrumb-demo', {
		url: '/breadcrumb-demo',
		templateUrl: 'partials/views/breadcrumb-demo.html'
	})
});