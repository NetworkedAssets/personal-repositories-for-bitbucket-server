define('UserRepositories', [ 'backbone', 'Repository', 'Config' ], function(
		Backbone, Repository, Config) {
	return Backbone.Collection.extend({

		model : Repository,

		initialize : function(models, options) {
			this.userId = options.userId;
			this.sortKey = 'size';
			this.orderKey = 'asc';
		},

		url : function() {
			return Config.urlBase + '/repositories/user/' + this.userId
					+ '?sort_by=' + this.sortKey + '&order=' + this.orderKey;
		},

		sortBy : function(sortKey) {
			this.sortKey = sortKey;
		},

		orderBy : function(order) {
			this.orderKey = order;
		}
	});
});