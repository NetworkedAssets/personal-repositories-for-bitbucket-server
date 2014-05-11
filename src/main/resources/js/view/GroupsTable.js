define('GroupsTable', [ 'Table', 'GroupRow', 'underscore', 'GroupBatch' ], function(Table,
		GroupRow, _, GroupBatch) {
	return Table.extend({
		template : PrivateRepos.table,
		itemView : GroupRow,
		
		searchUrl : function(term) {
			return '/stash/rest/privaterepos/1.0/groups/find/' + term;
		}, 

		handleAllow : function(values) {
			var groupBatch = new GroupBatch({
				names : values
			});
			groupBatch.save();
		}
	});
});