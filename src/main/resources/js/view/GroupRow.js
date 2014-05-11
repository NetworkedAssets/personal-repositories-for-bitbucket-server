define('GroupRow', [ 'backbone' ], function(Backbone) {
	return Backbone.View.extend({

		events : {
			'click .delete' : 'onDelete'
		},

		onDelete : function(e) {

		},

		render : function() {
			return this;
		}
	});
});