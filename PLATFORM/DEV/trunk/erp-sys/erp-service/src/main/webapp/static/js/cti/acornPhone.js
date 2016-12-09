/*
  软电话状态变化
  status，软电话可能状态。  
  0:就绪,1:离席,2:振铃,3:通话中,4:后处理,5:转接中,6:会议,7:注销,8:三方通话,9:保持
*/
//ani--主叫号码，dnsi--被叫号码，tollFreeNum--营销400号码,isOutBoound--是否外呼
function AcornPhone(ani,dnsi,tollFreeNum,isOutBoound){
	this.ani=ani;
	this.dnsi=dnsi;
	this.tollFreeNum=tollFreeNum;
	this.isOutBoound=isOutBoound;  //呼入或者呼出
    this.boundType=0; //1.电话回乎;
	this.status = 0;
	this.company="";// 媒体信息;
	this.province="" //所属的省份
	this.instId="";   //实例ID
    this.seconds = "";  //通话时长
	this.customerType=""; //当前客户类型	
	this.customerId=""; //当前客户类型	
	this.currentLeadId = ""; //当前的销售线索
    this.cticonnid="";
    this.connStates="";
	this.task=new Array(); //当前的任务;
    this.ctisdt=""; //Cti开始时间;
    this.ctiedt=""; //Cti结束时间;
    this.leavingReason = ""; //离席原因
    this.insure = "";//赠险展示
    this._expir = 300;//赠险展示
    this.campainId="";//预测外呼；
    this.acdId="";   //ACD组
    //-1:摘机
    this.onOffHook = null;
	//0:就绪
	this.onReady = null;
	//1:就绪-可外呼
	this.onReadyOut = null;
	//2:离席,
	this.onLeaving = null; 
	//3:振铃,
	this.onRinging = null;
	//4:通话中,
	this.onTalking = null;
	//5:呼出中,
	this.onDialing = null;
	//6:呼出通话,
	this.onTalkingOut = null;
	//7:后处理,
	this.onProcessing = null;
	//8:转接中,
	this.onTransfering = null;
	//9:会议
	this.onMeeting = null;
	//10:注销
	this.onLogout = null;
	//11:三方通话
	this.onTriTalking = null;
	//12:保持
	this.onHolding = null;
	//13:键盘拨号中 -- 二方通话
	this.onOutringing = null;
	//14:键盘拨号通话中 -- 二方通话
	this.onOutcall = null;
	//15:软电话未登录状态
	this.offline = null;
    //16:准备呼出
    this.ReadyOut = null;
}

//状态转换表
AcornPhone.prototype.statusTable=[[2,3,5,10],[0,2,5],[0,5,10],[4],[7],[6,7],[7],[0,1,2],[-1,2],[],[],[],[]];
AcornPhone.prototype.statusNams=["摘机","就绪","就绪-可外呼","离席","振铃","通话中","呼出中","呼出通话","后处理","转接中","会议","注销","三方通话","保持"];
AcornPhone.prototype.getValidStatus = function getValidStatus(){
	return (this.statusTable[this.status])
}
AcornPhone.prototype.getStatusName = function (status){
	if (status == null || status == undefined ){
		return (this.statusNams[this.status])
	}else {
		if (status >= -1 && status <=15){
			return (this.statusNams[status])
		}else{
			return null;
		}
	}
}	

AcornPhone.prototype.init=function(){
	if (this.offline != null){
        this.status = 15 ;
		this.offline(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);

	}
}

//调用事件响应函数
AcornPhone.prototype.onStatusChange=function(){
    if(this.ani)var anip = this.ani.substr(0,this.ani.length-4)+"****";
	switch (this.status){
        case -1:{
            if (this.onOffHook != null){
                this.onOffHook(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
            }
            break;
        }
		case 0:{
			if (this.onReady != null){
                if(checkSoftPhoneApplet()){
                    if($("#ctiPhoneType").val()==1){
                        if(!getDnStatus(null)){
                            console.info("========"+getDnStatus(null));
                            //alertWin("系统提示","电话没有挂机，请挂机！！");
                            changeCtiOnAndOff_off();
                            msgSlide("电话没有挂机，请挂机！！");
                            return;
                            break;
                        }else{
                            this.onReady(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
                        }
                    }else{
                            this.onReady(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
                    }
                }else{
                    this.onReady(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
                }

			}
			break;
		}
		case 1:{
			if (this.onReadyOut != null){
				this.onReadyOut(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 2:{
			if (this.onLeaving != null){
				this.onLeaving(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 3:{
			if (this.onRinging != null){
				this.onRinging(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 4:{
			if (this.onTalking != null){

				this.onTalking(anip,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 5:{
			if (this.onDialing != null){
				this.onDialing(anip,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 6:{
			if (this.onTalkingOut != null){
				this.onTalkingOut(anip,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 7:{
			if (this.onProcessing != null){
				this.onProcessing(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		case 8:{
			if (this.onTransfering != null){
				this.onTransfering(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);
			}
			break;
		}
		case 9:{
			if (this.onMeeting != null){
				this.onMeeting(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);
			}
			break;
		}
		case 10:{
			if (this.onLogout != null){
				this.onLogout(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);
			}
			break;
		}
		case 11:
		{
			if (this.onTriTalking != null){
				this.onTriTalking(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound);
			}
			break;
		}case 12:
		{
			if (this.onHolding != null){
				this.onHolding(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}case 13:
		{
			if (this.onOutringing != null){
				this.onOutringing(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}case 14:
		{
			if (this.onOutcall != null){
				this.onOutcall(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}case 15:
		{
			if (this.offline != null){
				this.offline(this.ani,this.dnsi,this.tollFreeNum,this.isOutBoound,this.company,this.province);
			}
			break;
		}
		default:
		{
			//
		}
	}
}

//根据状态变更表跳转状态
AcornPhone.prototype.changeStatus = function (newStatus,ani,dnsi,tollFreeNum,isOutBoound){
	var isValid = true;
	if (newStatus >=-1 && newStatus<=15){
		var validStatus = this.getValidStatus();
//		if (validStatus.length >0 && validStatus.indexOf(newStatus)>-1){
			this.status = newStatus;
			if (ani != null){
				this.ani = ani;
			}
			if (dnsi != null){
				this.dnsi = ani;
			}
			if (isOutBoound != null){
				this.isOutBoound = isOutBoound;
			}
			if (tollFreeNum != null){
				this.tollFreeNum = tollFreeNum;
			}
			this.onStatusChange();
//		} else	{
//			isValid = false;
//		}
	}else{
		isValid = false;
	}

	if (isValid == false){
		throw new Error("invalid status change from " + this.status + " to " + newStatus);
	}
	
}

/**
 * 
 * 设置当前的销售线索
 * 
 *  @param leadId ,线索ID 
 *  
 *  
 */
AcornPhone.prototype.setCurrentLeadId = function (leadId){
	 this.currentLeadId = leadId;
}

/**
 * 获取当前的销售线索
 * @param leadId
 * @returns {String}
 */
AcornPhone.prototype.getCurrentLeadId = function (leadId){
     return this.currentLeadId;
}

/**
 * 清除当前的线索
 */
AcornPhone.prototype.clearCurrentLeadId = function(){
	this.currentLeadId = "";
	this.task = new Array();
} 

/**
 * 
 * 添加任务 
 * 
 * @param taskId
 * @returns length
 */
AcornPhone.prototype.addTask=function(taskId){
	return this.task.push(taskId);
}

/**
 * 从当前的任务集合中删除指定的任务
 * @param taskId
 * @returns {String} 删除的任务,如果为"" 表示没有找到要删除的任务;
 */
AcornPhone.prototype.deleteTask=function(taskId){
	var result = "";
	   for (i = 0; i < this.task.length; i++){
		   if (this.task[i]==taskId){
			   result=arrayObj.splice(i,1); //删除从指定位置deletePos开始的指定数量deleteCount的元素，数组形式返回所移除的元素
		   }
        }
	   
	  return result;
}

AcornPhone.prototype.clean=function(){
	this.ani=null;
	this.dnsi=null;
	this.tollFreeNum=null;
	this.isOutBoound=null;
	this.status = 0;
	this.customerType=""; //当前客户类型	
	this.customerId="";
	this.company="";// 媒体信息;
	this.province=""; //所属的省份
	this.instId="";
    this.boundType=0;
    this.seconds="";
	this.currentLeadId = ""; //当前的销售线索
    this.cticonnid="";
	this.task=new Array(); //当前的任务;
    this.connStates="";
    this.ctisdt="";//电话的进线时间
    this.ctiedt="";//电话的结束时间
    this.insure = ""//赠险展示
    this.campainId="";//预测外呼的campainId;
    this.acdId="";//ACD组
}








	