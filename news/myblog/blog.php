<?php require('includes/config.php'); ?>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>RC Musings</title>

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


<!-- Musings Latest -->


		<div class="row"><h2 class="h2style_hd center col s12 m12 l12 xl12">MUSINGS..</h2></div>


<?php
			try {

				$stmt = $db->query('SELECT postID, postImage, postTitle, postDesc, postDate FROM blog_posts ORDER BY postID DESC ');
				while($row = $stmt->fetch()){
					
					echo '<div class="row " >';
					echo'<div class="col s12 m4 l4 xl4 offset-m1 offset-l1 offset-xl1 inner-images">';
					echo '<a class="h2style_tl" href="viewpost.php?id='.$row['postID'].'"><img src="assets/uploads/'.$row['postImage'].'"></a>';
					echo'</div>';
					echo '<div class="col s12 m6 l6 xl6  left-align">';
						echo '<h3 ><a class="h2style_tl" href="viewpost.php?id='.$row['postID'].'">'.$row['postTitle'].'</a></h3>';
						echo '<p class="pdate">Posted on '.date('jS M Y ', strtotime($row['postDate'])).'</p>';
						echo '<p>'.$row['postDesc'].'</p>';				
						echo '<p><a class="hover_a" href="viewpost.php?id='.$row['postID'].'">Read More</a></p>';				
					echo '</div></div>';
					echo '<div class="divider"></div>';
					

				}

			} catch(PDOException $e) {
			    echo $e->getMessage();
			}
		?>



	<!-- Footer -->
<?php include('footer.php');  ?>


</div>
<script>
  	 $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>

</html>