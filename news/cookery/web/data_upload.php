<?php
include('../api/connections.php');

$recipe_name = "";
$timeToCook  = "";
$calory      = "";
$serve_count = "";
$author      = "";
$rating      = "";
$type        = "";
$id          = "";
$step1       = "";

$step_array= array();
$step_count = 0;

$ingr_array= array();
$ingr_count = 0;
if(isset($_POST['type']))
{
    $type = $_POST['type'];
}

for ($x = 0; $x < 30; $x++) {
    if(isset($_POST["step".$x]))
    {
        $step_array[$x] = $_POST["step".$x];
        if(!empty($step_array[$x]))
        {
            $step_count++;
        }
    }
}


for ($x = 0; $x < 26; $x++) {
    if(isset($_POST["ingr".$x]))
    {
        $ingr_array[$x] = $_POST["ingr".$x];
        if(!empty($ingr_array[$x]))
        {
            $ingr_count++;
        }
    }
}


if (empty($_POST["recipe_name"])) {
} else {
    $recipe_name = ($_POST["recipe_name"]);
}

if (empty($_POST["timeToCook"])) {
} else {
    $timeToCook = ($_POST["timeToCook"]);
}

if (empty($_POST["calory"])) {
} else {
    $calory = ($_POST["calory"]);
}

if (empty($_POST["serve_count"])) {
} else {
    $serve_count = ($_POST["serve_count"]);
}

if (empty($_POST["author"])) {
} else {
    $author = ($_POST["author"]);
}

if (empty($_POST["rating"])) {
} else {
    $rating = ($_POST["rating"]);
}

if(isset($_FILES["image"]) && isset($_FILES["image_food"])){
    $file_name1 = $_FILES['image']['name'];
    $file_name2 = $_FILES['image_food']['name'];
    $file_tmp1 =$_FILES['image']['tmp_name'];
    $file_tmp2 =$_FILES['image_food']['tmp_name'];
    if(empty($file_name2) || empty($file_name1) || empty($recipe_name) || empty($rating) ||
        empty($author) || empty($serve_count) || empty($timeToCook) || empty($calory))
    {
        echo "Fill Everythinhg </br>";
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

        $query_insert = "INSERT INTO tbl_dishes (type, dishname, img_path) VALUES (".$type.",'".$recipe_name."','".$dir_name."')";
        $result_i=mysqli_query($con,$query_insert);

        $query_insert = "INSERT INTO tbl_dishinfo".
			" (id, calory, cooktimeinsec, serves, author, rating)".
			" VALUES (".$nextid.", ".$calory.", ".$timeToCook.",".$serve_count.", '".$author."', ".$rating.")";
        $result_i=mysqli_query($con,$query_insert);

        $x = $ingr_count;
        while($ingr_count >= 0)
        {
            if($x-$ingr_count == 0)
            {
                $ingr_count--;
                continue;
            }
            $query_insert = "INSERT INTO tbl_ingredients (id, ingredient) VALUES (".$nextid.",'".$ingr_array[$x - $ingr_count]."')";
            $ingr_count--;
            $result_i=mysqli_query($con,$query_insert);
        }

        $x = $step_count;
        while($step_count >= 0)
        {
            if($x-$step_count == 0)
            {
                $step_count--;
                continue;
            }
            $query_insert = "INSERT INTO tbl_steps (id, stepno, step) VALUES (".$nextid.",".($x - $step_count).",'".$step_array[($x - $step_count)]."')";
            $step_count--;
            $result_i=mysqli_query($con,$query_insert);
        }

        mkdir("../img/".$dir_name);
        move_uploaded_file($file_tmp1,"../img/".$dir_name."/box_preview.jpg");
        move_uploaded_file($file_tmp2,"../img/".$dir_name."/title_image.jpg");
	echo "DATA UPLOADED SUCCESSFULLY </br>";
    }
}
?>
<!DOCTYPE html>
<html>
<title> Upload cookery data </title>
<head>
<style>
* {
    box-sizing: border-box;
}

/* Create two equal columns that floats next to each other */
.column {
    float: left;
    width: 30%;
    padding: 10px;
}
.column2 {
    float: left;
    width: 40%;
    padding: 10px;

/* Clear floats after the columns */
.row:after {
    content: "";
    display: table;
    clear: both;
}
</style>
</head>
<body>
<link class="jsbin" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
<meta charset=utf-8 />

	<h1> Fill in Recipe data </h1>
	</br>
	<form action="" method="POST" enctype="multipart/form-data">
<div class="row" >
  <div class="column">
		<table border="0" cellspacing="10" >

		<tr>
			<td> Type  </td>
			<td>  <input type="radio" name="type" value="1" checked> Break Fast </input> 
                              <input type="radio" name="type" value="2"> Lunch  </input>
                              <input type="radio" name="type" value="3"> Snacks </input>
                        </td>
			<td> </td>
		</tr>

		<tr>
			<td> Recipe Name </td>
			<td> <textarea name="recipe_name" rows="2" cols="30" style="overflow:hidden"><?php echo $recipe_name;?></textarea> </td>
			<td> </td>
		</tr>

		<tr>
			<td> Time to cook in seconds </td>
			<td> <textarea name="timeToCook" style="overflow:hidden" rows="2" cols="30"><?php echo $timeToCook;?></textarea> </td>
			<td> </td>
		</tr>

		<tr>
			<td> Calory </td>
			<td> <textarea name="calory" style="overflow:hidden" rows="2" cols="30"><?php echo $calory;?></textarea> </td>
			<td> </td>
		</tr>
		<tr>
			<td> How many people can eat ? </td>
			<td> <textarea name="serve_count" style="overflow:hidden" rows="2" cols="30"><?php echo $serve_count;?></textarea> </td>
			<td> </td>
		</tr>
		<tr>
			<td> Author of Recipe </td>
			<td> <textarea name="author" style="overflow:hidden" rows="2" cols="30"><?php echo $author;?></textarea> </td>
			<td> </td>
		</tr>
		<tr>
			<td> Initial Rating </td>
			<td> <textarea name="rating" style="overflow:hidden" rows="2" cols="30"><?php echo $rating;?></textarea> </td>
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
  </div>
  <div class="column2" style="overflow-y: scroll; height:400px;" >
	<table border="0" cellspacing="1" >
		<tr>
			<td> Steps        </td>
		</tr>
		<tr>
			<td> Step 1: </td>
			<td> <textarea name="step1" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 2: </td>
			<td> <textarea name="step2" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 3: </td>
			<td> <textarea name="step3" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 4: </td>
			<td> <textarea name="step4" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 5: </td>
			<td> <textarea name="step5" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 6: </td>
			<td> <textarea name="step6" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 7: </td>
			<td> <textarea name="step7" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 8: </td>
			<td> <textarea name="step8" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 9: </td>
			<td> <textarea name="step9" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 10: </td>
			<td> <textarea name="step10" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 11: </td>
			<td> <textarea name="step11" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 12: </td>
			<td> <textarea name="step12" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 13: </td>
			<td> <textarea name="step13" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 14: </td>
			<td> <textarea name="step14" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 15: </td>
			<td> <textarea name="step15" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 16: </td>
			<td> <textarea name="step16" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 17: </td>
			<td> <textarea name="step17" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 18: </td>
			<td> <textarea name="step18" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 19: </td>
			<td> <textarea name="step19" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 20: </td>
			<td> <textarea name="step20" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 21: </td>
			<td> <textarea name="step21" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 22: </td>
			<td> <textarea name="step22" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 23: </td>
			<td> <textarea name="step23" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 24: </td>
			<td> <textarea name="step24" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 25: </td>
			<td> <textarea name="step25" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 26: </td>
			<td> <textarea name="step26" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 27: </td>
			<td> <textarea name="step27" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 28: </td>
			<td> <textarea name="step28" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 29: </td>
			<td> <textarea name="step29" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
		<tr>
			<td> Step 30: </td>
			<td> <textarea name="step30" style="overflow:hidden" rows="2" cols="50"></textarea> </td>
		</tr>
	</table>
  </div>
  <div class="column" style="overflow-y: scroll; height:400px;" >
	<table border="0" cellspacing="1" >
		<tr>
			<td> Ingredients </td>
		</tr>
		<tr>
			<td> <textarea name="ingr1" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr2" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr3" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr4" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr5" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr6" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr7" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr8" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr9" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr10" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr11" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr12" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr13" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr14" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr15" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr16" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr17" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr18" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr19" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr20" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr21" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr22" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr23" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr24" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr25" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
		<tr>
			<td> <textarea name="ingr26" style="overflow:hidden" rows="2" cols="40"></textarea> </td>
		</tr>
	</table>
  </div>
</div>
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
