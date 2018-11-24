<!doctype html>
<?php
//include config
require_once('../includes/config.php');


//check if already logged in
if( $user->is_logged_in() ){ header('Location: index.php'); } 
?>

<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Admin Login</title>
  <link rel="stylesheet" href="../style/normalize.css">
  <link rel="stylesheet" href="../style/main.css">
  <link rel="stylesheet" href="../style/materialize/css/materialize.css">
  <script src="../style/materialize/js/materialize.js"></script>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 
</head>
<body>

<div id="main_wrapper" class=" div1">
	<div class="div1 login_main_bg inner-images"> <img src="../assets/images/login_bg.jpeg"></div>

<div id="login">

	<?php

	//process login form if submitted
	if(isset($_POST['submit'])){

		$username = trim($_POST['username']);
		$password = trim($_POST['password']);
		
		if($user->login($username,$password)){ 

			//logged in return to index page
			header('Location: index.php');
			exit;
		

		} else {
			$message = '<p class="error">Wrong username or password</p>';
		}

	}//end if submit

	if(isset($message)){ echo $message; }
	?>

	<form action="" method="post">

	<div class="row">
    <div class="input-field col s12 m12 l12 xl12">
      <i class="material-icons prefix">face</i>
      <input id="uname"  type="text" name="username" >
      <label for="uname" >Username</label>
    </div>
    </div>
  <div class="row">
        <div class="input-field col s12">
        <i class="material-icons prefix">lock</i>
          <input id="password" name="password" type="password">
          <label for="password">Password</label>
        </div>

	<!-- <p><label>Username</label><input type="text" name="username" value=""  /></p>
	<p><label>Password</label><input type="password" name="password" value=""  /></p> -->
	<center><p><label></label><input class="waves-effect waves-light btn cyan" type="submit" name="submit" value="Login"  /></p></center>
	</form>

</div>
</div>
</body>
<script>
$(document).ready(function(){
  $(function(){
  $('.div1').css({ height: $(window).innerHeight() });
  $(window).resize(function(){
    $('.div1').css({ height: $(window).innerHeight() });
  });
});
});
</script>
</html>
