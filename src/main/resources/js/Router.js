define('Router', [ 'backbone' ], function(Backbone) {
	return Backbone.Router.extend({
		routes : {
			"" : "repositories",
			"permissions" : "permissions",
			"repositories" : "repositories"
		}
	});
});