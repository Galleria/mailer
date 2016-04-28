describe("EmailFormView test", function() {
    var emailFormView;
    var emailForm;
    var submitForm;
    var body;
    var errorAlert;

    beforeEach(function() {
        emailForm = new EmailForm();
        submitForm = jasmine.createSpyObj('form', ['submit']);
        body = jasmine.createSpyObj('body', ['val']);
        errorAlert = jasmine.createSpyObj('errorAlert', ['find', 'slideDown', 'hide']);

        errorAlert.find.and.returnValue({
            html: function() {}
        });

        emailFormView = new EmailFormView(emailForm, {
            errorAlert: errorAlert,
            form: submitForm,
            body: body
        });
    });

    describe("When press send button", function() {
        it("should block if there is an error", function() {
            spyOn(emailForm, "validate").and.returnValue(["Error"]);

            emailFormView.onClickSendButton();

            expect(submitForm.submit).not.toHaveBeenCalled();
        });

        it("should send request if there is no error", function() {
            spyOn(emailForm, "validate").and.returnValue([]);
            emailForm.body = "Test Body";

            emailFormView.onClickSendButton();

            expect(body.val).toHaveBeenCalledWith(emailForm.body);
            expect(submitForm.submit).toHaveBeenCalled();
        });
    });
});