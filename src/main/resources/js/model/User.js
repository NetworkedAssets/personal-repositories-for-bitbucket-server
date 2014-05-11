define('User', ['backbone'], function(Backbone) {
	return Backbone.Model.extend({
		urlRoot : '/stash/rest/privaterepos/1.0/users/user',
		idAttribute : 'name'
	});
});