function EmailForm() {
    this.recipients = [];
    this.topic = "";
    this.body = "";
    this.errorMessages = [];
}

EmailForm.prototype = {
    isEnterAllFields : function() {
        return Boolean(!_.isEmpty(this.recipients) && this.topic.trim() && this.body.trim());
    },

    validate : function() {
        this.errorMessages = [];
        var emailFormatError = this.validateEmailFormat();
        var maxEmailError = this.validateMaximumEmail();

        if(!_.isEmpty(emailFormatError)) this.errorMessages.push(emailFormatError);
        if(!_.isEmpty(maxEmailError)) this.errorMessages.push(maxEmailError);

        return this.errorMessages;
    },

    validateEmailFormat : function() {
        var self = this;
        var error = _.some(self.recipients, function(recipient){
            return !self.isEmailFormatValid(recipient);
        });

        return error ? "Invalid email format." : '';
    },

    validateMaximumEmail : function() {
        return "Maximum email is 20.";
    },

    setRecipients : function(recipientText) {
        var recipients = _.map(recipientText.split(','), function(recipient){
            return recipient.trim();
        });

        recipients = _.filter(recipients, function(recipient){
            return !_.isEmpty(recipient);
        });

        this.recipients = _.uniq(recipients);
    },

    isEmailFormatValid : function(email) {
        var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        return emailPattern.test(email);
    }
}

