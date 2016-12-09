var $common={
	
	/**
	 * 显示消息
	 */
	showMsg:function(obj,msg,time){
		$('#'+obj).find('span').text(msg);
		$('#'+obj).show();
		setTimeout( function(){$('#'+obj).fadeOut('slow');}, ( time * 1000 ) );
	},
	
	/**
	 * 显示成功
	 * @param msg
	 * @param time
	 */
	showSuccess:function(msg,time){
		this.showMsg('msg_success',msg,time);
	},
	
	/**
	 * 显示失败
	 * @param msg
	 * @param time
	 */
	showError:function(msg,time){
		this.showMsg('msg_error',msg,time);
	},
	
	/**
	 * 获取url的参数
	 * @param paras
	 * @returns
	 */
	getResponseParam : function(paras){
		var url = location.href;
		var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");   
		var paraObj = {}
		for (i=0; j=paraString[i]; i++){   
			paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);   
		}
		var returnValue = paraObj[paras.toLowerCase()];   
		if(typeof(returnValue)=="undefined"){   
			return "";   
		}else{   
			return returnValue;   
		}   
	},
	
	buildLink:function(url,_map){
		var appStr='';
		$.each(_map,function(key,value){
			appStr+=key+'='+value+'&';
		});
		if(url.indexOf("?")!=-1){
			url+='&'+appStr;
		}else{
			url+='?'+appStr;
		}
		return url;
	},
	
	buildIndexLink:function(url,p1,p2){
		var m={'search':p1,'menuCatalogId':p2};
		return this.buildLink(url,m);
	},
	
	getLocalFormatTime:function(nS){
		return new Date(parseInt(nS)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
	},
	
	removeObjects:function(path,value){
		if($.trim(value)==''){
			alert('请选择需要删除的数据');
			return;
		}
			
		if (window.confirm('您确定要删除选择的数据？')) {
			window.location.href = path+'?ids='+value;
		}
	},
	
	initResultMsg:function(){
		var a_m=$('#action_msg').val();
		var a_m_e=$('#action_msg_error').val();
		if($.trim(a_m)!='')
			this.showSuccess(a_m,3);
		if($.trim(a_m_e)!='')
			this.showError(a_m_e,3);
	}
	,
	mdate:function($date) {
			$text = '';
			$time=new Date($date);
			$t = new Date()- $time; //时间差 （秒）
			if ($t == 0)
				$text = '刚刚';
			else if ($t < 60)
				$text = $t + '秒前'; // 一分钟内
			else if ($t < 60 * 60)
				$text = floor($t / 60) + '分钟前'; //一小时内
			else if ($t < 60 * 60 * 24)
				$text = floor($t / (60 * 60)) + '小时前'; // 一天内
			else if ($t < 60 * 60 * 24 * 2)
				$text = '昨天 ' + date('H:i', $time); //两天内
			else if ($t < 60 * 60 * 24 * 3)
				$text = '前天 ' + date('H:i', $time); // 三天内
			else if ($t < 60 * 60 * 24 * 30)
				$text = date('m月d日 H:i', $time); //一个月内
			else if ($t < 60 * 60 * 24 * 365)
				$text = date('m月d日', $time); //一年内
			else
				$text = date('Y年m月d日', $time); //一年以前
			return $text;
		}
};