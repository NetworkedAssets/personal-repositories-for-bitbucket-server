define('RepositoriesController', [ 'jquery', 'RepositoriesTable', 'RepositoryOwners' ], function($,
		RepositoriesTable, RepositoryOwners) {
	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		initialize : function(opts) {
		},

		start : function() {
			this.fetchOwners();
			this.showTable();
		},
		
		fetchOwners : function() {
			this.repositoryOwners = new RepositoryOwners();
			this.repositoryOwners.fetch();
		},

		showTable : function() {
			var repositoriesTable = new RepositoriesTable({
				collection: this.repositoryOwners
			});
			$('.repositories-section').html(repositoriesTable.render().el);
		}

	});

	return constr;
});