<?php //include config
require_once('../includes/config.php');

//if not logged in redirect to login page
if(!$user->is_logged_in()){ header('Location: login.php'); }
?>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Admin - Edit Post</title>
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
	<h2>Edit Post</h2></div>
    </div></div>
	


	<?php

	//if form has been submitted process it
	if(isset($_POST['submit'])){

		$_POST = array_map( 'stripslashes', $_POST );

		//collect form data
		extract($_POST);

		//very basic validation
		if($id ==''){
			$error[] = 'This post is missing a valid id!.';
		}

		if($title ==''){
			$error[] = 'Please enter the title.';
		}

		if($Description ==''){
			$error[] = 'Please enter the description.';
		}

		if($content ==''){
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
      
			
			if($file_name ==''){
			$error[] = 'Please Upload image.';
		}


		if(!isset($error)){

			try {

		

		try {

			$stmt = $db->prepare('SELECT id, imgdir, title, Description, content FROM news_tbl_datainput WHERE id = :id') ;
			$stmt->execute(array(':id' => $_GET['id']));
			$row = $stmt->fetch(); 
			$del_name=$row['imgdir'];

		} catch(PDOException $e) {
		    echo $e->getMessage();
		}         
			if ($check_name != NULL) {
			
		
                 //Delete old image from the Folder 
				  unlink("../assets/uploads/".$del_name);
				//upload new image to the Folder  
				  move_uploaded_file($file_tmp,"../assets/uploads/".$file_name);

				  //insert into database
				$stmt = $db->prepare('UPDATE news_tbl_datainput SET title = :title, imgdir = :imgdir, Description = :Description, content = :content WHERE id = :id') ;
				$stmt->execute(array(
					':title' => $title,
					':imgdir' =>$file_name,
					':Description' => $Description,
					':content' => $content,
					':id' => $id
				));

				//redirect to index page
				header('Location: display.php?action=updated');
				exit;
					}

				else {
						
					
				//insert into database
				$stmt = $db->prepare('UPDATE news_tbl_datainput SET title = :title, Description = :Description, content = :content WHERE id = :id') ;
				$stmt->execute(array(
					':title' => $title,
					':Description' => $Description,
					':content' => $content,
					':id' => $id
				));

				//redirect to index page
				header('Location: display.php?action=updated');
				exit;
				}	

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

			$stmt = $db->prepare('SELECT id, imgdir, title, Description, content FROM news_tbl_datainput WHERE id = :id') ;
			$stmt->execute(array(':id' => $_GET['id']));
			$row = $stmt->fetch(); 
			

		} catch(PDOException $e) {
		    echo $e->getMessage();
		}

	?>



	<div class="container">

	<form action='' method='post' enctype='multipart/form-data'>
		<input type='hidden' name='id' value='<?php echo $row['id'];?>'>

		<div class="row">
			<div class="input-field col s12 m12 l12 xl12"><input id="title" type="text" name="title" value='<?php echo $row['title'];?>'>
				<label for="title">Title</label>
     		</div>
		</div>
<div class="row">
    <img src="../assets/uploads/<?php echo $row['imgdir'];?>" width='25%'; height='25%';>
		<div class="file-field input-field">
      <div class="btn">
        <span>Image</span>
        <input type="file" name="imageUpload" id="imageUpload">
      </div>
      <div class="file-path-wrapper">
        <input class="file-path validate" type="text" value='<?php echo $row['imgdir'];?>'>
      </div>
    </div>
 </div>   

		<div class="row">
        <div class="col s12">
        	<label for="description">Description</label>
          <textarea id="description" name='Description' class="materialize-textarea"><?php echo $row['Description'];?></textarea>
          
        </div>
      </div>	

      <div class="row">
        <div class="col s12">
        	<label for="content">Detailed Content</label>
          <textarea id="content" name='content' class="materialize-textarea"><?php echo $row['content'];?></textarea>
          
        </div>
      </div>	

      <center><p><label></label><input class="waves-effect waves-light btn cyan" type="submit" name="submit" value="Submit" /></p></center>
	
     
	</form>


</div>
<?php include('../footer.php');  ?>
</body>

<script>
  	 $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>
</html>	
