//验证邮箱输入
function checkEmailOnBlur(){
    var pattern =/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    var email=document.getElementById("user.email").value;
    if(email==""){
        document.getElementById("emailInfo").innerHTML="<font color='red'>邮箱不能为空</font>";
        return 1;
    }
    if(!pattern.test(email)){
        document.getElementById("emailInfo").innerHTML="<font color='red'>请输入正确的Email地址</font>";
        return 2;
    }
    if(email.length>40){
        document.getElementById("emailInfo").innerHTML="<font color='red'>email长度不超过40位</font>";
        return 3;
    }else{
    	document.getElementById("emailInfo").innerHTML="" ;
    }
}
//邮箱验证回调函数
function loadComplete_email(response){
    document.getElementById("emailInfo").innerHTML=response.responseText;
}

//验证联系人姓名
function check_name(){
    var name=document.getElementById("user.name").value;
    var nameInfo=document.getElementById("nameInfo");
    var Expression=/[^\u4E00-\u9FA5]/;
    var exception=/^[赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯昝管卢莫柯房裘缪干解应宗丁宣贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊于惠甄曲家封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭历戎祖武符刘景詹束龙叶幸司韶郜黎蓟溥印宿白怀蒲邰从鄂索咸籍赖卓蔺屠蒙池乔阳郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庾终暨居衡步都耿满弘匡国文寇广禄阙东欧殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后荆红游竺权逮盍益桓公上赫皇澹淳太轩令宇长盖况闫].{1,5}$/;
    if(name==""){
        nameInfo.innerHTML="<font color='red'>联系人姓名不能为空</font>";
        return 1;
    }
    if(name.length>10){
        nameInfo.innerHTML="<font color='red'>联系人姓名长度不能超过10位</font>";
        return 2;
    }
    if(name.length<2){
        nameInfo.innerHTML="<font color='red'>联系人姓名长度不能小于2位</font>";
        return 3;
    }
    if(Expression.test(name)&& !exception.test(name)){
        nameInfo.innerHTML="<font color='red'>请输入正确的中文姓名</font>";
        return 4;
    }else{
        nameInfo.innerHTML="" ;
    }
}

//验证身份证
function isIdCardNo(sId){
    var aCity = {11:'北京',12:'天津',13:'河北',14:'山西',15:'内蒙古',21:'辽宁',22:'吉林',23:'黑龙江',31:'上海',32:'江苏',33:'浙江',34:'安徽',35:'福建',36:'江西',37:'山东',41:'河南',42:'湖北',43:'湖南',44:'广东',45:'广西',46:'海南',50:'重庆',51:'四川',52:'贵州',53:'云南',54:'西藏',61:'陕西',62:'甘肃',63:'青海',64:'宁夏',65:'新疆',71:'台湾',81:'香港',82:'澳门',91:'国外'};
    var iSum = 0;
    var info = '';
    if (sId.length == 15) {
        if (!/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(sId)){
            return false;
        }else {
            if (aCity[parseInt(sId.substr(0, 2))] == null)
                return false;
            return true;
        }
    }
    if (!/^\d{17}(\d|x)$/i.test(sId))
        return false;
    sId = sId.replace(/x$/i, "a");
    if (aCity[parseInt(sId.substr(0, 2))] == null)
        return false;
    var sBirthday = sId.substr(6, 4) + "/" + Number(sId.substr(10, 2)) + "/" + Number(sId.substr(12, 2));
    var d = new Date(sBirthday);
    if (sBirthday != (d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate()))
        return false;
    for (var i = 17; i >= 0; i--)
        iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11)
    if (iSum % 11 != 1)
        return false;
    return true;
}

function check_idCard(){
    var idCard=document.getElementById("user.identityCard").value;
    var idCardInfo=document.getElementById("idCardInfo");
    if(idCard==""){
        idCardInfo.innerHTML="<font color='red'>身份证不能为空</font>";
        return 1;
    }
    if(!isIdCardNo(idCard)){
        idCardInfo.innerHTML="<font color='red'>请输入中国大陆地区正确的身份证号码</font>";
        return 2;
    }else{
        idCardInfo.innerHTML="" ;
    }
}

function checkPhoneAndMoblie(){
    var mobile=document.getElementById("user.mobile").value;
    var phone=document.getElementById("phone").value;
    var phoneInfo=document.getElementById("phoneInfo");
    var mobileInfo=document.getElementById("mobileInfo");
    var exce_moblie=/^1[3|4|5|8][0-9]\d{4,8}$/;
    var exce_phone=/^(\d{3,4}-)?\d{6,8}$/;
    if(mobile=="" && phone==""){
        return 1
    }
    if(mobile!="") {
        if(!exce_moblie.test(mobile) || mobile.length<11 || mobile.length>11){
            mobileInfo.innerHTML="<font color='red'>手机号码输入错误</font>";
            return 2;
        }else{
            mobileInfo.innerHTML="";
            if(phone != ""){
                if(!exce_phone.test(phone)){
                    phoneInfo.innerHTML="<font color='red'>请输入正确的电话号码如:021-86591234</font>";
                    return 3;
                }else{
                    phoneInfo.innerHTML="";
                    return 0;
                }
            }
            return 0;
        }
    }
    if(phone != ""){
        if(!exce_phone.test(phone)){
            phoneInfo.innerHTML="<font color='red'>请输入正确的电话号码如:021-86591234</font>";
            return 3;
        }else{
            phoneInfo.innerHTML="";
            if(mobile!=""){
                if(!exce_moblie.test(mobile) || mobile.length<11){
                    mobileInfo.innerHTML="<font color='red'>手机号码输入错误</font>";
                    return 2;
                }else{
                    mobileInfo.innerHTML="";
                    return 0;
                }
            }
            return 0;
        }
    }

}

//验证网站地址
function check_siteURl(){
    var siteURl=document.getElementById("user.siteURl").value;
    var siteURLInfo=document.getElementById("siteURLInfo");
    var Expression=/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
    if(siteURl==""){
        siteURLInfo.innerHTML="<font color='red'>网站地址不能为空</font>";
        return 1;
    }
    if(siteURl.length>100){
        siteURLInfo.innerHTML="<font color='red'>网站地址长度不能 超过100位</font>";
        return 2;
    }
    if(!Expression.test(siteURl)){
        siteURLInfo.innerHTML="<font color='red'>请输入正确的网址如：www.chinadrtv.com</font>";
        return 3;
    }else{
        siteURLInfo.innerHTML="" ;
    }
}

//验证网站名称
function check_siteName() {
    var pattern = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
    var siteName=document.getElementById("user.siteName").value;
    var siteNameInfo=document.getElementById("siteNameInfo");
    if(siteName==""){
        siteNameInfo.innerHTML="<font color='red'>联盟名称不能为空</font>";
        return 1;
    }
    if(siteName.length>20){
        siteNameInfo.innerHTML="<font color='red'>联盟名称长度不能超过20位</font>";
        return 2;
    }
    if(!pattern.test(siteName)){
        siteNameInfo.innerHTML="<font color='red'>联盟名称只允许包含中文、数字、字母、下滑线</font>";
        return 3;
    }else{
        document.getElementById("siteNameInfo").innerHTML="" ;
        $.ajax({
            type: 'POST',
            url: '/validExistUnionName',
            data: 'siteName='+siteName,
            beforeSend: function() {
            },
            success: function(response){
                if(response==""){
                    $('#siteNameInfo').html("该联盟号可以使用");
                }else{
                    $('#siteNameInfo').html("<font color='red'>"+response+"</font>");
                    return 4 ;
                }
            },
            complete:function(xhr,ts){
            }
        });
    }
    return 0 ;
}

function doSubmit(){
    var check_email_rs=checkEmailOnBlur();
    if(check_email_rs==1){alert("邮箱不能为空");return false;}
    if(check_email_rs==2){alert("请输入正确的Email地址");return false;}
    if(check_email_rs==3){alert("email长度不能超过40位");return false;}
    var emailInfo=document.getElementById("emailInfo").innerHTML;
    if(emailInfo.indexOf("Email")!=-1){alert("email已被使用，请重新输入");return false;}
    var check_name_rs=check_name();
    if(check_name_rs==1){alert("联系人姓名不能为空");return false;}
    if(check_name_rs==2){alert("联系人姓名长度不能超过10位");return false;}
    if(check_name_rs==3){alert("联系人姓名长度不能小于2位");return false;}
    if(check_name_rs==4){alert("请输入正确的中文姓名");return false;}
    var check_idCard_rs=check_idCard();
    if(check_idCard_rs==1){alert("身份证不能为空");return false;}
    if(check_idCard_rs==2){alert("请输入中国大陆地区正确的身份证号码");return false;}
    var checkPhoneAndMoblie_rs=checkPhoneAndMoblie();
    if(checkPhoneAndMoblie_rs==1){alert("请输入联系电话或手机");return false;}
    if(checkPhoneAndMoblie_rs==2){alert("手机号码输入错误");return false;}
    if(checkPhoneAndMoblie_rs==3){alert("请输入中国大陆地区正确的电话号码如：021-86591234 或0531-8695456");return false;}
    var check_siteURL_rs=check_siteURl();
    if(check_siteURL_rs==1){alert("网站地址不能为空");return false;}
    if(check_siteURL_rs==2){alert("网站地址长度不能超过100位");return false;}
    if(check_siteURL_rs==3){alert("请输入正确的网址如：www.chinadrtv.com");return false;}
    var check_siteName_rs=check_siteName();
    if(check_siteName_rs==1){alert("联盟名称不能为空");return false;}
    if(check_siteName_rs==2){alert("联盟名称长度不能超过20位");return false;}
    if(check_siteName_rs==3){alert("联盟名称只允许包含中文、数字、字母、下滑线");return false;}
    var siteNameInfo=document.getElementById("siteNameInfo").innerHTML;
    if(siteNameInfo.indexOf("网站")!=-1){alert("网站名称已被使用，请重新输入");return false;}
    document.getElementById("unionForm").submit();
}

function changDiv(){
    var baseInfoDiv=document.getElementById("baseInfo");
    var baseInfoEdit=document.getElementById("baseInfoEdit");
    baseInfoDiv.style.display="none";
    baseInfoEdit.style.display="";
}