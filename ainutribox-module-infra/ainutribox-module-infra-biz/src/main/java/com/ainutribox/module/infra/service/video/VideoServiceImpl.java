package com.ainutribox.module.infra.service.video;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.io.FileUtils;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.infra.controller.admin.file.vo.video.VideoCreateReqVO;
import com.ainutribox.module.infra.controller.admin.file.vo.video.VideoPageReqVO;
import com.ainutribox.module.infra.controller.admin.file.vo.video.VideoPresignedUrlRespVO;
import com.ainutribox.module.infra.dal.dataobject.video.VideoDO;
import com.ainutribox.module.infra.dal.mysql.video.VideoMapper;
import com.ainutribox.module.infra.framework.file.core.client.FileClient;
import com.ainutribox.module.infra.framework.file.core.client.s3.FilePresignedUrlRespDTO;
import com.ainutribox.module.infra.framework.file.core.utils.FileTypeUtils;
import com.ainutribox.module.infra.service.file.FileConfigService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.infra.enums.ErrorCodeConstants.FILE_NOT_EXISTS;

/**
 * 文件 Service 实现类
 *
 * @author 河南小泉山科技
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Resource
    private FileConfigService fileConfigService;

    @Resource
    private VideoMapper videoMapper;

    @Override
    public PageResult<VideoDO> getFilePage(VideoPageReqVO pageReqVO) {
        return videoMapper.selectPage(pageReqVO);
    }

    @Override
    @SneakyThrows
    public String createFile(String name, String path, byte[] content) {
        // 计算默认的 path 名
        String type = FileTypeUtils.getMineType(content, name);
        if (StrUtil.isEmpty(path)) {
            path = FileUtils.generatePath(content, name);
        }
        // 如果 name 为空，则使用 path 填充
        if (StrUtil.isEmpty(name)) {
            name = path;
        }

        // 上传到文件存储器
        FileClient client = fileConfigService.getMasterFileClient();
        Assert.notNull(client, "客户端(master) 不能为空");
        String url = client.upload(content, path, type);

        // 保存到数据库
        VideoDO file = new VideoDO();
        file.setConfigId(client.getId());
        file.setName(name);
        file.setPath(path);
        file.setUrl(url);
        file.setType(type);
        file.setSize(content.length);
        videoMapper.insert(file);
        return url;
    }

    @Override
    public Long createFile(VideoCreateReqVO createReqVO) {
        VideoDO file = BeanUtils.toBean(createReqVO, VideoDO.class);
        videoMapper.insert(file);
        return file.getId();
    }

    @Override
    public void deleteFile(Long id) throws Exception {
        // 校验存在
        VideoDO file = validateFileExists(id);

        // 从文件存储器中删除
        FileClient client = fileConfigService.getFileClient(file.getConfigId());
        Assert.notNull(client, "客户端({}) 不能为空", file.getConfigId());
        client.delete(file.getPath());

        // 删除记录
        videoMapper.deleteById(id);
    }

    private VideoDO validateFileExists(Long id) {
        VideoDO fileDO = videoMapper.selectById(id);
        if (fileDO == null) {
            throw exception(FILE_NOT_EXISTS);
        }
        return fileDO;
    }

    @Override
    public byte[] getFileContent(Long configId, String path) throws Exception {
        FileClient client = fileConfigService.getFileClient(configId);
        Assert.notNull(client, "客户端({}) 不能为空", configId);
        return client.getContent(path);
    }

    @Override
    public VideoPresignedUrlRespVO getFilePresignedUrl(String path) throws Exception {
        FileClient fileClient = fileConfigService.getMasterFileClient();
        FilePresignedUrlRespDTO presignedObjectUrl = fileClient.getPresignedObjectUrl(path);
        return BeanUtils.toBean(presignedObjectUrl, VideoPresignedUrlRespVO.class,
                object -> object.setConfigId(fileClient.getId()));
    }

}
