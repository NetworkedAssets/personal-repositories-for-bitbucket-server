define('PermissionsController', [ 'jquery', 'PermissionsMode', 'PermissionsLayout', 'UsersTable', 'Users',
		'GroupsTable', 'Groups' ], function($, PermissionsMode, PermissionsLayout, UsersTable, Users, GroupsTable,
		Groups) {

	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		initialize : function(opts) {
			_.bindAll(this, 'createViews');
			this.region = $(opts.region);
		},

		start : function() {
			this.fetchPermissionsMode();
		},
		
		fetchPermissionsMode : function() {
			this.permissionsMode = new PermissionsMode();
			this.permissionsMode.fetch().done(this.createViews);
		},
		
		createViews : function() {
			this.createlayout();
			this.startUsersTable();
			this.startGroupsTable();
		},
		
		createlayout : function() {
			this.layout = new PermissionsLayout({
				permissionsMode: this.permissionsMode
			});
			this.layout.on('mode-changed', this.onModeChange);
			this.region.html(this.layout.render().el);
		},
		
		onModeChange : function(mode) {
			this.permissionsMode.set('mode', mode);
			this.permissionsMode.save();
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