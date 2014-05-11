define('UserRow', [ 'backbone' ], function(Backbone) {
	return Backbone.View.extend({

		tagName : 'tr',
		template : PrivateRepos.userRow,

		events : {
			'click .delete' : 'onDelete'
		},

		onDelete : function(e) {

		},

		render : function() {
			this.el.innerHTML = this.template({
				user : this.model.toJSON()
			});
			return this;
		}
	});
});