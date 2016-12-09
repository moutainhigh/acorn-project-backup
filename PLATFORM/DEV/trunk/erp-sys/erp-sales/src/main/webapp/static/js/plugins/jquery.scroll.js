/**
 * jQuery Count Plugin (jquery.scroll.js)
 * @version 1.0.0
 * @author Huan Feng <fenghuan69@126.com>
 **/

(function($) {
    var time = 1;
    var scroll = function(a){
            var _s = a.text();
            var _h1  = _s.substr(0,1) ;
            var _h2 = _s.substr(1, _s.length) ;
            if(time==1) _h1 = "　"+_h1;time++ ;
            a.html('<span>'+_h2+_h1+'</span>');
    }
    var bindE = function() {

        var $thisObj = this;
        var _html =  $thisObj.html();
        if($thisObj.width()<$thisObj.children().width()){
            var _t =null;
            $thisObj.bind({
                 mouseenter:function(){
                     if(!_t)
                         _t = setInterval(function(){
                         scroll($thisObj)},200)  },
                 mouseout:function(){
                  if(_t)clearInterval(_t);_t=null;time = 1;
                     $thisObj.html(_html);
                 }
             })
        }
    }

    var methods = {
        init: function(options) {
            return this.each(function() {
                var $this = $(this);
                var settings = $this.data('scroll');

                if(typeof(settings) == 'undefined') {

                    var defaults = {
                        propertyName: 'value',
                        onSomeEvent: function() {}
                    }

                    settings = $.extend({}, defaults, options);

                    $this.data('scroll', settings);
                } else {
                    settings = $.extend({}, settings, options);
                }

                // 代码在这里运行

            });
        },
        destroy: function(options) {
            return $(this).each(function() {
                var $this = $(this);

                $this.removeData('scroll');
            });
        },
        val: function(options) {
            var someValue = this.eq(0).html();

            return someValue;
        },
        bindE:bindE
    };

    $.fn.scroll = function() {
        var method = arguments[0];

        if(methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if( typeof(method) == 'object' || !method ) {
            method = methods.init;
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.scroll' );
            return this;
        }

        return method.apply(this, arguments);

    }

})(jQuery);