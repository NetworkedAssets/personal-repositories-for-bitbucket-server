define('PermissionsLayout', [ 'backbone', 'underscore' ], function(Backbone, _) {
	return Backbone.View.extend({
		template : org.networkedassets.personalRepos.permissions.layout,
		
		render : function() {
			this.el.innerHTML = this.template();
			return this;
		}

	});
});