define('Table', [ 'backbone', 'underscore' ], function(Backbone, _) {
	return Backbone.View.extend({

		tagName : 'div',
		
		searchFormatResult :function(object) {
			return object.name;
		},
		
		searchFormatSelection  : function(object) {
			return object.name;
		},

		events : {
			'click .add-button' : 'onAdd'
		},

		initialize : function(options) {
			_.bindAll(this, 'addChildView');

			this.mode = options.mode;
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
			var me = this;
			this.$el.html(this.template(this.prepareTemplateParams()));

			var searchInput = this.$('.search-input');
			searchInput.auiSelect2({
				hasAvatar : true,
				multiple : true,
				minimumInputLength : 1,
				ajax : {
					url : this.searchUrl,
					dataType : 'json',
					results : function(data, page) {
						var results = _.map(data, function(object) {
							return me.parseSearchResult(object);
						});
						return {
							results : results
						};
					}
				},
				formatResult : this.searchFormatResult,
				formatSelection : this.searchFormatSelection
			});
			return this;
		},
		
		prepareTemplateParams : function() {
			return {
				mode : this.mode
			};
		},
		
		parseSearchResult : function(object) {
			return object;
		},

		onAdd : function(e) {
			e.preventDefault();
			var val = this.$('.search-input').select2('val');
			this.handleAdd(val);
		},
		
		clearSearchInput : function() {
			this.$('.search-input').select2('val', null);
		},
		
		switchMode : function() {
			this.render();
		}

	});
});