define('PrivateRepos', [ 'underscore', 'jquery', 'Router',
		'PermissionsController', 'RepositoriesController' ], function(_, $,
		Router, PermissionsController, RepositoriesController) {

	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		
		controllers : {
			permissions : {
				instance : null,
				constructor : PermissionsController,
				region: '.permissions-section',
				route : 'route:permissions'
			},
			repositories : {
				instance : null,
				constructor : RepositoriesController,
				region: '.repositories-section',
				route : 'route:repositories'
			}
		},
		
		initialize : function(opts) {
			_.bindAll(this, 'startController');
			this.currentController = null;
			this.router = new Router();
			this.bindToRouterRoutes();
		},
		
		bindToRouterRoutes : function() {
			_.each(this.controllers, function(value, key) {
				this.router.on(value.route, _.bind(function() {
					this.startController(this.controllers[key]);
				}, this));
			}, this);
		},
		
		startController : function(controller) {
			if (controller.instance === null) {
				controller.instance = new controller.constructor();
			}
			controller.instance.start();
			this.closeCurrentController();
			$(controller.region).show();
			this.currentController = controller.instance;
		},
		
		closeCurrentController : function() {
			if (this.currentController !== null) {
				this.currentController.close();
			}
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