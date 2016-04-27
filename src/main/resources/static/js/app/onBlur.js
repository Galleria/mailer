
function onBlurFactory(editor) {
    return function() {
          var to = $('#to').val(),
              topic = $('#topic').val(),
              body = $(editor.document.$).find('body').text();

          $("#send").prop( "disabled", !new SenderValidator().checkRequired(to, topic, body) );
    };
}