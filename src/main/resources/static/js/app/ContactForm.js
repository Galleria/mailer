
var ContactForm = function(){}

var addContactToTable = function(result){

    result.forEach(function(contact){
        $tr = $("<tr>");
        $tdFirstName = $("<td>").text( contact.firstName );
        $tdLastName = $("<td>").text( contact.lastName );
        $tdEMail = $("<td>").text( contact.email );

        $tr.append( $tdFirstName )
            .append($tdLastName)
            .append($tdEMail);

        $("#contactList").append( $tr );
    })

    clearContactInputFields();
    ContactForm.prototype.validate();
};

var clearContactInputFields = function(){
    $("#firstName").val("");
    $("#lastName").val("");
    $("#email").val("");
};

var onSuccess = function( result ) {
    response = result;

    $("#contactList").empty();

    ContactForm.prototype.loadContacts();
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
      success: onSuccess,
      fail: function(error) {
        response = error;
      }
    });

    return response;
}

ContactForm.prototype.validate = function(){
    var firstNameField = $("#firstName").val().trim();
    var lastNameField = $("#lastName").val().trim();
    var emailField = $("#email").val().trim();
    var button = $("#addContactButton");

    if(firstNameField && lastNameField && emailField){
        button.removeAttr("disabled");
        button.removeClass("disabled");
    }else{
        button.attr("disabled", true);
        button.addClass("disabled");
    }
}
