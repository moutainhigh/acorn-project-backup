
(function($){
    $.fn.capacityFixed = function(options) {
        var opts = $.extend({},$.fn.capacityFixed.deflunt,options);
//        var head = $('#head');
        var FixedFun = function(element) {
            var top = opts.top;
            var height = opts.height;
            element.css({
                "top":top
            });
            $('#content').scroll(function() {
                var scrolls = $(this).scrollTop();
//                head.css({borderBottom:"3px #fff solid"});
//                head.css({borderBottom:"3px #fff solid",boxShadow:"0 0 4px rgba(0,0,0,.2)"});
                if (scrolls > top) {
                    if (window.XMLHttpRequest) {
                        element.css({
                            position: "fixed",
                            top: 30 ,
                            borderBottom:'3px solid #fff',
                            boxShadow:"0 0 4px rgba(0,0,0,.2)"
                        });
                        element.next().css({marginTop:height});
                    } else {
                        element.css({
                            top: scrolls
                        });
                    }
                }else {
                    element.css({
                        position: "static",
                        borderBottom:'none',
                        boxShadow:"none"
                    });
                    element.next().css({marginTop:0});
//                    head.css({border:"none",boxShadow:"none"});
                }
            });
            element.find(".close-ico").click(function(event){
                element.remove();
                event.preventDefault();
            })
        };
        return $(this).each(function() {
            FixedFun($(this));
        });
    };
    $.fn.capacityFixed.deflunt={
		right : 0,//相对于页面宽度的右边定位
        top:37,
        height:33
	};
})(jQuery);