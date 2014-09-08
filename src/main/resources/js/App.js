define('PersonalStash', [ 'underscore', 'jquery', 'Router',
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
					this.startController(key, this.controllers[key]);
				}, this));
			}, this);
		},
		
		startController : function(controllerKey, controller) {
			this.switchActiveTab(controllerKey);
			if (controller.instance === null) {
				controller.instance = new controller.constructor({
					region: controller.region
				});
			}
			controller.instance.start();
			this.closeCurrentController();
			$(controller.region).show();
			this.currentController = controller;
		},
		
		switchActiveTab : function(controllerKey) {
			var activeTabClass = 'aui-nav-selected';
			$('.sections-menu .section-tab').removeClass(activeTabClass);
			$('.sections-menu .section-tab.' + controllerKey).addClass(activeTabClass);
		},
		
		closeCurrentController : function() {
			if (this.currentController !== null) {
				$(this.currentController.region).hide();
				this.currentController.instance.close();
			}
		},

		start : function() {
			Backbone.history.start();
		}

	});

	return constr;
});

AJS.$(document).ready(function($) {
	
	$.ajaxSetup({ cache: false });

	var go = function() {
		require([ "PersonalStash" ], function(PersonalStash) {
			var personalStash = new PersonalStash();
			personalStash.start();
		});
	};
	setTimeout(go, 10);
}(AJS.$));