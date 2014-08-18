define('PermissionsController', [ 'jquery', 'UsersTable', 'Users',
		'GroupsTable', 'Groups' ], function($, UsersTable, Users, GroupsTable,
		Groups) {

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
				collection : users
			});
			$('#users-table').html(usersTable.render().el);
			users.fetch();
		},

		startGroupsTable : function() {
			var groups = new Groups();
			var groupsTable = new GroupsTable({
				collection : groups
			});
			$('#groups-table').html(groupsTable.render().el);
			groups.fetch();

		},
		
		close : function() {
			
		}

	});

	return constr;
});