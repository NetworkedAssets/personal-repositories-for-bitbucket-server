define('RepositoriesTable', [ 'backbone', 'underscore', 'Util' ], function(
		Backbone, _, Util) {
	return Backbone.View.extend({

		initialize : function(opts) {
			this.collection = opts.collection;
			this.bindEvents();
			this.renderedOnce = false;
		},

		bindEvents : function() {
			this.collection.on('sync', this.render, this);
		},

		tagName : 'table',
		className : 'aui',
		template : org.networkedassets.personalRepos.repositories.table,
		ownerTemplate : org.networkedassets.personalRepos.repositories.owner,
		repositoryTemplate : '',

		render : function() {
			if (this.renderedOnce) {
				this.renderOwners();
			} else {
				this.el.innerHTML = this.template();
				this.renderPagination();
				this.renderOwners();
				this.renderedOnce = true;
			}
			return this;
		},

		renderOwners : function() {
			var rendered = "";
			this.collection.each(function(model) {
				var modelData = model.toJSON();
				modelData.repositoriesSize = Util
						.bytesToSize(modelData.repositoriesSize);
				rendered += this.ownerTemplate({
					owner : modelData
				});
			}, this);
			this.$('tbody').html(rendered);
		},

		renderPagination : function() {
			this.$('.pagination-holder').pagination({
				hrefTextPrefix : window.location.href + '/page-',
				items : this.collection.state.totalRecords,
				itemsOnPage : this.collection.state.pageSize,
				onPageClick : _.bind(function(pageNumber) {
					this.trigger('page-selected', pageNumber)
				}, this)
			});
		}

	});
});