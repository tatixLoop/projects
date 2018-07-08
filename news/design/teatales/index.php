<!DOCTYPE html>
<?php
	include ('connection.php');
	$con=mysqli_connect($dbhost,$dbusername,$dbpwd,$db);
	$result_img = mysqli_query($con,"SELECT * FROM table_newsimg where id=(SELECT max(id) from table_news)");
	$row_img_tag = mysqli_fetch_array($result_img);
	$img_preview=$row_img_tag['img'];
	$result_p = mysqli_query($con,"SELECT * FROM `table_news` WHERE id=".$row_img_tag['id']);
	$row_ptag = mysqli_fetch_array($result_p);
	$title=$row_ptag['heading'];
	$author_topnews= getAuthorName($row_ptag['userid'], $con);
	$topnewsdate=getFormatedNewsDate($row_ptag['datetime']);
	$catagorytopnews=getCatagoryNews($row_ptag['catagory']);
	$numCommentstopnews=getNumComments($row_ptag['id']);


	$selectIdQuery= "SELECT * FROM table_news ORDER BY id DESC LIMIT 3";
	$result_selectedNews = mysqli_query($con, $selectIdQuery);
	$i = 0;
	while($i <3)
	{
		$row_ptag = mysqli_fetch_array($result_selectedNews);
		$i++;
		
	}

function getNumComments($id)
{
	$numComments=5;
	return $numComments;
}
function getAuthorName($id, $con)
{
	$query="SELECT nickname from table_user WHERE id=".$id;
	$userdata=mysqli_query($con, $query);
	$userrow=mysqli_fetch_array($userdata);
	$author=$userrow['nickname'];
	return $author;
}
function getFormatedNewsDate($datetime)
{
	$phptime=strtotime($datetime);
	$newsdate=date("d-M-Y",$phptime);
	return $newsdate;
}
function getCatagoryNews($catagory)
{
	$catagory="General News";
	return $catagory;
}
?>
<html lang="zxx" class="no-js">
	
<meta http-equiv="content-type" content="text/html;charset=UTF-8" /><!-- /Added by HTTrack -->
<head>
		<!-- Mobile Specific Meta -->
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!-- Favicon-->
		<link rel="shortcut icon" href="img/fav.html">
		<!-- Author Meta -->
		<meta name="author" content="colorlib">
		<!-- Meta Description -->
		<meta name="description" content="">
		<!-- Meta Keyword -->
		<meta name="keywords" content="">
		<!-- meta character set -->
		<meta charset="UTF-8">
		<!-- Site Title -->
		<title>Magazine</title>
		<link href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700" rel="stylesheet">
		<!--
		CSS
		============================================= -->
		<link rel="stylesheet" href="css/linearicons.css">
		<link rel="stylesheet" href="css/font-awesome.min.css">
		<link rel="stylesheet" href="css/bootstrap.css">
		<link rel="stylesheet" href="css/magnific-popup.css">
		<link rel="stylesheet" href="css/nice-select.css">
		<link rel="stylesheet" href="css/animate.min.css">
		<link rel="stylesheet" href="css/owl.carousel.css">
		<link rel="stylesheet" href="css/jquery-ui.css">
		<link rel="stylesheet" href="css/main.css">
	</head>
	<body>
		<header>
			
			<div class="header-top">
				<div class="container">
					<div class="row">
						<div class="col-lg-6 col-md-6 col-sm-6 col-6 header-top-left no-padding">
							<ul>
								<li><a href="#"><i class="fa fa-facebook"></i></a></li>
								<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							</ul>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-6 header-top-right no-padding">
							<ul>
								<li><a href="tel:+91 9844930512"><span class="lnr lnr-phone-handset"></span><span>+91 9844930512</span></a></li>
								<li></span><span><span class="__cf_email__" data-cfemail="2a595f5a5a45585e6a494546455846434804494547">[email&#160;protected]</span></span></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="logo-wrap">
				<div class="container">
					<div class="row justify-content-between align-items-center">
						<div class="col-lg-4 col-md-4 col-sm-12 logo-left no-padding">
							<a href="index.php">
								<img class="img-fluid" src="img/logo.png" alt="">
							</a>
						</div>
						<div class="col-lg-8 col-md-8 col-sm-12 logo-right no-padding ads-banner">
							<img class="img-fluid" src="img/banner-ad.jpg" alt="">
						</div>
					</div>
				</div>
			</div>
			<div class="container main-menu" id="main-menu">
				<div class="row align-items-center justify-content-between">
					<nav id="nav-menu-container">
						<ul class="nav-menu">
							<li class="menu-active"><a href="index.php">Home</a></li>
							<li><a href="archive.html">Archive</a></li>
							<li><a href="category.html">Category</a></li>
							<li class="menu-has-children"><a href="#">Post Types</a>
							<ul>
								<li><a href="standard-post.html">Standard Post</a></li>
								<li><a href="image-post.html">Image Post</a></li>
								<li><a href="gallery-post.html">Gallery Post</a></li>
								<li><a href="video-post.html">Video Post</a></li>
								<li><a href="audio-post.html">Audio Post</a></li>
							</ul>
						</li>
						<li><a href="about.html">About</a></li>
						<li><a href="contact.html">Contact</a></li>
					</ul>
					</nav><!-- #nav-menu-container -->
					<div class="navbar-right">
						<form class="Search">
							<input type="text" class="form-control Search-box" name="Search-box" id="Search-box" placeholder="Search">
							<label for="Search-box" class="Search-box-label">
								<span class="lnr lnr-magnifier"></span>
							</label>
							<span class="Search-close">
								<span class="lnr lnr-cross"></span>
							</span>
						</form>
					</div>
				</div>
			</div>
		</header>
		
		<div class="site-main-container">
			<!-- Start top-post Area -->
			<section class="top-post-area pt-10">
				<div class="container no-padding ">
					<div class="row small-gutters">
						<div class="col-lg-8 top-post-left mt-10">
							<div class="feature-image-thumb relative">
								<div class="overlay overlay-bg"></div>
								<img class="img-fluid" src="<?php echo $host."/".$appdir."/img/".$img_preview;?>" alt="<?php echo $host."/".$appdir."/img/".$img_preview;?>">
							</div>
							<div class="top-post-details">

								<ul class="tags">
									<li><a href="#"><?php echo $catagorytopnews?></a></li>
								</ul>
								<a href="image-post.html">
									<h3><?php echo $title; ?></h3>
								</a>
								<ul class="meta">
									<li><a href="#"><span class="lnr lnr-user"></span><?php echo $author_topnews; ?></a></li>
									<li><a href="#"><span class="lnr lnr-calendar-full"></span><?php echo $topnewsdate; ?></a></li>
									<li><a href="#"><span class="lnr lnr-bubble"></span><?php echo $numCommentstopnews." Comments"; ?></a></li>
								</ul>
							</div>
						</div>
						<div class="col-lg-4 top-post-right" min-width="323px" height="376px" overflow="scroll">
							

							<div class="single-top-post mt-10">
								<div class="feature-image-thumb relative">
									<div class="overlay overlay-bg"></div>
									<img class="img-fluid" src="img/top-post2.jpg" alt="">
								</div>
								<div class="top-post-details">
									<ul class="tags">
										<li><a href="#">Food Habit</a></li>
									</ul>
									<a href="image-post.html">
										<h4>A Discount Toner Cartridge Is Better Than Ever.2</h4>
									</a>
									<ul class="meta">
										<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
										<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
										<li><a href="#"><span class="lnr lnr-bubble"></span>06 Comments</a></li>
									</ul>
								</div>
							</div>
							<div class="single-top-post mt-10">
								<div class="feature-image-thumb relative">
									<div class="overlay overlay-bg"></div>
									<img class="img-fluid" src="img/top-post3.jpg" alt="">
								</div>
								<div class="top-post-details">
									<ul class="tags">
										<li><a href="#">Food Habit</a></li>
									</ul>
									<a href="image-post.html">
										<h4>A Discount Toner Cartridge Is Better 3</h4>
									</a>
									<ul class="meta">
										<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
										<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
										<li><a href="#"><span class="lnr lnr-bubble"></span>06 Comments</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="col-lg-12">
							<div class="news-tracker-wrap">
								<h6><span>Breaking News:</span>   <a href="#">Astronomy Binoculars A Great Alternative</a></h6>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!-- End top-post Area -->
			<!-- Start latest-post Area -->
			<section class="latest-post-area pb-120">
				<div class="container no-padding">
					<div class="row">
						<div class="col-lg-8 post-list">
							<!-- Start latest-post Area -->
							<div class="latest-post-wrap">
								<h4 class="cat-title">Latest News</h4>
								<div class="single-latest-post row align-items-center">
									<div class="col-lg-5 post-left">
										<div class="feature-img relative">
											<div class="overlay overlay-bg"></div>
											<img class="img-fluid" src="img/l1.jpg" alt="">
										</div>
										<ul class="tags">
											<li><a href="#">Lifestyle</a></li>
										</ul>
									</div>
									<div class="col-lg-7 post-right">
										<a href="image-post.html">
											<h4>A Discount Toner Cartridge Is
											Better Than Ever.</h4>
										</a>
										<ul class="meta">
											<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
											<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
											<li><a href="#"><span class="lnr lnr-bubble"></span>06 Comments</a></li>
										</ul>
										<p class="excert">
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt.
										</p>
									</div>
								</div>
								<div class="single-latest-post row align-items-center">
									<div class="col-lg-5 post-left">
										<div class="feature-img relative">
											<div class="overlay overlay-bg"></div>
											<img class="img-fluid" src="img/l2.jpg" alt="">
										</div>
										<ul class="tags">
											<li><a href="#">Science</a></li>
										</ul>
									</div>
									<div class="col-lg-7 post-right">
										<a href="image-post.html">
											<h4>A Discount Toner Cartridge Is
											Better Than Ever.</h4>
										</a>
										<ul class="meta">
											<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
											<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
											<li><a href="#"><span class="lnr lnr-bubble"></span>06 Comments</a></li>
										</ul>
										<p>
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt.
										</p>
									</div>
								</div>
								<div class="single-latest-post row align-items-center">
									<div class="col-lg-5 post-left">
										<div class="feature-img relative">
											<div class="overlay overlay-bg"></div>
											<img class="img-fluid" src="img/l3.jpg" alt="">
										</div>
										<ul class="tags">
											<li><a href="#">Travel</a></li>
										</ul>
									</div>
									<div class="col-lg-7 post-right">
										<a href="image-post.html">
											<h4>A Discount Toner Cartridge Is
											Better Than Ever.</h4>
										</a>
										<ul class="meta">
											<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
											<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
											<li><a href="#"><span class="lnr lnr-bubble"></span>06 Comments</a></li>
										</ul>
										<p>
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt.
										</p>
									</div>
								</div>
								<div class="single-latest-post row align-items-center">
									<div class="col-lg-5 post-left">
										<div class="feature-img relative">
											<div class="overlay overlay-bg"></div>
											<img class="img-fluid" src="img/l4.jpg" alt="">
										</div>
										<ul class="tags">
											<li><a href="#">Fashion</a></li>
										</ul>
									</div>
									<div class="col-lg-7 post-right">
										<a href="image-post.html">
											<h4>A Discount Toner Cartridge Is
											Better Than Ever.</h4>
										</a>
										<ul class="meta">
											<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
											<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
											<li><a href="#"><span class="lnr lnr-bubble"></span>06 Comments</a></li>
										</ul>
										<p>
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt.
										</p>
									</div>
								</div>
							</div>
							<!-- End latest-post Area -->
							
							<!-- Start banner-ads Area -->
							<div class="col-lg-12 ad-widget-wrap mt-30 mb-30">
								<img class="img-fluid" src="img/banner-ad.jpg" alt="">
							</div>
							<!-- End banner-ads Area -->
							<!-- Start popular-post Area -->
							<div class="popular-post-wrap">
								<h4 class="title">Popular Posts</h4>
								<div class="feature-post relative">
									<div class="feature-img relative">
										<div class="overlay overlay-bg"></div>
										<img class="img-fluid" src="img/f1.jpg" alt="">
									</div>
									<div class="details">
										<ul class="tags">
											<li><a href="#">Food Habit</a></li>
										</ul>
										<a href="image-post.html">
											<h3>A Discount Toner Cartridge Is Better Than Ever. 4</h3>
										</a>
										<ul class="meta">
											<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
											<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
											<li><a href="#"><span class="lnr lnr-bubble"></span>06 Comments</a></li>
										</ul>
									</div>
								</div>
								<div class="row mt-20 medium-gutters">
									<div class="col-lg-6 single-popular-post">
										<div class="feature-img-wrap relative">
											<div class="feature-img relative">
												<div class="overlay overlay-bg"></div>
												<img class="img-fluid" src="img/f2.jpg" alt="">
											</div>
											<ul class="tags">
												<li><a href="#">Travel</a></li>
											</ul>
										</div>
										<div class="details">
											<a href="image-post.html">
												<h4>A Discount Toner Cartridge Is
												Better Than Ever.</h4>
											</a>
											<ul class="meta">
												<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
												<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
												<li><a href="#"><span class="lnr lnr-bubble"></span>06 </a></li>
											</ul>
											<p class="excert">
												Lorem ipsum dolor sit amet, consecteturadip isicing elit, sed do eiusmod tempor incididunt ed do eius.
											</p>
										</div>
									</div>
									<div class="col-lg-6 single-popular-post">
										<div class="feature-img-wrap relative">
											<div class="feature-img relative">
												<div class="overlay overlay-bg"></div>
												<img class="img-fluid" src="img/f3.jpg" alt="">
											</div>
											<ul class="tags">
												<li><a href="#">Travel</a></li>
											</ul>
										</div>
										<div class="details">
											<a href="image-post.html">
												<h4>A Discount Toner Cartridge Is
												Better Than Ever.</h4>
											</a>
											<ul class="meta">
												<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
												<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
												<li><a href="#"><span class="lnr lnr-bubble"></span>06 </a></li>
											</ul>
											<p class="excert">
												Lorem ipsum dolor sit amet, consecteturadip isicing elit, sed do eiusmod tempor incididunt ed do eius.
											</p>
										</div>
									</div>
								</div>
							</div>
							<!-- End popular-post Area -->
							<!-- Start relavent-story-post Area -->
							
						</div>
						<div class="col-lg-4">
							<div class="sidebars-area">
								<div class="single-sidebar-widget editors-pick-widget">
									<h6 class="title">Editor’s Pick</h6>
									<div class="editors-pick-post">
										<div class="feature-img-wrap relative">
											<div class="feature-img relative">
												<div class="overlay overlay-bg"></div>
												<img class="img-fluid" src="img/e1.jpg" alt="">
											</div>
											<ul class="tags">
												<li><a href="#">Travel</a></li>
											</ul>
										</div>
										<div class="details">
											<a href="image-post.html">
												<h4 class="mt-20">A Discount Toner Cartridge Is
												Better Than Ever.</h4>
											</a>
											<ul class="meta">
												<li><a href="#"><span class="lnr lnr-user"></span>Mark wiens</a></li>
												<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
												<li><a href="#"><span class="lnr lnr-bubble"></span>06 </a></li>
											</ul>
											<p class="excert">
												Lorem ipsum dolor sit amet, consecteturadip isicing elit, sed do eiusmod tempor incididunt ed do eius.
											</p>
										</div>
										<div class="post-lists">
											<div class="single-post d-flex flex-row">
												<div class="thumb">
													<img src="img/e2.jpg" alt="">
												</div>
												<div class="detail">
													<a href="image-post.html"><h6>Help Finding Information
													Online is so easy</h6></a>
													<ul class="meta">
														<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
														<li><a href="#"><span class="lnr lnr-bubble"></span>06</a></li>
													</ul>
												</div>
											</div>
											<div class="single-post d-flex flex-row">
												<div class="thumb">
													<img src="img/e3.jpg" alt="">
												</div>
												<div class="detail">
													<a href="image-post.html"><h6>Compatible Inkjet Cartr
													world famous</h6></a>
													<ul class="meta">
														<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
														<li><a href="#"><span class="lnr lnr-bubble"></span>06</a></li>
													</ul>
												</div>
											</div>
											<div class="single-post d-flex flex-row">
												<div class="thumb">
													<img src="img/e4.jpg" alt="">
												</div>
												<div class="detail">
													<a href="image-post.html"><h6>5 Tips For Offshore Soft
													Development </h6></a>
													<ul class="meta">
														<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
														<li><a href="#"><span class="lnr lnr-bubble"></span>06</a></li>
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="single-sidebar-widget ads-widget">
									<img class="img-fluid" src="img/sidebar-ads.jpg" alt="">
								</div>
								<div class="single-sidebar-widget newsletter-widget">
									<h6 class="title">Newsletter</h6>
									<p>
										Here, I focus on a range of items
										andfeatures that we use in life without
										giving them a second thought.
									</p>
									<div class="form-group d-flex flex-row">
										<div class="col-autos">
											<div class="input-group">
												<input class="form-control" placeholder="Email Address" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Email Address'" type="text">
											</div>
										</div>
										<a href="#" class="bbtns">Subcribe</a>
									</div>
									<p>
										You can unsubscribe us at any time
									</p>
								</div>
								<div class="single-sidebar-widget most-popular-widget">
									<h6 class="title">Most Popular</h6>
									<div class="single-list flex-row d-flex">
										<div class="thumb">
											<img src="img/m1.jpg" alt="">
										</div>
										<div class="details">
											<a href="image-post.html">
												<h6>Help Finding Information
												Online is so easy</h6>
											</a>
											<ul class="meta">
												<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
												<li><a href="#"><span class="lnr lnr-bubble"></span>06</a></li>
											</ul>
										</div>
									</div>
									<div class="single-list flex-row d-flex">
										<div class="thumb">
											<img src="img/m2.jpg" alt="">
										</div>
										<div class="details">
											<a href="image-post.html">
												<h6>Compatible Inkjet Cartr
												world famous</h6>
											</a>
											<ul class="meta">
												<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
												<li><a href="#"><span class="lnr lnr-bubble"></span>06</a></li>
											</ul>
										</div>
									</div>
									<div class="single-list flex-row d-flex">
										<div class="thumb">
											<img src="img/m3.jpg" alt="">
										</div>
										<div class="details">
											<a href="image-post.html">
												<h6>5 Tips For Offshore Soft
												Development </h6>
											</a>
											<ul class="meta">
												<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
												<li><a href="#"><span class="lnr lnr-bubble"></span>06</a></li>
											</ul>
										</div>
									</div>
									<div class="single-list flex-row d-flex">
										<div class="thumb">
											<img src="img/m4.jpg" alt="">
										</div>
										<div class="details">
											<a href="image-post.html">
												<h6>5 Tips For Offshore Soft
												Development </h6>
											</a>
											<ul class="meta">
												<li><a href="#"><span class="lnr lnr-calendar-full"></span>03 April, 2018</a></li>
												<li><a href="#"><span class="lnr lnr-bubble"></span>06</a></li>
											</ul>
										</div>
									</div>
								</div>
								<div class="single-sidebar-widget social-network-widget">
									<h6 class="title">Social Networks</h6>
									<ul class="social-list">
										<li class="d-flex justify-content-between align-items-center fb">
											<div class="icons d-flex flex-row align-items-center">
												<i class="fa fa-facebook" aria-hidden="true"></i>
												<p>983 Likes</p>
											</div>
											<a href="#">Like our page</a>
										</li>
										<li class="d-flex justify-content-between align-items-center tw">
											<div class="icons d-flex flex-row align-items-center">
												<i class="fa fa-twitter" aria-hidden="true"></i>
												<p>983 Followers</p>
											</div>
											<a href="#">Follow Us</a>
										</li>
										<li class="d-flex justify-content-between align-items-center yt">
											<div class="icons d-flex flex-row align-items-center">
												<i class="fa fa-youtube-play" aria-hidden="true"></i>
												<p>983 Subscriber</p>
											</div>
											<a href="#">Subscribe</a>
										</li>
										<li class="d-flex justify-content-between align-items-center rs">
											<div class="icons d-flex flex-row align-items-center">
												<i class="fa fa-rss" aria-hidden="true"></i>
												<p>983 Subscribe</p>
											</div>
											<a href="#">Subscribe</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!-- End latest-post Area -->
		</div>
		
		<!-- start footer Area -->
		<footer class="footer-area section-gap">
			<div class="container">
				<div class="row">
					<div class="col-lg-3 col-md-6 single-footer-widget">
						<h4>Top Products</h4>
						<ul>
							<li><a href="#">Managed Website</a></li>
							<li><a href="#">Manage Reputation</a></li>
							<li><a href="#">Power Tools</a></li>
							<li><a href="#">Marketing Service</a></li>
						</ul>
					</div>
					<div class="col-lg-2 col-md-6 single-footer-widget">
						<h4>Quick Links</h4>
						<ul>
							<li><a href="#">Jobs</a></li>
							<li><a href="#">Brand Assets</a></li>
							<li><a href="#">Investor Relations</a></li>
							<li><a href="#">Terms of Service</a></li>
						</ul>
					</div>
					<div class="col-lg-2 col-md-6 single-footer-widget">
						<h4>Features</h4>
						<ul>
							<li><a href="#">Jobs</a></li>
							<li><a href="#">Brand Assets</a></li>
							<li><a href="#">Investor Relations</a></li>
							<li><a href="#">Terms of Service</a></li>
						</ul>
					</div>
					<div class="col-lg-2 col-md-6 single-footer-widget">
						<h4>Resources</h4>
						<ul>
							<li><a href="#">Guides</a></li>
							<li><a href="#">Research</a></li>
							<li><a href="#">Experts</a></li>
							<li><a href="#">Agencies</a></li>
						</ul>
					</div>
					<div class="col-lg-3 col-md-6 single-footer-widget">
						<h4>Instragram Feed</h4>
						<ul class="instafeed d-flex flex-wrap">
							<li><img src="img/i1.jpg" alt=""></li>
							<li><img src="img/i2.jpg" alt=""></li>
							<li><img src="img/i3.jpg" alt=""></li>
							<li><img src="img/i4.jpg" alt=""></li>
							<li><img src="img/i5.jpg" alt=""></li>
							<li><img src="img/i6.jpg" alt=""></li>
							<li><img src="img/i7.jpg" alt=""></li>
							<li><img src="img/i8.jpg" alt=""></li>
						</ul>
					</div>
				</div>
				<div class="footer-bottom row align-items-center">
					<p class="footer-text m-0 col-lg-8 col-md-12"><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
Copyright &copy;<script data-cfasync="false" src="../../../cdn-cgi/scripts/f2bf09f8/cloudflare-static/email-decode.min.js"></script><script>document.write(new Date().getFullYear());</script> All rights reserved 
<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
					<div class="col-lg-4 col-md-12 footer-social">
						<a href="#"><i class="fa fa-facebook"></i></a>
						<a href="#"><i class="fa fa-twitter"></i></a>
					</div>
				</div>
			</div>
		</footer>
		<!-- End footer Area -->
		<script src="js/vendor/jquery-2.2.4.min.js"></script>
		<script src="../../../../cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		<script src="js/vendor/bootstrap.min.js"></script>
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhOdIF3Y9382fqJYt5I_sswSrEw5eihAA"></script>
		<script src="js/easing.min.js"></script>
		<script src="js/hoverIntent.js"></script>
		<script src="js/superfish.min.js"></script>
		<script src="js/jquery.ajaxchimp.min.js"></script>
		<script src="js/jquery.magnific-popup.min.js"></script>
		<script src="js/mn-accordion.js"></script>
		<script src="js/jquery-ui.js"></script>
		<script src="js/jquery.nice-select.min.js"></script>
		<script src="js/owl.carousel.min.js"></script>
		<script src="js/mail-script.js"></script>
		<script src="js/main.js"></script>

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src=""></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-23581568-13');
</script>

	</body>

<!-- Mirrored from colorlib.com/preview/theme/magazine/ by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 15 Jun 2018 11:30:43 GMT -->
</html>
