define('RepositoriesTable', [ 'backbone', 'underscore', 'Util' ], function(Backbone, _, Util) {
	return Backbone.View.extend({
		
		initialize : function(opts) {
			this.collection = opts.collection;
			this.bindEvents();
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
			this.el.innerHTML = this.template();
			this.renderOwners();
			this.renderPagination();
			return this;
		},
		
		renderOwners : function() {
			var rendered = "";
			this.collection.each(function(model) {
				var modelData = model.toJSON();
				modelData.repositoriesSize = Util.bytesToSize(modelData.repositoriesSize);
				rendered += this.ownerTemplate({owner : modelData});
			}, this);
			this.$('tbody').html(rendered);
		},
		
		renderPagination : function() {
			this.$('.pagination-holder').pagination({
				items: this.collection.state.totalRecords,
		        itemsOnPage: this.collection.state.pageSize
			});
		}
		
	});
});