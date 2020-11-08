package com.zac.flycloud.constants;

public class MgtConstant {
    // 文件生成路径
    public static final String TARGETPROJECT = "src\\main\\java\\";
    // 文件生成的包路径
    public static final String TARGETPACKAGE = "com.zac.flycloud";
    public static final String TARGETPACKAGE_SERVICE = TARGETPACKAGE+".service";
    public static final String TARGETPACKAGE_DTO = TARGETPACKAGE+".dto";
    public static final String TARGETPACKAGE_EXAMPLE = TARGETPACKAGE_DTO+".example";
    public static final String TARGETPACKAGE_DAO = TARGETPACKAGE+".dao";
    public static final String TARGETPACKAGE_MAPPER = TARGETPACKAGE+".mapper";

    public static final String CONTROLLER_SUFFIX = "Controller";
    public static final String SERVICE_SUFFIX = "Service";
    public static final String DAO_SUFFIX = "Dao";
    public static final String MAPPER_SUFFIX = "Mapper";
    public static final String DTO_SUFFIX = "DTO";
    public static final String IMPL_SUFFIX = "Impl";
    public static final String EXAMPLE_SUFFIX = "Example";

    public static final String ANNOTATION_RESTCONTROLLER = "@RestController";
    public static final String ANNOTATION_REQUESTMAPPING = "@RequestMapping";
    public static final String ANNOTATION_POSTMAPPING = "@PostMapping";
    public static final String ANNOTATION_SERVICE = "@Service";
    public static final String ANNOTATION_SL4J = "@Slf4j";
    public static final String ANNOTATION_AUTOWIRED = "@Autowired";
    public static final String ANNOTATION_REPOSITORY = "@Repository";
    public static final String ANNOTATION_OVERRIDE = "@Override";
    public static final String ANNOTATION_REQUESTBODY = "@RequestBody";
}
