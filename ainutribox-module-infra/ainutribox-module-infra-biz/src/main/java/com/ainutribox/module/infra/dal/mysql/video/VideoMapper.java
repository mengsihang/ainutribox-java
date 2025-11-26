package com.ainutribox.module.infra.dal.mysql.video;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import com.ainutribox.module.infra.controller.admin.file.vo.video.VideoPageReqVO;
import com.ainutribox.module.infra.dal.dataobject.file.FileDO;
import com.ainutribox.module.infra.dal.dataobject.video.VideoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件操作 Mapper
 *
 * @author 河南小泉山科技
 */
@Mapper
public interface VideoMapper extends BaseMapperX<VideoDO> {

    default PageResult<VideoDO> selectPage(VideoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VideoDO>()
                .likeIfPresent(VideoDO::getPath, reqVO.getPath())
                .likeIfPresent(VideoDO::getType, reqVO.getType())
                .betweenIfPresent(VideoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VideoDO::getId));
    }

}
