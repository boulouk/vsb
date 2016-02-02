<%-- 
    Document   : BC Manager
    Created on : Jan 29, 2016, 6:34:21 PM
    Author     : Georgios Bouloukakis (boulouk@gmail.com)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import = "eu.chorevolution.vsb.bc.manager.BcManager" %>
<%@page import= "eu.chorevolution.vsb.gmdl.utils.BcConfiguration"%>

<%@page import = "eu.chorevolution.vsb.bcs.weather.BCStarter" %>

<% BCStarter st = new BCStarter();%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="assets/img/favicon.png">
    
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

    <title>CHOReVOLUTION | Binding Component Manager</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="assets/css/main.css" rel="stylesheet">
    
    <!-- Custom styles for notifications -->
    <link href="assets/css/style.css" rel="stylesheet">

    <!-- Fonts from Google Fonts -->
	<link href='http://fonts.googleapis.com/css?family=Lato:300,400,900' rel='stylesheet' type='text/css'>
  
  </head>

  <body>

    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#"><b>CHOReVOLUTION | BC Manager</b></a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">Generate BC</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

	<div id="headerwrap">
		<div class="container">
			<div class="row">
				<div class="col-lg-3">
					<!--<h1>Make your landing page<br/>look really good.</h1>-->
                                        <form action="" class="form-inline" role="form" method="POST">
<!--					  <div class="form-group">
					    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter your email address">
					  </div>-->
					  <button id="start" type="submit" name="start" class="btn btn-warning btn-lg">Start BC</button>
                                          <button id="stop" type="submit" name="stop" class="btn btn-warning btn-lg">Stop BC</button>
					</form>					
				</div>
                                <div class="col-lg-9">
                                    
                                    <div id="w">
                                        <div id="content">
                                          <!-- Icons source http://dribbble.com/shots/913555-Flat-Web-Elements -->
<!--                                          <div class="notify successbox">
                                            <p><span class="alerticon"><img src="assets/img/mail.png" alt="message" /></span>Request to REST: </p>
                                          </div>-->
                                          <div class="btns clearfix">
                                            <a href="#" id="newSuccessBox" class="flatbtn">New Success Box</a>
                                          </div>
                                        </div><!-- @end #content -->
                                      </div><!-- @end #w -->
				</div>
				
			</div><!-- /row -->
		</div><!-- /container -->
	</div><!-- /headerwrap -->
	
	<div class="container">
		<hr>
		<div class="row centered">
                    <div class="col-lg-2">
                        <a href="http://www.chorevolution.eu" target="_blank"><img src="assets/img/logo-chor.png" alt="checkmark"  height="95px"/></a>
                    </div>
                    <div class="col-lg-offset-91">
                        <a href="https://mimove.inria.fr" target="_blank"><img src="assets/img/logo-mimove.png" alt="checkmark" height="60px" /></a>
                        <a href="http://www.inria.fr" target="_blank"><img src="assets/img/logo-inria.png" alt="checkmark" height="60px" /></a>
                    </div>
                    
		</div><!-- /row -->
		<hr>
		<p class="centered">Copyright © Inria 2016. All Rights Reserved</p>
	</div><!-- /container -->
	

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function(){
          $('#content').on('click', '.notify', function(){
            $(this).fadeOut(350, function(){
              $(this).remove(); // after fadeout remove from DOM
            });
          });
        });
        // handle the additional windows
        $('#newSuccessBox').on('click', function(e){
          e.preventDefault();
          var samplehtml = $('<div class="notify successbox"><p><span class="alerticon"><img src="assets/img/mail.png" alt="message" /></span>Request to REST:</p></div>').prependTo('#content');
        });
        
        $('#start').on('click', function(e){
          e.preventDefault();
          var samplehtml = $('<div class="notify successbox"><p><span class="alerticon"><img src="assets/img/play.png" alt="start" /></span><%=st.start()%>.</p></div>').prependTo('#content');
        });
        
        
          
        
    </script>
    
    
    
    
        
  </body>
</html>
