
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
<html>
<head>
                                                                                                                           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">                                        
                      <title>User</title>                                                                              
                                                                                                                                             
                 <script src="<%=basePath%>/viewJs/user.js"></script>                              
                                                                                                                                                             
                </head>                                                                                                         
                <body>                                                                                                          
                	 <input id="ctxHiidenId" type="hidden" value="${ctx}"/>                                                  
                      	 <form id="queryForm" style="margin:10;text-align: center;">                                               
                            <table width="100%">                                                                                  
                              <tr>                                                                                                
                            <td>id：<input name="id" style="width: 9%"></td>            
                                                                   <td>name：<input name="name" style="width: 9%"></td>            
                                                                   <td>addr：<input name="addr" style="width: 9%"></td>            
                                                                   <td>desc：<input name="desc" style="width: 9%"></td>            
                                                                   <td>birth：<input name="birth" style="width: 9%"></td>            
                                                                   <td>qq：<input name="qq" style="width: 9%"></td>            
                                                                   <td>gender：<input name="gender" style="width: 9%"></td>            
                                                                   <td>cellphone：<input name="cellphone" style="width: 9%"></td>            
                                                                   <td>username：<input name="username" style="width: 9%"></td>            
                                                                   <td>password：<input name="password" style="width: 9%"></td>            
                                                                   <td>isdelete：<input name="isdelete" style="width: 9%"></td>            
                                                                   </tr>                                                                                                
                           <tr>                                                                                                 
                           <td align="center"><a href="#" onclick="clearForm();" class="easyui-linkbutton" iconCls="icon-search">清空</a></td>
            <td align="center"><a href="#" onclick="searchData();" class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
            </tr>                                                                                                                
        </table>                                                                                                                 
    </form>                                                                                                                      
    <div style="padding:10" id="tabdiv">                                                                                         
        <table id="dataTable"></table>                                                                                           
    </div>                                                                                                                       
                                                                                                                                 
<div id="addElement" class="easyui-dialog" closed="true" modal="true" shadow="false" title="add Element"  style="display:none;width:350px;height:400px;padding:10px">   
	<div style="margin:20px 0;"></div>                                                                                                                                     
		<form id="addElementForm" class="easyui-form" method="post" data-options="validate:true">                                                                          
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="id:" name="id" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="name:" name="name" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="addr:" name="addr" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="desc:" name="desc" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="birth:" name="birth" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="qq:" name="qq" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="gender:" name="gender" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="cellphone:" name="cellphone" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="username:" name="username" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="password:" name="password" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox addField" label="isdelete:" name="isdelete" style="width:100%" data-options="required:true">                                         
			</div>                                                                                                                                                         
		</form>                                                                                                                                                            
		<div style="text-align:center;padding:5px 0">                                                                                                                      
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAddForm()" style="width:80px">Submit</a>                                                 
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#addElement').dialog('close')" style="width:80px">Cancel</a>                                
		</div>                                                                                                                                                             
</div>                                                                                                                                                                  
<!-- editElement -->                                                                                                                                                    
                                                                                                                                                                        
<div id="editElement" class="easyui-dialog" closed="true" modal="true" shadow="false" title="edit Element"  style="width:350px;height:400px;padding:10px">              
	<div style="margin:20px 0;"></div>                                                                                                                                     
		<form id="editElementForm" class="easyui-form" method="post" data-options="validate:false">                                                                        
			<input type="hidden" class="editHidden editField" name="id" >                                                                                                  
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="id:" name="id" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="name:" name="name" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="addr:" name="addr" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="desc:" name="desc" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="birth:" name="birth" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="qq:" name="qq" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="gender:" name="gender" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="cellphone:" name="cellphone" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="username:" name="username" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="password:" name="password" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
			<div style="margin-bottom:20px">                                                                                                                               
				<input class="easyui-textbox editField" label="isdelete:" name="isdelete" style="width:100%" data-options="required:true">                                        
			</div>                                                                                                                                                         
		</form>                                                                                                                                                            
		<div style="text-align:center;padding:5px 0">                                                                                                                      
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitEditForm()" style="width:80px">Submit</a>                                                
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#editElement').dialog('close')" style="width:80px">Cancel</a>                               
		</div>                                                                                                                                                             
</div>                                                                                                                                                                  
	                                                                                                                                
	                                                                                                                                
</body>                                                                                                                          
</html>                                                                                                                          
