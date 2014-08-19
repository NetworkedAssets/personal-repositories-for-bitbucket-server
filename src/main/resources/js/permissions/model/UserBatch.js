define('UserBatch', ['backbone', 'Config'], function(Backbone, Config) {
	return Backbone.Model.extend({
		defaults : {
			ids : []
		},
		url : Config.urlBase + '/users/list',
	});
});