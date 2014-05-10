define('UsersTable', ['Table', 'GroupRow'], function(Table, GroupRow) {
	return Table.extend({
		itemView : GroupRow
	});
});