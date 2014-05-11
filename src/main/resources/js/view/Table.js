define('Table', [ 'backbone', 'underscore' ], function(Backbone, _) {
	return Backbone.View.extend({

		tagName : 'table',
		className : 'private-repos-permissions-table',

		initialize : function(options) {
			_.bindAll(this, 'addChildView');

			this.collection = options.collection;
			this.collection.on('add', this.addChildView);
		},

		addChildView : function(model) {
			var itemViewClass = this.itemView;
			this.$('tbody').append(new itemViewClass({
				model : model
			}).render().el);
		}

	});
});