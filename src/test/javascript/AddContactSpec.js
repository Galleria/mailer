describe("ContactForm", function() {
  var contactForm;

  var contact = {
    firstName: "Penny",
    lastName: "Lopez",
    email: "plopez@gmail.com"
  };

  beforeEach(function() {
    contactForm = new ContactForm();
  });

  it("call request", function() {
    spyOn($, "ajax");
    contactForm.add(contact);
    expect($.ajax.calls.mostRecent().args[0]["url"]).toEqual("/addcontact");
  });

//  it("add contact successfully", function() {
//    spyOn($, "ajax").and.callFake(function(options) {
//        options.success(contact);
//    });
//    expect(contact).toBe( contactForm.add(contact) );
//  });
//
//  it("should disable add contact button when first name is missing", function(){
//   var $addButton = document.getElementById("addContactButton");
//    expect($addButton.hasClass("disabled")).toBe(true);
//  });

});
