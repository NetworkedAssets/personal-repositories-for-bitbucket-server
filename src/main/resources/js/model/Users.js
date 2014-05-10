define('Users', ['Backbone', 'User'], function(Backbone, User) {
	return Backbone.Collection.extend({
		model: User
	});
});