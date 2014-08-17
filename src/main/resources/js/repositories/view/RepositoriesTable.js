define('RepositoriesTable', [ 'backbone', 'underscore', 'Util', 'jquery' ], function(
		Backbone, _, Util, $) {
	return Backbone.View.extend({

		initialize : function(opts) {
			this.collection = opts.collection;
			this.repositoriesEvents = opts.repositoriesEvents;
			this.bindEvents();
			this.renderedOnce = false;
		},

		bindEvents : function() {
			this.collection.on('sync', this.render, this);
			this.repositoriesEvents.on('userRepositoriesFetched', this.showUserRepositories, this);
		},

		tagName : 'table',
		className : 'aui',
		template : org.networkedassets.personalRepos.repositories.table,
		ownerTemplate : org.networkedassets.personalRepos.repositories.owner,
		repositoryTemplate : org.networkedassets.personalRepos.repositories.repository,
		
		events : {
			'click .expander' : 'toggleUserRepositories'
		},

		render : function() {
			if (this.renderedOnce) {
				this.renderOwners();
			} else {
				this.el.innerHTML = this.template();
				this.renderPagination();
				this.renderOwners();
				this.renderedOnce = true;
			}
			return this;
		},

		renderOwners : function() {
			var rendered = "";
			this.collection.each(function(model) {
				var modelData = model.toJSON();
				modelData.repositoriesSize = Util
						.bytesToSize(modelData.repositoriesSize);
				rendered += this.ownerTemplate({
					owner : modelData
				});
			}, this);
			this.$('tbody').html(rendered);
		},

		renderPagination : function() {
			this.$('.pagination-holder').pagination({
				hrefTextPrefix : window.location.href + '/page-',
				items : this.collection.state.totalRecords,
				itemsOnPage : this.collection.state.pageSize,
				onPageClick : _.bind(function(pageNumber) {
					this.trigger('page-selected', pageNumber);
				}, this)
			});
		},
		
		toggleUserRepositories : function(e) {
			var $el = $(e.currentTarget);
			var userId = $el.data('user-id');
			if ($el.hasClass('expanded')) {
				this.hideUserRepositories(userId);
			} else {
				this.showRepositoriesLoader($el);
				this.trigger('user-expanded', userId);
			}
		},
		
		hideUserRepositories : function(userId) {
			this.$('.repo-owner-' + userId).remove();
		},
		
		showUserRepositories : function(data) {
			var rendered = '';
			data.repositories.each(function(repo) {
				rendered += this.repositoryTemplate({
					repository : repo.toJSON(),
					owner : {
						id : data.userId
					}
				});
			}, this);
			
			this.$('.repository-owner-id-' + data.userId).after(rendered);
//			this.showRepositoriesLoader();
		},
		
		showRepositoriesLoader : function() {
			
		}

	});
});