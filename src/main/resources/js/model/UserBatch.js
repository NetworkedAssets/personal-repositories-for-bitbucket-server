define('UserBatch', ['backbone', 'Config'], function(Backbone, Config) {
	return Backbone.Model.extend({
		defaults : {
			names : []
		},
		url : Config.urlBase + '/users/list',
	});
});