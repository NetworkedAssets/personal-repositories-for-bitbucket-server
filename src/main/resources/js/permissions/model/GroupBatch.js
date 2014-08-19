define('GroupBatch', [ 'backbone', 'Config' ], function(Backbone, Config) {
	return Backbone.Model.extend({
		defaults : {
			ids : []
		},
		url : Config.urlBase + '/groups/list',
	});
});