package com.ainutribox.module.member.service.code;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.code.vo.*;
import com.ainutribox.module.member.dal.dataobject.code.CodeDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.code.CodeMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 积分兑换码 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class CodeServiceImpl implements CodeService {

    @Resource
    private CodeMapper codeMapper;

    @Override
    public Long createCode(CodeSaveReqVO createReqVO) {
        // 校验兑换码存在
        validateCodeExistsByCode(createReqVO.getCode());
        // 插入
        CodeDO code = BeanUtils.toBean(createReqVO, CodeDO.class);
        codeMapper.insert(code);
        // 返回
        return code.getId();
    }

    @Override
    public void updateCode(CodeSaveReqVO updateReqVO) {
        // 校验存在
        validateCodeExists(updateReqVO.getId());
        // 更新
        CodeDO updateObj = BeanUtils.toBean(updateReqVO, CodeDO.class);
        codeMapper.updateById(updateObj);
    }

    @Override
    public void deleteCode(Long id) {
        // 校验存在
        validateCodeExists(id);
        // 删除
        codeMapper.deleteById(id);
    }

    private void validateCodeExists(Long id) {
        if (codeMapper.selectById(id) == null) {
            throw exception(CODE_NOT_EXISTS);
        }
    }

    private void validateCodeExistsByCode(Integer code) {
        if (codeMapper.selectOne("code", code)!=null ) {
            throw exception(CODE_IS_EXISTS);
        }
    }

    @Override
    public CodeDO getCode(Long id) {
        return codeMapper.selectById(id);
    }

    @Override
    public PageResult<CodeDO> getCodePage(CodePageReqVO pageReqVO) {
        return codeMapper.selectPage(pageReqVO);
    }

}