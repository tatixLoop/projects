<?php
include('connection.php');
$uname = "";
$pwd = "";
$email="";


  if (empty($_POST["uname"])) {
    $pgm_nameErr = "Enter the Heading";
  } else {
    $uname = ($_POST["uname"]);
    $pwd=$uname;
  }

  if (!empty($_POST["email"])) {
    $email = ($_POST["email"]);
  } 


  if(empty($uname))
  {
  }
  else if(empty($pwd))
  {
  }
  else
  {
	$date = date('Y-m-d H:i:s');
	$query="INSERT INTO table_user (username, password, firstlogin, nickname,email) VALUES ('".$uname."','".$pwd."', 1 ,'".$uname."','".$email."')";

	//echo $query;
        $con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);
        $result = mysqli_query($con,$query);
        if($result)
        {

        }
        else
        {
                echo "<br>";
                echo "ERRROR IN CREATING POST.";
                echo "<br>";
        }
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
Create User
</title>

<body>
<link class="jsbin" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
<meta charset=utf-8 />

</body>
<body>

<h2> Create User </h2>


<form action="" method="POST" enctype="multipart/form-data">
<table border="0" cellspacing="10" >

<tr>
<td>
Username
</td>
<td>
	<textarea name="uname" rows="1" cols="40"><?php echo $uname;?></textarea>
</td>
</tr>

<tr>
<td>
email
</td>
<td>
        <textarea name="email" rows="1" cols="40"><?php echo $email;?></textarea>
</td>
</tr>


<tr>
<td>
         <input type="submit"/>
</td>
<td>
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
