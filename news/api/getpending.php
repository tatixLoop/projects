<?php

   include('../connection.php');
   // array for JSON response
   $response = array();

   $con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $result = mysqli_query($con,"SELECT * FROM table_news WHERE approvals=0");


   if (!empty($result)) {
        // check for empty result
        if (mysqli_num_rows($result) > 0) {

            $response["news"] = array();

            while ($row = mysqli_fetch_array($result)) {
            // temp user array
              $program = array();
              $program["id"] = $row["id"];
              $program["title"] = $row["heading"];
              $program["subheading"] = $row["subheading"];
              $program["contents"] = $row["contents"];

              $resultimg =  mysqli_query($con,"SELECT * FROM table_newsimg WHERE id=".$row["id"]);
              $img_row=mysqli_fetch_array($resultimg);
              $program["image"]= $img_row["img"];

              // push single product into final response array
              array_push($response["news"], $program);
            }
            // success
            $response["success"] = 1;

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no program found
            $response["success"] = 0;
            $response["message"] = "No program found";

            // echo no users JSON
            echo json_encode($response);
        }
   }


   mysqli_close($con);


?>
