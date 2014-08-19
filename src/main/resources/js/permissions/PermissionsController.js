define('PermissionsController', [ 'jquery', 'PermissionsLayout', 'UsersTable', 'Users',
		'GroupsTable', 'Groups' ], function($, PermissionsLayout, UsersTable, Users, GroupsTable,
		Groups) {

	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		initialize : function(opts) {
			this.region = $(opts.region);
		},

		start : function() {
			this.createlayout();
			this.startUsersTable();
			this.startGroupsTable();
		},
		
		createlayout : function() {
			this.layout = new PermissionsLayout();
			this.layout.on('mode-changed', this.onModeChange);
			this.region.html(this.layout.render().el);
		},
		
		onModeChange : function(mode) {
			console.log('mode changed to ', mode)
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