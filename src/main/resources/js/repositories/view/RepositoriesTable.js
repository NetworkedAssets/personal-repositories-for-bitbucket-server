define('RepositoriesTable', [ 'backbone', 'underscore' ], function(Backbone, _) {
	return Backbone.View.extend({
		
		initialize : function(opts) {
			this.collection = opts.collection;
			this.bindEvents();
		},
		
		bindEvents : function() {
			this.collection.on('sync', this.renderOwners, this);
		},
		
		tagName : 'table',
		template : org.networkedassets.personalRepos.repositories.table,
		ownerTemplate : org.networkedassets.personalRepos.repositories.owner,
		repositoryTemplate : '',
		
		render : function() {
			this.el.innerHTML = this.template(); 
			return this;
		},
		
		renderOwners : function() {
			var rendered = "";
			this.collection.each(function(model) {
				rendered += this.ownerTemplate({owner : model.toJSON()});
			}, this);
			this.$('tbody').html(rendered);
		}
		
	});
});