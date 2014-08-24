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

		template : org.networkedassets.personalRepos.repositories.table,
		ownerTemplate : org.networkedassets.personalRepos.repositories.owner,
		repositoryTemplate : org.networkedassets.personalRepos.repositories.repository,
		emptyTemplate : org.networkedassets.personalRepos.repositories.empty,
		
		events : {
			'click .expander' : 'toggleUserRepositories',
			'click .sort-column' : 'changeSort'
		},

		render : function() {
			if (this.renderedOnce) {
				this.renderOwners();
			} else if (this.collection.length === 0) {
				this.renderEmptyCollectionMessage();
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
		
		renderEmptyCollectionMessage : function() {
			this.el.innerHTML = this.emptyTemplate();
		},

		renderPagination : function() {
			if (this.paginationNeeded()) {
				this.$('.pagination-holder').pagination({
					hrefTextPrefix : window.location.href + '/page-',
					items : this.collection.state.totalRecords,
					itemsOnPage : this.collection.state.pageSize,
					onPageClick : _.bind(function(pageNumber) {
						this.trigger('page-selected', pageNumber);
					}, this)
				});	
			}
		},
		
		paginationNeeded : function() {
			return this.collection.length  > this.collection.state.pageSize;
		},
		
		toggleUserRepositories : function(e) {
			var $el = $(e.currentTarget);
			var userId = $el.data('user-id');
			if ($el.hasClass('loading')) {
				return;
			}
			if ($el.hasClass('expanded')) {
				this.hideUserRepositories(userId);
			} else {
				this.showRepositoriesLoader($el);
				this.trigger('user-expanded', userId);
			}
		},
		
		hideUserRepositories : function(userId) {
			this.$('.repo-owner-' + userId).remove();
			this.enableExpanding(this.findUserRow(userId).find('.expander'));
		},
		
		showUserRepositories : function(data) {
			var rendered = '';
			data.repositories.each(function(repo) {
				rendered += this.repositoryTemplate(this.prepareRepositoryViewData(data.userId, repo));
			}, this);
			
			var userRow = this.findUserRow(data.userId);
			userRow.after(rendered);
			this.hideRepositoriesLoader(userRow.find('.expander'));
		},
		
		prepareRepositoryViewData : function(userId, repo) {
			var repoJSON = repo.toJSON();
			repoJSON.size = Util.bytesToSize(repoJSON.size);
			return {
				repository : repoJSON,
				owner : {
					id : userId
				}
			};
		},
		
		findUserRow : function(userId) {
			return this.$('.repository-owner-id-' + userId);
		},
		
		enableExpanding : function(el) {
			el.removeClass('expanded');
			this.changeExpanderIcon(el, 'aui-iconfont-expanded', 'aui-iconfont-collapsed');
		},
		
		showRepositoriesLoader : function(el) {
			el.addClass('loading');
			this.changeExpanderIcon(el, 'aui-iconfont-collapsed', 'aui-icon-wait');
		},
		
		hideRepositoriesLoader : function(el) {
			el.removeClass('loading').addClass('expanded');
			this.changeExpanderIcon(el, 'aui-icon-wait', 'aui-iconfont-expanded');
		},
		
		changeExpanderIcon : function(parentEl, oldClass, newClass) {
			parentEl.find('.aui-icon').removeClass(oldClass).addClass(newClass);
		},
		
		changeSort : function(e) {
			$el = $(e.currentTarget);
			if ($el.hasClass('selected')) {
				$el.toggleClass('asc desc');
				this.trigger('sort-change-order');
			} else {
				var prev = $el.siblings('.selected').removeClass('selected');
				$el.addClass('selected');
				this.trigger('sort-change-field', $el.data('sort-by'));
			}
		}

	});
});