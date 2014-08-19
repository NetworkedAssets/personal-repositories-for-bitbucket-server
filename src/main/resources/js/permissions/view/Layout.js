define('PermissionsLayout', [ 'backbone', 'underscore', 'jquery' ], function(Backbone, _, $) {
	return Backbone.View.extend({
		template : org.networkedassets.personalRepos.permissions.layout,
		
		pressedButtonAttr : 'aria-pressed',
		
		events : {
			'click .repository-permissions-mode .aui-button': 'onModeButtonClick'
		},
		
		onModeButtonClick : function(e) {
			var $el = $(e.currentTarget);
			if (!this.isPressed($el)) {
				this.$('.aui-button[aria-pressed="true"]').attr('aria-pressed', 'false');
				$el.attr('aria-pressed', 'true');
			}
		},
		
		isPressed : function($el) {
			return ($el.attr('aria-pressed') == 'true');
		},
		
		render : function() {
			this.el.innerHTML = this.template();
			return this;
		}

	});
});