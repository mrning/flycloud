package com.zac.flycloud.service.impl;

import cn.hutool.core.convert.impl.UUIDConverter;
import cn.hutool.core.lang.UUID;
import com.zac.flycloud.service.MybatisGeneratorService;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class MybatisGeneratorServiceImpl implements MybatisGeneratorService {
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Override
    public void doDenerator()  {
        boolean overwrite = true;
        Configuration config = new Configuration();

        // conditional:*这是默认值*,这个模型和下面的hierarchical类似，除了如果那个单独的类将只包含一个字段，将不会生成一个单独的类。
        // 因此,如果一个表的主键只有一个字段,那么不会为该字段生成单独的实体类,会将该字段合并到基本实体类中。

        // flat:该模型为每一张表只生成一个实体类。这个实体类包含表中的所有字段。**这种模型最简单，推荐使用。**

        // hierarchical:如果表有主键,那么该模型会产生一个单独的主键实体类,如果表还有BLOB字段，
        // 则会为表生成一个包含所有BLOB字段的单独的实体类,然后为所有其他的字段生成一个单独的实体类。 MBG会在所有生成的实体类之间维护一个继承关系。
        Context context = new Context(ModelType.FLAT);
        context.setId(UUID.randomUUID().toString(true));
        // 设置分隔符 前后分隔符
        context.addProperty("autoDelimitKeywords","true");
        context.addProperty("beginningDelimiter","`");
        context.addProperty("endingDelimiter","`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(dbUrl);
        jdbcConnectionConfiguration.setDriverClass(driverClassName);
        jdbcConnectionConfiguration.setUserId(dbUserName);
        jdbcConnectionConfiguration.setPassword(dbPassword);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        // 配置要生成代码的表
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName("sys_user");
        GeneratedKey generatedKey = new GeneratedKey("id","JDBC",true,"post");
        tableConfiguration.setGeneratedKey(generatedKey);
        context.addTableConfiguration(tableConfiguration);

        //生成注释
        CommentGeneratorConfiguration commentGenerator = new CommentGeneratorConfiguration();
        commentGenerator.setConfigurationType("DEFAULT");
        commentGenerator.addProperty("suppressDate","true");
        context.setCommentGeneratorConfiguration(commentGenerator);

        // 用来控制生成的实体类
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject("D:\\IdeaProjects\\flycloud\\makecode\\src\\main\\java\\");
        javaModelGeneratorConfiguration.setTargetPackage("com.zac.flycloud");
        javaModelGeneratorConfiguration.addProperty("enableSubPackages","true");
        javaModelGeneratorConfiguration.addProperty("trimStrings","true");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // 设置mapper接口的生成
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("ANNOTATEDMAPPER");
        javaClientGeneratorConfiguration.setTargetProject("D:\\IdeaProjects\\flycloud\\makecode\\src\\main\\java\\");
        javaClientGeneratorConfiguration.setTargetPackage("com.zac.flycloud");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        try {
            config.addContext(context);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, new ArrayList<>());
            ProgressCallback progressCallback = new VerboseProgressCallback();
            myBatisGenerator.generate(progressCallback);
        } catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
