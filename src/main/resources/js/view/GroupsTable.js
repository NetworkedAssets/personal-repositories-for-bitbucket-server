define('GroupsTable', [ 'Table', 'GroupRow', 'underscore', 'GroupBatch', 'Config' ], function(Table,
		GroupRow, _, GroupBatch, Config) {
	return Table.extend({
		
		initialize : function() {
			Table.prototype.initialize.apply(this, arguments);
			_.bindAll(this, 'onAllowSuccess', 'handleAllow');
		},
		
		template : PrivateRepos.table,
		itemView : GroupRow,
		
		searchUrl : function(term) {
			return Config.urlBase + '/groups/find/' + term;
		}, 

		handleAllow : function(values) {
			var groupBatch = new GroupBatch({
				names : values
			});
			groupBatch.save({}, {
				success : this.onAllowSuccess
			});
		},
		
		onAllowSuccess : function() {
			this.collection.fetch();
			this.clearSearchInput();
		}
	});
});