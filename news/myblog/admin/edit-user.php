<?php //include config
require_once('../includes/config.php');

//if not logged in redirect to login page
if(!$user->is_logged_in()){ header('Location: login.php'); }
?>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Admin - Edit User</title>
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
	<h2>Edit User</h2></div>
    </div></div>


	<?php

	//if form has been submitted process it
	if(isset($_POST['submit'])){

		//collect form data
		extract($_POST);

		//very basic validation
		

		if( strlen($password) > 0){

			if($password ==''){
				$error[] = 'Please enter the password.';
			}

			if($passwordConfirm ==''){
				$error[] = 'Please confirm the password.';
			}

			if($password != $passwordConfirm){
				$error[] = 'Passwords do not match.';
			}

		}
		

		if($email ==''){
			$error[] = 'Please enter the email address.';
		}
		if (!filter_var($email, FILTER_VALIDATE_EMAIL)) 
		{
			$error[] = 'Please enter a Valid email address.';
		}	



		if(!isset($error)){

			try {

				if(isset($password)){

					$hashedpassword = $user->password_hash($password, PASSWORD_BCRYPT);

					//update into database
					$stmt = $db->prepare('UPDATE blog_members SET  password = :password, email = :email WHERE memberID = :memberID') ;
					$stmt->execute(array(
						':password' => $hashedpassword,
						':email' => $email,
						':memberID' => $memberID
					));


				} else {

					//update database
					$stmt = $db->prepare('UPDATE blog_members SET email = :email WHERE memberID = :memberID') ;
					$stmt->execute(array(
						':email' => $email,
						':memberID' => $memberID
					));

				}
				

				//redirect to index page
				header('Location: users.php?action=updated');
				exit;

			} catch(PDOException $e) {
			    echo $e->getMessage();
			}

		}

	}

	?>


	<?php
	//check for any errors
	if(isset($error)){
		foreach($error as $error){
			echo $error.'<br />';
		}
	}

		try {

			$stmt = $db->prepare('SELECT memberID, username, email FROM blog_members WHERE memberID = :memberID') ;
			$stmt->execute(array(':memberID' => $_GET['id']));
			$row = $stmt->fetch(); 

		} catch(PDOException $e) {
		    echo $e->getMessage();
		}

	?>

	<div class="container">

	<form action='' method='post'>


		<input type='hidden' name='memberID' value='<?php echo $row['memberID'];?>'>

		<p><label>Username</label><label ><h5><?php echo $row['username'];?></h5></label><br />
		</p>

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
			<div class="input-field col s12 m12 l12 xl12"><input id="email" type='text' name='email' value='<?php echo $row['email'];?>'></p></p>
     		 <label for="email" >Email</label>
     		</div>
		</div>


		<center><p><label></label><input class="waves-effect waves-light btn cyan" type="submit" name="submit" value="Update User" /></p></center>


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
