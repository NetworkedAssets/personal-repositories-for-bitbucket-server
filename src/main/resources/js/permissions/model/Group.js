define('Group', ['backbone', 'Config'], function(Backbone, Config) {
	return Backbone.Model.extend({
		urlRoot : Config.urlBase + '/groups/group',
		idAttribute : 'name'
	});
});