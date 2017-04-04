
$(function() {
$('#dataTable').datagrid({
	url : $("#ctxHiidenId").val() + "/User/findByPage.do",
	columns : [ [ 
	{field:'ck',checkbox:true,width:2}, 
	{										
		field : 'id', // 这个对应的是实体类类里面属性
		title : 'id',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'name', // 这个对应的是实体类类里面属性
		title : 'name',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'addr', // 这个对应的是实体类类里面属性
		title : 'addr',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'desc', // 这个对应的是实体类类里面属性
		title : 'desc',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'birth', // 这个对应的是实体类类里面属性
		title : 'birth',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'qq', // 这个对应的是实体类类里面属性
		title : 'qq',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'gender', // 这个对应的是实体类类里面属性
		title : 'gender',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'cellphone', // 这个对应的是实体类类里面属性
		title : 'cellphone',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'username', // 这个对应的是实体类类里面属性
		title : 'username',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'password', // 这个对应的是实体类类里面属性
		title : 'password',
		width : '9%',
		align : 'center'
	}
    ,
	{										
		field : 'isdelete', // 这个对应的是实体类类里面属性
		title : 'isdelete',
		width : '9%',
		align : 'center'
	}
 ] ],
	toolbar : [ {
		text : '新增User',
		iconCls : 'icon-add',
		handler : function() {
			$('#addElement').dialog('open');
			$('#addElement').panel('move',{top:$(document).scrollTop()+($(window).height()-400)*0.5})
		}
	}, '-', {
		text : '更新User',
		iconCls : 'icon-edit',
		handler : function() {
			var selections = $('#dataTable').datagrid('getSelections');//返回第一个被选中的行
			if( selections.length !=1){
				showWarningDialog("请选择一行数据！");
				return;
			}
			$('#editElement').dialog('open');
			$('#editElement').panel('move',{top:$(document).scrollTop()+($(window).height()-400)*0.5});
			
			var row = selections[0];
			var ele = $(".editField");
			var fieldName = '';
			for (var i = 0; i < ele.length; i++) {
				var input = $(ele[i]);
				if(i==0){
					if(input.hasClass("editHidden")){
						input.val(row[input.attr("name")]);//设置值
						input.attr("textboxname",input.attr("name"));//加一个属性，再提交时，都从textboxname的value作为属性名传值给后台
					}
				}else{
					fieldName = input.attr("textboxname");
					input.next().children("input[type=text]").click();//获取鼠标点击事件，让鼠标点（别的input聚焦，这个input就获取离焦事件，触发easyui验证）
					input.next().children("input[type=text]").focus();//鼠标点击后，获取聚焦事件，
					input.next().children("input[type=text]").val(row[fieldName]);//设值
				}
			}
		}
	}, '-', {
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
			var selections = $('#dataTable').datagrid('getSelections');//返回第一个被选中的行
			if( selections.length < 1){
				showWarningDialog("请至少选择一行数据！");
				return;
			}
			deleteSubmit(selections);
		}
	}, '-' ],
	onLoadSuccess : function() {
		$('#dataTable').datagrid('clearSelections'); // 一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题
	},
	iconCls:'icon-edit', //图标  
    singleSelect:false, //多选  
    height:360, //高度  
    fitColumns: true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。  
    striped: true, //奇偶行颜色不同  
    collapsible:true,//可折叠  
	pagination : true,
	rownumbers : true,
	pageNumber : 1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ]

});
})
function submitAddForm(){                                                                                      
	var ele = $(".addField");                                                                                   
	var param = {};                                                                                             
	for (var i = 0; i < ele.length; i++) {                                                                      
		var input = $(ele[i]);                                                                                  
		var dop = input.attr("data-options");//属性名，是否必须                                                         
		var fieldNameChn = '';//属性中文名称                                                                          
		var fieldValue = input.val();//属性的值                                                                     
		if(dop!=undefined){                                                                                     
			fieldNameChn = input.attr("label");                                                                 
			fieldNameChn = fieldNameChn.replace(":","");                                                        
			//如果包含required:true 就是必填字段，注意jsp页面，data-options属性的值不要有多余的空格                                         
			if(dop.indexOf("required:true") >-1 || (dop.indexOf("required") > -1 && dop.indexOf("true") > -1)){ 
				if(fieldValue==undefined || fieldValue==null || fieldValue==''){                                
					showWarningDialog(fieldNameChn + " 不能为空！");                                                 
					return;                                                                                     
				}                                                                                               
			}                                                                                                   
		}                                                                                                       
		param[input.attr("textboxname")] = fieldValue;                                                          
		                                                                                                        
	}                                                                                                           
	$.ajax({                                                                                                    
		url : $("#ctxHiidenId").val() + "/User/insert.do",                                                   
		cache : false,                                                                                          
		type : "POST",                                                                                          
		dataType : "json",                                                                                      
		data :param,                                                                                            
		success : function(result) {                                                                            
			if (result != null && result != undefined && result.success != null && result.success != undefined){
				showSuccessDialog(result.success);                                                              
				searchData();                                                                                   
				$('#addElementForm').form('clear');                                                             
				$('#addElement').dialog('close');                                                               
			}else{                                                                                              
				showErrorDialog(result.msg);                                                                    
			}                                                                                                   
		},                                                                                                      
		error : function() {                                                                                    
			showErrorDialog("添加失败");                                                                            
			return;                                                                                             
		}                                                                                                       
	});                                                                                                         
}                                                                                                              
function submitEditForm(){                                                                                                
	var ele = $(".editField");                                                                                            
	var param = {};                                                                                                       
	for (var i = 0; i < ele.length; i++) {                                                                                
		var input = $(ele[i]);                                                                                            
		var dop = input.attr("data-options");//属性名，是否必须                                                                   
		var fieldNameChn = '';//属性中文名称                                                                                    
		var fieldValue = input.val();//属性的值                                                                               
		if(dop!=undefined){                                                                                               
			fieldNameChn = input.attr("label");                                                                           
			fieldNameChn = fieldNameChn.replace(":","");                                                                  
			//如果包含required:true 就是必填字段，注意jsp页面，data-options属性的值不要有多余的空格                                                   
			if(dop.indexOf("required:true") >-1 || (dop.indexOf("required") > -1 && dop.indexOf("true") > -1)){           
				if(fieldValue==undefined || fieldValue==null || fieldValue==''){                                          
					showWarningDialog(fieldNameChn + " 不能为空！");                                                           
					return;                                                                                               
				}                                                                                                         
			}                                                                                                             
		}                                                                                                                 
		param[input.attr("textboxname")] = fieldValue;                                                                    
		                                                                                                                  
	}                                                                                                                     
	$.ajax({                                                                                                              
		url : $("#ctxHiidenId").val() + "/User/update.do",                                                             
		cache : false,                                                                                                    
		type : "POST",                                                                                                    
		dataType : "json",                                                                                                
		data :param,                                                                                                      
		success : function(result) {                                                                                      
			if (result != null && result != undefined && result.success != null && result.success != undefined) {         
				showSuccessDialog(result.success);                                                                        
				searchData();                                                                                             
				$('#editElementForm').form('clear');                                                                      
				$('#editElement').dialog('close');                                                                        
			}else{                                                                                                        
				showErrorDialog(result.msg);                                                                              
			}                                                                                                             
		},                                                                                                                
		error : function() {                                                                                              
			showErrorDialog("编辑失败");                                                                                      
			return;                                                                                                       
		}                                                                                                                 
	});                                                                                                                   
}                                                                                                                         
function deleteSubmit(selections){                                                                                    
	var idsStr = '';                                                                                                  
	for (var i = 0; i < selections.length; i++) {                                                                     
		if(i>0){                                                                                                      
			idsStr += ',';                                                                                            
		}                                                                                                             
		idsStr += selections[i].id;                                                                                   
	}                                                                                                                 
	var param = {};                                                                                                   
	param['ids'] = idsStr;                                                                                            
	$.ajax({                                                                                                          
		url : $("#ctxHiidenId").val() + "/User/delete.do",                                                         
		cache : false,                                                                                                
		type : "POST",                                                                                                
		dataType : "json",                                                                                            
		data :param,                                                                                                  
		success : function(result) {                                                                                  
			if (result != null && result != undefined && result.success != null && result.success != undefined) {     
				showSuccessDialog(result.success);                                                                    
				searchData();                                                                                         
				$('#addElementForm').form('clear');                                                                   
				$('#addElement').dialog('close');                                                                     
			}else{                                                                                                    
				showErrorDialog(result.msg);                                                                          
			}                                                                                                         
		},                                                                                                            
		error : function() {                                                                                          
			showErrorDialog("删除失败");                                                                                  
			return;                                                                                                   
		}                                                                                                             
	});                                                                                                               
}                                                                                                                     
function searchData() {                                                         
	var params = $('#dataTable').datagrid('options').queryParams; // 先取得        
	// datagrid                                                                 
	// 的查询参数                                                                    
	var fields = $('#queryForm').serializeArray(); // 自动序列化表单元素为JSON对象          
	$.each(fields, function(i, field) {                                         
		params[field.name] = field.value; // 设置查询参数                             
	});                                                                         
	$('#dataTable').datagrid('reload'); // 设置好查询参数 reload 一下就可以了                
}                                                                               
// 清空查询条件                                                                       
function clearForm() {                                                          
	$('#queryForm').form('clear');                                              
	searchData();                                                               
}                                                                               
