define('RepositoryOwners', ['backbone', 'RepositoryOwner', 'Config'], function(Backbone, RepositoryOwner, Config) {
	return Backbone.PageableCollection.extend({
		url : Config.urlBase + '/repositories/owners',
		model: RepositoryOwner,
		
		parseRecords: function (resp, options) {
			return resp.items;
		}
	});
});