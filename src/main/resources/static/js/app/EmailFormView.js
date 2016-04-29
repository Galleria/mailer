function EmailFormView(model, elements, subview) {
    this._model = model;
    this._elements = elements;
    this._subview = subview;
}

EmailFormView.prototype = {
    initialize : function() {
        this._elements.recipients.change(_.bind(this.onRecipientsChanged, this));
        this._elements.topic.change(_.bind(this.onTopicChanged, this));
        this._elements.editor.on('change', _.bind(this.onEditorChanged, this));

        this._elements.send.click(_.bind(this.onClickSendButton, this));
        this._elements.errorAlert.find('.close').click(_.bind(this.onClickAlertCloseButton, this));
        this._elements.contactButton.click(_.bind(this.onClickContactButton, this));

        this.setDisabledSendButton();
    },

    onRecipientsChanged : function(event) {
        this._model.setRecipients($(event.currentTarget).val());
        this.setDisabledSendButton();
    },

    onTopicChanged : function(event) {
        this._model.topic = $(event.currentTarget).val();
        this.setDisabledSendButton();
    },

    onEditorChanged : function(event) {
        this._model.setBody(this._elements.editor.getData());
        this.setDisabledSendButton();
    },

    onClickContactButton: function(){
        this._subview.contactView.initializeContacts();
    },

    onClickSendButton : function() {
        var errorAlert = this._elements.errorAlert;
        var errorMessages = this._model.validate();

        errorAlert.hide();

        if(!_.isEmpty(errorMessages)) {
            errorAlert.find('.message').html(errorMessages.join('<br/>'));
            errorAlert.slideDown();
        } else {
            this._elements.body.val(this._model.body);
            this._elements.form.submit();
        }
    },

    onClickAlertCloseButton : function() {
        this._elements.errorAlert.slideUp();
    },

    setDisabledSendButton : function() {
        this._elements.send.prop("disabled", !this._model.isEnterAllFields());
    },
}