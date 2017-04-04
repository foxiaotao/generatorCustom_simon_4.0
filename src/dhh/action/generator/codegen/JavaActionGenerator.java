package dhh.action.generator.codegen;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


public class JavaActionGenerator extends AbstractJavaActionGenerator{
	 public JavaActionGenerator() {
	        super(true);
	    }

	    public JavaActionGenerator(boolean requiresMatchedXMLGenerator) {
	        super(requiresMatchedXMLGenerator);
	    }
	    
	    @Override
	    public List<CompilationUnit> getCompilationUnits() {
	    	FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
	        progressCallback.startTask(getString(
	                "Progress.6", table.toString())); //$NON-NLS-1$
	        CommentGenerator commentGenerator = context.getCommentGenerator();
	        JDBCConnectionConfiguration jdbcConnectionConfiguration = context.getJdbcConnectionConfiguration();
	        
	        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
	                introspectedTable.getJavaActionType());
	        
	        FullyQualifiedJavaType demo = new FullyQualifiedJavaType(
	        		introspectedTable.getBaseRecordType());
	        
	        TopLevelClass topLevelClass = new TopLevelClass(type);
	        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
	        topLevelClass.addAnnotation("@Controller");
	        topLevelClass.addAnnotation("@RequestMapping(value=\"/"+demo.getShortName()+"\")");
	        topLevelClass.addImportedType("org.springframework.stereotype.Controller");
	        topLevelClass.addImportedType("org.springframework.web.bind.annotation.RequestMapping");
	        topLevelClass.addImportedType("org.springframework.web.bind.annotation.ResponseBody");
	        commentGenerator.addJavaFileComment(topLevelClass);

	        Field field = new Field();
	        //field.setVisibility(JavaVisibility.PUBLIC);
	        field.setType(new FullyQualifiedJavaType(introspectedTable.getJavaIServiceType()));
	        
	        topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
	        topLevelClass.addImportedType(new FullyQualifiedJavaType(introspectedTable.getJavaIServiceType()));
	        field.setName(StringUtility.classNameToObjectName(new FullyQualifiedJavaType(introspectedTable.getJavaServiceImplType()).getShortName()));//属性名
	        field.addAnnotation("@Autowired");
	        commentGenerator.addFieldComment(field, introspectedTable);
	        topLevelClass.addField(field);
	        
	        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
	        Method method = null;
	        FullyQualifiedJavaType fqjt=null;
	        FullyQualifiedJavaType returnType=null;
	        FullyQualifiedJavaType listType=null;    
	        
	        /**index方法的实现***/
	        if(introspectedTable.getRules().generateDeleteByPrimaryKey()){
			        method = new Method();
			        method.addAnnotation("@RequestMapping(value=\"/index.do\")");
			        method.setVisibility(JavaVisibility.PUBLIC);
			        List<IntrospectedColumn> introspectedColumns = introspectedTable
			                .getPrimaryKeyColumns();
			        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			            type = introspectedColumn
			                    .getFullyQualifiedJavaType();
			            Parameter parameter = new Parameter(type, introspectedColumn
			                    .getJavaProperty());
			            method.addParameter(parameter);
			        }
			        method.addBodyLine("return \""+demo.getShortName().toLowerCase()+"\";");
			        
			        method.setName("index");
			        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
			        method.addException(new FullyQualifiedJavaType("Exception"));
			        commentGenerator.addGeneralMethodComment(method, introspectedTable);
			        topLevelClass.addMethod(method);
	        }
	        
	        /**insert方法的实现***/
	        method = new Method();
	        method.setVisibility(JavaVisibility.PUBLIC);
	        fqjt = new FullyQualifiedJavaType(
	                introspectedTable.getBaseRecordType());
	        method.setName("insert");
	        method.addAnnotation("@RequestMapping(value=\"/insert.do\",method = RequestMethod.POST)");
	        method.addAnnotation("@ResponseBody");
	        method.addParameter(new Parameter(fqjt, "record"));
	        method.addBodyLine("Map<String,Object> map = new HashMap<String, Object>();");
	        method.addBodyLine(field.getName()+".insertSelective(record);//mapper.insertSelective(...)");
	        method.addBodyLine("map.put(\"success\", \"提示：添加成功！\");");
	        method.addBodyLine("return map;");
	        
	        returnType = FullyQualifiedJavaType.getNewMapInstance();
	        returnType.addTypeArgument(new FullyQualifiedJavaType("String,Object"));
	        method.setReturnType(returnType);
	        method.addException(new FullyQualifiedJavaType("Exception"));
	        topLevelClass.addImportedType(fqjt);
	        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMethod"));//
	        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.HashMap"));
	        commentGenerator.addGeneralMethodComment(method, introspectedTable);
	        topLevelClass.addMethod(method);
	        /**insert方法的实现***/

	        
	        
	        /**insertSelective方法的实现***/
//	        method = new Method();
//	        method.setVisibility(JavaVisibility.PUBLIC);
//	        fqjt = new FullyQualifiedJavaType(
//	                introspectedTable.getBaseRecordType());
//	        method.setName("insertSelective");
//	        method.addParameter(new Parameter(fqjt, "record"));
//	        method.addBodyLine("return "+field.getName()+".insertSelective(record);");
//	        method.addBodyLine("map.put(\"success\", \"提示：添加成功！\");");
//	        method.addBodyLine("return map;");
//	        returnType = FullyQualifiedJavaType.getNewMapInstance();
//	        returnType.addTypeArgument(new FullyQualifiedJavaType("String,Object"));
//	        method.setReturnType(returnType);
//	        method.addException(new FullyQualifiedJavaType("Exception"));
//	        topLevelClass.addImportedType(fqjt);
//	        commentGenerator.addGeneralMethodComment(method, introspectedTable);
//	        topLevelClass.addMethod(method);
	        /**insertSelective方法的实现***/
	        
    
	        
	        /**delete方法的实现***/
	        if(introspectedTable.getRules().generateDeleteByPrimaryKey()){
			        method = new Method();
			        method.addAnnotation("@RequestMapping(value=\"/delete.do\",method = RequestMethod.POST)");
			        method.addAnnotation("@ResponseBody");
			        method.setVisibility(JavaVisibility.PUBLIC);
			        List<IntrospectedColumn> introspectedColumns = introspectedTable
			                .getPrimaryKeyColumns();
			        method.addBodyLine("Map<String,Object> map = new HashMap<String, Object>();");
			        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			            type = introspectedColumn
			                    .getFullyQualifiedJavaType();
			            //topLevelClass.addImportedType(type);
			            Parameter parameter = new Parameter(type, introspectedColumn
			                    .getJavaProperty());
			            method.addParameter(parameter);
			            method.addBodyLine(field.getName()+".deleteByPrimaryKey("+introspectedColumn
			                    .getJavaProperty()+");");
			        }
			        method.addBodyLine("map.put(\"success\", \"提示：删除成功！\");");
			        method.addBodyLine("return map;");
			        returnType = FullyQualifiedJavaType.getNewMapInstance();
			        returnType.addTypeArgument(new FullyQualifiedJavaType("String,Object"));
			        method.setName("delete");
			        method.setReturnType(returnType);
			        method.addException(new FullyQualifiedJavaType("Exception"));
			        commentGenerator.addGeneralMethodComment(method, introspectedTable);
			        topLevelClass.addMethod(method);
	        }
	        
	        /**delete批量方法的实现***/
	        if(introspectedTable.getRules().generateDeleteByPrimaryKey()){
			        method = new Method();
			        method.addAnnotation("@RequestMapping(value=\"/deleteBatch.do\",method = RequestMethod.POST)");
			        method.addAnnotation("@ResponseBody");
			        method.setVisibility(JavaVisibility.PUBLIC);
//			        List<IntrospectedColumn> introspectedColumns = introspectedTable
//			                .getPrimaryKeyColumns();
			        
			        type = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();
		            Parameter parameter = new Parameter(FullyQualifiedJavaType.getStringInstance(), "ids");
		            method.addParameter(parameter);
		            
		            method.addBodyLine("Map<String,Object> map = new HashMap<String, Object>();");
		            method.addBodyLine("String idsArr[] = ids.split(\",\");");
		            method.addBodyLine("for (int i = 0; i < idsArr.length; i++) {");
		            method.addBodyLine(field.getName()+".deleteByPrimaryKey("+type+".valueOf(idsArr[i]));");
		            method.addBodyLine("}");
		            method.addBodyLine("map.put(\"success\", \"提示：批量删除成功！\");");
		            method.addBodyLine("return map;");
//			            method.addBodyLine(field.getName()+".deleteByPrimaryKey("+introspectedColumn
//			            		.getJavaProperty()+");");
			        returnType = FullyQualifiedJavaType.getNewMapInstance();
			        returnType.addTypeArgument(new FullyQualifiedJavaType("String,Object"));
			        method.setName("deleteBatch");
			        method.setReturnType(returnType);
			        method.addException(new FullyQualifiedJavaType("Exception"));
			        commentGenerator.addGeneralMethodComment(method, introspectedTable);
			        topLevelClass.addMethod(method);
	        }
	        

	        
	        /**updateByPrimaryKeySelective方法的实现***/
	        if(introspectedTable.getRules().generateDeleteByPrimaryKey()){
		        method = new Method();
		        
		        if (introspectedTable.getRules().generateBaseRecordClass()) {
		            listType = new FullyQualifiedJavaType(introspectedTable
		                    .getBaseRecordType());
		        } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
		            listType = new FullyQualifiedJavaType(introspectedTable
		                    .getPrimaryKeyType());
		        } else {
		            throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
		        }
		        
		        returnType = FullyQualifiedJavaType.getNewMapInstance();
		        returnType.addTypeArgument(new FullyQualifiedJavaType("String,Object"));
		        method.addAnnotation("@RequestMapping(value=\"/update.do\",method = RequestMethod.POST)");
		        method.addAnnotation("@ResponseBody");
		        method.setVisibility(JavaVisibility.PUBLIC);
		        fqjt = new FullyQualifiedJavaType(
		                introspectedTable.getExampleType());
		        method.setName("update");
		        
		        method.addParameter(new Parameter(listType, "record"));
		        method.addBodyLine("Map<String,Object> map = new HashMap<String, Object>();");
		        method.addBodyLine(field.getName()+".updateByPrimaryKeySelective(record);");
		        method.addBodyLine("map.put(\"success\", \"提示：编辑成功！\");");
		        method.addBodyLine("return map;");
		        method.setReturnType(returnType);
		        method.addException(new FullyQualifiedJavaType("Exception"));
		        commentGenerator.addGeneralMethodComment(method, introspectedTable);
		        topLevelClass.addMethod(method);
	        }
	        /**updateByPrimaryKeySelective方法的实现***/
	        
	        
	        
	        /**selectByExample方法的实现***/
//	        method = new Method();
//	        
//	        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
//	                .getNewListInstance();
//	        
//	        method.setVisibility(JavaVisibility.PUBLIC);
//	        fqjt = new FullyQualifiedJavaType(
//	                introspectedTable.getExampleType());
//	        method.setName("selectByExample");
//	        method.addParameter(new Parameter(fqjt, "example"));
//	        method.addBodyLine("return "+field.getName()+".selectByExample(example);");
//	        method.setReturnType(returnType);
//	        
//	        FullyQualifiedJavaType listType;
//	        if (introspectedTable.getRules().generateBaseRecordClass()) {
//	            listType = new FullyQualifiedJavaType(introspectedTable
//	                    .getBaseRecordType());
//	        } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
//	            listType = new FullyQualifiedJavaType(introspectedTable
//	                    .getPrimaryKeyType());
//	        } else {
//	            throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
//	        }
//	        returnType.addTypeArgument(listType);
//	        topLevelClass.addImportedType(returnType);
//	        method.addException(new FullyQualifiedJavaType("Exception"));
//	        commentGenerator.addGeneralMethodComment(method, introspectedTable);
//	        topLevelClass.addMethod(method);
	        /**selectByExample方法的实现***/
	        
	        /**FindByPage方法的实现***/
	        //mysql
	        if(jdbcConnectionConfiguration.getDriverClass().toString().contains("mysql")){
	        	method = new Method();
		        method.addAnnotation("@RequestMapping(value=\"/findByPage.do\",method = RequestMethod.POST)");
		        method.addAnnotation("@ResponseBody");
		        returnType = FullyQualifiedJavaType.getNewMapInstance();
		        returnType.addTypeArgument(new FullyQualifiedJavaType("String,Object"));
		        
		        if (introspectedTable.getRules().generateBaseRecordClass()) {
		            listType = new FullyQualifiedJavaType(introspectedTable
		                    .getBaseRecordType());
		        } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
		            listType = new FullyQualifiedJavaType(introspectedTable
		                    .getPrimaryKeyType());
		        } else {
		            throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
		        }
		        
		        method.setVisibility(JavaVisibility.PUBLIC);
		        
		        method.setName("findByPage");
		        method.addParameter(new Parameter(listType, "record"));
		        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "rows"));
		        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "page"));
		        method.addBodyLine("int startPage=rows*(page-1);");
		        method.addBodyLine("return "+field.getName()+".findByPage(record,startPage,rows);");
		        method.setReturnType(returnType);
		        topLevelClass.addImportedType(returnType);
		        method.addException(new FullyQualifiedJavaType("Exception"));
		        commentGenerator.addGeneralMethodComment(method, introspectedTable);
		        topLevelClass.addMethod(method);
	        }else if(jdbcConnectionConfiguration.getDriverClass().toString().contains("oracle")){
	        	//oracle
	        	method = new Method();
		        method.addAnnotation("@RequestMapping(value=\"/findByPage.do\",method = RequestMethod.POST)");
		        method.addAnnotation("@ResponseBody");
		        returnType = FullyQualifiedJavaType.getNewMapInstance();
		        returnType.addTypeArgument(new FullyQualifiedJavaType("String,Object"));
		        
		        if (introspectedTable.getRules().generateBaseRecordClass()) {
		            listType = new FullyQualifiedJavaType(introspectedTable
		                    .getBaseRecordType());
		        } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
		            listType = new FullyQualifiedJavaType(introspectedTable
		                    .getPrimaryKeyType());
		        } else {
		            throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
		        }
		        
		        method.setVisibility(JavaVisibility.PUBLIC);
		        
		        method.setName("findByPage");
		        method.addParameter(new Parameter(listType, "record"));
		        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "rows"));
		        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "page"));
		        method.addBodyLine("int startPage=rows*(page-1)+1;");
		        method.addBodyLine("int endPage=rows*page;");
		        method.addBodyLine("return "+field.getName()+".findByPage(record,startPage,endPage);");
		        method.setReturnType(returnType);
		        topLevelClass.addImportedType(returnType);
		        method.addException(new FullyQualifiedJavaType("Exception"));
		        commentGenerator.addGeneralMethodComment(method, introspectedTable);
		        topLevelClass.addMethod(method);
	        }
	        /**FindByPage方法的实现***/
	    
	        
	        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
	        if (context.getPlugins().modelExampleClassGenerated(
	                topLevelClass, introspectedTable)) {
	            answer.add(topLevelClass);
	        }

	        return answer;
	    }

		@Override
		public AbstractXmlGenerator getMatchedXMLGenerator() {
			return new XMLMapperGenerator();
		}
}
