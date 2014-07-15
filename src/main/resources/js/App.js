define('PrivateRepos', [ 'underscore', 'jquery', 'Router',
		'PermissionsController', 'RepositoriesController' ], function(_, $,
		Router, PermissionsController, RepositoriesController) {

	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		initialize : function(opts) {
			_.bindAll(this, 'startPermissions', 'startRepositories');

			this.repositoriesController = null;
			this.permissionsController = null;

			this.router = new Router();
			this.bindToRouterRoutes();
		},

		bindToRouterRoutes : function() {
			this.router.on('route:permissions', this.startPermissions);
			this.router.on('route:repositories', this.startRepositories);
		},

		startPermissions : function() {
			if (this.permissionsController === null) {
				this.permissionsController = new PermissionsController();
				this.permissionsController.start();
			}
			$('.repositories-section').hide();
			$('.permissions-section').show();
		},

		startRepositories : function() {
			if (this.repositoriesController === null) {
				this.repositoriesController = new RepositoriesController();
				this.repositoriesController.start();
			}
			$('.permissions-section').hide();
			$('.repositories-section').show();
		},

		start : function() {
			Backbone.history.start();
		}

	});

	return constr;
});

AJS.$(document).ready(function($) {

	var go = function() {
		require([ "PrivateRepos" ], function(PrivateRepos) {
			var privateRepos = new PrivateRepos();
			privateRepos.start();
		});
	};
	setTimeout(go, 10);
}(AJS.$));