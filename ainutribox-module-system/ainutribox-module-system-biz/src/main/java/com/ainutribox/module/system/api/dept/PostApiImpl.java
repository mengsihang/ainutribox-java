package com.ainutribox.module.system.api.dept;

import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.system.api.dept.dto.PostRespDTO;
import com.ainutribox.module.system.dal.dataobject.dept.PostDO;
import com.ainutribox.module.system.service.dept.PostService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 岗位 API 实现类
 *
 * @author 河南小泉山科技
 */
@Service
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public void validPostList(Collection<Long> ids) {
        postService.validatePostList(ids);
    }

    @Override
    public List<PostRespDTO> getPostList(Collection<Long> ids) {
        List<PostDO> list = postService.getPostList(ids);
        return BeanUtils.toBean(list, PostRespDTO.class);
    }

}
