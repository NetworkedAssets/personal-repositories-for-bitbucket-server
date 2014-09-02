define('RepositoryOwners', ['backbone', 'RepositoryOwner', 'Config'], function(Backbone, RepositoryOwner, Config) {
	return Backbone.PageableCollection.extend({
		url : Config.urlBase + '/repositories/owners',
		model: RepositoryOwner,
		mode: "server",
		
		state : {
			pageSize : 20
		},
		
		parseRecords: function (resp, options) {
			return resp.items;
		},
		
		parseState : function(resp, options) {
			return {
				totalRecords : resp.totalItems
			};
		},
	});
});