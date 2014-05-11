define('UsersTable', ['Table', 'UserRow'], function(Table, UserRow) {
	return Table.extend({
		template : PrivateRepos.table,
		itemView : UserRow,

		render : function() {
			this.$el.html(this.template({
				name : 'User'
			}));

			var searchInput = this.$('.search-input');

			searchInput.auiSelect2({
				hasAvatar : true,
				multiple : true,
				minimumInputLength : 1,
				ajax : {
					url : function(term) {
						return '/stash/rest/privaterepos/1.0/users/find/'
								+ term;
					},
					dataType : 'json',
					results : function(data, page) {
						results = _.map(data, function(object) {
							return {
								id : object.name,
								text : object.name
							}
						});
						return {
							results : results
						};
					},
					formatResult : function(object) {
						return object.name;
					}
				}
			});

			return this;
		}
	});
});