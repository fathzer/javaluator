function testAdBlocked(id, message, donateButton) {
	$.getScript("//astesana.net/adBlockStats/jquery.isAdBlocked.js")
		.done(function(script, textStatus) {
			try {
				var params = {id:id, callback:function(data) {
					console.log(data);
				}};
				var blocked = $("#adContainer").isAdBlocked(params);
				if (blocked) {
					console.log('blocked');
					if (message==undefined) {
						console.log("No alternate add message defined");
					} else {
						$("#adContainer").append(message);
						$("#adContainer").append('<div id="donate">'+donateButton+'</div>');
						$("#adContainer").addClass("altAdvertising");
					}
				}
			} catch (error) {
				console.log(error);
			}
		}).fail(function(jqxhr, settings, exception) {
			console.log("Unable to call addBlockStats.");
			console.log(jqxhr);
		});
}
