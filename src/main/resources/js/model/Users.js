define('Users', ['backbone', 'User'], function(Backbone, User) {
	return Backbone.Collection.extend({
		url : '/stash/rest/privaterepos/1.0/users',
		model: User
	});
});