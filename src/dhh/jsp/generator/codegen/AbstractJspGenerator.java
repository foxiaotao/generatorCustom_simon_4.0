package dhh.jsp.generator.codegen;

import org.mybatis.generator.api.dom.xml.TextDocument;
import org.mybatis.generator.codegen.AbstractGenerator;

public abstract class AbstractJspGenerator extends AbstractGenerator {
    public abstract TextDocument getDocument();
}
