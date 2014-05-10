define('Groups', ['Backbone', 'Group'], function(Backbone, Group) {
	return Backbone.Collection.extend({
		model: Group
	});
});