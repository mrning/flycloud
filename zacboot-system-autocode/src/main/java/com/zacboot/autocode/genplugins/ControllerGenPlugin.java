package com.zacboot.autocode.genplugins;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zacboot.autocode.constants.MgtConstant;
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

public class ControllerGenPlugin extends PluginAdapter {

    private String controllerPath = "";
    private String controllerPackage = "";
    private String controllerPlatform = "";
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

        if (!StringUtility.stringHasValue(this.properties.getProperty("controllerPlatform"))) {
            warnings.add(Messages.getString("ValidationError.18", "controllerPlatform", "controllerPlatform"));
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
        controllerPlatform = this.properties.getProperty("controllerPlatform");
        String desc = this.properties.getProperty("controllerDesc");
        // dto名称
        dtoName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // base文件名
        String baseDomainName = dtoName.replace(MgtConstant.DTO_SUFFIX, "");
        // service名称
        firstLowerServiceName = StringUtils.firstToLowerCase(baseDomainName) + MgtConstant.SERVICE_SUFFIX;
        // 引入包
        String className = (controllerPlatform.substring(0, 1).toUpperCase() + controllerPlatform.substring(1)) + baseDomainName + MgtConstant.CONTROLLER_SUFFIX;
        TopLevelClass topLevelClass = new TopLevelClass(controllerPackage + "." + className);
        topLevelClass.addImportedType("cn.hutool.db.PageResult");
        topLevelClass.addImportedType("org.springframework.beans.factory.annotation.*");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.*");
        topLevelClass.addImportedType("lombok.extern.slf4j.Slf4j");
        topLevelClass.addImportedType("io.swagger.annotations.Api");
        topLevelClass.addImportedType("io.swagger.annotations.ApiOperation");

        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        // javaDoc
        topLevelClass.addJavaDocLine("/**\n" +
                " * AutoCreateFile " + desc + " \n" +
                " * @date " + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + "\n" +
                " * @author zac\n" +
                " */");
        // 注解
        topLevelClass.addAnnotation(MgtConstant.ANNOTATION_API + ("(tags = " + "\"" + desc + "\")"));
        topLevelClass.addAnnotation(MgtConstant.ANNOTATION_RESTCONTROLLER);
        topLevelClass.addAnnotation(MgtConstant.ANNOTATION_REQUESTMAPPING + ("(\"" + API_APP + controllerPlatform + "/" + StringUtils.firstToLowerCase(baseDomainName) + "\")"));
        topLevelClass.addAnnotation(MgtConstant.ANNOTATION_SL4J);
        // 成员变量
        createField(baseDomainName, topLevelClass);
        // 方法
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
        topLevelClass.addImportedType(MgtConstant.TARGETPACKAGE_SERVICE + "." + baseDomainName + MgtConstant.SERVICE_SUFFIX);
        Field field = new Field(firstLowerServiceName, new FullyQualifiedJavaType(baseDomainName + MgtConstant.SERVICE_SUFFIX));
        field.addAnnotation(MgtConstant.ANNOTATION_AUTOWIRED);
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
    }

    private void createMethod(String methodName, TopLevelClass topLevelClass) {
        String firstLowerDtoName = StringUtils.firstToLowerCase(dtoName);
        Method method = new Method(methodName);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addJavaDocLine("/**\n" +
                "     * AutoCreateFile " + methodName + "\n" +
                "     * @date " + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + "\n" +
                "     * @author zac\n" +
                "     */");
        // 加注解
        method.addAnnotation(MgtConstant.ANNOTATION_POSTMAPPING + ("(\"/" + methodName + "\")"));
        method.addAnnotation(MgtConstant.ANNOTATION_APIOPERATION + ("(\"" + (methodName.equals("add") ? "新增" :
                methodName.equals("del") ? "删除" : methodName.equals("update") ? "更新" : methodName.equals("queryPage") ? "分页查询" : methodName)
                + "\")"));
        // 加返回类型
        if (methodName.contains("queryPage")) {
            method.setReturnType(new FullyQualifiedJavaType("Result<PageResult<" + dtoName + ">>"));
            // 加参数
            method.addParameter(new Parameter(
                    new FullyQualifiedJavaType(dtoName + "PageRequest"),
                    "pageRequest",
                    MgtConstant.ANNOTATION_REQUESTBODY));
            // 加内容
            method.addBodyLine("return Result.success(" +
                    firstLowerServiceName + "." + methodName + "(pageRequest));");
        } else {
            method.setReturnType(new FullyQualifiedJavaType("Result<Integer>"));
            // 加参数
            method.addParameter(new Parameter(
                    new FullyQualifiedJavaType(dtoName),
                    firstLowerDtoName,
                    MgtConstant.ANNOTATION_REQUESTBODY));
            // 加内容
            method.addBodyLine("return Result.success(" +
                    firstLowerServiceName + "." + methodName + "(" + firstLowerDtoName + "));");
        }
        topLevelClass.addMethod(method);
    }
}
