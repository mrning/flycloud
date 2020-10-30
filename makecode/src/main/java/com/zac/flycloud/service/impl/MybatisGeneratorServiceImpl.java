package com.zac.flycloud.service.impl;

import cn.hutool.core.convert.impl.UUIDConverter;
import cn.hutool.core.lang.UUID;
import com.zac.flycloud.service.MybatisGeneratorService;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        Configuration config = new Configuration();
        Context context = new Context(ModelType.CONDITIONAL);
        context.setId(UUID.randomUUID().toString(true));
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(dbUrl);
        jdbcConnectionConfiguration.setDriverClass(driverClassName);
        jdbcConnectionConfiguration.setUserId(dbUserName);
        jdbcConnectionConfiguration.setPassword(dbPassword);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName("sys_user");
        context.addTableConfiguration(tableConfiguration);


        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject("flycloud");
        javaModelGeneratorConfiguration.setTargetPackage("com.zac.flycloud");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        try {
            config.addContext(context);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
