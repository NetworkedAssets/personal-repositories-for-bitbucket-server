define('Users', ['backbone', 'User', 'Config'], function(Backbone, User, Config) {
	return Backbone.Collection.extend({
		url : Config.urlBase + '/users/list',
		model: User
	});
});