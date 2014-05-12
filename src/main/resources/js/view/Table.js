define('Table', [ 'backbone', 'underscore' ], function(Backbone, _) {
	return Backbone.View.extend({

		tagName : 'table',
		className : 'private-repos-permissions-table aui',

		events : {
			'click .allow-button' : 'onAllow'
		},

		initialize : function(options) {
			_.bindAll(this, 'addChildView');

			this.collection = options.collection;
			this.collection.on('add', this.addChildView);
			this.collection.on('reset', this.addChildViews);
		},
		
		addChildViews : function() {
			this.collection.each(function(model) {
				this.addChildView(model);
			}, this);
		},

		addChildView : function(model) {
			var itemViewClass = this.itemView;
			this.$('tbody').append(new itemViewClass({
				model : model
			}).render().el);
		},

		render : function() {
			this.$el.html(this.template());

			var searchInput = this.$('.search-input');
			searchInput.auiSelect2({
				hasAvatar : true,
				multiple : true,
				minimumInputLength : 1,
				ajax : {
					url : this.searchUrl,
					dataType : 'json',
					results : function(data, page) {
						results = _.map(data, function(object) {
							return {
								id : object.name,
								text : object.name
							};
						});
						return {
							results : results
						};
					},
					formatResult : function(object) {
						return object.name;
					}
				}
			});
			return this;
		},

		onAllow : function(e) {
			e.preventDefault();
			var val = this.$('.search-input').select2('val');
			this.handleAllow(val);
		},
		
		clearSearchInput : function() {
			this.$('.search-input').select2('val', null);
		}

	});
});