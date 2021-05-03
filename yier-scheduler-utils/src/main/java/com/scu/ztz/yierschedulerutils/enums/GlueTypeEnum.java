package com.scu.ztz.yierschedulerutils.enums;
// Inspired by XXL-job
public enum GlueTypeEnum {
    GLUE_GROOVY("GLUE(Java)", "java");
    // GLUE_SQL("GLUE(SQL)", ".sql");


    private String desc;
    private String suffix;

    private GlueTypeEnum(String desc, String suffix) {
        this.desc = desc;
        this.suffix = suffix;
    }
    
    public String getDesc() {
        return desc;
    }

    public String getSuffix() {
        return suffix;
    }
    
    public static GlueTypeEnum match(String name){
        for (GlueTypeEnum item: GlueTypeEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
