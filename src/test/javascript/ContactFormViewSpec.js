
describe("EmailFormView test", function() {

    var contactForm;
    var contactFormView;

      beforeEach(function() {

        $firstNameTxt = $('<input>').attr("id","firstName");
        $lastNameTxt = $('<input>').attr("id","lastName");
        $emailTxt = $('<input>').attr("id","email");
        $addContactBtn = $('<input>').attr("id","addContactButton").attr("type","button");
        $contactTable = $("<tbody>").attr("id","contactList");

        contactForm = new ContactForm();
        contactFormView = new ContactFormView(contactForm, {
            firstName: $firstNameTxt,
            lastName: $lastNameTxt,
            email: $emailTxt,
            addContactButton: $addContactBtn,
            contactTable: $contactTable
        });
        contactFormView.initialize();
      });

    describe("load contacts", function() {
        it("should call correct contacts url", function() {
            spyOn($, "ajax");
            contactFormView.loadContacts();
            expect($.ajax.calls.mostRecent().args[0]["url"]).toEqual("/contacts");
        });
    });

    describe("add contact", function() {
        it("should call correct contact url", function() {
            spyOn($, "ajax");
            contactFormView.addContact();
            expect($.ajax.calls.mostRecent().args[0]["url"]).toEqual("/addcontact");
        });
    });

});