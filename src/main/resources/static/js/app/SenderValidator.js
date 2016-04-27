function SenderValidator() {
}
SenderValidator.prototype.checkRequired = function(recipient, topic, body) {
    return Boolean(recipient.trim() && topic.trim() && body.trim());
};

SenderValidator.prototype.checkEmailFormat = function(email) {
    var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    return emailPattern.test(email);
};