package com.zac.flycloud.genplugins;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.zac.flycloud.constants.MgtConstant.*;

/**
 * dao和dao实现类的创建插件
 */
public class DaoGenPlugin extends PluginAdapter {
    private String daoPath = "";
    private String daoPackage = "";
    private String firstLowerMapperName = "";
    private String dtoName = "";

    public DaoGenPlugin() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
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
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<>();
        daoPath = this.properties.getProperty("daoPath");
        daoPackage = this.properties.getProperty("daoPackage");
        // dto名称
        dtoName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // base文件名
        String baseDomainName = dtoName.replace(DTO_SUFFIX,"");
        // mapper名称 首字符小写
        firstLowerMapperName = StringUtils.firstCharToLower(baseDomainName)+MAPPER_SUFFIX;

        generatedJavaFiles.add(buildServiceFile(baseDomainName,daoPackage+"."+ baseDomainName +DAO_SUFFIX));
        generatedJavaFiles.add(buildServiceFile(baseDomainName,daoPackage+".impl."+ baseDomainName +DAO_SUFFIX+IMPL_SUFFIX));
        return generatedJavaFiles;
    }

    private GeneratedJavaFile buildServiceFile(String baseDomainName,String fullFileName) {
        if(fullFileName.contains(IMPL_SUFFIX)){
            TopLevelClass genClass = new TopLevelClass(fullFileName);
            genClass.setVisibility(JavaVisibility.PUBLIC);
            genClass.addJavaDocLine("/**\n" +
                    " * AutoCreateFile\n" +
                    " * @date "+ LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) +"\n" +
                    " * @author zac\n" +
                    " */");
            genClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.*"));
            genClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository;"));
            genClass.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.session.RowBounds;"));
            genClass.addImportedType(new FullyQualifiedJavaType("lombok.extern.slf4j.Slf4j"));
            genClass.addImportedType(FullyQualifiedJavaType.getNewListInstance());
            genClass.addImportedType(new FullyQualifiedJavaType(daoPackage+"."+baseDomainName+DAO_SUFFIX));
            genClass.addImportedType(new FullyQualifiedJavaType(TARGETPACKAGE_EXAMPLE+"."+dtoName+EXAMPLE_SUFFIX));

            genClass.addAnnotation(ANNOTATION_REPOSITORY);
            genClass.addAnnotation(ANNOTATION_SL4J);
            genClass.addSuperInterface(new FullyQualifiedJavaType(baseDomainName+DAO_SUFFIX));

            createField(baseDomainName,genClass);
            createMethod("add", genClass);
            createMethod("del", genClass);
            createMethod("update", genClass);
            createMethod("queryPage", genClass);
            createMethod("queryPageCount", genClass);
            return new GeneratedJavaFile(genClass, daoPath, new DefaultJavaFormatter());
        }else{
            Interface genInterface = new Interface(fullFileName);
            genInterface.setVisibility(JavaVisibility.PUBLIC);
            genInterface.addJavaDocLine("/**\n" +
                    " * AutoCreateFile\n" +
                    " * @date "+ LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) +"\n" +
                    " * @author zac\n" +
                    " */");
            genInterface.addImportedType(FullyQualifiedJavaType.getNewListInstance());
            genInterface.addImportedType(new FullyQualifiedJavaType("cn.hutool.db.Page"));

            createMethod("add", genInterface);
            createMethod("del", genInterface);
            createMethod("update", genInterface);
            createMethod("queryPage", genInterface);
            createMethod("queryPageCount", genInterface);
            return new GeneratedJavaFile(genInterface, daoPath, new DefaultJavaFormatter());
        }
    }

    private void createField(String domainObjectName, TopLevelClass topLevelClass) {
        topLevelClass.addImportedType(TARGETPACKAGE+".mapper."+ dtoName+MAPPER_SUFFIX);
        Field field = new Field(firstLowerMapperName,new FullyQualifiedJavaType(dtoName +MAPPER_SUFFIX));
        field.addAnnotation(ANNOTATION_AUTOWIRED);
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
    }

    private void createMethod(String methodName, CompilationUnit compilationUnit) {
        String firstLowerDtoName = StringUtils.firstCharToLower(dtoName);
        String firstLowerExample = firstLowerDtoName+EXAMPLE_SUFFIX;
        Method method = new Method(methodName);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(0,new Parameter(
                new FullyQualifiedJavaType(TARGETPACKAGE_DTO+"."+dtoName),
                firstLowerDtoName));
        method.addBodyLine(0,dtoName+EXAMPLE_SUFFIX+" "+firstLowerExample+" = new "+dtoName+EXAMPLE_SUFFIX+"();");
        switch (methodName){
            case "queryPage":
                method.addParameter(1,new Parameter(
                        new FullyQualifiedJavaType("cn.hutool.db.Page"),
                        "page"));
                method.setReturnType(new FullyQualifiedJavaType("List<"+dtoName+">"));
                compilationUnit.addImportedType(new FullyQualifiedJavaType(TARGETPACKAGE+".dto."+dtoName));
                method.addBodyLine(1,"return "+firstLowerMapperName+".selectByExampleWithRowbounds("+firstLowerExample+",new RowBounds(page.getPageNumber(),page.getPageSize()));");
                break;
            case "queryPageCount":
                method.setReturnType(PrimitiveTypeWrapper.getLongInstance());
                method.addBodyLine(1,"return "+firstLowerMapperName+".countByExample("+firstLowerExample+");");
                break;
            case "add":
                method.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
                method.getBodyLines().clear();
                method.addBodyLine("return "+firstLowerMapperName+".insertSelective("+firstLowerDtoName+");");
                break;
            case "del":
                method.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
                method.addBodyLine(1,"return "+firstLowerMapperName+".deleteByExample("+firstLowerExample+");");
                break;
            case "update":
                method.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
                method.addBodyLine(1,"return "+firstLowerMapperName+".updateByExampleSelective("+firstLowerDtoName+","+firstLowerExample+");");
                break;
            default:
                break;
        }

        if (compilationUnit instanceof TopLevelClass) {
            ((TopLevelClass) compilationUnit).addMethod(method);
        } else {
            method.setAbstract(true);
            ((Interface) compilationUnit).addMethod(method);
        }
    }
}
