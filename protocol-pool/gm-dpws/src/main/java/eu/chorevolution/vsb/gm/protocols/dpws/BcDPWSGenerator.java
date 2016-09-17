package eu.chorevolution.vsb.gm.protocols.dpws;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.ComplexType;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.codemodel.writer.SingleStreamCodeWriter;

import eu.chorevolution.vsb.gm.protocols.generators.BcSubcomponentGenerator;
import eu.chorevolution.vsb.gmdl.utils.BcConfiguration;
import eu.chorevolution.vsb.gmdl.utils.Data;
import eu.chorevolution.vsb.gmdl.utils.GmServiceRepresentation;
import eu.chorevolution.vsb.gmdl.utils.Operation;
import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;

public class BcDPWSGenerator extends BcSubcomponentGenerator {

	private final String classComment = "This class was generated by the CHOReVOLUTION BindingComponent Generator using com.sun.codemodel 2.6";	

	public BcDPWSGenerator(GmServiceRepresentation componentDescription, BcConfiguration bcConfiguration) {
		super(componentDescription, bcConfiguration);
	}

	@Override
	protected void generateEndpoint() {
		JCodeModel codeModel = new JCodeModel();

		Collection<Operation> operations = this.componentDescription.getOperations();
		for (Operation operation : operations) {
			this.buildOperation(operation, codeModel);
		}
	}

	private void buildOperation(final Operation operation, final JCodeModel codeModel) {
		String operationName = operation.getOperationID();
		JDefinedClass dpwsOperationClass = generateDefinedClass(codeModel, operationName);

		this.addComment(dpwsOperationClass, this.classComment);

		JMethod constructor = dpwsOperationClass.constructor(JMod.PUBLIC);

		constructor.body().directStatement("super(\""+operationName+"\", new QName(\"BasicServices\", DPWSDevice.DOCU_NAMESPACE));") ;

		wrapOperationParams(operation, codeModel, constructor);

		buildInvokeImpl(operation, codeModel, dpwsOperationClass);
		
		this.buildGeneratedClass(codeModel);
	}

	private void wrapOperationParams(final Operation operation,
			final JCodeModel codeModel, JMethod constructor) {
		JClass ComplexTypeClass = codeModel.ref(ComplexType.class);
		JClass ElementClass = codeModel.ref(Element.class);
		JClass DPWSDeviceClass = codeModel.ref(DPWSDevice.class);
		JClass QNameClass = codeModel.ref(QName.class);
		JClass SchemaUtilClass = codeModel.ref(SchemaUtil.class);

		JVar complexInputVar = constructor.body().decl(ComplexTypeClass, "complexInputElem");

		constructor.body().assign(complexInputVar, 
				JExpr._new(ComplexTypeClass).arg(JExpr._new(QNameClass).arg("complexInput").
						arg(DPWSDeviceClass.staticRef("DOCU_NAMESPACE"))).arg(ComplexTypeClass.staticRef("CONTAINER_SEQUENCE")));

		for (Data<?> data : operation.getGetDatas()) {
			JVar inputComponentVar = constructor.body().decl(ElementClass, data.getName() + "Elem");
			constructor.body().assign(inputComponentVar, 
					JExpr._new(ElementClass).arg(JExpr._new(QNameClass).arg(data.getName()).
							arg(DPWSDeviceClass.staticRef("DOCU_NAMESPACE"))).arg(SchemaUtilClass.staticRef("TYPE_STRING")));
			complexInputVar.invoke("addElement").arg(inputComponentVar);
		}

		constructor.body().invoke("setInput").arg(complexInputVar);

		if (operation.getOperationType() != OperationType.ONE_WAY) {
			JClass stringClass = codeModel.ref(String.class);
			JVar outputComponentVar = constructor.body().decl(ElementClass, "serialized" + operation.getPostData().getName());
			constructor.body().assign(outputComponentVar, 
					JExpr._new(ElementClass).arg(JExpr._new(QNameClass).arg(operation.getPostData().getName()).
							arg(DPWSDeviceClass.staticRef("DOCU_NAMESPACE"))).arg(SchemaUtilClass.staticRef("TYPE_STRING")));
			constructor.body().invoke("setOutput").arg(outputComponentVar);
		}
	}
	
	private void buildInvokeImpl(final Operation operation,
			final JCodeModel codeModel, JDefinedClass dpwsOperationClass) {
		JClass ParameterValueClass = codeModel.ref(ParameterValue.class);
		JClass CredentialInfoClass = codeModel.ref(CredentialInfo.class);
		JClass ParameterValueManagementClass = codeModel.ref(ParameterValueManagement.class);

		JMethod invokeImplMethod = null;
		invokeImplMethod = dpwsOperationClass.method(JMod.PUBLIC, ParameterValueClass, "invokeImpl");
		invokeImplMethod._throws(InvocationException.class);
		invokeImplMethod._throws(CommunicationException.class);

		JVar parameterValueVar = invokeImplMethod.param(ParameterValueClass, "parameterValue");
		invokeImplMethod.param(CredentialInfoClass, "credentialInfo");

		JClass dataListClass = codeModel.ref(List.class).narrow(codeModel.ref(Data.class).narrow(codeModel.wildcard()));
		JVar dataList = invokeImplMethod.body().decl(dataListClass, "datas");
		dataList.init(JExpr._new(codeModel.ref(ArrayList.class).narrow(codeModel.ref(Data.class).narrow(codeModel.wildcard()))));

		JClass StringClass = codeModel.ref(String.class);
		
		JInvocation dataCreation = null;
		for (Data<?> data : operation.getGetDatas()) {
			JVar inputComponentVar = null;
			switch(data.getClassName()) {
			case "String":
				inputComponentVar = invokeImplMethod.body().decl(StringClass, data.getName());
				invokeImplMethod.body().assign(inputComponentVar, 
						ParameterValueManagementClass.staticInvoke("getString").arg(parameterValueVar).arg(data.getName()));
				dataCreation = JExpr._new(codeModel.ref(Data.class).narrow(codeModel.directClass(data.getClassName())));
				dataCreation.arg(data.getName()).arg(data.getClassName()).arg(JExpr.lit(data.isPrimitiveType())).arg(JExpr.ref(data.getName()))
				.arg(JExpr.lit(data.getContext().toString()));
				break;
			}
			JInvocation addData2List = dataList.invoke("add").arg(dataCreation);
			invokeImplMethod.body().add(addData2List);
		}
	}

	@Override
	protected void generatePojo(Data<?> definition) {
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass definitionClass = this.generateDefinedClass(codeModel, definition.getClassName());
		this.addComment(definitionClass, this.classComment);

		definitionClass.annotate(javax.xml.bind.annotation.XmlAccessorType.class).param("value", javax.xml.bind.annotation.XmlAccessType.FIELD);

		definitionClass.annotate(javax.xml.bind.annotation.XmlRootElement.class).param("name", definition.getClassName());

		System.out.println(definition.getName());

		for (Data<?> attr : definition.getAttributes()) { 
			JFieldVar attrField = null;
			if(attr.getClassName().indexOf("<")!=-1) {
				//	        JClass ListClass = codeModel.ref(attr.getClassName().substring(0, attr.getClassName().indexOf("<")));
				//	        JClass argClass = codeModel.ref(attr.getClassName().substring(attr.getClassName().indexOf("<")+1, attr.getClassName().length()-1));
				//	        ListClass = ListClass.narrow(argClass);
				JClass ListClass = null;
				JClass argClass = codeModel.ref(attr.getClassName().substring(attr.getClassName().indexOf("<")+1, attr.getClassName().length()-1));
				ListClass = codeModel.ref(List.class).narrow(argClass);
				attrField = definitionClass.field(JMod.PRIVATE, ListClass, attr.getName());
				attrField.mods().setFinal(false);
				//includePackageReference(attr.getClassName(), codeModel);
			}
			else {
				attrField = generateAttribute(codeModel, definitionClass, codeModel.directClass(attr.getClassName()), attr.getName(), false);
			}
			JAnnotationUse attrAnnotation = attrField.annotate(codeModel.ref("javax.xml.bind.annotation.XmlElement"));
			attrAnnotation.param("name", attr.getName());
			attrAnnotation.param("required", attr.isRequired());

			// generate getters
			definitionClass.method(JMod.PUBLIC, attrField.type(), "get" + attr.getName()).body()._return(attrField);

			// generate setters
			JMethod setterMethod = definitionClass.method(JMod.PUBLIC, codeModel.VOID, "set" + attr.getName());
			setterMethod.param(attrField.type(), attr.getName());
			setterMethod.body().assign(JExpr._this().ref(attr.getName()), JExpr.ref(attr.getName()));

			// if complex type, generate its definition
			if (!attr.isPrimitiveType()) {
				this.generatePojo(attr);
			}
		}

		this.buildGeneratedClass(codeModel);

	}

	private JDefinedClass generateDefinedClass(final JCodeModel codeModel, final String className) {
		JDefinedClass definedClass = null;
		try {
			definedClass = codeModel._class(this.bcConfiguration.getTargetNamespace() + "." + className);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		return definedClass;
	}

	private JFieldVar generateAttribute(final JCodeModel codeModel, final JDefinedClass definedClass, final Class<?> attrClass,
			final String attrName, final Boolean isFinal) {
		return this.generateAttribute(codeModel, definedClass, codeModel.ref(attrClass), attrName, isFinal);
	}

	private JFieldVar generateAttribute(final JCodeModel codeModel, final JDefinedClass definedClass, final JClass attrClass,
			final String attrName, final Boolean isFinal) {
		JFieldVar attrField = definedClass.field(JMod.PRIVATE, attrClass, attrName);
		attrField.mods().setFinal(isFinal);
		return attrField;
	}

	private void addComment(final JDefinedClass definedClass, final String comment) {
		definedClass.javadoc().add(comment);
	}

	private void buildGeneratedClass(final JCodeModel codeModel) {
		// TESTING PURPOSE
		// Write the generated class to the console output
		if (debug) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				codeModel.build(new SingleStreamCodeWriter(out));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(out);
		}

		try {
			codeModel.build(new File("."));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}