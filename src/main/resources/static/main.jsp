<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link href="css/bootstrap-theme.min.css" rel="stylesheet">



    <script src="js/jquery-2.2.3.js"></script>

    <script src="js/bootstrap.min.js"></script>

    <script src="js/app/SenderValidator.js"></script>

    <script src="js/ckeditor/ckeditor.js"></script>

    <script src="js/init_ck_plugin.js"></script>

    <script src="js/addContact.js"></script>

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
          <a class="btn btn-default" href="#" data-toggle="modal" data-backdrop="static" data-target="#contact">


            <span class="glyphicon glyphicon-book"></span>


          </a>
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



    <div class="modal fade" id="contact" tabindex="-1" role="dialog" aria-labelledby="contactLabel">

    <div class="modal-dialog" role="document">

        <div class="modal-content">

            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>

                <h4 class="modal-title" id="contactLabel">Contact</h4>

            </div>

            <div class="modal-body">

                <div class="row">


                    <div class="col-md-3">


                        <input type="text" class="form-control" id="firstName" placeholder="First Name">


                    </div>
                    


                    <div class="col-md-3">


                        <input type="text" class="form-control" id="lastName" placeholder="Last Name">


                    </div>
                    


                    <div class="col-md-3">


                        <input type="text" class="form-control" id="email" placeholder="E-mail">


                    </div>
                    


                    <div class="col-md-3">


                        <button type="button" class="btn btn-success">Add Contact</button>


                    </div>

                </div>
                
<table class="table table-striped table-bordered">


                    <thead>


                        <tr>


                            <th>First Name</th>
                        


                            <th>Last Name</th>
                        


                            <th>E-mail</th>
                        


                        </tr>
                    


                    </thead>
                    


                    <tbody>
</tbody>

                </table>

            </div>

        </div>

    </div>

    </div>
</body>
</html>



