
<div class="navbar-fixed">
<nav>
    <div class="nav-wrapper">
      <a href="#!" class="brand-logo center"><img src="../assets/images/logo_arpo.png" class="logo_img"></a>
      <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>
      <ul class="right hide-on-med-and-down" id='adminmenu'>
       <li><a href='index.php'>Articles</a></li>
       <li><a href="gallery.php">Gallery</a></li>
<?php
if ($_SESSION['access'] == 15)
{
?>
	<li><a href='users.php'>Users</a></li>
<?php
}
?>
	<li><a href="../" target="_blank">View Website</a></li>
	<li><a href='logout.php'>Logout</a></li>
      </ul>
    </div>
  </nav>
  </div>

  <ul class="sidenav" id="mobile-demo">
    <li><a href='index.php'>Articles</a></li>
    <li><a href="gallery.php">Gallery</a></li>
<?php
if ($_SESSION['access'] == 15)
{
?>
	<li><a href='users.php'>Users</a></li>
<?php
}
?>
	<li><a href="../" target="_blank">View Website</a></li>
	<li><a href='logout.php'>Logout</a></li>
  </ul>
    <div class='clear'></div>      
   
