define('UsersTable', [ 'Table', 'UserRow', 'UserBatch', 'Config' ], function(Table,
		UserRow, UserBatch, Config) {
	return Table.extend({

		template : org.networkedassets.personalRepos.permissions.table,
		itemView : UserRow,
		searchFormatResult : function(object) {
			return PrivateRepos.userSearchResult({user: object}) 
		},
		
		searchFormatSelection : function(object) {
			return PrivateRepos.userSearchSelection({user: object})
		},

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