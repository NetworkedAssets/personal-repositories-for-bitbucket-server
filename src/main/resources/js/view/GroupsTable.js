define('GroupsTable', [ 'Table', 'GroupRow' ], function(Table, GroupRow) {
	return Table.extend({
		template : PrivateRepos.groupsTable,
		itemView : GroupRow
	});
});