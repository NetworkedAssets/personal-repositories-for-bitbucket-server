define('UserRepositories', [ 'backbone', 'Repository', 'Config' ], function(
		Backbone, Repository, Config) {
	return Backbone.Collection.extend({
		
		initialize : function(models, options) {
			this.userId = options.userId;
		},
		
		url : function() {
			return Config.urlBase + '/repositories/user/' + this.userId;
		},
		model : Repository
	});
});