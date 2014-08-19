define('PermissionsController', [ 'underscore', 'jquery', 'PermissionsMode', 'PermissionsLayout', 'UsersTable', 'Users',
		'GroupsTable', 'Groups' ], function(_, $, PermissionsMode, PermissionsLayout, UsersTable, Users, GroupsTable,
		Groups) {

	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		initialize : function(opts) {
			_.bindAll(this, 'createViews', 'onModeChange', 'switchMode');
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
			this.permissionsMode.save().done(this.switchMode);
		},
		
		switchMode : function() {
			this.usersTable.remove();
			this.groupsTable.remove();
			this.startUsersTable();
			this.startGroupsTable();
		},

		startUsersTable : function() {
			this.users = new Users();
			this.usersTable = new UsersTable({
				collection : this.users,
				mode : this.permissionsMode.get('mode')
			});
			$('#users-table').html(this.usersTable.render().el);
			this.users.fetch();
		},

		startGroupsTable : function() {
			this.groups = new Groups();
			this.groupsTable = new GroupsTable({
				collection : this.groups,
				mode : this.permissionsMode.get('mode')
			});
			$('#groups-table').html(this.groupsTable.render().el);
			this.groups.fetch();
		},
		
		close : function() {
			
		}

	});

	return constr;
});