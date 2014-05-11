define('Groups', ['backbone', 'Group'], function(Backbone, Group) {
	return Backbone.Collection.extend({
		url : '/stash/rest/privaterepos/1.0/groups/list',
		model: Group
	});
});