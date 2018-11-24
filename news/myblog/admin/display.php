<?php
//include config
require_once('../includes/config.php');

//if not logged in redirect to login page
if(!$user->is_logged_in()){ header('Location: login.php'); }



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
</head>
<body>

  <div id="main_wrapper">

  <?php include('menu.php');?>

  <?php 
  //show message from add / edit page
  if(isset($_GET['action'])){ 
    echo '<div class="container"> ';
    echo '<div class="row"> ';
    echo '<div class="col s8 m8 l8 xl8"><h3>Post '.$_GET['action'].'.</h3></div>'; 
    echo '<div class="col s4 m4 l4 xl4"><h3 ><a class="btn-floating btn-large cyan pulse" href="index.php"><i class="material-icons">navigate_next</i></a></h3></div>';
    echo '</div>';
    echo '</div>';
  } 
  ?>



</div>
<?php include('../footer.php');  ?>
</body>
<script>
     $(document).ready(function(){
    $('.sidenav').sidenav();
  });
        
  </script>

</html>