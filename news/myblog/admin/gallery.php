<?php
//include config
require_once('../includes/config.php');

//if not logged in redirect to login page
if(!$user->is_logged_in()){ header('Location: login.php'); }

//show message from add / edit page
if(isset($_GET['delpost'])){ 

	try{
     $stmtd = $db->prepare('SELECT img_id, postImage FROM gallery_upload WHERE img_id = :postID') ;
			$stmtd->execute(array(':postID' => $_GET['delpost']));
			$row = $stmtd->fetch(); 
			$del_name=$row['postImage'];

		} catch(PDOException $e) {
		    echo $e->getMessage();
		}
                 //Delete old image from the Folder 
				  unlink("../assets/gallery/".$del_name);


	$stmt = $db->prepare('DELETE FROM gallery_upload WHERE img_id = :postID') ;
	$stmt->execute(array(':postID' => $_GET['delpost']));

	header('Location: gallery.php?action=deleted');
	exit;
} 

?>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Admin</title>
  <link rel="stylesheet" href="../style/normalize.css">
  <link rel="stylesheet" href="../style/main.css">
 
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

   <link rel="stylesheet" href="../style/materialize/css/materialize.css">
  <script src="../style/materialize/js/materialize.js"></script>

 

  <script language="JavaScript" type="text/javascript">
  function delpost(id, title)
  {
	  if (confirm("Are you sure you want to delete '" + title + "'"))
	  {
	  	window.location.href = 'gallery.php?delpost=' + id;
	  }
  }
  </script>
</head>
<body>

	<div id="main_wrapper">
<!-- Menu -->
	<?php include('menu.php');?>

 <?php 
  //show message from delete page
  if(isset($_GET['action'])){ 
    echo '<div class="container">';
    echo '<div class="row"> ';
    echo '<div class="col s8 m8 l8 xl8"><h3>Image '.$_GET['action'].'.</h3></div>'; 
    echo '<div class="col s4 m4 l4 xl4"><h3 ><a class="btn-floating btn-large cyan pulse" href="gallery.php"><i class="material-icons">navigate_next</i></a></h3></div>';
    echo '</div>';
    echo '</div>';
  } 
  ?>

	

  	<div class="row">
  		<div class="col s12 m12 l12 xl12 center"><h1>Gallery</h1>
  		</div>
  	</div>
<?php

	//if form has been submitted process it
	if(isset($_POST['submit'])){

		$_POST = array_map( 'stripslashes', $_POST );

		//collect form data
		extract($_POST);

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
				 move_uploaded_file($file_tmp,"../assets/gallery/".$file_name);
				$stmt = $db->prepare('INSERT INTO gallery_upload (postImage) VALUES (:postImage)') ;
				$stmt->execute(array(
					':postImage' =>$file_name
				));
				

				//redirect to index page
				header('Location: gallery.php?action=Uploaded');
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

	<label>UPLOAD IMAGE</label>
	<div class="file-field input-field">
      <div class="btn">
        <span>Image</span>
        <input type="file" name="imageUpload" id="imageUpload">
      </div>
      <div class="file-path-wrapper">
        <input class="file-path validate" type="text" name="">
      </div>
    </div>

 <center><p><label></label><input class="waves-effect waves-light btn cyan" type="submit" name="submit" value="Submit"  /></p></center>
	
     
	</form>
</div>
<div class="divider"></div>
<div class="section"></div>

    <?php
			try {
				echo '<div class="row">';
				echo '<label class="col s12 m12 l12 xl12 center">DISPLAY IMAGES</label>';
				echo '</div>';
				echo '<div class="section"></div>';
				echo '<div class="row">';
				$stmt = $db->query('SELECT img_id, postImage FROM gallery_upload ORDER BY img_id DESC');
				while($row = $stmt->fetch()){
					
					
					echo '<div class ="col s6 m2 l2 xl2 inner-images" style="width:200px;height:200px;">';
						echo '<img src="../assets/gallery/'.$row['postImage'].'">';
						echo '</div>';
						echo '<div class ="col s6 m1 l1 xl1 left-align">';
						?> 
						<a href="javascript:delpost('<?php echo $row['img_id'];?>','<?php echo $row['postImage'];?>')"><i class="small material-icons">delete</i></a>
						<?php
						echo '</div>';
						
					}
				echo '</div>';

			} catch(PDOException $e) {
			    echo $e->getMessage();
			}
		?>



<?php include('../footer.php');  ?>
</div>
<!-- Footer -->





</body>
<script>
  	 $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>
</html>