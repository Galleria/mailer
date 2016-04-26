<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-2.2.3.js"></script>

    <script>
        $( document ).ready(function() {

            $("#send").click(function( event ) {

                alert( "Thanks for visiting!" );

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



