define('Router', [ 'backbone' ], function(Backbone) {
	return Backbone.Router.extend({
		routes : {
			"" : "home",
			"permissions" : "permissions",
			"repositories" : "repositories"
		},
		
		home : function() {
			window.location = window.location + '#permissions';
		}
	});
});