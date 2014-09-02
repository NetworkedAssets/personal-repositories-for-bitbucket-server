define('PluginState', ['backbone', 'Config'], function(Backbone, Config) {
	return Backbone.Model.extend({
		urlRoot : Config.urlBase + '/state'
	});
});