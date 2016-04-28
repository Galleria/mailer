describe("EmailForm test", function() {

  var emailForm;

  beforeEach(function() {
    emailForm = new EmailForm();
  });

  describe("When checking all fields are entered", function() {

      it("should return true if all required fields are not empty", function(){
        emailForm.recipients = ['penny@hotmail.com'];
        emailForm.topic = 'Hello penny';
        emailForm.plainBody = "Penny please check the document";

        expect(emailForm.isEnterAllFields()).toEqual(true);
      });

      it("should return false if recipient is empty", function(){
        emailForm.recipients = [];
        emailForm.topic = 'Hello penny';
        emailForm.plainBody = "Penny please check the document";

        expect(emailForm.isEnterAllFields()).toEqual(false);
      });

      it("should return false if topic is empty", function(){
        emailForm.recipients = ['penny@hotmail.com'];
        emailForm.topic = '';
        emailForm.plainBody = "Penny please check the document";

        expect(emailForm.isEnterAllFields()).toEqual(false);
      });

      it("should return false if body is empty", function(){
        emailForm.recipients = ['penny@hotmail.com'];
        emailForm.topic = 'Hello penny';
        emailForm.plainBody = '';

        expect(emailForm.isEnterAllFields()).toEqual(false);
      });
  });

  describe("When checking email format", function() {
    it("should return no error message if all emails are valid", function(){
        emailForm.recipients = ['penny@hotmail.com', 'penny1@hotmail.com','penny2@hotmail.com'];

        expect(_.isEmpty(emailForm.validateEmailFormat())).toEqual(true);
    });

    it("should return error message if any emails are invalid", function(){
        emailForm.recipients = ['pennyhotmail.com', 'penny1@hotmail.com','penny2@hotmail.com'];

        expect(_.isEmpty(emailForm.validateEmailFormat())).toEqual(false);
    });
  });

  describe("When checking maximum emails", function(){
    it("should return no error message if emails are less than 20", function(){
        emailForm.recipients = ['penny@hotmail.com', 'penny1@hotmail.com','penny2@hotmail.com'];

        expect(_.isEmpty(emailForm.validateMaximumEmail())).toEqual(true);
    });

    it("should return error message if emails are more than 20", function(){
        for(var i=0; i<30; i++){
            emailForm.recipients.push('penny'+i+'@hotmail.com');
        }

        expect(_.isEmpty(emailForm.validateMaximumEmail())).toEqual(false);
    });
  });

});
