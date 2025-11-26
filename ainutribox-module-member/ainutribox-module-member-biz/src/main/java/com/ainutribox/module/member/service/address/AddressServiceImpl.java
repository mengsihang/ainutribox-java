package com.ainutribox.module.member.service.address;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ainutribox.framework.common.exception.ErrorCode;
import com.ainutribox.module.member.controller.app.address.vo.AppAddressCreateReqVO;
import com.ainutribox.module.member.controller.app.address.vo.AppAddressUpdateReqVO;
import com.ainutribox.module.member.convert.address.AddressConvert;
import com.ainutribox.module.member.dal.dataobject.address.MemberAddressDO;
import com.ainutribox.module.member.dal.mysql.address.MemberAddressMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import jakarta.annotation.Resource;
import org.springframework.web.client.RestTemplate;
import java.util.*;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 用户收件地址 Service 实现类
 *
 * @author 河南小泉山科技
 */
@Service
@Validated
public class AddressServiceImpl implements AddressService {

    @Resource
    private MemberAddressMapper memberAddressMapper;
    @Resource
    private RestTemplate restTemplate;



    @Value("${tjAddress.key}")
    private String tsAddressKey;

    @Value("${tjAddress.api}")
    private String tsAddressApi;

    @Value("${tjAddress.domain}")
    private String tsAddressDomain;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAddress(Long userId, AppAddressCreateReqVO createReqVO) {
        // 如果添加的是默认收件地址，则将原默认地址修改为非默认
        if (Boolean.TRUE.equals(createReqVO.getDefaultStatus())) {
            List<MemberAddressDO> addresses = memberAddressMapper.selectListByUserIdAndDefaulted(userId, true);
            addresses.forEach(address -> memberAddressMapper.updateById(new MemberAddressDO().setId(address.getId()).setDefaultStatus(false)));
        }

        // 插入
        MemberAddressDO address = AddressConvert.INSTANCE.convert(createReqVO);
        address.setUserId(userId);
        memberAddressMapper.insert(address);
        // 返回
        return address.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAddress(Long userId, AppAddressUpdateReqVO updateReqVO) {
        // 校验存在,校验是否能够操作
        validAddressExists(userId, updateReqVO.getId());

        // 如果修改的是默认收件地址，则将原默认地址修改为非默认
        if (Boolean.TRUE.equals(updateReqVO.getDefaultStatus())) {
            List<MemberAddressDO> addresses = memberAddressMapper.selectListByUserIdAndDefaulted(userId, true);
            addresses.stream().filter(u -> !u.getId().equals(updateReqVO.getId())) // 排除自己
                    .forEach(address -> memberAddressMapper.updateById(new MemberAddressDO().setId(address.getId()).setDefaultStatus(false)));
        }

        // 更新
        MemberAddressDO updateObj = AddressConvert.INSTANCE.convert(updateReqVO);
        memberAddressMapper.updateById(updateObj);
    }

    @Override
    public void deleteAddress(Long userId, Long id) {
        // 校验存在,校验是否能够操作
        validAddressExists(userId, id);
        // 删除
        memberAddressMapper.deleteById(id);
    }

    private void validAddressExists(Long userId, Long id) {
        MemberAddressDO addressDO = getAddress(userId, id);
        if (addressDO == null) {
            throw exception(ADDRESS_NOT_EXISTS);
        }
    }

    @Override
    public MemberAddressDO getAddress(Long userId, Long id) {
        return memberAddressMapper.selectByIdAndUserId(id, userId);
    }

    @Override
    public List<MemberAddressDO> getAddressList(Long userId) {
        return memberAddressMapper.selectListByUserIdAndDefaulted(userId, null);
    }

    @Override
    public MemberAddressDO getDefaultUserAddress(Long userId) {
        List<MemberAddressDO> addresses = memberAddressMapper.selectListByUserIdAndDefaulted(userId, true);
        return CollUtil.getFirst(addresses);
    }

    @Override
    public Object getAiAddress(String addressStr) {
        if (ObjectUtil.isEmpty(addressStr)) {
            throw exception(ADDRESS_NULL_STR);
        }
        String url =tsAddressDomain+tsAddressApi+"?key="+tsAddressKey+"&text="+addressStr;
        Map<String,Object> reMap = restTemplate.getForObject(url, Map.class);
        if (null == reMap) {
            throw exception(ADDRESS_NULL_RES);
        }
        if(!reMap.get("code").toString().equals("200")){
            throw exception(new ErrorCode(ADDRESS_NOT_ZERO_RES.getCode(), reMap.get("msg").toString()));
        }
        return reMap.get("result");
    }


}
