<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="js/jquery-2.2.3.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <script src="js/bootstrap.min.js"></script>
    <script src="js/addContact.js"></script>

</head>
<body>
    <div class="row">
        <h1>Hello World</h1>

        to: <input id="to" name="to">


        <a class="btn btn-default" href="#" data-toggle="modal" data-backdrop="static" data-target="#contact">
          <span class="glyphicon glyphicon-book"></span>
        </a>

        <div class="modal fade" id="contact" tabindex="-1" role="dialog">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Contact Page</h4>
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
                <hr>
                <table class="table table-striped">
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
    </div>


</body>
</html>



