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

//  it("call correct add contact request", function() {
//    spyOn($, "ajax");
//    contactForm.add(contact);
//    expect($.ajax.calls.mostRecent().args[0]["url"]).toEqual("/addcontact");
//  });
//
//  it("add contact successfully", function() {
//    spyOn($, "ajax").and.callFake(function(options) {
//        options.success(contact);
//    });
//    expect(contact).toBe( contactForm.add(contact) );
//  });

});
