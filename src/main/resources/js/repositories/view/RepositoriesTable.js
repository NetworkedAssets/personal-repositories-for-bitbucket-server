define('RepositoriesTable', [ 'backbone', 'underscore' ], function(Backbone, _) {
	return Backbone.View.extend({
		tagName : 'table',
		template : org.networkedassets.personalRepos.repositories.table,
		
		render : function() {
			this.el.innerHTML = this.template(); 
			return this;
		}
	});
});