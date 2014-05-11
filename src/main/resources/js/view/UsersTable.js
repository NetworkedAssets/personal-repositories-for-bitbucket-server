define('UsersTable', [ 'Table', 'UserRow' ], function(Table, UserRow) {
	return Table.extend({
		template : PrivateRepos.table,
		itemView : UserRow,
		searchUrl : function(term) {
			return '/stash/rest/privaterepos/1.0/users/find/' + term;
		},

		handleAllow : function(values) {
			console.log(values);
		}

	});
});