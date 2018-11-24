<?php //include config
require_once('../includes/config.php');

//if not logged in redirect to login page
if(!$user->is_logged_in()){ header('Location: login.php'); }
?>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Admin - Add Post</title>
  <link rel="stylesheet" href="../style/normalize.css">
  <link rel="stylesheet" href="../style/main.css">
  <link rel="stylesheet" href="../style/materialize/css/materialize.css">
  <script src="../style/materialize/js/materialize.js"></script>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

   <script src="//tinymce.cachefly.net/4.0/tinymce.min.js"></script>
  <script>
          tinymce.init({
              selector: "textarea",
              plugins: [
                  "advlist autolink lists link image charmap print preview anchor",
                  "searchreplace visualblocks code fullscreen",
                  "insertdatetime media table contextmenu paste"
              ],
              toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image"
          });
  </script>
<link rel="stylesheet" href="../style/materialize/css/materialize.css">
  <script src="../style/materialize/js/materialize.js"></script>
  
</head>
<body>

<div id="main-wrapper">

	<?php include('menu.php');?>
	

	<div class="container"><div class="row"><div class="col s4 m4 l4 xl4"><h2><a class="btn-floating btn-large cyan " href="index.php"><i class="material-icons">navigate_before</i></a>
	</h2></div>
	<div class="col s8 m8 l8 xl8">
	<h2>Add Post</h2></div>
    </div></div>

	<?php

	//if form has been submitted process it
	if(isset($_POST['submit'])){

		$_POST = array_map( 'stripslashes', $_POST );

		//collect form data
		extract($_POST);

		//very basic validation
		if($postTitle ==''){
			$error[] = 'Please enter the title.';
		}

		if($postDesc ==''){
			$error[] = 'Please enter the description.';
		}

		if($postCont ==''){
			$error[] = 'Please enter the content.';
		}
    $check_name = $_FILES['imageUpload']['name'];
	$path_parts = pathinfo($_FILES['imageUpload']['name']);       
 	$file_name = $path_parts['filename'].'_'.time().'.'.$path_parts['extension'];
      $file_size = $_FILES['imageUpload']['size'];
      $file_tmp = $_FILES['imageUpload']['tmp_name'];
      $file_type = $_FILES['imageUpload']['type'];
      $file_ext=strtolower(end(explode('.',$_FILES['imageUpload']['name'])));
      
      $expensions= array("jpeg","jpg","png");
      
      if(in_array($file_ext,$expensions)=== false){
         $errors[]="extension not allowed, please choose a JPEG or PNG file.";
      }
      
      if($file_size > 2097152) {
         $errors[]='File size must be excately 2 MB';
      }
      
			
			if($check_name ==''){
			$error[] = 'Please Upload image.';
		}






		if(!isset($error)){


 			

			try {

				//insert into database
				 move_uploaded_file($file_tmp,"../assets/uploads/".$file_name);
				$stmt = $db->prepare('INSERT INTO blog_posts (postTitle,postImage,postDesc,postCont,postDate) VALUES (:postTitle,:postImage,:postDesc, :postCont, :postDate)') ;
				$stmt->execute(array(
					':postTitle' => $postTitle,
					':postImage' =>$file_name,
					':postDesc' => $postDesc,
					':postCont' => $postCont,
					':postDate' => date('Y-m-d H:i:s')
				));
				

				//redirect to index page
				header('Location: display.php?action=added');
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

	<form action='' method='post' enctype='multipart/form-data'>


		<div class="row">
			<div class="input-field col s12 m12 l12 xl12"><input id="title"  type="text" name="postTitle" value='<?php if(isset($error)){ echo $_POST['postTitle'];}?>'>
     		 <label for="title" >Title</label>
     		</div>
		</div>

	<div class="file-field input-field">
      <div class="btn">
        <span>Image</span>
        <input type="file" name="imageUpload" id="imageUpload">
      </div>
      <div class="file-path-wrapper">
        <input class="file-path validate" type="text" name="">
      </div>
    </div>


		<div class="row">
        <div class="col s12">
        	<label for="description">Description</label>
          <textarea id="description" name='postDesc' class="materialize-textarea"><?php if(isset($error)){ echo $_POST['postDesc'];}?></textarea>
          
        </div>
      </div>	

      <div class="row">
        <div class="col s12">
        	<label for="content">Detailed Content</label>
          <textarea id="content" name='postCont' class="materialize-textarea"><?php if(isset($error)){ echo $_POST['postCont'];}?></textarea>
          
        </div>
      </div>	

      <center><p><label></label><input class="waves-effect waves-light btn cyan" type="submit" name="submit" value="Submit"  /></p></center>
	
     
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
