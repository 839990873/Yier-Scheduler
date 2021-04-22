package com.scu.ztz.yierschedulerutils.enums.service;

// 单例，被创建一次，保存在全局中。
public class CommonServiceEnums extends ServiceEnums{
    public static final String RETURN = "return_common";

    private static final String[] commonServiceEnums = new String[]{RETURN};

    @Override
    public String[] getEnums() {
        return commonServiceEnums;
    }
    
}
// public enum CommonServiceEnums {
//     RETURN("return_common");
//     private String name;

//     CommonServiceEnums(String name) {
//         this.name = name;
//     }
    
//     public static CommonServiceEnums match(String name) {
//         if (name != null) {
//             for (CommonServiceEnums item : CommonServiceEnums.values()) {
//                 if (item.name().equals(name)) {
//                     return item;
//                 }
//             }
//         }
//         return null;
//     }
// }
