define('Group', ['backbone'], function(Backbone) {
	return Backbone.Model.extend({
		urlRoot : '/stash/rest/privaterepos/1.0/groups/group',
		idAttribute : 'name'
	});
});