define('UsersTable', [ 'Table', 'UserRow', 'UserBatch', 'Config' ], function(Table,
		UserRow, UserBatch, Config) {
	return Table.extend({

		template : PrivateRepos.table,
		itemView : UserRow,

		initialize : function() {
			Table.prototype.initialize.apply(this, arguments);
			_.bindAll(this, 'onAllowSuccess', 'handleAllow');
		},

		searchUrl : function(term) {
			return  Config.urlBase + '/users/find/' + term;
		},

		handleAllow : function(values) {
			var userBatch = new UserBatch({
				names : values
			});
			userBatch.save({}, {
				success : this.onAllowSuccess
			});
		},

		onAllowSuccess : function() {
			this.collection.fetch();
			this.clearSearchInput();
		}

	});
});