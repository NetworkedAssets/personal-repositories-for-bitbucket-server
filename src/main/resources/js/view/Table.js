define('Table', [ 'backbone', 'UserRowView' ], function(Backbone) {
	return Backbone.View.extend({

		initialize : function() {
		},

		addChildView : function(model) {
			this.el.append(new this.itemView({
				model : model
			}).render());
		},

		render : function() {
			return this;
		}

	});
});