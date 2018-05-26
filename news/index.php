<!DOCTYPE html>
<?php
include ('connection.php')
?>
<html>
<head>
<link rel="stylesheet" href="main.css">
	<?php
	$con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);
	?>
	<?php
		$result_img = mysqli_query($con,"SELECT * FROM table_newsimg where id=(SELECT max(id) from table_news)");
		$row_img_tag = mysqli_fetch_array($result_img);
		$img_preview=$row_img_tag['img'];
		$result_p = mysqli_query($con,"SELECT * FROM `table_news` WHERE id=".$row_img_tag['id']);
		$row_ptag = mysqli_fetch_array($result_p);
		$title=$row_ptag['heading'];

	?>
	
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta property="og:url" content="http://tatixtech.com/<?php echo $appdir; ?>/view_post/?id=<?php echo $id ?>" />         
<meta property="og:type" content="article" />
<meta property="og:site_name" content="tatixTech" />
<meta property="og:title" content="<?php echo $title; ?>" />       
<meta property="og:description" content="<?php echo $title; ?>" />  
<meta property="og:image" content="<?php echo "http://tatixtech.com/".$appdir."/img/".$img_preview; ?>"/>
<meta property="og:image:width" content="200" />
<meta property="og:image:height" content="200" />


<style>
* {
  box-sizing: border-box;
}
.menu {
  float:left;
  width:30%;
  text-align:center;
}

.menu a {
  
  padding:8px;
  margin-top:7px;
  display:block;
  width:100%;
  color:black;
}
.main {
  float:left;
  width:70%;
  padding:0 20px;
}
.right {
  background-color:#e5e5e5;
  float:left;
  width:20%;
  padding:15px;
  margin-top:7px;
  text-align:center;
}

@media only screen and (max-width:620px) {
  /* For mobile phones: */
  .menu, .main, .right {
    width:100%;
  }
}
h1 {
    color: white;
}
</style>
</head>
<body style="font-family:Verdana;color:#aaaaaa;">

<div style="background-color:#4ac475;padding:15px;text-align:center;">
<a href='<?php echo $host?>/<?php echo $appdir; ?>/' style="text-decoration:none;"><h1>Good News</h1> </a>
</div>

<div style="overflow:auto">
  
<?php

	$result = mysqli_query($con,"SELECT * FROM `table_news` WHERE id=(SELECT max(id) from table_news)");
	while($row = mysqli_fetch_array($result))
	{
		$id = $row['id'];	

?>
  <div class="main">
    <h2><?php  echo $row['heading']; ?></h2>
author  | date
</br>
</br>
<?php
		$result_img = mysqli_query($con,"SELECT * FROM table_newsimg where id=".$id);
		$row_img = mysqli_fetch_array($result_img);
		$img=$row_img['img'];

		?>

		<img src="<?php echo "img/".$img; ?>"  style="display:inline" alt="<?php echo "img/".$img; ?>" />
		
    <p><?php  echo $row['contents']; ?></p>
  </div>
<?php
}

?>
 <div class="menu">

<div class="bod">
<?php
        $result = mysqli_query($con,"SELECT * FROM table_news");
	while($row = mysqli_fetch_array($result))
	{
		$id = $row['id'];
?>
		
		<div class="box">
  

		<?php
		$result_img = mysqli_query($con,"SELECT * FROM table_newsimg where id=".$id);
		$row_img = mysqli_fetch_array($result_img);
		$img=$row_img['img'];

		?>

		<img src="<?php echo "img/".$img; ?>"  style="display:inline" alt="<?php echo "img/".$img; ?>" />
		
		
			<div class="container">
			    <a href='<?php echo $host?>/<?php echo $appdir; ?>/view_post/?id=<?php echo $id ?>' style="text-decoration:none;">
				<?php
				echo $row['heading'];
				?>
		           </a>
			</div>
		</div>
	


<?php
	}
?>
  </div>
  </div>

</div>

</body>
</html>

