define('GroupBatch', ['backbone', 'Config'], function(Backbone, Config) {
	return Backbone.Model.extend({
		defaults : {
			names : []
		},
		url : Config.urlBase + '/groups/list',
	});
});