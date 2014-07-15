define('RepositoriesController', [ 'jquery', 'RepositoriesTable' ], function($,
		RepositoriesTable) {
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
			var repositoriesTable = new RepositoriesTable();
			$('.repositories-section').html(repositoriesTable.render().el);
		}

	});

	return constr;
});