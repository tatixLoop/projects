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

for ($x = 0; $x < 30; $x++) {
    if(isset($_GET["step".$x]))
    {
        $step_array[$x] = $_GET["step".$x];
        if(!empty($step_array[$x]))
        {
            $step_count++;
        }
    }
}

for ($x = 0; $x < 26; $x++) {
    if(isset($_GET["ingr".$x]))
    {
        $ingr_array[$x] = $_GET["ingr".$x];
        if(!empty($ingr_array[$x]))
        {
            $ingr_count++;
        }
    }
}


if (empty($_GET["type"])) {
} else {
    $type = ($_GET["type"]);
}

if (empty($_GET["recipe_name"])) {
} else {
    $recipe_name = ($_GET["recipe_name"]);
}

if (empty($_GET["timeToCook"])) {
} else {
    $timeToCook = ($_GET["timeToCook"]);
}

if (empty($_GET["calory"])) {
} else {
    $calory = ($_GET["calory"]);
}

if (empty($_GET["serve_count"])) {
} else {
    $serve_count = ($_GET["serve_count"]);
}

if (empty($_GET["author"])) {
} else {
    $author = ($_GET["author"]);
}

if (empty($_GET["rating"])) {
} else {
    $rating = ($_GET["rating"]);
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

//        $query_insert = "INSERT INTO tbl_dishes (type, dishname, img_path) VALUES (".$type.",'".$recipe_name."','".$dir_name."')";
        $query_insert = "INSERT INTO tbl_dishes (type, dishname, img_path, calory, cooktimeinsec, serves, author, rating) VALUES ".
		 "(".$type.",'".$recipe_name."','".$dir_name."', ".$calory.", ".$timeToCook.",".$serve_count.", '".$author."', ".$rating.")";
        $result_i=mysqli_query($con,$query_insert);

//        $query_insert = "INSERT INTO tbl_dishinfo".
//			" (id, calory, cooktimeinsec, serves, author, rating)".
//			" VALUES (".$nextid.", ".$calory.", ".$timeToCook.",".$serve_count.", '".$author."', ".$rating.")";
//        $result_i=mysqli_query($con,$query_insert);

        $x = $ingr_count;
        while($ingr_count > 0)
        {
            $query_insert = "INSERT INTO tbl_ingredients (id, ingredient) VALUES (".$nextid.",'".$ingr_array[$x - $ingr_count]."')";
            $ingr_count--;
            $result_i=mysqli_query($con,$query_insert);
        }

        $x = $step_count;
        while($step_count > 0)
        {
            $query_insert = "INSERT INTO tbl_steps (id, stepno, step) VALUES (".$nextid.",".($x - $step_count + 1).",'".$step_array[($x - $step_count)]."')";
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
