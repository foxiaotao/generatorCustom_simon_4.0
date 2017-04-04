package dhh.jsp.generator.codegen;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextDocument;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.InsertElementGenerator;

import dhh.jsp.generator.element.AbstractTextElementGenerator;
import dhh.jsp.generator.element.jspContentElementGenerator;


public class JspGenerator extends AbstractJspGenerator{
	 public JspGenerator() {
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
	        sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\r\n");
	        sb.append("<%@ include file=\"base.jsp\"%>\r\n");

			sb.append("<html>\r\n");
			sb.append("<head>\r\n                                                                                                                           ");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">                                        \r\n                      ");
			sb.append("<title>"+baseRecordType+"</title>                                                                              \r\n                             ");
			sb.append("                                                                                                                \r\n                 ");
			sb.append("<script src=\"<%=basePath%>/viewJs/"+baseRecordType.toLowerCase()+".js\"></script>                              \r\n                                             ");
			sb.append("                                                                                                                \r\n                ");
			sb.append("</head>                                                                                                         \r\n                ");
			sb.append("<body>                                                                                                          \r\n                ");
			sb.append("	 <input id=\"ctxHiidenId\" type=\"hidden\" value=\"${ctx}\"/>                                                  \r\n                      ");
			sb.append("	 <form id=\"queryForm\" style=\"margin:10;text-align: center;\">                                               \r\n                    ");
			sb.append("        <table width=\"100%\">                                                                                  \r\n                  ");
			sb.append("            <tr>                                                                                                \r\n                ");
			for (String field : fieldList) {
				sb.append("            <td>"+field+"：<input name=\""+field+"\" style=\"width: "+colnumWidth+"%\"></td>            \r\n                                                       ");
			}
			sb.append("            </tr>                                                                                                \r\n               ");
			sb.append("            <tr>                                                                                                 \r\n               ");
			sb.append("            <td align=\"center\"><a href=\"#\" onclick=\"clearForm();\" class=\"easyui-linkbutton\" iconCls=\"icon-search\">清空</a></td>\r\n");
			sb.append("            <td align=\"center\"><a href=\"#\" onclick=\"searchData();\" class=\"easyui-linkbutton\" iconCls=\"icon-search\">查询</a></td>\r\n");
			sb.append("            </tr>                                                                                                                \r\n");
			sb.append("        </table>                                                                                                                 \r\n");
			sb.append("    </form>                                                                                                                      \r\n");
			sb.append("    <div style=\"padding:10\" id=\"tabdiv\">                                                                                         \r\n");
			sb.append("        <table id=\"dataTable\"></table>                                                                                           \r\n");
			sb.append("    </div>                                                                                                                       \r\n");
			sb.append("                                                                                                                                 \r\n");
			
			
			
			sb.append("<div id=\"addElement\" class=\"easyui-dialog\" closed=\"true\" modal=\"true\" shadow=\"false\" title=\"add Element\"  style=\"display:none;width:350px;height:400px;padding:10px\">   \r\n");
			sb.append("	<div style=\"margin:20px 0;\"></div>                                                                                                                                     \r\n");
			sb.append("		<form id=\"addElementForm\" class=\"easyui-form\" method=\"post\" data-options=\"validate:true\">                                                                          \r\n");
			for (String field : fieldList) {
				
				sb.append("			<div style=\"margin-bottom:20px\">                                                                                                                               \r\n");
				sb.append("				<input class=\"easyui-textbox addField\" label=\""+field+":\" name=\""+field+"\" style=\"width:100%\" data-options=\"required:true\">                                         \r\n");
				sb.append("			</div>                                                                                                                                                         \r\n");
			}
			sb.append("		</form>                                                                                                                                                            \r\n");
			sb.append("		<div style=\"text-align:center;padding:5px 0\">                                                                                                                      \r\n");
			sb.append("			<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"submitAddForm()\" style=\"width:80px\">Submit</a>                                                 \r\n");
			sb.append("			<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#addElement').dialog('close')\" style=\"width:80px\">Cancel</a>                                \r\n");
			sb.append("		</div>                                                                                                                                                             \r\n");
			sb.append("</div>                                                                                                                                                                  \r\n");
			sb.append("<!-- editElement -->                                                                                                                                                    \r\n");
			sb.append("                                                                                                                                                                        \r\n");
			sb.append("<div id=\"editElement\" class=\"easyui-dialog\" closed=\"true\" modal=\"true\" shadow=\"false\" title=\"edit Element\"  style=\"width:350px;height:400px;padding:10px\">              \r\n");
			sb.append("	<div style=\"margin:20px 0;\"></div>                                                                                                                                     \r\n");
			sb.append("		<form id=\"editElementForm\" class=\"easyui-form\" method=\"post\" data-options=\"validate:false\">                                                                        \r\n");
			sb.append("			<input type=\"hidden\" class=\"editHidden editField\" name=\"id\" >                                                                                                  \r\n");
			for (String field : fieldList) {
				sb.append("			<div style=\"margin-bottom:20px\">                                                                                                                               \r\n");
						sb.append("				<input class=\"easyui-textbox editField\" label=\""+field+":\" name=\""+field+"\" style=\"width:100%\" data-options=\"required:true\">                                        \r\n");
				sb.append("			</div>                                                                                                                                                         \r\n");
			}
			sb.append("		</form>                                                                                                                                                            \r\n");
			sb.append("		<div style=\"text-align:center;padding:5px 0\">                                                                                                                      \r\n");
			sb.append("			<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"submitEditForm()\" style=\"width:80px\">Submit</a>                                                \r\n");
			sb.append("			<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#editElement').dialog('close')\" style=\"width:80px\">Cancel</a>                               \r\n");
			sb.append("		</div>                                                                                                                                                             \r\n");
			sb.append("</div>                                                                                                                                                                  \r\n");
			sb.append("	                                                                                                                                \r\n");
			sb.append("	                                                                                                                                \r\n");
			sb.append("</body>                                                                                                                          \r\n");
			sb.append("</html>                                                                                                                          \r\n");
	        
	        
	        
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
	}
