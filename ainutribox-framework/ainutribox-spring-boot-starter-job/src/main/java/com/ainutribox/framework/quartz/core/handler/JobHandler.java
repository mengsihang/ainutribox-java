package com.ainutribox.framework.quartz.core.handler;

/**
 * 任务处理器
 *
 * @author 河南小泉山科技
 */
public interface JobHandler {

    /**
     * 执行任务
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    String execute(String param) throws Exception;

}
