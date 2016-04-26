describe("Sender Tester", function() {

  var senderValidator;

  beforeEach(function() {
    senderValidator = new SenderValidator();
  });

  it("Passing required fields", function(){
    var recipient = "penny@hotmail.com",
        topic = "Hello penny",
        body = "Penny please check the document";

    var result = senderValidator.checkRequired(recipient, topic, body);
    expect(result).toEqual(true);
  });

  it("Missing required fields", function(){
      var recipient = "",
          topic,
          body = "Penny please check the document";

      var result = senderValidator.checkRequired(recipient, topic, body);
      expect(result).toEqual(false);
    });

});
