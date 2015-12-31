<!-- Superfish menus declarations -->
<?php $sfishRoot="http://".$_SERVER['SERVER_NAME']."/site/superfish/"; ?>
<link rel="stylesheet" type="text/css" href="<?php echo $sfishRoot;?>css/superfish.css" media="screen"/> 
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery.hoverintent/1.8.1/jquery.hoverIntent.min.js"></script> 
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/superfish/1.7.7/js/superfish.min.js"></script>
<script type="text/javascript"> 
	// initialise plugins
	jQuery(function(){
		jQuery('ul.sf-menu').superfish();
	});
 </script>
<!-- End Superfish menus -->
