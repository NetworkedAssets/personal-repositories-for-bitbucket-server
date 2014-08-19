define('PermissionsMode', [ 'backbone', 'Config' ], function(Backbone, Config) {
	return Backbone.Model.extend({
		url : Config.urlBase + '/permissions/mode',

		sync : function(method, model, options) {
			if (method === "create") {
				method = "update";
			}
			return Backbone.sync(method, model, options);
		},
	});
});