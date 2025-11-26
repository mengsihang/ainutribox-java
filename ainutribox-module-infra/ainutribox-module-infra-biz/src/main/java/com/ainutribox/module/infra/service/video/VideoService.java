package com.ainutribox.module.infra.service.video;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.module.infra.controller.admin.file.vo.file.FileCreateReqVO;
import com.ainutribox.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import com.ainutribox.module.infra.controller.admin.file.vo.file.FilePresignedUrlRespVO;
import com.ainutribox.module.infra.controller.admin.file.vo.video.VideoCreateReqVO;
import com.ainutribox.module.infra.controller.admin.file.vo.video.VideoPageReqVO;
import com.ainutribox.module.infra.controller.admin.file.vo.video.VideoPresignedUrlRespVO;
import com.ainutribox.module.infra.dal.dataobject.file.FileDO;
import com.ainutribox.module.infra.dal.dataobject.video.VideoDO;

/**
 * 文件 Service 接口
 *
 * @author 河南小泉山科技
 */
public interface VideoService {

    /**
     * 获得文件分页
     *
     * @param pageReqVO 分页查询
     * @return 文件分页
     */
    PageResult<VideoDO> getFilePage(VideoPageReqVO pageReqVO);

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param name    文件名称
     * @param path    文件路径
     * @param content 文件内容
     * @return 文件路径
     */
    String createFile(String name, String path, byte[] content);

    /**
     * 创建文件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFile(VideoCreateReqVO createReqVO);

    /**
     * 删除文件
     *
     * @param id 编号
     */
    void deleteFile(Long id) throws Exception;

    /**
     * 获得文件内容
     *
     * @param configId 配置编号
     * @param path     文件路径
     * @return 文件内容
     */
    byte[] getFileContent(Long configId, String path) throws Exception;

    /**
     * 生成文件预签名地址信息
     *
     * @param path 文件路径
     * @return 预签名地址信息
     */
    VideoPresignedUrlRespVO getFilePresignedUrl(String path) throws Exception;

}
