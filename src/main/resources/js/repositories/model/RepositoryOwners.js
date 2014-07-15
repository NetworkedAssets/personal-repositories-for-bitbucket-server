define('RepositoryOwners', ['backbone', 'RepositoryOwner', 'Config'], function(Backbone, RepositoryOwner, Config) {
	return Backbone.Collection.extend({
		url : Config.urlBase + '/repositories/owners/list',
		model: RepositoryOwner
	});
});