function ContactForm() {
    this.firstName = "";
    this.lastName = "";
    this.email = "";
}

ContactForm.prototype = {

    validateRequired : function() {
        return !_.isEmpty(this.firstName) && !_.isEmpty(this.lastName) && !_.isEmpty(this.email) ;
    },

    validateEmail : function() {
        var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        return emailPattern.test(this.email);
    },

    setFirstName: function(firstName) {
        this.firstName = firstName;
    },

    setLastName: function(lastName){
        this.lastName = lastName;
    },

    setEmail: function(email){
        this.email = email;
    }
};