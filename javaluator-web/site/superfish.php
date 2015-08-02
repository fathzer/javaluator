<!-- Superfish menus declarations -->
<?php $sfishRoot="http://".$_SERVER['SERVER_NAME']."/site/superfish/"; ?>
<link rel="stylesheet" type="text/css" href="<?php echo $sfishRoot;?>css/superfish.css" media="screen"/> 
<script type="text/javascript" src="<?php echo $sfishRoot;?>js/jquery-1.2.6.min.js"></script> 
<script type="text/javascript" src="<?php echo $sfishRoot;?>js/hoverIntent.js"></script> 
<script type="text/javascript" src="<?php echo $sfishRoot;?>js/superfish.js"></script> 
<script type="text/javascript"> 
	// initialise plugins
	jQuery(function(){
		jQuery('ul.sf-menu').superfish();
	});
 </script>
<!-- End Superfish menus -->
