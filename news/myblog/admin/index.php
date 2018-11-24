<?php
//include config
require_once('../includes/config.php');

//if not logged in redirect to login page
if(!$user->is_logged_in()){ header('Location: login.php'); }

//Delete blog on delete button click
if(isset($_GET['delpost'])){ 

	try{
     $stmtd = $db->prepare('SELECT id, postImage, title, Description, postCont FROM blog_posts WHERE id = :id') ;
			$stmtd->execute(array(':id' => $_GET['delpost']));
			$row = $stmtd->fetch(); 
			$del_name=$row['postImage'];

		} catch(PDOException $e) {
		    echo $e->getMessage();
		}
                 //Delete old image from the Folder 
				  unlink("../assets/uploads/".$del_name);


	$stmt = $db->prepare('DELETE FROM blog_posts WHERE id = :id') ;
	$stmt->execute(array(':id' => $_GET['delpost']));

	header('Location: index.php?action=deleted');
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

 
<!-- DELETE request -->
  <script language="JavaScript" type="text/javascript">
  function delpost(id, title)
  {
	  if (confirm("Are you sure you want to delete '" + title + "'"))
	  {
	  	window.location.href = 'index.php?delpost=' + id;
	  }
  }
  </script>
</head>
<body>

	<div id="main_wrapper">

	<?php include('menu.php');?>

	

  <div class="container">
  	<div class="row">
  		<div class="col s6 m6 l8 xl8"><h1>Article Details</h1></div>
  		<div class="col s6 m6 l4 xl4 right-align"><h1 ><a class="btn-floating btn-large cyan pulse" href='add-post.php'><i class="material-icons">add</i></a></h1></div>
  		
  	</div>
  	 <div class="divider"></div>
  </div>

<!-- Display -->

 <div class="container">

<?php
			try {

				$stmt = $db->query('SELECT id, title, Description, date FROM news_tbl_datainput ORDER BY id DESC');
				while($row = $stmt->fetch()){
					
					echo '<div class="row">';
					echo '<div class ="col s12 m6 l6 xl6 ">';
						echo '<h5><a href="">'.$row['title'].'</a></h3>';
						echo '</div>';
						echo '<div class ="col s12 m3 l3 xl3 ">';
						echo '<p>Created on '.date('jS M Y H:i:s', strtotime($row['date'])).'</p>';
						echo '</div>';
						echo '<div class ="col s12 m3 l3 xl3 right-align">';
						?>
						<a href="edit-post.php?id=<?php echo $row['id'];?>"><i class="small material-icons">edit</i></a>  
						<a href="javascript:delpost('<?php echo $row['id'];?>','<?php echo $row['title'];?>')"><i class="small material-icons">delete</i></a>
						<?php
						echo '</div>';
					echo '</div>';	
					echo '<div class="divider"></div>';
					
                   
				}

			} catch(PDOException $e) {
			    echo $e->getMessage();
			}
		?>


	
</div>
    	<!-- Footer -->
<?php include('../footer.php');  ?>

</div>

</body>
 <script>
  	 $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>

</html>
