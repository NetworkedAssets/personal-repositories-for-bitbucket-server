define('UsersTable', ['Table', 'UserRow'], function(Table, UserRow) {
	return Table.extend({
		template : function() {
			return '';
		},
		itemView : UserRow
	});
});