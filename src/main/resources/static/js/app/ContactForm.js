
var ContactForm = function(){}

var addContactToTable = function(result){
    $("#contactList").empty();

    $.each(result, function(index, contact){
        $tr = $("<tr>");
        $tdFirstName = $("<td>").text( contact.firstName );
        $tdLastName = $("<td>").text( contact.lastName );
        $tdEMail = $("<td>").text( contact.email );

        $tr.append( $tdFirstName )
            .append($tdLastName)
            .append($tdEMail);

        $("#contactList").append( $tr );
    });

    clearContactInputFields();
    ContactForm.prototype.validateRequired();
};

var clearContactInputFields = function(){
    $("#firstName").val("");
    $("#lastName").val("");
    $("#email").val("");
};

ContactForm.prototype.loadContacts = function(){
    $.ajax({
      url: "/contacts",
      type: "GET",
      async : false,
      success: addContactToTable,
      fail: function(error) {
        response = error;
      }
    });
}

ContactForm.prototype.add = function(){
    var response;
    var firstNameField = $("#firstName").val();
    var lastNameField = $("#lastName").val();
    var emailField = $("#email").val();

    var contact = {
      firstName : firstNameField,
      lastName : lastNameField,
      email : emailField
    };

    $.ajax({
      url: "/addcontact",
      type: "POST",
      async : false,
      data : contact,
      success: function(result){
        response = result;
        ContactForm.prototype.loadContacts();
      },
      fail: function(error) {
        response = error;
      }
    });

    return response;
}

ContactForm.prototype.validateEmail = function(){
    var senderValidator = new SenderValidator();
    var emailField = $("#email").val();

    if( ! senderValidator.checkEmailFormat(emailField) ){
        alert( "Invaild Email format !!" );
    }else{
        ContactForm.prototype.add();
    }
}

ContactForm.prototype.validateRequired = function(){
    var firstNameField = $("#firstName").val() ? $("#firstName").val().trim() : "";
    var lastNameField = $("#lastName").val() ? $("#lastName").val().trim() : "";
    var emailField = $("#email").val() ? $("#email").val().trim() : "";
    var button = $("#addContactButton");

    if(firstNameField && lastNameField && emailField){
        button.removeAttr("disabled");
        button.removeClass("disabled");
    }else{
        button.attr("disabled", true);
        button.addClass("disabled");
    }
}
