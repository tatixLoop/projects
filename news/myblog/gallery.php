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


		<div class="row"><h2 class="h2style_hd center col s12 m12 l12 xl12">Gallery</h2></div>
 <?php
			try {
				echo '<div class="row">';
				$stmt = $db->query('SELECT img_id, postImage FROM gallery_upload ORDER BY img_id DESC');
				while($row = $stmt->fetch()){
					
					
					echo '<div class ="col s6 m3 l3 xl3 inner-images" style="width:250px;height:250px;">';
						echo '<img src="assets/gallery/'.$row['postImage'].'">';
						echo '</div>';
						
					}
				echo '</div>';

			} catch(PDOException $e) {
			    echo $e->getMessage();
			}
		?>







</div>
<!-- Footer -->
<?php include('footer.php');  ?>

</body>
<script>
  	 $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>
</html>
