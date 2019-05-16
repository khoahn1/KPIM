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
	}).state('phases.phase-create', {
		url: '/phase-create',
		templateUrl: 'partials/modules/phase/phase-create.html',
		controller: 'PhaseCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('phases.phase-update', {
		url: '/phase-update',
		templateUrl: 'partials/modules/phase/phase-update.html',
		controller: 'PhaseUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('type-of-works.type-of-work-create', {
		url: '/type-of-work-create',
		templateUrl: 'partials/modules/type-of-work/type-of-work-create.html',
		controller: 'TypeOfWorkCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('type-of-works.type-of-work-update', {
		url: '/type-of-work-update',
		templateUrl: 'partials/modules/type-of-work/type-of-work-update.html',
		controller: 'TypeOfWorkUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('units.unit-create', {
		url: '/unit-create',
		templateUrl: 'partials/modules/unit/unit-create.html',
		controller: 'UnitCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('units.unit-update', {
		url: '/unit-update',
		templateUrl: 'partials/modules/unit/unit-update.html',
		controller: 'UnitUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('projects.project-read.project-create', {
		url: '/project-create',
		templateUrl: 'partials/modules/project/project-create.html',
		controller: 'ProjectCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('projects.project-read.project-update', {
		url: '/project-update',
		templateUrl: 'partials/modules/project/project-update.html',
		controller: 'ProjectUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('projects.project-read.project-import', {
		url: '/project-import',
		templateUrl: 'partials/modules/project/project-import.html',
		controller: 'ProjectImportController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('companies.company-create', {
		url: '/company-create',
		templateUrl: 'partials/modules/company/company-create.html',
		controller: 'CompanyCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('companies.company-update', {
		url: '/company-update',
		templateUrl: 'partials/modules/company/company-update.html',
		controller: 'CompanyUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('products.product-create', {
		url: '/product-create',
		templateUrl: 'partials/modules/product/product-create.html',
		controller: 'ProductCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('products.product-update', {
		url: '/product-update',
		templateUrl: 'partials/modules/product/product-update.html',
		controller: 'ProductUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('parent-departments.parent-department-create', {
		url: '/parent-department-create',
		templateUrl: 'partials/modules/parent-department/parent-department-create.html',
		controller: 'ParentDepartmentCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('parent-departments.parent-department-update', {
		url: '/parent-department-update',
		templateUrl: 'partials/modules/parent-department/parent-department-update.html',
		controller: 'ParentDepartmentUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('departments.department-create', {
		url: '/department-create',
		templateUrl: 'partials/modules/department/department-create.html',
		controller: 'DepartmentCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('departments.department-update', {
		url: '/department-update',
		templateUrl: 'partials/modules/department/department-update.html',
		controller: 'DepartmentUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('tasks.task-update', {
		url: '/task-update',
		templateUrl: 'partials/modules/task/task-update.html',
		controller: 'TaskUpdateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('tasks.task-create', {
		url: '/task-create',
		templateUrl: 'partials/modules/task/task-create.html',
		controller: 'TaskCreateController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('tasks.task-import', {
		url: '/task-import',
		templateUrl: 'partials/modules/task/task-import.html',
		controller: 'TaskImportController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('worklogs.import', {
		url: '/worklogs-import',
		templateUrl: 'partials/modules/worklog/worklog-import.html',
		controller: 'WorkLogImportController',
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
	}).state('phases', {
		url: '/phases',
		templateUrl: 'partials/modules/phase/phase-read.html',
		controller: 'PhaseReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('companies', {
		url: '/companies',
		templateUrl: 'partials/modules/company/company-read.html',
		controller: 'CompanyReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('parent-departments', {
		url: '/parent-departments',
		templateUrl: 'partials/modules/parent-department/parent-department-read.html',
		controller: 'ParentDepartmentReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('departments', {
		url: '/departments',
		templateUrl: 'partials/modules/department/department-read.html',
		controller: 'DepartmentReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('type-of-works', {
		url: '/type-of-works',
		templateUrl: 'partials/modules/type-of-work/type-of-work-read.html',
		controller: 'TypeOfWorkReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	}).state('units', {
		url: '/units',
		templateUrl: 'partials/modules/unit/unit-read.html',
		controller: 'UnitReadController',
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
	.state('projects', {
		url: '/projects',
		templateUrl: 'partials/views/common-2.html',
	    redirectTo: 'projects.project-read',
	})
	.state('projects.project-read', {
		url: '',
		templateUrl: 'partials/modules/project/project-read.html',
		controller: 'ProjectReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	})
	.state('projects.project-detail', {
		url: '/project-detail',
		templateUrl: 'partials/modules/project/project-detail.html',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	})
	.state('projects.project-detail.info', {
		url: '/info',
		templateUrl: 'partials/modules/project/project-info.html',
		controller: 'ProjectInfoController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	})
	.state('projects.project-detail.member', {
		url: '/members',
		templateUrl: 'partials/modules/project/project-member.html',
		controller: 'ProjectMemberController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	})
	.state('products', {
		url: '/products',
		templateUrl: 'partials/modules/product/product-read.html',
		controller: 'ProductReadController',
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
	}).state('tasks', {
		url: '/tasks',
		templateUrl: 'partials/modules/task/task-read.html',
		controller: 'TaskReadController',
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

	// ------------------------------
	// WORK LOG
	// ------------------------------
	.state('worklogs', {
		url: '/worklogs',
		templateUrl: 'partials/modules/worklog/worklog-read.html',
		controller: 'WorkLogReadController',
		access: {
			loginRequired: true,
			authorizedRoles: [USER_ROLES.all]
		}
	})
});