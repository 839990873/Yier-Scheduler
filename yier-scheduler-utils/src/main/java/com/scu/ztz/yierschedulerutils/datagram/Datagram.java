package com.scu.ztz.yierschedulerutils.datagram;

import com.scu.ztz.yierschedulerutils.enums.DelimiterEnum;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

// 网络传输的真正对象，任何Datagram的被传输之前，都需要先通过DatagramConverter转换为该对象。其中type为该数据的类型，json为该数据序列化的json结果。然后再写入到netty channel中
// 在接收端，通过反序列化获得一个Datagram，通过type来确认该对象事实上的type，并通过json来反序列化得到真正的数据类型
// 缺点:需要两次序列化和反序列化，影响效率。
public class Datagram {
    String type;
    String json;

    public String getJSON() {
        return json;
    }

    public String getType() {
        return type;
    }

    Datagram(String type, String json) {
        this.type = type;
        this.json = json;
    }

    // Datagram序列化
    public String toJsonWithDelimiter() {
        String DatagramJson = GsonTool.toJson(this);
        return DatagramJson + DelimiterEnum.SERVICE_DELIMITER.getDelimiter();
    }

    // 反序列化Datagram
    public static Datagram fromJson(String json) {
        Datagram datagram = GsonTool.fromJson(json, Datagram.class);
        return datagram;
    }
}
