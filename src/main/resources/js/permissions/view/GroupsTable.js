define('GroupsTable', [ 'Table', 'GroupRow', 'underscore', 'GroupBatch', 'Config' ], function(Table,
		GroupRow, _, GroupBatch, Config) {
	return Table.extend({
		
		initialize : function() {
			Table.prototype.initialize.apply(this, arguments);
			_.bindAll(this, 'onAddSuccess', 'handleAdd');
		},
		
		searchFormatResult : function(object) {
			return org.networkedassets.personalstash.permissions.groupSearchResult({group: object}) 
		},
		
		searchFormatSelection : function(object) {
			return org.networkedassets.personalstash.permissions.groupSearchSelection({group: object})
		},
		
		template : org.networkedassets.personalstash.permissions.table,
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
					allow: AJS.I18n.getText('org.networkedassets.atlassian.stash.personalstash.permissions.groups.header.denied'),
					deny: AJS.I18n.getText('org.networkedassets.atlassian.stash.personalstash.permissions.groups.header.allowed')
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