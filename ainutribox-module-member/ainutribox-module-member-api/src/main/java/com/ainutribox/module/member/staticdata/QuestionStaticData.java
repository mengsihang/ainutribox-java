package com.ainutribox.module.member.staticdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionStaticData {
    // 内部静态类，用于延迟加载数据
    private static class DataHolder {
        // 初始化静态数据
        private static final Map<Integer, String> DATA_LIST = new HashMap<>();

        static {

            //初始化数据
            DATA_LIST.put(0, "基础问题");
            DATA_LIST.put(1, "专项问题");


        }
    }

    // 获取数据列表的方法
    public static Map<Integer, String> getDataMap() {
        return DataHolder.DATA_LIST;
    }

    // 私有构造方法防止实例化
    private QuestionStaticData() {
        throw new UnsupportedOperationException("StaticData class cannot be instantiated.");
    }


}
