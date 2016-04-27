$( document ).ready(function() {
    $("#send").prop( "disabled", true );
    var senderValidator = new SenderValidator();

    var onBlur = onBlurFactory();
    $("#to, #topic").blur(onBlur);
    $('#send').click(function(event){
        $('#errorAlert').hide();
        var to = $('#to').val();
        var result = senderValidator.checkEmailFormat(to);

        if(!result) {
            $('#errorAlert .message').text('Invalid email format.');
            $('#errorAlert').slideDown();
        }
    });

    $('#errorAlert .close').click(function(){
        $('#errorAlert').slideUp();
    });

});