define('GroupRow', [ 'backbone' ], function(Backbone) {
	return Backbone.View.extend({

		tagName : 'tr',
		template : PrivateRepos.groupRow,

		events : {
			'click .delete-button' : 'onDelete'
		},

		onDelete : function(e) {
			this.model.destroy();
			this.remove();
		},

		render : function() {
			this.el.innerHTML = this.template({
				group : this.model.toJSON()
			});
			return this;
		}
	});
});