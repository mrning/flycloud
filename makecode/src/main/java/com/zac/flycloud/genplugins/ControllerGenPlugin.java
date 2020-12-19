package com.zac.flycloud.genplugins;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.SneakyThrows;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.zac.flycloud.constants.MgtConstant.*;

public class ControllerGenPlugin extends PluginAdapter {

    private String controllerPath = "";
    private String controllerPackage = "";
    private String firstLowerServiceName = "";
    private String dtoName = "";
    // 要生成api/app的controller注解
    public static final String API_APP = "/api/";


    public ControllerGenPlugin() {
        super();
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

    @Override
    public boolean validate(List<String> warnings) {
        boolean valid = true;
        if (!StringUtility.stringHasValue(this.properties.getProperty("controllerPath"))) {
            warnings.add(Messages.getString("ValidationError.18", "ControllerGenPlugin", "controllerPath"));
            valid = false;
        }

        if (!StringUtility.stringHasValue(this.properties.getProperty("controllerPackage"))) {
            warnings.add(Messages.getString("ValidationError.18", "ControllerGenPlugin", "controllerPackage"));
            valid = false;
        }

        return valid;
    }

    @SneakyThrows
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<>();
        controllerPath = this.properties.getProperty("controllerPath");
        controllerPackage = this.properties.getProperty("controllerPackage");
        // dto名称
        dtoName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // base文件名
        String baseDomainName = dtoName.replace(DTO_SUFFIX,"");
        // service名称
        firstLowerServiceName = StringUtils.firstToLowerCase(baseDomainName)+SERVICE_SUFFIX;

        TopLevelClass topLevelClass = new TopLevelClass(controllerPackage+"."+baseDomainName+CONTROLLER_SUFFIX);
        topLevelClass.addImportedType(TARGETPACKAGE+".BaseController");
        topLevelClass.addImportedType(TARGETPACKAGE+".basebean.DataResponseResult");
        topLevelClass.addImportedType(TARGETPACKAGE_DTO+"."+dtoName);
        topLevelClass.addImportedType(TARGETPACKAGE_SERVICE+"."+baseDomainName+SERVICE_SUFFIX);
        topLevelClass.addImportedType("cn.hutool.db.PageResult");
        topLevelClass.addImportedType("org.springframework.beans.factory.annotation.*");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.*");
        topLevelClass.addImportedType("lombok.extern.slf4j.Slf4j");

        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addJavaDocLine("/**\n" +
                " * AutoCreateFile\n" +
                " * @date "+ LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) +"\n" +
                " * @author zac\n" +
                " */");
        topLevelClass.addAnnotation(ANNOTATION_RESTCONTROLLER);
        topLevelClass.addAnnotation(ANNOTATION_REQUESTMAPPING+("(\""+API_APP+StringUtils.firstToLowerCase(baseDomainName)+"\")"));
        topLevelClass.addAnnotation(ANNOTATION_SL4J);
        topLevelClass.setSuperClass("BaseController");

        createField(baseDomainName, topLevelClass);

        createMethod("add", topLevelClass);
        createMethod("del", topLevelClass);
        createMethod("update", topLevelClass);
        createMethod("queryPage", topLevelClass);

        DefaultJavaFormatter javaFormatter = new DefaultJavaFormatter();
        javaFormatter.setContext(context);
        javaFormatter.visit(topLevelClass);
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(topLevelClass, controllerPath, javaFormatter);
        generatedJavaFiles.add(generatedJavaFile);
        return generatedJavaFiles;
    }

    private void createField(String baseDomainName, TopLevelClass topLevelClass) {
        topLevelClass.addImportedType(TARGETPACKAGE_SERVICE+"."+ baseDomainName+SERVICE_SUFFIX);
        Field field = new Field(firstLowerServiceName,new FullyQualifiedJavaType(baseDomainName +SERVICE_SUFFIX));
        field.addAnnotation(ANNOTATION_AUTOWIRED);
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
    }

    private void createMethod(String methodName, TopLevelClass topLevelClass) {
        String firstLowerDtoName = StringUtils.firstToLowerCase(dtoName);
        Method method = new Method(methodName);
        method.setVisibility(JavaVisibility.PUBLIC);
        // 加注解
        method.addAnnotation(ANNOTATION_POSTMAPPING+("(\"/"+ methodName +"\")"));
        // 加返回类型
        if(methodName.contains("queryPage")){
            method.setReturnType(new FullyQualifiedJavaType("DataResponseResult<PageResult<"+dtoName+">>"));
            topLevelClass.addImportedType(TARGETPACKAGE_DTO+"."+dtoName);
        }else{
            method.setReturnType(new FullyQualifiedJavaType("DataResponseResult<Integer>"));
        }
        // 加参数
        method.addParameter(new Parameter(
                new FullyQualifiedJavaType(TARGETPACKAGE_DTO+"."+dtoName),
                firstLowerDtoName,
                ANNOTATION_REQUESTBODY));
        // 加内容
        method.addBodyLine("return DataResponseResult.success("+
                firstLowerServiceName+"."+methodName+"("+firstLowerDtoName+"));");
        topLevelClass.addMethod(method);
    }
}
