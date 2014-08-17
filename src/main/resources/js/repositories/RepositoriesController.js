define('RepositoriesController', [ 'underscore', 'jquery', 'RepositoriesTable',
		'RepositoryOwners', 'UserRepositories', 'backbone' ], function(_, $,
		RepositoriesTable, RepositoryOwners, UserRepositories, Backbone) {
	var constr = function(opts) {
		this.initialize(opts);
	};

	_.extend(constr.prototype, {
		initialize : function(opts) {
			_.bindAll(this, 'onPageSelected', 'onUserExpanded');
		},

		start : function() {
			this.createModels();
			this.createView();
			this.showTable();
			this.fetchFirstPage();
		},

		createModels : function() {
			this.repositoryOwners = new RepositoryOwners();
			this.repositoriesEvents = {};
			_.extend(this.repositoriesEvents, Backbone.Events);
		},

		createView : function() {
			this.repositoriesTable = new RepositoriesTable({
				collection : this.repositoryOwners,
				repositoriesEvents : this.repositoriesEvents
			});

			this.repositoriesTable.on('page-selected', this.onPageSelected);
			this.repositoriesTable.on('user-expanded', this.onUserExpanded);
		},

		showTable : function() {
			$('.repositories-section').html(this.repositoriesTable.el);
		},

		fetchFirstPage : function() {
			this.repositoryOwners.getFirstPage();
		},

		onPageSelected : function(pageNumber) {
			this.repositoryOwners.getPage(pageNumber);
		},

		onUserExpanded : function(userId) {
			var repositories = new UserRepositories([], {
				userId : userId
			});
			repositories.fetch().done(_.bind(function() {
				this.notifyAboutFetchedRepositories(userId, repositories);
			}, this));
		},

		notifyAboutFetchedRepositories : function(userId, repos) {
			this.repositoriesEvents.trigger('userRepositoriesFetched', {
				repositories : repos,
				userId : userId
			});
		}

	});

	return constr;
});