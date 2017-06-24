var user;
$(function(){
	user=JSON.parse(getCookie("user"));
});
function MyGrid(tabWidth,tabTitle,objId){
	this.objId=objId;
	this.tabWidth=tabWidth;
	this.tabTitle=tabTitle;
	this.maxrow=0;//0默认全部查询
	//初始化table
	var allwidth=0;
	for(var j=0;j<tabWidth.length;j++){allwidth+=tabWidth[j];}
	var html='<table width="'+allwidth+'px" class="mygrid"><thead><tr>';
	//初始化标题
	var i=0;
	for(var key in tabTitle){
		html+='<th width="'+tabWidth[i]+'px">'+tabTitle[key]+'</th>';
		i++;
	}
	html+="</tr></thead><tbody></tbody></table>"
	$("#"+objId).html(html);
}
MyGrid.prototype.addrow=function(thedata){
	var html='<tr onclick="clicktd(this)">';
	var i=0;
	for(var key in this.tabTitle){
		var value=thedata[key];
		if(value==undefined){value="";}
		var index=$("#"+this.objId+" > table tbody tr").size();
		html+='<td id="'+(key+"__"+index)+'" width="'+this.tabWidth[i]+'px">'+value+'</td>';
		i++;
	}
	html+="</tr>"
	$("#"+this.objId+" > table tbody").append(html);
}
MyGrid.prototype.clear=function(){
	$("#"+this.objId+" > table tbody").html("");
}
MyGrid.prototype.editGrid=function(td,colomnArry){
	var index =td.attr("id").split("__")[1];
	for(var i in colomnArry){
		var tdid=colomnArry[i]+"__"+index;
		$("#"+tdid).html('<input name="'+colomnArry[i]+'" type="text" class="input" style="width:calc(100% - 8px);" value="'+$("#"+tdid).text()+'">');
	}
}
MyGrid.prototype.getEditGrid=function(td){
	var obj=new Object();
	td.parent().find("input").each(function(){
		obj[$(this).attr("name")]=$(this).val()
	});
	return obj;
}
MyGrid.prototype.initpage=function(page,allpage,max,pagedivid){
	var prepage=page-1;
	var nextpage=page+1;
	if(prepage<1){prepage=1;}
	if(nextpage>allpage){nextpage=allpage;}
	if(max==0){max="全部";}
	var html="";
	html+='<div style="padding-right: 30px;padding-left:10px;font-size: 14px;height: 100%;line-height: 40px;">';
	html+='<div id="displayrow" style="position: absolute;width: 52px;z-index: 10;margin-top: -97px;margin-left:61px;border: 1px solid #C4C4C4;line-height: 25px;text-align: center;border-radius:5px;background-color: white;font-size: 13px;display: none;">';
	html+='<div onclick="updateMaxRow(20)" style="border-bottom: 1px solid #C4C4C4;cursor: pointer;">20条</div>';
	html+='<div onclick="updateMaxRow(30)" style="border-bottom: 1px solid #C4C4C4;cursor: pointer;">30条</div>';
	html+='<div onclick="updateMaxRow(50)" style="border-bottom: 1px solid #C4C4C4;cursor: pointer;">50条</div>';
	html+='<div onclick="updateMaxRow(80)" style="border-bottom: 1px solid #C4C4C4;cursor: pointer;">80条</div>';
	html+='<div onclick="updateMaxRow(0)" style="cursor: pointer;">全部</div>';
	html+='</div>';
	html+='每页显示<span style="border-right: #2982C6 1px solid;padding: 3px;margin-left: 5px;padding-left: 11px;padding-right: 5px;border-top-left-radius:5px;border-bottom-left-radius:5px;background-color: #1395ED;color: white;"><span id="currentdisplayrow" style="color: white;">'+max+'</span>';
	html+='</span><span onclick="displayrow()" style="padding: 3px;margin-right: 5px;padding-left: 4px;padding-right: 5px;border-top-right-radius:5px;border-bottom-right-radius:5px;background-color: #1395ED;color: white;cursor: pointer;"><i class="fa fa-caret-up"></i></span>条</div>';
	html+='<div style="padding-left: 10px;padding-right: 10px;height: 100%;line-height: 40px;color: #1395ED;"><i onclick="updatePage(1)" style="cursor: pointer;" class="fa fa-step-backward" title=""></i></div>';
	html+='<div style="padding-left: 10px;padding-right: 15px;height: 100%;line-height: 45px;color: #1395ED;"><i onclick="updatePage('+prepage+')" style="font-size: 25px;cursor: pointer;" class="fa fa-caret-left"></i></div>';
	html+='<div style="line-height: 40px;padding-right: 10px;"><span style="border-left: 1px solid #CCCCCC;padding-left: 7px;">Page</span><span style="padding-left: 5px;padding-right: 5px;">'+page+'</span><span style="padding-left: 5px;padding-right: 5px;">of</span><span style="padding-left: 5px;padding-right: 7px;border-right: 1px solid #CCCCCC;">'+allpage+'</span></div>';
	html+='<div style="padding-right: 10px;padding-left:5px;height: 100%;line-height: 45px;color: #1395ED;"><i onclick="updatePage('+nextpage+')" style="font-size: 25px;cursor: pointer;" class="fa fa-caret-right"></i></div>';
	html+='<div style="padding-left: 10px;padding-right: 10px;height: 100%;line-height: 40px;color: #1395ED;"><i onclick="updatePage('+allpage+')" style="cursor: pointer;" class="fa fa-step-forward"></i></div>';
	if(pagedivid==null || pagedivid== undefined){pagedivid="pagediv";}
	$("#"+pagedivid).html(html);
}
function Sqls(){
	this.listsql=new Array();
}
Sqls.prototype.add=function(sqlmode,args){
	this.listsql[this.listsql.length]={sql:sqlmode,args:args};
}
Sqls.prototype.get=function(){
	var sqls_obj={"sqls":JSON.stringify(this.listsql)};
	return sqls_obj;
}
//异步不加密post提交数据
function $post(url,data,fun,dataType){
	$.ajax({
        url:url,
        type:'POST',
        data:data,
        success:fun,
        async:false,
        dataType:dataType
     });
}
//同步不加密post提交数据
function post(url,data,fun,dataType){
	$.ajax({
        url:url,
        type:'POST',
        data:data,
        success:fun,
        async:false,
        dataType:dataType
     });
}
function clicktd(element){
	$(".mygrid tbody tr").css("background-color","");
	$(element).css("background-color","#E7E7E7");
}
function displayrow(){
	var displayrow = $("#displayrow").css("display");
	if(displayrow=="none"){
		$("#displayrow").css("display","");
	}else{
		$("#displayrow").css("display","none");
	}
}
function openurl(url,thedata){
	if(thedata==null){
		thedata=new Object();
		thedata["menu1Name"]=$("#menu1Name").text();
		thedata["menu2Name"]=$("#menu2Name").text();
	}else{
		thedata["menu1Name"]=$("#menu1Name").text();
		thedata["menu2Name"]=$("#menu2Name").text();
	}
	parent.openframe(url,thedata);
};
function getorderbynum(minNum,maxNum){
	var zhijian=Math.round((Math.random()*(maxNum-minNum)+minNum)*100000)/100000;
	if(zhijian==minNum || zhijian==maxNum){
		for(var i=0;i<10;i++){
			var temp=Math.round((Math.random()*(maxNum-minNum)+minNum)*100000)/100000;
			if(temp > minNum && temp < maxNum ){
				zhijian=temp;
			}
		}
	}
	return zhijian;
}
/*checkForm 为自动检验form表单，1:非必填写项要加入class:noinput,2:必填项 必须加入title作为提示，3:parentObjId 为包含所有表单元素的id  
支持input,checkbox(返回多值会用","隔开),radio,textarea,select
*/
function checkForm(parentObjId){
	var returnvalue=new Object();
	var istongguo=true;
	$("#"+parentObjId+" input[type!='checkbox'][type!='radio'][type!='file'],#"+parentObjId+" input:checked,#"+parentObjId+" select,#"+parentObjId+" textarea").each(function(){
		if(istongguo){
			if($.trim($(this).val())=="" && !$(this).hasClass("noinput") && $(this).css("display") != "none"){
				alert("抱歉,["+$(this).attr("title")+"]为必选项");
				istongguo=false;
			}else{
				if($(this).attr("type")=="checkbox"){
					if(returnvalue[$(this).attr("name")]==undefined){
						returnvalue[$(this).attr("name")]=$(this).val();
					}else{
						returnvalue[$(this).attr("name")]=returnvalue[$(this).attr("name")]+","+$(this).val();
					}
				}else{
					returnvalue[$(this).attr("name")]=$(this).val();
				}
			}
		}
	});
	if(istongguo){
		return returnvalue;
	}else{
		return null;
	}
}
/*自动填充数据  支持input,radio,checkbox(多值用,号隔开),select,textarea
*/
function fillForm(parentObjId,thedata){
	for(var key in thedata){
		var formObj = $("#"+parentObjId+" *[name='"+key+"'][type!='file']");
		var jsObj=formObj[0];
		if(jsObj != undefined){
			if(jsObj.tagName=="SELECT"){
				formObj.children("option[value='"+thedata[key]+"']").prop("selected",true);
			}else if(jsObj.tagName=="INPUT" && formObj.size()>0 && formObj.first().attr("type")=="radio"){
				formObj.filter("[value='"+thedata[key]+"']").prop("checked",true);
			}else if(jsObj.tagName=="INPUT" && formObj.size()>0 && formObj.first().attr("type")=="checkbox"){
				if(thedata[key]!=undefined){
					var checkvalues=thedata[key].split(",");
					for(var i=0;i<checkvalues.length;i++){
						var checkvalue=checkvalues[i];
						formObj.filter("[value='"+checkvalue+"']").prop("checked",true);
					}
				}
			}else{
				formObj.val(thedata[key])
			}
		}
	}
}
function uploadfile(fileid,fun){
	$.ajaxFileUpload(
	    {
	        url:'uploadfile',
	        secureuri:false,
	        fileElementId:fileid,
	        dataType: 'JSON',
	        success: function (data, status){
	        	if (((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)) || ((navigator.userAgent.toLowerCase().indexOf("trident") > -1 && navigator.userAgent.indexOf("rv") > -1))){
	        		
        		}else if (navigator.userAgent.indexOf('Firefox') >= 0){
        			data=$(data).text();
        		}else if (navigator.userAgent.indexOf('Opera') >= 0){
        			data=$(data).text();
        		}else{
        			data=$(data).text();
        		}
	        	fun.apply(this,[data]);
	        }
	    }
	)
}
function downloadfile(fileurl){
	if(new RegExp("^<").test(fileurl)){
		fileurl=$(fileurl).text();
	}
	if(new RegExp("^http").test(fileurl)){
		$("body").append('<iframe style="display: none;" src="'+fileurl+'"></iframe>');
	}else{
		$("body").append('<iframe style="display: none;" src="'+fileRootUrl+fileurl+'"></iframe>');
	}
}
//对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
function dateFormat(thedate,fmt)   
{ //author: meizz   
  var o = {   
    "M+" : thedate.getMonth()+1,                 //月份   
    "d+" : thedate.getDate(),                    //日   
    "h+" : thedate.getHours(),                   //小时   
    "m+" : thedate.getMinutes(),                 //分   
    "s+" : thedate.getSeconds(),                 //秒   
    "q+" : Math.floor((thedate.getMonth()+3)/3), //季度   
    "S"  : thedate.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (thedate.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 
function getuipush(username,title,message){
	post("send_mobile",{username:username,title:title,message:message},function(data){
	},"text");
}
//写cookies
function setCookie(name,value)
{
var Days = 30;
var exp = new Date();
exp.setTime(exp.getTime() + Days*24*60*60*1000);
document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name)
{
var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
if(arr=document.cookie.match(reg))
return unescape(arr[2]);
else
return null;
}
function delCookie(name)
{
var exp = new Date();
exp.setTime(exp.getTime() - 1);
var cval=getCookie(name);
if(cval!=null)
document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
function trim(param){
	if(param==undefined || param==null){
		return "";
	}else{
		return $.trim(param);
	}
}
//转换对象
function base64encodeByObj(obj){
	var encodeObj=new Object();
	for(var key in obj){
		if(obj[key] != null){
			encodeObj[key]=base64encode(toString(obj[key]));
		}else{
			encodeObj[key]=obj[key];
		}
	}
	return encodeObj;
}
//转换字符串
function base64encode(str){
	$.base64.utf8encode = true;
	return $.base64.btoa(str);
}
function toString(str){ 
	if((typeof str=='string')&&str.constructor==String){
		return str;
	}else{
		return String(str); 
	}
} 
(function() {
	  // Private array of chars to use
	  var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	 
	  Math.uuid1 = function (len, radix) {
	    var chars = CHARS, uuid = [], i;
	    radix = radix || chars.length;
	 
	    if (len) {
	      // Compact form
	      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
	    } else {
	      // rfc4122, version 4 form
	      var r;
	 
	      // rfc4122 requires these characters
	      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	      uuid[14] = '4';
	 
	      // Fill in random data.  At i==19 set the high bits of clock sequence as
	      // per rfc4122, sec. 4.1.5
	      for (i = 0; i < 36; i++) {
	        if (!uuid[i]) {
	          r = 0 | Math.random()*16;
	          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	        }
	      }
	    }
	 
	    return uuid.join('');
	  };
	 
	  // A more performant, but slightly bulkier, RFC4122v4 solution.  We boost performance
	  // by minimizing calls to random()
	  Math.uuid = function() {
	    var chars = CHARS, uuid = new Array(36), rnd=0, r;
	    for (var i = 0; i < 36; i++) {
	      if (i==8 || i==13 ||  i==18 || i==23) {
	        uuid[i] = '-';
	      } else if (i==14) {
	        uuid[i] = '4';
	      } else {
	        if (rnd <= 0x02) rnd = 0x2000000 + (Math.random()*0x1000000)|0;
	        r = rnd & 0xf;
	        rnd = rnd >> 4;
	        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	      }
	    }
	    return uuid.join('');
	  };
	 
	  // A more compact, but less performant, RFC4122v4 solution:
	  Math.uuidCompact = function() {
	    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	      var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	      return v.toString(16);
	    });
	  };
	})();