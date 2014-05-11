define('GroupsTable', [ 'Table', 'GroupRow' ], function(Table, GroupRow) {
	return Table.extend({
		template : PrivateRepos.groupsTable,
		itemView : GroupRow,

		render : function() {
			Table.prototype.render.call(this);

			this.$('.search-input').auiSelect2(
			// {
			// minimumInputLength: 1,
			// ajax: {
			// url: url,
			// dataType: 'jsonp',
			// data: function(term, page) {
			//
			// return {
			// q: term,
			// page_limit: 10,
			// apikey: "z4vbb4bjmgsb7dy33kvux3ea" //my own apikey
			// };
			// },
			// results: function(data, page) {
			// return {
			// results: data.movies
			// };
			// }
			// }
			// }
			);

			return this;
		}
	});
});