<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-2.2.3.js"></script>
    <script src="js/app/SenderValidator.js"></script>
    <script src="js/ckeditor/ckeditor.js"></script>
    <script src="js/init_ck_plugin.js"></script>

    <script>
        $( document ).ready(function() {
            initCkPlugin();

            $("#send").prop( "disabled", true );

            var senderValidator = new SenderValidator();

            $("#to, #topic, #body").blur(function( event ) {
                var to = $('#to').val(),
                    topic = $('#topic').val(),
                    body = $('#body').val();

                var result = senderValidator.checkRequired(to, topic, body);

                if (result) {
                    $("#send").prop( "disabled", false );
                }else {
                    $("#send").prop( "disabled", true );
                }
            });

        });


    </script>

</head>
<body>
    <div class="well col-xs-8 col-xs-offset-2">
    <h1>Mass Mailer</h1>
    <form>
      <div class="form-group">
        <div class="input-group">
          <div class="input-group-addon">To</div>
          <input type="text" class="form-control" id="to" name="to" placeholder="To">
        </div>
      </div>

      <div class="form-group">
        <div class="input-group">
            <div class="input-group-addon">Topic</div>
            <input type="text" class="form-control" id="topic" name="topic" placeholder="Topic" maxlength="500">
        </div>
      </div>

      <div class="form-group">
            <div id="editor"></div>
      </div>

      <div class=text-right>
        <button type="button" id="send" name="send" class="btn btn-primary">Send</button>
      </div>

    </form>
    </div>
</body>
</html>



