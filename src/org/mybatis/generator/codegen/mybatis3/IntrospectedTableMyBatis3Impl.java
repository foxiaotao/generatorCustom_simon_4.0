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
package org.mybatis.generator.codegen.mybatis3;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedTextFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextDocument;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.MixedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.ObjectFactory;

import dhh.action.generator.codegen.AbstractJavaActionGenerator;
import dhh.action.generator.codegen.JavaActionGenerator;
import dhh.jsp.generator.codegen.AbstractJsGenerator;
import dhh.jsp.generator.codegen.AbstractJspGenerator;
import dhh.jsp.generator.codegen.JsGenerator;
import dhh.jsp.generator.codegen.JspGenerator;
import dhh.service.generator.codegen.AbstractJavaServiceGenerator;
import dhh.service.generator.codegen.JavaIServiceGenerator;
import dhh.service.generator.codegen.JavaServiceImplGenerator;

/**
 * 
 * 
 * @author Jeff Butler
 * 
 */
public class IntrospectedTableMyBatis3Impl extends IntrospectedTable {
    protected List<AbstractJavaGenerator> javaModelGenerators;
    protected List<AbstractJavaGenerator> clientGenerators;
    protected AbstractXmlGenerator xmlMapperGenerator;
    protected AbstractJspGenerator jspGenerator;
    protected AbstractJsGenerator jsGenerator;
    protected List<AbstractJavaGenerator> IServiceGenerators;
    protected List<AbstractJavaGenerator> ServiceImplGenerators;
    protected List<AbstractJavaGenerator> ActionGenerators;
    
    public IntrospectedTableMyBatis3Impl() {
        super(TargetRuntime.MYBATIS3);
        javaModelGenerators = new ArrayList<AbstractJavaGenerator>();
        clientGenerators = new ArrayList<AbstractJavaGenerator>();
        IServiceGenerators=new ArrayList<AbstractJavaGenerator>();
        ServiceImplGenerators=new ArrayList<AbstractJavaGenerator>();
        ActionGenerators=new ArrayList<AbstractJavaGenerator>();
    }

    @Override
    public void calculateGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        calculateJavaModelGenerators(warnings, progressCallback);
        
        AbstractJavaClientGenerator javaClientGenerator =
            calculateClientGenerators(warnings, progressCallback);
        
        calculateJavaIServiceGenerators(warnings, progressCallback);
        calculateJavaServiceImplGenerators(warnings, progressCallback);
        
        calculateJavaActionGenerators(warnings, progressCallback);
            
        calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);
        calculateJspGenerator(javaClientGenerator, warnings, progressCallback);
        calculateJsGenerator(javaClientGenerator, warnings, progressCallback);
    }

    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, 
            List<String> warnings,
            ProgressCallback progressCallback) {
        if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                xmlMapperGenerator = new XMLMapperGenerator();
            }
        } else {
            xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
        }
        
        initializeAbstractGenerator(xmlMapperGenerator, warnings,
                progressCallback);
    }
    protected void calculateJspGenerator(AbstractJavaClientGenerator javaClientGenerator, 
    		List<String> warnings,
    		ProgressCallback progressCallback) {
    	if (javaClientGenerator == null) {
    		if (context.getJspGeneratorConfiguration() != null) {
    			jspGenerator = new JspGenerator();
    		}
    	} else {
    		jspGenerator = javaClientGenerator.getMatchedJspGenerator();
    	}
    	
    	initializeAbstractGenerator(jspGenerator, warnings,
    			progressCallback);
    }
    protected void calculateJsGenerator(AbstractJavaClientGenerator javaClientGenerator, 
    		List<String> warnings,
    		ProgressCallback progressCallback) {
    	if (javaClientGenerator == null) {
    		if (context.getJsGeneratorConfiguration() != null) {
    			jsGenerator = new JsGenerator();
    		}
    	} else {
    		jsGenerator = javaClientGenerator.getMatchedJsGenerator();
    	}
    	
    	initializeAbstractGenerator(jsGenerator, warnings,
    			progressCallback);
    }
    

    /**
     * 
     * @param warnings
     * @param progressCallback
     * @return true if an XML generator is required
     */
    protected AbstractJavaClientGenerator calculateClientGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (!rules.generateJavaClient()) {
            return null;
        }
        
        AbstractJavaClientGenerator javaGenerator = createJavaClientGenerator();
        if (javaGenerator == null) {
            return null;
        }

        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        clientGenerators.add(javaGenerator);
        
        return javaGenerator;
    }
    
    protected AbstractJavaServiceGenerator calculateJavaIServiceGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
    	/*if (!rules.generateJavaClient()) {
            return null;
        }*/
        
    	AbstractJavaServiceGenerator javaGenerator = createJavaIServiceGenerator();
        if (javaGenerator == null) {
            return null;
        }

        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        IServiceGenerators.add(javaGenerator);
        
        return javaGenerator;
	}
    
    protected AbstractJavaServiceGenerator calculateJavaServiceImplGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
    	/*if (!rules.generateJavaClient()) {
            return null;
        }*/
        
    	AbstractJavaServiceGenerator javaGenerator = createJavaServiceImplGenerator();
        if (javaGenerator == null) {
            return null;
        }

        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        ServiceImplGenerators.add(javaGenerator);
        
        return javaGenerator;
	}
    
    protected AbstractJavaActionGenerator calculateJavaActionGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
    	/*if (!rules.generateJavaClient()) {
            return null;
        }*/
        
    	AbstractJavaActionGenerator javaGenerator = createJavaActionGenerator();
        if (javaGenerator == null) {
            return null;
        }

        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        ActionGenerators.add(javaGenerator);
        
        return javaGenerator;
	}
    
    
    
    
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }
        
        String type = context.getJavaClientGeneratorConfiguration()
                .getConfigurationType();

        AbstractJavaClientGenerator javaGenerator;
        if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator();
        } else if ("MIXEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new MixedClientGenerator();
        } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new AnnotatedClientGenerator();
        } else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator();
        } else {
            javaGenerator = (AbstractJavaClientGenerator) ObjectFactory
                    .createInternalObject(type);
        }
        
        return javaGenerator;
    }
    
    protected AbstractJavaServiceGenerator createJavaIServiceGenerator() {
        if (context.getJavaIServiceGeneratorConfiguration() == null) {
            return null;
        }
        
        String type = context.getJavaIServiceGeneratorConfiguration()
                .getConfigurationType();

        AbstractJavaServiceGenerator javaGenerator;
        /*if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaIServiceGenerator();
        } else {
            javaGenerator = (AbstractJavaClientGenerator) ObjectFactory
                    .createInternalObject(type);
        }*/
        
        return new JavaIServiceGenerator();
    }
    
    protected AbstractJavaServiceGenerator createJavaServiceImplGenerator() {
        if (context.getJavaServiceImplGeneratorConfiguration() == null) {
            return null;
        }
        
        String type = context.getJavaServiceImplGeneratorConfiguration()
                .getConfigurationType();

        AbstractJavaServiceGenerator javaGenerator;
        /*if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaIServiceGenerator();
        } else {
            javaGenerator = (AbstractJavaClientGenerator) ObjectFactory
                    .createInternalObject(type);
        }*/
        
        return new JavaServiceImplGenerator();
    }
    
    protected AbstractJavaActionGenerator createJavaActionGenerator() {
        if (context.getJavaActionGeneratorConfiguration() == null) {
            return null;
        }
        
        String type = context.getJavaActionGeneratorConfiguration()
                .getConfigurationType();

        AbstractJavaServiceGenerator javaGenerator;
        /*if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaIServiceGenerator();
        } else {
            javaGenerator = (AbstractJavaClientGenerator) ObjectFactory
                    .createInternalObject(type);
        }*/
        
        return new JavaActionGenerator();
    }

    protected void calculateJavaModelGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateBaseRecordClass()) {
            AbstractJavaGenerator javaGenerator = new BaseRecordGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }
    }

    
    
    protected void initializeAbstractGenerator(
            AbstractGenerator abstractGenerator, List<String> warnings,
            ProgressCallback progressCallback) {
        if (abstractGenerator == null) {
            return;
        }
        
        abstractGenerator.setContext(context);
        abstractGenerator.setIntrospectedTable(this);
        abstractGenerator.setProgressCallback(progressCallback);
        abstractGenerator.setWarnings(warnings);
    }

    @Override
    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();

        for (AbstractJavaGenerator javaGenerator : javaModelGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaModelGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        for (AbstractJavaGenerator javaGenerator : clientGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator//����daomapper�еĽӿڷ���
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaClientGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }
        
        for (AbstractJavaGenerator javaGenerator : IServiceGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator//
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaIServiceGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }
        
        for (AbstractJavaGenerator javaGenerator : ServiceImplGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator//
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaServiceImplGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }
        
        for (AbstractJavaGenerator javaGenerator : ActionGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator//
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaActionGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }
        

        return answer;
    }

    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

        if (xmlMapperGenerator != null) {
            Document document = xmlMapperGenerator.getDocument();
            GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                getMyBatis3XmlMapperFileName(), getMyBatis3XmlMapperPackage(),
                context.getSqlMapGeneratorConfiguration().getTargetProject(),
                true, context.getXmlFormatter());
            if (context.getPlugins().sqlMapGenerated(gxf, this)) {
                answer.add(gxf);
            }
        }
        return answer;
    }
    @Override
    public List<GeneratedTextFile> getGeneratedTextFiles() {
    	List<GeneratedTextFile> answer2 = new ArrayList<GeneratedTextFile>();
    	
    	if (jspGenerator != null) {
    		TextDocument document = jspGenerator.getDocument();
    		GeneratedTextFile gxf = new GeneratedTextFile(document,
    				getJspFileName(), getJspPackage(),
    				context.getJspGeneratorConfiguration().getTargetProject(),
    				true, context.getTextFormatter());
    		answer2.add(gxf);
    	}
    	if (jsGenerator != null) {
    		TextDocument document = jsGenerator.getDocument();
    		GeneratedTextFile gxf = new GeneratedTextFile(document,
    				getJsFileName(), getJsPackage(),
    				context.getJsGeneratorConfiguration().getTargetProject(),
    				true, context.getTextFormatter());
    		answer2.add(gxf);
    	}
    	
    	return answer2;
    }

    @Override
    public int getGenerationSteps() {
        return javaModelGenerators.size() + clientGenerators.size() +
            (xmlMapperGenerator == null ? 0 : 1);
    }

    @Override
    public boolean isJava5Targeted() {
        return true;
    }

    @Override
    public boolean requiresXMLGenerator() {
        AbstractJavaClientGenerator javaClientGenerator =
            createJavaClientGenerator();
        
        if (javaClientGenerator == null) {
            return false;
        } else {
            return javaClientGenerator.requiresXMLGenerator();
        }
    }
}
