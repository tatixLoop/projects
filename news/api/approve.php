<?php

   include('../connection.php');
   // array for JSON response
   $response = array();

   $con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $id=$_GET['id'];
   $query="UPDATE table_news set approvals=approvals+1 where id=".$id;
   echo $query;
   $result = mysqli_query($con,$query);


   if (!empty($result)) {
            $response["success"] = 1;

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no program found
            $response["success"] = 0;

            // echo no users JSON
            echo json_encode($response);
        }

   mysqli_close($con);


?>
