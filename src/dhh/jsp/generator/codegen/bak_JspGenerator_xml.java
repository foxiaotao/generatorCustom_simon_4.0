package dhh.jsp.generator.codegen;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextDocument;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.InsertElementGenerator;


public class bak_JspGenerator_xml extends AbstractJspGenerator{
	 public bak_JspGenerator_xml() {
	        super();
	    }

	    protected XmlElement getJspElement() {
	        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
	        progressCallback.startTask(getString(
	                "Progress.12", table.toString())); //$NON-NLS-1$
	        XmlElement answer = new XmlElement("html"); //$NON-NLS-1$

	        context.getCommentGenerator().addRootComment(answer);

	        addInsertElement(answer);

	        return answer;
	    }

		
	    protected void addInsertElement(XmlElement parentElement) {
	        if (introspectedTable.getRules().generateInsert()) {
	            AbstractXmlElementGenerator elementGenerator = new InsertElementGenerator(false);
	            initializeAndExecuteGenerator(elementGenerator, parentElement);
	        }
	    }
	    
	    

	    protected void initializeAndExecuteGenerator(
	            AbstractXmlElementGenerator elementGenerator,
	            XmlElement parentElement) {
	        elementGenerator.setContext(context);
	        elementGenerator.setIntrospectedTable(introspectedTable);
	        elementGenerator.setProgressCallback(progressCallback);
	        elementGenerator.setWarnings(warnings);
	        elementGenerator.addElements(parentElement);
	    }

		@Override
		public TextDocument getDocument() {
			// TODO Auto-generated method stub
			return null;
		}

//	    @Override
//	    public Document getDocument() {
//	        Document document = new Document(
//	                XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
//	                XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
//	        document.setRootElement(getJspElement());
//
//	        if (!context.getPlugins().sqlMapDocumentGenerated(document,
//	                introspectedTable)) {
//	            document = null;
//	        }
//
//	        return document;
//	    }
	}
