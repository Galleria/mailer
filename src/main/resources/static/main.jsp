<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-2.2.3.js"></script>
    <script src="js/app/SenderValidator.js"></script>

    <script>
        $( document ).ready(function() {

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
    <div class="well center-block" style="max-width:400px">
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
        <textarea class="form-control" rows="10" id="body" name="body" maxlength="2000"> </textarea>
      </div>

      <div class=text-right>
        <button type="button" id="send" name="send" class="btn btn-primary">Send</button>
      </div>

    </form>
    </div>
</body>
</html>



