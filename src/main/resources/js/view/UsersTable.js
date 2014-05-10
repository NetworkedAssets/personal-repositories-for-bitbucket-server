define('UsersTable', ['Table', 'UserRow'], function(Table, UserRow) {
	return Table.extend({
		itemView : UserRow
	});
});