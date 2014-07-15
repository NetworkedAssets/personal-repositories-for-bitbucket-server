define('User', ['backbone', 'Config'], function(Backbone, Config) {
	return Backbone.Model.extend({
		urlRoot : Config.urlBase + '/users/user',
		idAttribute : 'name'
	});
});