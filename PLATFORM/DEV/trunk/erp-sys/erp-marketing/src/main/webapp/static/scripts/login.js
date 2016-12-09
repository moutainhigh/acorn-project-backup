(function(){
var body = (document.compatMode&&document.compatMode.toLowerCase() == "css1compat")?document.documentElement:document.body;
var bodyWidth = body.clientWidth;
var bodyHeight = body.clientHeight;
document.getElementById("wrap").style.height = bodyHeight +"px";
document.getElementById("do_b").style.marginTop = (bodyHeight-411)/2 +"px";
})();