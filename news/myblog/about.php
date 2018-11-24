<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>About Me</title>

 <link rel="stylesheet" href="style/normalize.css">
    <link rel="stylesheet" href="style/main.css">

     

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

  <link rel="stylesheet" href="style/materialize/css/materialize.css">
  <script src="style/materialize/js/materialize.js"></script>

 

</head>
<body>

	<div id="main_wrapper">
		<!-- Menu -->
<?php include('menu_site.php');?>

<!-- About me -->

<div class="row">
	<div class="col s12 m6 l6 xl6 ">
		<h2 class="h2style_abt center">ABOUT ME</h2>
		<p class="center-align">See that result, second from the top? That's an "About Me" I wrote for my sample blog!
Now, rankings aside, the truth is that About me pages are almost always one of the most visited pages on any website.
They’re incredibly important because your audience cares to know who you are and wants to be able to relate to you.
They love seeing the face behind the blog!
So my question is...</p>

	</div>
	<div class="col hide-on-small-only m1 l1 xl1"></div>
	<div class="col s12 m5 l5 xl5 inner-images">
		<img src="assets/images/about.jpg">
	</div>
</div>
<div class="divider"></div>


	<!-- Footer -->
<?php include('footer.php');  ?>

	
</div>

</body>


<script>
  	 $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>

</html>