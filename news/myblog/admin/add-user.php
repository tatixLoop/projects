<?php //include config
require_once('../includes/config.php');

//if not logged in redirect to login page
if(!$user->is_logged_in()){ header('Location: login.php'); }
?>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Admin - Add User</title>
  <link rel="stylesheet" href="../style/normalize.css">
  <link rel="stylesheet" href="../style/main.css">
  
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

  <link rel="stylesheet" href="../style/materialize/css/materialize.css">
  <script src="../style/materialize/js/materialize.js"></script>
</head>
<body>

<div id="main_wrapper">

	<?php include('menu.php');?>
	
	<div class="container"><div class="row"><div class="col s4 m4 l4 xl4"><h2><a class="btn-floating btn-large cyan " href="users.php"><i class="material-icons">navigate_before</i></a>
	</h2></div>
	<div class="col s8 m8 l8 xl8">
	<h2>Add User</h2></div>
    </div></div>
	<?php

	//if form has been submitted process it
	if(isset($_POST['submit'])){
                // Only Editor right is given by default
                $access=14;

		//collect form data
		extract($_POST);

		//very basic validation
		if($username ==''){
			$error[] = 'Please enter the username.';
		}
		if (!preg_match("/^[a-zA-Z ]*$/",$username)) {
 		    $error[] = "Only letters and white space allowed for username"; 
		}

		if($password ==''){
			$error[] = 'Please enter the password.';
		}

		if($passwordConfirm ==''){
			$error[] = 'Please confirm the password.';
		}

		if($password != $passwordConfirm){
			$error[] = 'Passwords do not match.';
		}

		if($email ==''){
			$error[] = 'Please enter the email address.';
		}
		if (!filter_var($email, FILTER_VALIDATE_EMAIL)) 
		{
			$error[] = 'Please enter a Valid email address.';
		}	

		if(!isset($error)){

			$hashedpassword = $user->password_hash($password, PASSWORD_BCRYPT);

			try {

				//insert into database
                                $sql = "INSERT INTO news_tbl_accounts (name, lastname, username, access, password, email) VALUES ('".$firstname."','".$lastname."', '".$username."', ".$access.", '".$hashedpassword."','".$email."')";
//                                $sql = "INSERT INTO news_tbl_accounts (name, lastname, username, access, password, email) VALUES (:firstname, :lastname, :username, ".$access.", :password, :email)";

				$stmt = $db->prepare($sql);

				$stmt->execute(array(
					':name' => $firstname,
					':lastname' => $lastname,
					':username' => $username,
					':access' => $access,
					':password' => $hashedpassword,
					':email' => $email
				));

				//redirect to index page
				header('Location: users.php?action=added');
				exit;

			} catch(PDOException $e) {
			    echo $e->getMessage();
			}

		}

	}

	//check for any errors
	if(isset($error)){
		foreach($error as $error){
			echo '<div class="container"><p class="error">'.$error.'</p></div>';
		}
	}
	?>


	<div class="container">

	<form action='' method='post'>

		<div class="row">
			<div class="input-field col s12 m12 l12 xl12"><input id="name" type='text' name='username' value='<?php if(isset($error)){ echo $_POST['username'];}?>'></p>
     		 <label for="name" >Username</label>
     		</div>
		</div>

		<div class="row">
			<div class="input-field col s12 m12 l12 xl12"><input id="firstname" type='text' name='firstname' value='<?php if(isset($error)){ echo $_POST['firstname'];}?>'></p>
     		 <label for="firstname" > First name</label>
     		</div>
		</div>

		<div class="row">
			<div class="input-field col s12 m12 l12 xl12"><input id="lastname" type='text' name='lastname' value='<?php if(isset($error)){ echo $_POST['lastname'];}?>'></p>
     		 <label for="lastname" > Last name</label>
     		</div>
		</div>

		<div class="row">
			<div class="input-field col s12 m12 l12 xl12"><input id="password" type='password' name='password' value='<?php if(isset($error)){ echo $_POST['password'];}?>'></p>
     		 <label for="password" >Password</label>
     		</div>
		</div>

		<div class="row">
			<div class="input-field col s12 m12 l12 xl12"><input id="passwordconfirm" type='password' name='passwordConfirm' value='<?php if(isset($error)){ echo $_POST['passwordConfirm'];}?>'></p>
     		 <label for="passwordconfirm" >Confirm Password</label>
     		</div>
		</div>


		<div class="row">
			<div class="input-field col s12 m12 l12 xl12"><input id="email" type='text' name='email' value='<?php if(isset($error)){ echo $_POST['email'];}?>'></p>
     		 <label for="email" >Email</label>
     		</div>
		</div>

		<center><p><label></label><input class="waves-effect waves-light btn cyan" type="submit" name="submit" value="Add User" /></p></center>


		

	</form>
   </div>
<?php include('../footer.php');  ?>
</div>
</body>
<script>
  	 $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>


</html>
