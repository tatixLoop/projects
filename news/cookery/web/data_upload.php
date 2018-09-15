<?php
include('../api/connections.php');

$recipe_name = "";
$timeToCook  = "";
$calory      = "";
$serve_count = "";
$author      = "";
$rating      = "";
$id          = "";


if(isset($_FILES["image"]) && isset($_FILES["image_food"])){
    $file_name1 = $_FILES['image']['name'];
    $file_name2 = $_FILES['image_food']['name'];
    $file_tmp1 =$_FILES['image']['tmp_name'];
    $file_tmp2 =$_FILES['image_food']['tmp_name'];
    if(empty($file_name2) || empty($file_name1))
    {
        echo "File not selected</br>";
    }
    else
    {
        // fetch max Id
        $con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);
        $query = "SELECT max(id) from tbl_dishes";
        $result_i=mysqli_query($con,$query);
        $row=mysqli_fetch_array($result_i);
        $nextid=$row['max(id)']+1;
        $dir_name = "recipe_".$nextid;
        mkdir("../img/".$dir_name);
        move_uploaded_file($file_tmp1,"../img/".$dir_name."/box_preview.jpg");
        move_uploaded_file($file_tmp2,"../img/".$dir_name."/title_image.jpg");
    }
}
?>
<!DOCTYPE html>
<html>
<title> Upload cookery data </title>
<body>
<link class="jsbin" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
<meta charset=utf-8 />

	<h1> Fill in Recipe data </h1>
	</br>
	<form action="" method="POST" enctype="multipart/form-data">
		<table border="0" cellspacing="10" >

		<tr>
			<td> Recipe Name </td>
			<td> <textarea name="recipe_name" rows="2" cols="40" style="overflow:hidden"><?php echo $recipe_name;?></textarea> </td>
			<td> </td>
		</tr>

		<tr>
			<td> Time to cook in seconds </td>
			<td> <textarea name="timeToCook" style="overflow:hidden" rows="2" cols="40"><?php echo $timeToCook;?></textarea> </td>
			<td> </td>
		</tr>

		<tr>
			<td> Calory </td>
			<td> <textarea name="calory" style="overflow:hidden" rows="2" cols="40"><?php echo $calory;?></textarea> </td>
			<td> </td>
		</tr>
		<tr>
			<td> How many people can eat ? </td>
			<td> <textarea name="serve_count" style="overflow:hidden" rows="2" cols="40"><?php echo $serve_count;?></textarea> </td>
			<td> </td>
		</tr>
		<tr>
			<td> Author of Recipe </td>
			<td> <textarea name="author" style="overflow:hidden" rows="2" cols="40"><?php echo $author;?></textarea> </td>
			<td> </td>
		</tr>
		<tr>
			<td> Initial Rating </td>
			<td> <textarea name="rating" style="overflow:hidden" rows="2" cols="40"><?php echo $rating;?></textarea> </td>
			<td> </td>
		</tr>


		<tr>
			<td> Upload Preview image </td>
			<td>  <input type="file" name="image" onchange="readURL(this);"/> </td>
			<td>  <img id="blah" src="#" alt="" /> </td>
		</tr>
		<tr>
			<td> Upload Food image </td>
			<td>  <input type="file" name="image_food" onchange="readURL_FOOD(this);"/> </td>
			<td>  <img id="blah_food" src="#" alt="" /> </td>
		</tr>


		<tr>
			<td> <input type="submit"/> </td>
			<td> </td>
		</tr>

		</table>
	</form>

<script>

function readURL_FOOD(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#blah_food')
                        .attr('src', e.target.result)
                        .width(300)
                        .height(100);
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#blah')
                        .attr('src', e.target.result)
                        .width(100)
                        .height(100);
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
</script>

</body>
</html>
