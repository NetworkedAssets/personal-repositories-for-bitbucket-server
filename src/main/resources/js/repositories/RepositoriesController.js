define('RepositoriesController', [ 'jquery', 'RepositoriesTable', 'RepositoryOwners' ], function($,
		RepositoriesTable, RepositoryOwners) {
	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		initialize : function(opts) {
		},

		start : function() {
			this.showTable();
		},

		showTable : function() {
			this.repositoryOwners = new RepositoryOwners();
			var repositoriesTable = new RepositoriesTable({
				collection: this.repositoryOwners
			});
			$('.repositories-section').html(repositoriesTable.el);
			this.repositoryOwners.getFirstPage();
		}

	});

	return constr;
});