define('PermissionsLayout', [ 'backbone', 'underscore', 'jquery' ], function(Backbone, _, $) {
	return Backbone.View.extend({
		template : org.networkedassets.personalstash.permissions.layout,
		pressedButtonAttr : 'aria-pressed',
		
		events : {
			'click .repository-permissions-mode .aui-button': 'onModeButtonClick'
		},
		
		initialize : function(opts) {
			this.permissionsMode = opts.permissionsMode;
		},
		
		onModeButtonClick : function(e) {
			var $el = $(e.currentTarget);
			if (!this.isPressed($el)) {
				this.$('.aui-button[aria-pressed="true"]').attr('aria-pressed', 'false');
				$el.attr('aria-pressed', 'true');
				this.trigger('mode-changed', $el.hasClass('allow') ? 'allow' : 'deny')
			}
		},
		
		isPressed : function($el) {
			return ($el.attr('aria-pressed') == 'true');
		},
		
		render : function() {
			this.el.innerHTML = this.template({
				mode: this.permissionsMode.get('mode')
			});
			return this;
		}

	});
});