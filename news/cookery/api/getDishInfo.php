<?php

   include('connections.php');
   // array for JSON response
   $response = array();

   $id = $_GET['id'];
   $con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $result = mysqli_query($con,"SELECT * FROM tbl_dishinfo WHERE id=".$id);


   if (!empty($result)) {
        // check for empty result
        if (mysqli_num_rows($result) > 0) {

            $response["dishinfo"] = array();

            while ($row = mysqli_fetch_array($result)) {
            // temp user array
              $program = array();
              $program["id"] = $row["id"];
              $program["cooktimeinsec"] = $row["cooktimeinsec"];
              $program["calory"] = $row["calory"];
              $program["serves"] = $row["serves"];
              $program["author"] = $row["author"];
              $program["rating"] = $row["rating"];

              // push single product into final response array
              array_push($response["dishinfo"], $program);
            }

            $result_ret = mysqli_query($con,"SELECT * FROM tbl_ingredients WHERE id=".$id);
            if (!empty($result_ret)) {
                // check for empty result
                if (mysqli_num_rows($result_ret) > 0) {
                    $response["ingredients"] = array();

                    while ($row = mysqli_fetch_array($result_ret)) {
                    // temp user array
                        $program = array();
                        $program["ingredient"] = $row["ingredient"];

                       // push single product into final response array
                       array_push($response["ingredients"], $program);
                     }
                 }
            }

            $result_step = mysqli_query($con,"SELECT * FROM tbl_steps WHERE id=".$id);
            if (!empty($result_step)) {
                // check for empty result
                if (mysqli_num_rows($result_step) > 0) {
                    $response["steps"] = array();

                    while ($row = mysqli_fetch_array($result_step)) {
                    // temp user array
                        $program = array();
                        $program["stepno"] = $row["stepno"];
                        $program["step"] = $row["step"];

                       // push single product into final response array
                       array_push($response["steps"], $program);
                     }
                 }
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
