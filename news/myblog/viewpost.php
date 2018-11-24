<?php require('includes/config.php'); 

$stmt = $db->prepare('SELECT postID, postImage, postTitle, postCont, postDate FROM blog_posts WHERE postID = :postID');
$stmt->execute(array(':postID' => $_GET['id']));
$row = $stmt->fetch();

//if post does not exists redirect user.
if($row['postID'] == ''){
	header('Location: ./');
	exit;
}

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>RC - <?php echo $row['postTitle'];?></title>
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
	



		<?php	
			echo '<div class="row">';
			echo '<div class="col s12 m10 l10 xl10 offset-xl1 offset-m1 offset-l1">';
				echo '<h1 class="h2style_tl">'.$row['postTitle'].'</h1>';
				echo '<p class="pdate">Posted on '.date('jS M Y', strtotime($row['postDate'])).'</p>';
			echo '</div>';	
			echo '</div>';	
			echo '<div class="row">';
			echo '<div class="col s12 m6 l6 xl6 offset-m3 offset-l3 offset-xl3 inner-images">';
			echo '<img src="assets/uploads/'.$row['postImage'].'">';
			echo '</div>';
			echo '</div>';	
			echo '<div class="row">';
			echo '<div class="col s12 m10 l10 xl10 offset-m1 offset-l1 offset-xl1">';		
				echo '<p>'.nl2br($row['postCont']).'</p>';	
				echo '</div>';	
			echo '</div>';
		?>



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