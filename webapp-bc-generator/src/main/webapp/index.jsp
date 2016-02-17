<%-- 
    Document   : VSB Manager
    Created on : Jan 29, 2016, 6:34:21 PM
    Author     : Georgios Bouloukakis (boulouk@gmail.com)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<!DOCTYPE html>
<html lang="en">
    <head>
        
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script type="text/javascript" src="assets/js/ajax.js"></script>
        <script type="text/javascript">
            document.getElementById("uploadBtn").onchange = function () {
            document.getElementById("uploadFile").value = this.value;
            };
        </script>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="assets/img/favicon.png">



        <title>CHOReVOLUTION | VSB Manager</title>

        <!-- Bootstrap core CSS -->
        <link href="assets/css/bootstrap.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="assets/css/main.css" rel="stylesheet">

        <!-- Custom styles for notifications -->
        <link href="assets/css/style.css" rel="stylesheet">

        <!-- Fonts from Google Fonts -->
        <link href='http://fonts.googleapis.com/css?family=Lato:300,400,900'
              rel='stylesheet' type='text/css'>

    </head>

    <body>

        <!-- Fixed navbar -->
        <div class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target=".navbar-collapse">
                        <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"><b>CHOReVOLUTION | VSB Manager</b></a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <!--<li><a href="#"></a></li>-->
                    </ul>
                </div>
                <!--/.nav-collapse -->
            </div>
        </div>

        <div id="headerwrap">
            <div class="container">
                <hr>
		<div class="row centered">
			<div class="col-lg-6 col-lg-offset-3">
				<form class="form-inline" role="form">
<!--				  <div class="form-group">
				    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Insert the Service Interface Descritpion File">
				  </div>-->
                                    
                                    
                                    <!--<div class="fileUpload btn btn-primary">-->
                                        <!--<span>Upload</span>-->
                                        <div class="form-group">
                                        <input id="uploadBtn" type="file" class="btn btn-warning btn-lg" />
                                        </div>
                                    <!--</div>-->
                                     
                                    
                                  <button id="start" type="button" name="start" class="btn btn-warning btn-lg" onclick="ajaxCallGet('generate')">
                                      Generate BC</button>
				</form>					
			</div>
                        
		</div><!-- /row -->
		<hr>
            <div id="genermsg" class="col-lg-6 col-lg-offset-3">
                        
                        </div>
            
            </div>
            <!-- /container -->
        </div>
        <!-- /headerwrap -->

        <div class="container">
            <hr>
            <div class="row centered">
                <div class="col-lg-2">
                    <a href="http://www.chorevolution.eu" target="_blank"><img
                            src="assets/img/logo-chor.png" alt="checkmark" height="95px" /></a>
                </div>
                <div class="col-lg-offset-91">
                    <a href="https://mimove.inria.fr" target="_blank"><img
                            src="assets/img/logo-mimove.png" alt="checkmark" height="60px" /></a>
                    <a href="http://www.inria.fr" target="_blank"><img
                            src="assets/img/logo-inria.png" alt="checkmark" height="60px" /></a>
                </div>

            </div>
            <!-- /row -->
            <hr>
            <p class="centered">Copyright © Inria 2016. All Rights Reserved</p>
        </div>
        <!-- /container -->
        <!-- Bootstrap core JavaScript
    ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
