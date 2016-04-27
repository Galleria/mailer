describe("SenderValidator test", function() {

  var senderValidator;

  beforeEach(function() {
    senderValidator = new SenderValidator();
  });

  describe("When checking required fields", function() {
      it("should return true if all required fields are not empty", function(){
        var recipient = "penny@hotmail.com",
            topic = "Hello penny",
            body = "Penny please check the document";

        var result = senderValidator.checkRequired(recipient, topic, body);
        expect(result).toEqual(true);
      });

      it("should return false if recipient is empty", function(){
        var recipient = "",
            topic = "Hello penny",
            body = "Penny please check the document";

        var result = senderValidator.checkRequired(recipient, topic, body);
        expect(result).toEqual(false);
      });

      it("should return false if topic is empty", function(){
        var recipient = "penny@hotmail.com",
            topic = "",
            body = "Penny please check the document";

        var result = senderValidator.checkRequired(recipient, topic, body);
        expect(result).toEqual(false);
      });

      it("should return false if body is empty", function(){
        var recipient = "penny@hotmail.com",
            topic = "Hello penny",
            body = "";

        var result = senderValidator.checkRequired(recipient, topic, body);
        expect(result).toEqual(false);
      });
  });

  describe("When checking email format", function() {
    it("should return true if the email is valid", function(){
        var email = "penny@gmail.com";
        var result = senderValidator.checkEmailFormat(email);
        expect(result).toEqual(true);
    });

    it("should return false if the email is invalid", function(){
        var email = "penny.gmail.com";
        var result = senderValidator.checkEmailFormat(email);
        expect(result).toEqual(false);
    });
  });
});
