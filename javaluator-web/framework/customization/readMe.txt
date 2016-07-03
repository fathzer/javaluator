custom.json
===========
- languages attribute contains the array of languages available in your site.
  Your site should have a folder per language (example: "languages":["fr","en","pt"] implies you have three folders defined (fr, en and pt)).
  Each folder should have at its root a file siteLocalization.json and a file menu.csv (see below their description).
- hosted attribute (optional) can contains data about hosting platform. If you want to thanks them with a "hosted by" message in your site's footer,
  define a hosted attribute like this one:
  	"hosted":{
		"link":"http://sourceforge.net/",
		"icon":"http://sflogo.sourceforge.net/sflogo.php?group_id=276272&amp;type=10",
		"alt":"SourceForge.net"
	}


Per language files :
====================
+ siteLocalization.json: contains wordings specific to your site:
  - keyWords: The keywords listed in the meta keywords tag of the head of every pages of your site..
  - description: The description in the meta description tag of the head of every pages of your site.
  - adBlockMessage: An alternate message for users that use add blockers.
+ menu.csv: contains menu hierachy of your site:
  Each line refers to a page of your site. It is composed of fields separated by a tab character.
  1: The address of the page relative to the language folder (where menu.csv is located)
  2: The menu item wording
  3: The title (which will be in the title tag in the head section) of the page.


