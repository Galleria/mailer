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
    <div class="container">

      <h1>Hello World</h1>
    <div class="row">


        to: <input id="to" name="to">
    </div>

        <div class="row">
            Topic: <input id="topic" name="topic">
        </div>


        <div class="row">
            <textarea rows="10" id="body" name="body"></textarea>
        </div>


        <div class="row">
            <input type="button" id="send" name="send" value="Send">
        </div>



    </div>
</body>
</html>



