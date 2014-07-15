define('RepositoriesTable', [ 'backbone', 'underscore' ], function(Backbone, _) {
	return Backbone.View.extend({
		tagName : 'tr',
		template : org.networkedassets.personalRepos.repositories.owner,
		
		render : function() {
			this.el.innerHTML = this.template({
				owner : this.model.toJSON()
			}); 
			return this;
		}
	});
});