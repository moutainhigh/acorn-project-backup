var radio_type=new HashMap();
radio_type.put("1","2"); 
radio_type.put("2","1");
radio_type.put("3","1");
radio_type.put("4","1");
radio_type.put("5","1");
radio_type.put("6","1");
radio_type.put("7","1");

function getvalues(){
    if ($($(".sec")[0]).hasClass("active")) {//快速设置模式
        var quicikMinutes = $("input:checkbox[name=quickMinutes]:checked").length;
        if (quicikMinutes == 60) {//全选
            return "0 0/1 * * * ?";
        } else if (quicikMinutes == 0) {//全不选
            return "0 * * * * ?";
        } else {//选择了有限个
            var fen="";
            $("input:checkbox[name=quickMinutes]:checked").each(function(){
                fen+=$(this).val()+",";
            });
            return "0 " + fen.substring(0, fen.length - 1) + " * * * ?";
        }
    } else {//标准设置模式
        var corn = "";
       // 获取月份
        var yue = "";
        var months = $("input:checkbox[name=months]:checked").length;
        if (months == 0 || months == 12) {
            yue = "*";
        } else {
            $("input:checkbox[name=months]:checked").each(function(){
                yue+=$(this).val()+",";
            });
            yue = yue.substring(0, yue.length - 1);
        }

        if ($(".btn-success02").hasClass("active")) { //获取星期
            var zhou = "";
            var weeks = $("input:checkbox[name=weeks]:checked").length;
            if (weeks == 0 || weeks == 7) {
                zhou = "*";
            } else {
                $("input:checkbox[name=weeks]:checked").each(function(){
                    zhou+=$(this).val()+",";
                });
                zhou = zhou.substring(0, zhou.length - 1);
            }
            corn = " ? " + yue + " " + zhou;
        } else { //获取日期
            var tian = "";
            var days = $("input:checkbox[name=days]:checked").length;
            if (days == 0 || days == 31) {
                tian = "*";
            } else {
                $("input:checkbox[name=days]:checked").each(function(){
                    tian+=$(this).val()+",";
                });
                tian = tian.substring(0, tian.length - 1);
            }
            corn = " " + tian + " " + yue + " ?";
        }

        //获取小时
        var shi = "";
        var hours = $("input:checkbox[name=hours]:checked").length;
        if (hours == 0 || hours == 24) {
            shi = "*";
        } else {
            $("input:checkbox[name=hours]:checked").each(function(){
                shi+=$(this).val()+",";
            });
            shi = shi.substring(0, shi.length - 1);
        }
        corn = " " + shi + corn;

        //获取分钟
        var fen = "";
        var minutes = $("input:checkbox[name=minutes]:checked").length;
        if (minutes == 60) {//全选
            fen = "0/1";
        } else if (minutes == 0) {//全不选
            fen = "*";
        } else {//选择了有限个
            var fen="";
            $("input:checkbox[name=minutes]:checked").each(function(){
                fen+=$(this).val()+",";
            });
            fen = fen.substring(0, fen.length - 1);
        }
        corn = " " + fen + corn;

        //获取秒钟
        var miao = "";
        var seconds = $("input:checkbox[name=seconds]:checked").length;
        if (seconds == 60 || seconds == 0) {
            miao = "0";
        } else {//选择了有限个
            var miao="";
            $("input:checkbox[name=seconds]:checked").each(function(){
                miao+=$(this).val()+",";
            });
            miao = miao.substring(0, miao.length - 1);
        }
        corn = miao + corn;
    }
    return corn;
}