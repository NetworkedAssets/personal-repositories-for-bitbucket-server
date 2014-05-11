define('PrivateRepos', [ 'jquery', 'UsersTable', 'Users', 'GroupsTable', 'Groups' ], function($,
		UsersTable, Users, GroupsTable, Groups) {

	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		initialize : function(opts) {
		},
		
		start : function() {
			this.startUsersTable();
			this.startGroupsTable();
		},
		
		startUsersTable : function() {
			var users = new Users();
			var usersTable = new UsersTable({
				collection: users
			});
			$('#users-table').html(usersTable.render().el);
			users.fetch();
		},
		
		startGroupsTable : function() {
			var groups = new Groups();
			var groupsTable = new GroupsTable({
				collection: groups
			});
			$('#groups-table').html(groupsTable.render().el);
			groups.fetch();
		}
	});

	return constr;
});

AJS.$(document).ready(function($) {
	require([ "PrivateRepos" ], function(PrivateRepos) {
		console.log('Starting Private Repos');
		var privateRepos = new PrivateRepos();
		privateRepos.start();
	});
}(AJS.$));