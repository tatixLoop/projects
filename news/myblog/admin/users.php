<?php
//include config
require_once('../includes/config.php');

//if not logged in redirect to login page
if(!$user->is_logged_in()){ header('Location: login.php'); }

//show message from add / edit page
if(isset($_GET['deluser'])){ 

	//if user id is 1 ignore
	if($_GET['deluser'] !='1'){

		$stmt = $db->prepare('DELETE FROM blog_members WHERE memberID = :memberID') ;
		$stmt->execute(array(':memberID' => $_GET['deluser']));

		header('Location: users.php?action=deleted');
		exit;

	}
} 

?>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Admin - Users</title>
  <link rel="stylesheet" href="../style/normalize.css">
  <link rel="stylesheet" href="../style/main.css">
 
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>


  <script language="JavaScript" type="text/javascript">
  function deluser(id, title)
  {
	  if (confirm("Are you sure you want to delete '" + title + "'"))
	  {
	  	window.location.href = 'users.php?deluser=' + id;
	  }
  }
  </script>
 <link rel="stylesheet" href="../style/materialize/css/materialize.css">
  <script src="../style/materialize/js/materialize.js"></script>
  
</head>
<body>

	<div id="main_wrapper">

	<?php include('menu.php');?>

	<?php 
	//show message from add / edit page
	if(isset($_GET['action'])){ 
		echo '<div class="container"><h3>User '.$_GET['action'].'.</h3></div>'; 
	} 
	?>

<div class="container">
  	<div class="row">
  		<div class="col s6 m6 l8 xl8"><h1>User Details</h1></div>
  		<div class="col s6 m6 l4 xl4 right-align"><h1 ><a class="btn-floating btn-large cyan pulse" href='add-user.php'><i class="material-icons">group_add</i></a></h1></div>
  		
  	</div>
  	 <div class="divider"></div>
  </div>

<div class="container">
	<table>
	<tr>
		<th>Username</th>
		<th>Email</th>
		<th>Action</th>
	</tr>
	<?php
		try {

			$stmt = $db->query('SELECT memberID, username, email FROM blog_members ORDER BY username');
			while($row = $stmt->fetch()){
				
				echo '<tr>';
				echo '<td>'.$row['username'].'</td>';
				echo '<td>'.$row['email'].'</td>';
				?>

				<td>
					<a href="edit-user.php?id=<?php echo $row['memberID'];?>"><i class="small material-icons">edit</i></a> 
					<?php if($row['memberID'] != 1){?>
						| <a href="javascript:deluser('<?php echo $row['memberID'];?>','<?php echo $row['username'];?>')"><i class="small material-icons">delete</i></a>
					<?php } ?>
				</td>
				
				<?php 
				echo '</tr>';

			}

		} catch(PDOException $e) {
		    echo $e->getMessage();
		}
	?>
	</table>


</div>

</div>
<?php include('../footer.php');  ?>
</body>
<script>
  	 $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>

</html>
