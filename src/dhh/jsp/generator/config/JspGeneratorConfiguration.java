package dhh.jsp.generator.config;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyHolder;

/**
 * @author Jeff Butler
 */
public class JspGeneratorConfiguration extends PropertyHolder {
	private String targetPackage;

	private String targetProject;

	/**
		 *  
		 */
	public JspGeneratorConfiguration() {
		super();
	}

	public String getTargetProject() {
		return targetProject;
	}

	public void setTargetProject(String targetProject) {
		this.targetProject = targetProject;
	}

	public String getTargetPackage() {
		return targetPackage;
	}

	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}

	public XmlElement toXmlElement() {
		XmlElement answer = new XmlElement("jspGenerator"); //$NON-NLS-1$

		if (targetPackage != null) {
			answer.addAttribute(new Attribute("targetPackage", targetPackage)); //$NON-NLS-1$
		}

		if (targetProject != null) {
			answer.addAttribute(new Attribute("targetProject", targetProject)); //$NON-NLS-1$
		}

		addPropertyXmlElements(answer);

		return answer;
	}

	public void validate(List<String> errors, String contextId) {
		if (!stringHasValue(targetProject)) {
			errors.add(getString("ValidationError.1", contextId)); //$NON-NLS-1$
		}

		if (!stringHasValue(targetPackage)) {
			errors.add(getString("ValidationError.12", //$NON-NLS-1$
					"jsGenerator", contextId)); //$NON-NLS-1$
		}
	}
}
