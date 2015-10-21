define('PermissionsLayout', [ 'backbone', 'underscore', 'jquery' ], function(Backbone, _, $) {
	return Backbone.View.extend({
		template : org.networkedassets.personalrepos.permissions.layout,
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
				this.showConfirmation({
					ok : _.bind(function() {
						this.switchMode($el);
					}, this)
				});
			}
		},
		
		showConfirmation : function(opts) {
			var dialog = new AJS.Dialog({
			    width: 500, 
			    height: 250, 
			    closeOnOutsideClick: true
			});
			dialog.addHeader(AJS.I18n.getText('org.networkedassets.atlassian.bitbucket.personalrepos.permissions.mode.change.confirmation.header'));
			dialog.addPanel("Panel", AJS.I18n.getText('org.networkedassets.atlassian.bitbucket.personalrepos.permissions.mode.change.confirmation.text'), "panel-body");
			dialog.addButton(AJS.I18n.getText("org.networkedassets.atlassian.bitbucket.personalrepos.permissions.mode.change.confirmation.button.cancel"), function (dialog) {
			    dialog.hide();
			});
			dialog.addButton(AJS.I18n.getText("org.networkedassets.atlassian.bitbucket.personalrepos.permissions.mode.change.confirmation.button.ok"), _.bind(function (dialog) {
				dialog.hide();
				opts.ok();
			}, this));
			dialog.show();
		},
		
		switchMode : function($el) {
			this.$('.aui-button[aria-pressed="true"]').attr('aria-pressed', 'false');
			$el.attr('aria-pressed', 'true');
			this.trigger('mode-changed', $el.hasClass('allow') ? 'allow' : 'deny')
		},
		
		isPressed : function($el) {
			return ($el.attr('aria-pressed') == 'true');
		},
		
		render : function() {
			this.$el.html(this.template({
				mode: this.permissionsMode.get('mode')
			}));
			return this;
		}

	});
});