define('RepositoriesController', [ 'jquery', 'RepositoriesTable', 'RepositoryOwners' ], function($,
		RepositoriesTable, RepositoryOwners) {
	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		initialize : function(opts) {
			_.bindAll(this, 'onPageSelected');
		},

		start : function() {
			this.showTable();
		},

		showTable : function() {
			this.repositoryOwners = new RepositoryOwners();
			var repositoriesTable = new RepositoriesTable({
				collection: this.repositoryOwners
			});
			repositoriesTable.on('page-selected', this.onPageSelected);
			$('.repositories-section').html(repositoriesTable.el);
			this.repositoryOwners.getFirstPage();
		},
		
		onPageSelected : function(pageNumber) {
			this.repositoryOwners.getPage(pageNumber);
		}

	});

	return constr;
});