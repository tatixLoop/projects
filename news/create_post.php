<?php
include('connection.php');
$heading_text = "";
$subheading_text = "";
$content_text = "";

if(isset($_FILES['image'])){
      $errors= array();
      $file_name = $_FILES['image']['name'];
      $thumb = $_FILES['image']['name'];
      $file_size =$_FILES['image']['size'];
      $file_tmp =$_FILES['image']['tmp_name'];
      $file_type=$_FILES['image']['type'];
      $file_ext=strtolower(end(explode('.',$_FILES['image']['name'])));

      $expensions= array("jpeg","jpg","png");

      if(in_array($file_ext,$expensions)=== false){
         $errors[]="extension not allowed, please choose a JPEG or PNG file.";
      }

      if($file_size > 2097152){
         $errors[]='File size must be excately 2 MB';
      }

      if(empty($errors)==true){
         move_uploaded_file($file_tmp,"./img/".$file_name);
         echo "Success !!! Thumbnail is uploaded succesfully <br";
      }else{
         print_r($errors);
      }
   }

 if (empty($_POST["heading_text"])) {
    $pgm_nameErr = "Enter the Heading";
  } else {
    $heading_text = test_input($_POST["heading_text"]);
  }
if (!empty($_POST["subheading_text"])) {
    $subheading_text = test_input($_POST["subheading_text"]);
  } 
if (empty($_POST["content_text"])) {
    $pgm_nameErr = "Enter the News content";
  } else {
    $content_text = test_input($_POST["content_text"]);
  }

  if(empty($heading_text))
  {
      
  }
  else if(empty($file_name))
  {
  }
  else if(empty($content_text))
  {
      echo "<br> Please enter Content Text <br>";
  }
  else
  {
	$date = date('Y-m-d H:i:s');
	$query="INSERT INTO table_news (datetime, heading, subheading, contents, views, rating) VALUES ('".$date."','".$heading_text."','".$subheading_text."','".$content_text."',0,0)";

	//echo $query;
        $con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);
        $result = mysqli_query($con,$query);
        if($result)
        {
                // SUCCESS
                $pgm_name = $pgm_live_time = $thumb = $pgm_info = $pgm_timing = "";
		echo "<br>";
		echo "Post Created Successfully";
		echo "<br>";
		$news_id =mysqli_insert_id($con);
		echo "ID is ".$news_id;
		$query="INSERT INTO table_newsimg (id, img) VALUES(".$news_id.",'".$file_name."' )";
		$result = mysqli_query($con,$query);
        }
        else
        {
                //ERROR
                echo "<br>";
                echo "ERRROR IN CREATING POST.";
        }
  }

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>
<?php
session_start();
if(isset($_SESSION['id']))
{
}
else
{
    header("Location: ".$host."/".$appdir."/post.php");
}
?>


<html>
<title>
Create POST
</title>

<body>
<link class="jsbin" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
<meta charset=utf-8 />

</body>
<body>

<h2> Create POST </h2>


<form action="" method="POST" enctype="multipart/form-data">
<table border="0" cellspacing="10" >

<tr>
<td>
HEADING
</td>
<td>
	<textarea name="heading_text" rows="5" cols="40"><?php echo $heading_text;?></textarea>
</td>
</tr>

<tr>
<td>
Discription
</td>
<td>
	<textarea name="subheading_text" rows="5" cols="40"><?php echo $subheading_text;?></textarea>
</td>
</tr>

<tr>
<td>
CONTENTS
</td>
<td>
	<textarea name="content_text" rows="5" cols="40"><?php echo $content_text;?></textarea>
</td>
</tr>

<tr>
<td>
         <input type="submit"/>
</td>
<td>
</td>
</tr>
<tr>
<td>
         <input type="file" name="image" onchange="readURL(this);"/>
</td>
<td>
  <img id="blah" src="#" alt="" />
</td>
</tr>

</table>
</form>


<script>

function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#blah')
                        .attr('src', e.target.result)
                        .width(300)
                        .height(300);
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
</script>


</body>
</html>
