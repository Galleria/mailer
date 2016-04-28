function ContactFormView(model, elements) {
    this._model = model;
    this._elements = elements;
}

ContactFormView.prototype = {

    initialize : function() {
        this._elements.firstName.keyup(_.bind(this.onFirstNameChanged, this));
        this._elements.lastName.keyup(_.bind(this.onLastNameChanged, this));
        this._elements.email.keyup(_.bind(this.onEmailChanged, this));
        this._elements.addContactButton.click(_.bind(this.onAddContactButtonClicked, this));
    },

    onAddContactButtonClicked: function(){
        var self = this;
        var isValidEmail = this._model.validateEmail();
        if(!isValidEmail){
            alert("Email format is invalid");
        }else{
            var contact = {
              firstName : this._model.firstName,
              lastName : this._model.lastName,
              email : this._model.email
            };

            $.ajax({
              url: "/addcontact",
              type: "POST",
              async : false,
              data : contact,
              success: _.bind(self.onAddContactSuccess, self)
            });
        }
    },

    onAddContactSuccess: function(contact){
        var self = this;
        this.clearInputFields();
        this.toggleAddButton();
        this.loadContacts().then(function(contacts){
            self.reloadContactTable(contacts);
        });
    },

    clearInputFields: function(){
        this._elements.firstName.val("");
        this._elements.lastName.val("");
        this._elements.email.val("");
    },

    reloadContactTable: function(contacts){
        this._elements.contactTable.empty();
        var self = this;

        $.each(contacts, function(index, contact){
            $tr = $("<tr>");
            $tdFirstName = $("<td>").text( contact.firstName );
            $tdLastName = $("<td>").text( contact.lastName );
            $tdEMail = $("<td>").text( contact.email );

            $tr.append($tdFirstName)
                .append($tdLastName)
                .append($tdEMail);

            self._elements.contactTable.append( $tr );
        });
    },

    loadContacts: function(){
        var self = this;
        var response;
        return $.ajax({
          url: "/contacts",
          type: "GET"
        });
    },

    onFirstNameChanged: function(event){
        this._model.setFirstName($(event.currentTarget).val());
        this.toggleAddButton();
    },

    onLastNameChanged: function(event){
        this._model.setLastName($(event.currentTarget).val());
        this.toggleAddButton();
    },

    onEmailChanged: function(event){
        this._model.setEmail($(event.currentTarget).val());
        this.toggleAddButton();
    },

    toggleAddButton: function(){
        this._elements.addContactButton.prop("disabled", !this._model.validateRequired());
        this._elements.addContactButton.toggleClass("disabled", !this._model.validateRequired());
    }

}