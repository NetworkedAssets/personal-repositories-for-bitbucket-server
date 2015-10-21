define('RepositoriesLoader', [ 'backbone', 'underscore', 'jquery' ], function(
		Backbone, _, $) {
	return Backbone.View.extend({
		template : org.networkedassets.personalrepos.repositories.loader,
		className : 'loading-screen',

		render : function() {
			this.$el.html(this.template());
			return this;
		}
	});
});