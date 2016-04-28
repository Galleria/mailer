describe("ContactForm testing", function() {

  var contactForm;

  beforeEach(function() {
    contactForm = new ContactForm();
  });

  describe("When all required fields are entered", function() {

      it("should return true if all required fields are not empty", function(){
        contactForm.firstName = 'Penny';
        contactForm.lastName = 'Anything';
        contactForm.email = 'penny@hotmail.com';

        expect(contactForm.validateRequired()).toEqual(true);
      });

      it("should return false if firstName is empty", function(){
        contactForm.firstName = '';
        contactForm.lastName = 'Anything';
        contactForm.email = 'penny@hotmail.com';

        expect(contactForm.validateRequired()).toEqual(false);
      });

      it("should return false if lastName is empty", function(){
        contactForm.firstName = 'Penny';
        contactForm.lastName = '';
        contactForm.email = 'penny@hotmail.com';
        expect(contactForm.validateRequired()).toEqual(false);
      });

      it("should return false if email is empty", function(){
        contactForm.firstName = 'Penny';
        contactForm.lastName = 'Anything';
        contactForm.email = '';
        expect(contactForm.validateRequired()).toEqual(false);
      });
  });

  describe("When checking email format", function() {

    it("should return true if email format is valid", function(){
        contactForm.email = 'penny@hotmail.com';
        expect(contactForm.validateEmail()).toEqual(true);
    });

    it("should return false if email format is invalid", function(){
        contactForm.email = 'penny@@hotmail.com';
        expect(contactForm.validateEmail()).toEqual(false);
    });

  });

});
