<?php require('includes/config.php'); ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Rohit Chennithala Musings</title>
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
	

		<!-- Slider -->

     <div class="blue-grey lighten-5 dark-text div1" href="#">
     <div class="row ">	
     	<div class="col s12 m6 l5 xl5 center ">
      <h2 class="h2style">ROHIT CHENNITHALA</h2>
      <p class="dark-text">RADIOLOGIST | WANNABE WRITER  </p>
      </div>
      <div class="col s12 m6 l7 xl7 center inner-images">
      	<img src="assets/images/slider.png">
      </div>
      </div>
   
    
  </div>


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



		<!-- Blog Latest -->


		<div class="row"><h2 class="h2style_hd center col s12 m12 l12 xl12">LATEST MUSING</h2></div>


<?php
			try {

				$stmt = $db->query('SELECT postID, postImage, postTitle, postDesc, postDate FROM blog_posts ORDER BY postID DESC LIMIT 1');
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
					

				}

			} catch(PDOException $e) {
			    echo $e->getMessage();
			}
		?>
<div class="divider"></div>



        <!-- Blog recent 5 Content -->


        <div class="row"><h2 class="h2style_hd center col s12 m12 l12 xl12">RECENT MUSINGS</h2></div>

   

		<?php
			try {

				$stmt = $db->query('SELECT postID, postImage, postTitle, postDesc, postDate FROM blog_posts ORDER BY postID DESC LIMIT 5');
				echo '<div class="carousel" >';
				while($row = $stmt->fetch()){
					
						
						echo '<a class="carousel-item" href="viewpost.php?id='.$row['postID'].'">';
						echo '<h5 class="truncate h2style_tl">'.$row['postTitle'].'</h5>';
						echo'<img src="assets/uploads/'.$row['postImage'].'">';
						echo '<p class="pdate">Posted on '.date('jS M Y ', strtotime($row['postDate'])).'</p>';
						echo'</a>';
						
									
									}	
					echo '</div>';
					

				

			} catch(PDOException $e) {
			    echo $e->getMessage();
			}
		?>

<div class="container center"><a class="waves-effect waves-light btn-large hov" href="blog.php">More</a></div>
<div class="section"></div>

		<!-- Footer -->
<?php include('footer.php');  ?>

	</div>


</body>

 <script>
	 $('.carousel.carousel-slider').carousel({
    fullWidth: true
  });
       
</script>

<script>
	$(document).ready(function(){
    $('.carousel').carousel();
  });
</script>

 <script>
  	 $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>

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