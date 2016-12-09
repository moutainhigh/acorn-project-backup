/*
  软电话状态变化
  status，软电话可能状态。  
  0:就绪,1:离席,2:振铃,3:通话中,4:后处理,5:转接中,6:会议,7:注销,8:二方通话,9:三方通话,10:保持
*/
//ani--主叫号码，dnsi--被叫号码，isOutBoound--是否外呼
function AcornPhone(ani,dnsi,isOutBoound){
	this.ani=ani;
	this.dnsi=dnsi;
	this.isOutBoound=isOutBoound;
	this.status = 0;
	
	//0:就绪
	this.onReady = null;
	//1:离席,
	this.onLeaving = null; 
	//2:振铃,
	this.onRinging = null;
	//3:通话中,
	this.onTalking = null;
	//4:后处理,
	this.onProcessing = null;
	//5:转接中,
	this.onTransfering = null;
	//6:会议
	this.onMeeting = null;
	//7:注销
	this.onLogout = null;
	//8:二方通话
	this.onBiTalking = null;
	//9:三方通话
	this.onTriTalking = null;
	//10:保持
	this.onHolding = null;
}

//状态转换表
AcornPhone.prototype.statusTable=[[1,2,3,7],[0,3,7],[3,4],[4,5,6,10],[0,1],[8,9],[4,8,9],[],[],[],[]];
AcornPhone.prototype.statusNams=["就绪","离席","振铃","通话中","后处理","转接中","会议","注销","二方通话","三方通话","保持"];
AcornPhone.prototype.getValidStatus = function getValidStatus(){
	return (this.statusTable[this.status])
}
AcornPhone.prototype.getStatusName = function (status){
	if (status == null || status == undefined ){
		return (this.statusNams[this.status])
	}else {
		if (status >= 0 && status <=10){
			return (this.statusNams[status])
		}else{
			return null;
		}
	}
}	
//调用事件响应函数
AcornPhone.prototype.onStatusChange=function(){
	switch (this.status){
		case 0:{
			if (this.onReady != null){
				this.onReady(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}
		case 1:{
			if (this.onLeaving != null){
				this.onLeaving(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}
		case 2:{
			if (this.onRinging != null){
				this.onRinging(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}
		case 3:{
			if (this.onTalking != null){
				this.onTalking(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}
		case 4:{
			if (this.onProcessing != null){
				this.onProcessing(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}
		case 5:{
			if (this.onTransfering != null){
				this.onTransfering(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}
		case 6:{
			if (this.onMeeting != null){
				this.onMeeting(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}
		case 7:{
			if (this.onLogout != null){
				this.onLogout(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}
		case 8:{
			if (this.onBiTalking != null,isOutBoound){
				this.onBiTalking(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}case 9:
		{
			if (this.onTriTalking != null){
				this.onTriTalking(this.ani,this.dnsi,this.isOutBoound);
			}
			break;
		}case 10:
		{
			if (this.onHolding != null){
				this.onHolding(this.ani,this.dnsi,this.isOutBoound);
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
AcornPhone.prototype.changeStatus = function (newStatus,ani,dnsi,isOutBoound){
	var isValid = true;
	if (newStatus >=0 && newStatus<=10){
		var validStatus = this.getValidStatus();
		if (validStatus.length >0 && validStatus.indexOf(newStatus)>-1){
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
			this.onStatusChange();
		} else	{
			isValid = false;
		}
	}else{
		isValid = false;
	}

	if (isValid == false){
		throw new Error("invalid status change from " + this.status + " to " + newStatus);
	}
}
	