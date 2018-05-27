<?php
include('connection.php');
$uname="";
$pwd="";
$userid=-1;
$valid=0;

if (isset($_GET["uname"])) {
    $uname = $_GET["uname"];
    echo "username entered is ".$uname;
}
else
{
    echo "ERROR";
}

if (!empty($_GET["psw"])) {
    $pwd = $_GET["psw"];
    echo "password entered is ".$pwd;
}
else
{
    echo "ERROR";
}
// do password check
$query="SELECT id FROM table_user where username='".$uname."' AND password='".$pwd."'";
echo $query;
echo "<br>";
$con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);
if($result = mysqli_query($con,$query))
{
  {
    $row = mysqli_fetch_array($result);

    if($row == NULL)
    {
        $valid=0;
    }
    else
    {
      $userid=$row['id'];
      $valid=1;
    }
  }
}
echo "User id ".$userid;
if($valid==1)
{
    session_start();
    $_SESSION['id'] = $userid;
if(isset($_SESSION['id']))
echo "Session is alive JJJJKS";
else
echo"-----------------------";
    header("Location: ".$host."/".$appdir."/create_post.php");
}
else
{
//    header("Location: ".$host."/".$appdir."/post.php");
    exit();
}
?>
