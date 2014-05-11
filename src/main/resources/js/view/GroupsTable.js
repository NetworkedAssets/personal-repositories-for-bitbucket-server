define('GroupsTable', [ 'Table', 'GroupRow' ], function(Table, GroupRow) {
	return Table.extend({
		itemView : GroupRow
	});
});