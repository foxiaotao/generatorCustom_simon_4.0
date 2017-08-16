/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package dhh.jsp.generator.codegen;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.TextDocument;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.codegen.XmlConstants;

import dhh.jsp.generator.element.AbstractTextElementGenerator;
import dhh.jsp.generator.element.jspContentElementGenerator;


public class JsGenerator extends AbstractJsGenerator{
	 public JsGenerator() {
	        super();
	    }

	 protected TextElement getJspElement() {
	        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
	        //实体名
	        String baseRecordType = introspectedTable.getBaseRecordType();
	        baseRecordType = baseRecordType.substring(baseRecordType.lastIndexOf(".")+1);//User
	        
	        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
	        
	        List<String> fieldList = new ArrayList<String>();
	        
	        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
	        	String actualColumnName = introspectedColumn.getJavaProperty();
	        	fieldList.add(actualColumnName);
	        }
	        int colnumWidth = 100/fieldList.size();
	        
	        
	        progressCallback.startTask(getString(
	                "Progress.12", table.toString())); //$NON-NLS-1$
	        StringBuffer sb = new StringBuffer();
	        sb.append("$(function() {\r\n");//jquery头信息
	        sb.append("$('#dataTable').datagrid({\r\n");
	    	sb.append("	url : $(\"#ctxHiidenId\").val() + \"/"+baseRecordType+"/findByPage.do\",\r\n");
	    	sb.append("	columns : [ [ \r\n");
	    	sb.append("	{field:'ck',checkbox:true,width:2}, \r\n");
	    	for (int i = 0;i<fieldList.size();i++) {
	    		if(i>0){
	    			sb.append("    ,\r\n");
	    		}
	    		sb.append("	{										\r\n");
	    		sb.append("		field : '"+fieldList.get(i)+"', // 这个对应的是实体类类里面属性\r\n");
	    		sb.append("		title : '"+fieldList.get(i)+"',\r\n");
	    		sb.append("		width : '"+colnumWidth+"%',\r\n");
	    		sb.append("		align : 'center'\r\n");
	    		sb.append("	}\r\n");
			}
	    	sb.append(" ] ],\r\n");
	    	sb.append("	toolbar : [ {\r\n");
	    	sb.append("		text : '新增"+baseRecordType+"',\r\n");
	    	sb.append("		iconCls : 'icon-add',\r\n");
	    	sb.append("		handler : function() {\r\n");
	    	sb.append("			$('#addElement').dialog('open');\r\n");
	    	sb.append("			$('#addElement').panel('move',{top:$(document).scrollTop()+($(window).height()-400)*0.5})\r\n");
	    	sb.append("		}\r\n");
	    	sb.append("	}, '-', {\r\n");
	    	sb.append("		text : '更新"+baseRecordType+"',\r\n");
	    	sb.append("		iconCls : 'icon-edit',\r\n");
	    	sb.append("		handler : function() {\r\n");
	    	sb.append("			var selections = $('#dataTable').datagrid('getSelections');//返回第一个被选中的行\r\n");
	    	sb.append("			if( selections.length !=1){\r\n");
	    	sb.append("				showWarningDialog(\"请选择一行数据！\");\r\n");
	    	sb.append("				return;\r\n");
	    	sb.append("			}\r\n");
	    	sb.append("			$('#editElement').dialog('open');\r\n");
	    	sb.append("			$('#editElement').panel('move',{top:$(document).scrollTop()+($(window).height()-400)*0.5});\r\n");
	    	sb.append("			\r\n");
	    	sb.append("			var row = selections[0];\r\n");
	    	sb.append("			var ele = $(\".editField\");\r\n");
	    	sb.append("			var fieldName = '';\r\n");
	    	sb.append("			for (var i = 0; i < ele.length; i++) {\r\n");
	    	sb.append("				var input = $(ele[i]);\r\n");
	    	sb.append("				if(i==0){\r\n");
	    	sb.append("					if(input.hasClass(\"editHidden\")){\r\n");
	    	sb.append("						input.val(row[input.attr(\"name\")]);//设置值\r\n");
	    	sb.append("						input.attr(\"textboxname\",input.attr(\"name\"));//加一个属性，再提交时，都从textboxname的value作为属性名传值给后台\r\n");
	    	sb.append("					}\r\n");
	    	sb.append("				}else{\r\n");
	    	sb.append("					fieldName = input.attr(\"textboxname\");\r\n");
	    	sb.append("					input.next().children(\"input[type=text]\").click();//获取鼠标点击事件，让鼠标点（别的input聚焦，这个input就获取离焦事件，触发easyui验证）\r\n");
	    	sb.append("					input.next().children(\"input[type=text]\").focus();//鼠标点击后，获取聚焦事件，\r\n");
	    	sb.append("					input.next().children(\"input[type=text]\").val(row[fieldName]);//设值\r\n");
	    	sb.append("				}\r\n");
	    	sb.append("			}\r\n");
	    	sb.append("		}\r\n");
	    	sb.append("	}, '-', {\r\n");
	    	sb.append("		text : '删除',\r\n");
	    	sb.append("		iconCls : 'icon-remove',\r\n");
	    	sb.append("		handler : function() {\r\n");
	    	sb.append("			var selections = $('#dataTable').datagrid('getSelections');//返回第一个被选中的行\r\n");
	    	sb.append("			if( selections.length < 1){\r\n");
	    	sb.append("				showWarningDialog(\"请至少选择一行数据！\");\r\n");
	    	sb.append("				return;\r\n");
	    	sb.append("			}\r\n");
	    	sb.append("			deleteSubmit(selections);\r\n");
	    	sb.append("		}\r\n");
	    	sb.append("	}, '-' ],\r\n");
	    	sb.append("	onLoadSuccess : function() {\r\n");
	    	sb.append("		$('#dataTable').datagrid('clearSelections'); // 一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题\r\n");
	    	sb.append("	},\r\n");
	    	sb.append("	iconCls:'icon-edit', //图标  \r\n");
	        sb.append("    singleSelect:false, //多选  \r\n");
	        sb.append("    height:360, //高度  \r\n");
	        sb.append("    fitColumns: true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。  \r\n");
	        sb.append("    striped: true, //奇偶行颜色不同  \r\n");
	        sb.append("    collapsible:true,//可折叠  \r\n");
	    	sb.append("	pagination : true,\r\n");
	    	sb.append("	rownumbers : true,\r\n");
	    	sb.append("	pageNumber : 1,\r\n");
	    	sb.append("	pageSize : 10,\r\n");
	    	sb.append("	pageList : [ 10, 20, 30, 40, 50 ]\r\n");
            sb.append("\r\n");
	    	sb.append("});\r\n");
	    	sb.append("})\r\n");
	    	//**加载数据的实现***//*
	    	
	    	
	    	//**添加提交***//*
	    	
	    	//添加提交
	    	sb.append("function submitAddForm(){                                                                                      \r\n"); 
	    	sb.append("	var ele = $(\".addField\");                                                                                   \r\n"); 
	    	sb.append("	var param = {};                                                                                             \r\n"); 
	    	sb.append("	for (var i = 0; i < ele.length; i++) {                                                                      \r\n"); 
	    	sb.append("		var input = $(ele[i]);                                                                                  \r\n"); 
	    	sb.append("		var dop = input.attr(\"data-options\");//属性名，是否必须                                                         \r\n"); 
	    	sb.append("		var fieldNameChn = '';//属性中文名称                                                                          \r\n"); 
	    	sb.append("		var fieldValue = input.val();//属性的值                                                                     \r\n"); 
	    	sb.append("		if(dop!=undefined){                                                                                     \r\n"); 
	    	sb.append("			fieldNameChn = input.attr(\"label\");                                                                 \r\n"); 
	    	sb.append("			fieldNameChn = fieldNameChn.replace(\":\",\"\");                                                        \r\n"); 
	    	sb.append("			//如果包含required:true 就是必填字段，注意jsp页面，data-options属性的值不要有多余的空格                                         \r\n"); 
	    	sb.append("			if(dop.indexOf(\"required:true\") >-1 || (dop.indexOf(\"required\") > -1 && dop.indexOf(\"true\") > -1)){ \r\n"); 
	    	sb.append("				if(fieldValue==undefined || fieldValue==null || fieldValue==''){                                \r\n"); 
	    	sb.append("					showWarningDialog(fieldNameChn + \" 不能为空！\");                                                 \r\n"); 
	    	sb.append("					return;                                                                                     \r\n"); 
	    	sb.append("				}                                                                                               \r\n"); 
	    	sb.append("			}                                                                                                   \r\n"); 
	    	sb.append("		}                                                                                                       \r\n"); 
	    	sb.append("		param[input.attr(\"textboxname\")] = fieldValue;                                                          \r\n"); 
	    	sb.append("		                                                                                                        \r\n"); 
	    	sb.append("	}                                                                                                           \r\n"); 
	    	sb.append("	$.ajax({                                                                                                    \r\n"); 
	    	sb.append("		url : $(\"#ctxHiidenId\").val() + \"/"+baseRecordType+"/insert.do\",                                                   \r\n"); 
	    	sb.append("		cache : false,                                                                                          \r\n"); 
	    	sb.append("		type : \"POST\",                                                                                          \r\n"); 
	    	sb.append("		dataType : \"json\",                                                                                      \r\n"); 
	    	sb.append("		data :param,                                                                                            \r\n"); 
	    	sb.append("		success : function(result) {                                                                            \r\n"); 
	    	sb.append("			if (result != null && result != undefined && result.success != null && result.success != undefined){\r\n");
	    	sb.append("				showSuccessDialog(result.success);                                                              \r\n"); 
	    	sb.append("				searchData();                                                                                   \r\n"); 
	    	sb.append("				$('#addElementForm').form('clear');                                                             \r\n"); 
	    	sb.append("				$('#addElement').dialog('close');                                                               \r\n"); 
	    	sb.append("			}else{                                                                                              \r\n"); 
	    	sb.append("				showErrorDialog(result.msg);                                                                    \r\n"); 
	    	sb.append("			}                                                                                                   \r\n"); 
	    	sb.append("		},                                                                                                      \r\n"); 
	    	sb.append("		error : function() {                                                                                    \r\n"); 
	    	sb.append("			showErrorDialog(\"添加失败\");                                                                            \r\n"); 
	    	sb.append("			return;                                                                                             \r\n"); 
	    	sb.append("		}                                                                                                       \r\n"); 
	    	sb.append("	});                                                                                                         \r\n"); 
	    	sb.append("}                                                                                                              \r\n"); 
	    	
	    	//**添加提交***//*
	    			
	    	//**编辑提交***//*
	    	//修改提交
	    	sb.append("function submitEditForm(){                                                                                                \r\n");
	    	sb.append("	var ele = $(\".editField\");                                                                                            \r\n");
	    	sb.append("	var param = {};                                                                                                       \r\n");
	    	sb.append("	for (var i = 0; i < ele.length; i++) {                                                                                \r\n");
	    	sb.append("		var input = $(ele[i]);                                                                                            \r\n");
	    	sb.append("		var dop = input.attr(\"data-options\");//属性名，是否必须                                                                   \r\n");
	    	sb.append("		var fieldNameChn = '';//属性中文名称                                                                                    \r\n");
	    	sb.append("		var fieldValue = input.val();//属性的值                                                                               \r\n");
	    	sb.append("		if(dop!=undefined){                                                                                               \r\n");
	    	sb.append("			fieldNameChn = input.attr(\"label\");                                                                           \r\n");
	    	sb.append("			fieldNameChn = fieldNameChn.replace(\":\",\"\");                                                                  \r\n");
	    	sb.append("			//如果包含required:true 就是必填字段，注意jsp页面，data-options属性的值不要有多余的空格                                                   \r\n");
	    	sb.append("			if(dop.indexOf(\"required:true\") >-1 || (dop.indexOf(\"required\") > -1 && dop.indexOf(\"true\") > -1)){           \r\n");
	    	sb.append("				if(fieldValue==undefined || fieldValue==null || fieldValue==''){                                          \r\n");
	    	sb.append("					showWarningDialog(fieldNameChn + \" 不能为空！\");                                                           \r\n");
	    	sb.append("					return;                                                                                               \r\n");
	    	sb.append("				}                                                                                                         \r\n");
	    	sb.append("			}                                                                                                             \r\n");
	    	sb.append("		}                                                                                                                 \r\n");
	    	sb.append("		param[input.attr(\"textboxname\")] = fieldValue;                                                                    \r\n");
	    	sb.append("		                                                                                                                  \r\n");
	    	sb.append("	}                                                                                                                     \r\n");
	    	sb.append("	$.ajax({                                                                                                              \r\n");
	    	sb.append("		url : $(\"#ctxHiidenId\").val() + \"/"+baseRecordType+"/update.do\",                                                             \r\n");
	    	sb.append("		cache : false,                                                                                                    \r\n");
	    	sb.append("		type : \"POST\",                                                                                                    \r\n");
	    	sb.append("		dataType : \"json\",                                                                                                \r\n");
	    	sb.append("		data :param,                                                                                                      \r\n");
	    	sb.append("		success : function(result) {                                                                                      \r\n");
	    	sb.append("			if (result != null && result != undefined && result.success != null && result.success != undefined) {         \r\n");
	    	sb.append("				showSuccessDialog(result.success);                                                                        \r\n");
	    	sb.append("				searchData();                                                                                             \r\n");
	    	sb.append("				$('#editElementForm').form('clear');                                                                      \r\n");
	    	sb.append("				$('#editElement').dialog('close');                                                                        \r\n");
	    	sb.append("			}else{                                                                                                        \r\n");
	    	sb.append("				showErrorDialog(result.msg);                                                                              \r\n");
//	    	sb.append("				showWarningDialog("警告消息提示");                                                                              \r\n");
	    	sb.append("			}                                                                                                             \r\n");
	    	sb.append("		},                                                                                                                \r\n");
	    	sb.append("		error : function() {                                                                                              \r\n");
	    	sb.append("			showErrorDialog(\"编辑失败\");                                                                                      \r\n");
	    	sb.append("			return;                                                                                                       \r\n");
	    	sb.append("		}                                                                                                                 \r\n");
	    	sb.append("	});                                                                                                                   \r\n");
	    	sb.append("}                                                                                                                         \r\n");
	    	//**编辑提交***//*
	    	
	    	
	    	//**delete提交***//*
	    	//删除提交
	    	sb.append("function deleteSubmit(selections){                                                                                    \r\n");
	    	sb.append("	var idsStr = '';                                                                                                  \r\n");
	    	sb.append("	for (var i = 0; i < selections.length; i++) {                                                                     \r\n");
	    	sb.append("		if(i>0){                                                                                                      \r\n");
	    	sb.append("			idsStr += ',';                                                                                            \r\n");
	    	sb.append("		}                                                                                                             \r\n");
	    	sb.append("		idsStr += selections[i].id;                                                                                   \r\n");
	    	sb.append("	}                                                                                                                 \r\n");
	    	sb.append("	var param = {};                                                                                                   \r\n");
	    	sb.append("	param['ids'] = idsStr;                                                                                            \r\n");
	    	sb.append("	$.ajax({                                                                                                          \r\n");
	    	sb.append("		url : $(\"#ctxHiidenId\").val() + \"/"+baseRecordType+"/delete.do\",                                                         \r\n");
	    	sb.append("		cache : false,                                                                                                \r\n");
	    	sb.append("		type : \"POST\",                                                                                                \r\n");
	    	sb.append("		dataType : \"json\",                                                                                            \r\n");
	    	sb.append("		data :param,                                                                                                  \r\n");
	    	sb.append("		success : function(result) {                                                                                  \r\n");
	    	sb.append("			if (result != null && result != undefined && result.success != null && result.success != undefined) {     \r\n");
	    	sb.append("				showSuccessDialog(result.success);                                                                    \r\n");
	    	sb.append("				searchData();                                                                                         \r\n");
	    	sb.append("				$('#addElementForm').form('clear');                                                                   \r\n");
	    	sb.append("				$('#addElement').dialog('close');                                                                     \r\n");
	    	sb.append("			}else{                                                                                                    \r\n");
	    	sb.append("				showErrorDialog(result.msg);                                                                          \r\n");
//	    	sb.append("				showWarningDialog("警告消息提示");                                                                          \r\n");
	    	sb.append("			}                                                                                                         \r\n");
	    	sb.append("		},                                                                                                            \r\n");
	    	sb.append("		error : function() {                                                                                          \r\n");
	    	sb.append("			showErrorDialog(\"删除失败\");                                                                                  \r\n");
	    	sb.append("			return;                                                                                                   \r\n");
	    	sb.append("		}                                                                                                             \r\n");
	    	sb.append("	});                                                                                                               \r\n");
	    	sb.append("}                                                                                                                     \r\n");
	    	//**delete提交***//*
	    	
	    	
	    	//**search提交***//*

	    	// 表格查询
	    	sb.append("function searchData() {                                                         \r\n");
	    	sb.append("	var params = $('#dataTable').datagrid('options').queryParams; // 先取得        \r\n");
	    	sb.append("	// datagrid                                                                 \r\n");
	    	sb.append("	// 的查询参数                                                                    \r\n");
	    	sb.append("	var fields = $('#queryForm').serializeArray(); // 自动序列化表单元素为JSON对象          \r\n");
	    	sb.append("	$.each(fields, function(i, field) {                                         \r\n");
	    	sb.append("		params[field.name] = field.value; // 设置查询参数                             \r\n");
	    	sb.append("	});                                                                         \r\n");
	    	sb.append("	$('#dataTable').datagrid('reload'); // 设置好查询参数 reload 一下就可以了                \r\n");
	    	sb.append("}                                                                               \r\n");
	    	sb.append("// 清空查询条件                                                                       \r\n");
	    	sb.append("function clearForm() {                                                          \r\n");
	    	sb.append("	$('#queryForm').form('clear');                                              \r\n");
	    	sb.append("	searchData();                                                               \r\n");
	    	sb.append("}                                                                               \r\n");
	    	//**search提交***//*
	    	
	        
	        
	        
	        TextElement textElement = new TextElement(sb.toString()); //$NON-NLS-1$

//	        context.getCommentGenerator().addRootComment(answer);
	        AbstractTextElementGenerator elementGenerator = new jspContentElementGenerator(false);
	        initializeAndExecuteGenerator(elementGenerator);

	        return textElement;
	    }


	    protected void initializeAndExecuteGenerator(
	    		AbstractTextElementGenerator elementGenerator) {
	        elementGenerator.setContext(context);
	        elementGenerator.setIntrospectedTable(introspectedTable);
	        elementGenerator.setProgressCallback(progressCallback);
	        elementGenerator.setWarnings(warnings);
	    }

	    @Override
	    public TextDocument getDocument() {
	    	TextDocument document = new TextDocument(
	                XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
	                XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
	        document.setTextElement(getJspElement());

	        return document;
	    }
	    
	    
	    private StringBuffer getLine(StringBuffer temp,String lineStr){
	    	return temp.append(lineStr+"\r\n");
	    }
	}
