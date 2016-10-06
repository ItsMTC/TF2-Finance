<?php
//include("cache.php");
//cache("tf2fprices", "", "-1 Hour");
/*if(isCached("tf2fprices")){
	echo cacheData("tf2fprices");
} else {
	$out = getPrices();
	cacheData("tf2fprices", $out, "+5 Minute");
	//echo 'If you see this, please tell ItsMTC';
	echo $out;
}*/
echo "2.3:4.74!August 4, 2016 GMT:Prices Now Manually Updated!19.66:2.06!August 4, 2016 GMT:Prices Now Manually Updated";
/*
function getPrices(){
	$buds = split("<span style=\"font-size:x-large; vertical-align:middle; line-height:1.5\"><strong>1 Earbuds =</strong> ", get_data("http://tf2finance.com/"));
	$keys = split("<span style=\"font-size:x-large; vertical-align:middle; line-height:1.5\"><strong>1 Key =</strong> ", get_data("http://tf2finance.com/keys/"));
	//dobuds
	$budpricekey = split("</span>", $buds[1]);
	$budpricekeyfinal = str_replace(" Keys", "", $budpricekey[0]);
	$budpriceusd = split("</span>", $buds[2]);
	$budpriceusdfinal = str_replace(" USD", "", $budpriceusd[0]);
	$buddatekey = split("</p>", $budpricekey[1]);
	$buddatekeyfinal = str_replace("<p>", "", $buddatekey[0]);
	$buddateusd = split("</p>", $budpriceusd[1]);
	$buddateusdfinal = str_replace("<p>", "", $buddateusd[0]);
	//dokeys
	$keyspriceref = split("</span>", $keys[1]);
	$keyspricereffinal = str_replace(" Refined", "", $keyspriceref[0]);
	$keyspriceusd = split("</span>", $keys[2]);
	$keyspriceusdfinal = str_replace(" USD", "", $keyspriceusd[0]);
	$keysdateref = split("</p>", $keyspriceref[1]);
	$keysdatereffinal = str_replace("<p>", "", $keysdateref[0]);
	$keysdateusd = split("</p>", $keyspriceusd[1]);
	$keysdateusdfinal = str_replace("<p>", "", $keysdateusd[0]);
	return str_replace("            ", "", str_replace("\n", "", $budpricekeyfinal.':'.$budpriceusdfinal.'!'.$buddatekeyfinal.':'.$buddateusdfinal.'!'.$keyspricereffinal.':'.$keyspriceusdfinal.'!'.$keysdatereffinal.':'.$keysdateusdfinal))/*.'!'.$lastbudkey.':'.$lastbudusd.':'.$lastkeyref.':'.$lastkeyusd;
}

function get_data($url) {
	$ch = curl_init();
	$timeout = 5;
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, $timeout);
	$data = curl_exec($ch);
	curl_close($ch);
	return $data;
}

function cacheData($id, $value = "", $time = ""){
	$data = cache($id);
if ($data===null) {
    // write cache
    cache($id,$value,$time);
	return cache($id);
} else {
	return cache($id);
}
}

function isCached($id){
		$data = cache($id);
if ($data===null) {
	return false;
} else {
	return true;
}
}
*/
?>