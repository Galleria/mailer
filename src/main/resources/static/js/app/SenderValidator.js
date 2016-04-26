function SenderValidator() {
}
SenderValidator.prototype.checkRequired = function(recipient, topic, body) {

    if(!recipient || !topic || !body) {
        return false;
    }


    return true;
};