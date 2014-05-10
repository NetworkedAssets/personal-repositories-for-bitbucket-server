define('private-repositories', [ 'jquery', 'UsersTable' ], function($,
		UsersTable) {

	var constr = function() {
		this.initialize();
	};

	_.extend(constr.prototype, {
		initialize : function() {
			var table = new UsersTable();
			table.render();
			$('body').prepend(table.el);
		}
	});

	return constr;
});

AJS.$(document).ready(function($) {
	require([ "private-repositories" ], function(PrivateRepos) {
		var privateRepos = new PrivateRepos();
	});
}(AJS.$));