define('GroupsTable', [ 'Table', 'GroupRow', 'underscore', 'GroupBatch', 'Config' ], function(Table,
		GroupRow, _, GroupBatch, Config) {
	return Table.extend({
		
		initialize : function() {
			Table.prototype.initialize.apply(this, arguments);
			_.bindAll(this, 'onAddSuccess', 'handleAdd');
		},
		
		searchFormatResult : function(object) {
			return org.networkedassets.personalRepos.permissions.groupSearchResult({group: object}) 
		},
		
		searchFormatSelection : function(object) {
			return org.networkedassets.personalRepos.permissions.groupSearchSelection({group: object})
		},
		
		template : org.networkedassets.personalRepos.permissions.table,
		itemView : GroupRow,
		
		searchUrl : function(term) {
			return Config.urlBase + '/groups/find/' + term;
		}, 
		
		parseSearchResult : function(object) {
			return _.extend(object, {
				id: object.name
			});
		},
		
		prepareTemplateParams : function() {
			return {
				mode : this.mode,
				header : {
					allow: 'Denied groups',
					deny: 'Allowed groups'
				}
			};
		},

		handleAdd: function(values) {
			var groupBatch = new GroupBatch({
				ids : values
			});
			groupBatch.save({}, {
				success : this.onAddSuccess
			});
		},
		
		onAddSuccess : function() {
			this.collection.fetch();
			this.clearSearchInput();
		}
	});
});