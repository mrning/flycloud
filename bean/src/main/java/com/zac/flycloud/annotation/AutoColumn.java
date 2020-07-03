package com.zac.flycloud.annotation;

import java.lang.annotation.*;

/**
 * 建表的必备注解,该注解不是必须的，如果没有配置，按系统默认的来设置
 *
 */
// 该注解用于方法声明
@Target(ElementType.FIELD)
// VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
// 将此注解包含在javadoc中
@Documented
// 允许子类继承父类中的注解
@Inherited
public @interface AutoColumn {

    /**
     * 字段名
     *
     * @return 字段名
     */
    String name() default "";

    /**
     * 字段类型
     *
     * @return 字段类型
     */
    String type() default "";

    /**
     * 字段长度，默认是0
     *
     * @return 字段长度，默认是0
     */
    int length() default 0;

    /**
     * 小数点长度，默认是0
     *
     * @return 小数点长度，默认是0
     */
    int decimalLength() default 0;

    /**
     * 是否为可以为null，true是可以，false是不可以，默认为true
     *
     * @return 是否为可以为null，true是可以，false是不可以，默认为true
     */
    boolean isNull() default true;

    /**
     * 是否是主键，默认false
     *
     * @return 是否是主键，默认false
     */
    boolean isKey() default false;

    /**
     * 是否自动递增，默认false 只有主键才能使用
     *
     * @return 是否自动递增，默认false 只有主键才能使用
     */
    boolean isAutoIncrement() default false;

    /**
     * 默认值，默认为null
     *
     * @return 默认值，默认为null
     */
    String defaultValue() default "";


    /**
     * 忽略该字段，不自动生成数据库字段
     *
     * @return
     */
    boolean isIgnore() default false;

}
