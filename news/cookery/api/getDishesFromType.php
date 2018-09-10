<?php

   include('connections.php');
   // array for JSON response
   $response = array();

   $type = $_GET['type'];
   $con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $result = mysqli_query($con,"SELECT * FROM tbl_dishes WHERE type=".$type);


   if (!empty($result)) {
        // check for empty result
        if (mysqli_num_rows($result) > 0) {

            $response["dishes"] = array();

            while ($row = mysqli_fetch_array($result)) {
            // temp user array
              $program = array();
              $program["id"] = $row["id"];
              $program["type"] = $row["type"];
              $program["dishname"] = $row["dishname"];
              $program["img_path"] = $row["img_path"];

         
              // push single product into final response array
              array_push($response["dishes"], $program);
            }
            // success
            $response["success"] = 1;

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no program found
            $response["success"] = 0;
            $response["message"] = "No dishes found";

            // echo no users JSON
            echo json_encode($response);
        }
   }


   mysqli_close($con);


?>
