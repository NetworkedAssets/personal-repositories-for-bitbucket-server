define('UserBatch', ['backbone'], function(Backbone) {
	return Backbone.Model.extend({
		defaults : {
			names : []
		},
		url : '/stash/rest/privaterepos/1.0/users/list',
	});
});