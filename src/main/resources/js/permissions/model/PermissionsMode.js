define('PermissionsMode', ['backbone', 'Config'], function(Backbone, Config) {
	return Backbone.Model.extend({
		url : Config.urlBase + '/permissions/mode'
	});
});