define('Groups', [ 'backbone', 'Group', 'Config' ], function(Backbone, Group, Config) {
	return Backbone.Collection.extend({
		url : Config.urlBase + '/groups/list',
		model : Group
	});
});