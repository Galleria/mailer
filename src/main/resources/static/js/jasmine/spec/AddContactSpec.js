describe("addContact", function() {
  var addContact;

  beforeEach(function() {
    addContact = new AddContact();
  });

  it("add contact", function() {
    expect("added").toBe( addContact.add() );

  });

});
