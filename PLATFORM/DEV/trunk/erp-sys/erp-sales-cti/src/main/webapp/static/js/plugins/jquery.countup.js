/**
 * jQuery Count Plugin (jquery.timer.js)
 * @version 1.0.0
 * @author Huan Feng <fenghuan69@126.com>
 **/
(function($){
    var doWork = function(callback)
    {

        var seconds= 0;
        //init
        callback(seconds, {second:"00",minute:"00"});
        doWork.instance = this;
        this.timer = function() {
            if(typeof(callback) == "function")
            {
                var data = {
                    total : seconds ,
                    second : Math.floor(seconds % 60).toString().length<2?"0"+Math.floor(seconds % 60):Math.floor(seconds % 60) ,
                    minute : Math.floor((seconds/60)%60).toString().length<2?"0"+Math.floor((seconds / 60) % 60):Math.floor((seconds / 60) % 60) ,
                    hour : Math.floor((seconds/3600)%24).toString().length<2?"0"+Math.floor((seconds/3600)%24):Math.floor((seconds / 3600) % 24) ,
                    day : Math.floor(seconds / 86400)

                };
                callback(seconds, data);
            }
            if(phone)phone.seconds =  seconds;
            seconds ++;
        };

        this.id= window.setInterval(this.timer, 1000);

        this.stop = function(){clearInterval(this.id);doWork.instance =undefined;return seconds-1;};
        this.reset = function(){
            this.stop();
//            this.id = window.setInterval(this.timer, 1000);
            doWork.instance =undefined;
        };
    };
   $.countup = function(callback)
    {
            var t;
                    if(typeof doWork.instance == "undefined") {
                        t =  new doWork(callback);
                    }else {
//                        $.messager.show({
//                            title:'提醒',
//                            msg:'正在通话中...',
//                            showType:'show'
////                            style:{
////                                right:'',
////                                bottom:''
////                            }
//                        });
                        return;
                    }
            return t;
    };

})(jQuery);

Date.prototype.format = function(format)
{
    /*
     * format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    }

    if (/(y+)/.test(format))
    {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
            - RegExp.$1.length));
    }

    for (var k in o)
    {
        if (new RegExp("(" + k + ")").test(format))
        {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

var printLog = function(msg){
    console.log(new Date().format("MM-dd hh:mm:ss.S") +' >>>>>> '+msg);
}