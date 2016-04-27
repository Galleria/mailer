
function onBlurFactory() {
    return function() {
          var to = $('#to').val(),
              topic = $('#topic').val(),
              body = $(CKEDITOR.instances.editor.document.$).find('body').text();

          $("#send").prop( "disabled", !new SenderValidator().checkRequired(to, topic, body) );
    };
}