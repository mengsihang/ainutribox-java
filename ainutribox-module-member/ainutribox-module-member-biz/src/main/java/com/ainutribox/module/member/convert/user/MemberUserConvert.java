package com.ainutribox.module.member.convert.user;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.module.member.api.user.dto.MemberUserRespDTO;
import com.ainutribox.module.member.controller.admin.user.vo.MemberUserRespVO;
import com.ainutribox.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import com.ainutribox.module.member.controller.app.user.vo.AppMemberUserInfoRespVO;
import com.ainutribox.module.member.convert.address.AddressConvert;
import com.ainutribox.module.member.dal.dataobject.group.MemberGroupDO;
import com.ainutribox.module.member.dal.dataobject.level.MemberLevelDO;
import com.ainutribox.module.member.dal.dataobject.tag.MemberTagDO;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.system.api.user.dto.AdminUserRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static com.ainutribox.framework.common.util.collection.CollectionUtils.convertList;
import static com.ainutribox.framework.common.util.collection.CollectionUtils.convertMap;

@Mapper(uses = {AddressConvert.class})
public interface MemberUserConvert {

    MemberUserConvert INSTANCE = Mappers.getMapper(MemberUserConvert.class);

    AppMemberUserInfoRespVO convert(MemberUserDO bean);

    @Mapping(source = "level", target = "level")
    @Mapping(source = "bean.experience", target = "experience")
    AppMemberUserInfoRespVO convert(MemberUserDO bean, MemberLevelDO level);

    MemberUserRespDTO convert2(MemberUserDO bean);

    List<MemberUserRespDTO> convertList2(List<MemberUserDO> list);

    MemberUserDO convert(MemberUserUpdateReqVO bean);

    PageResult<MemberUserRespVO> convertPage(PageResult<MemberUserDO> page);

    @Mapping(source = "areaId", target = "areaName", qualifiedByName = "convertAreaIdToAreaName")
    MemberUserRespVO convert03(MemberUserDO bean);




    default PageResult<MemberUserRespVO> convertPage(PageResult<MemberUserDO> pageResult,
                                                     List<MemberTagDO> tags,
                                                     List<MemberLevelDO> levels,
                                                     List<MemberGroupDO> groups,
                                                     List<MemberUserDO> members,
                                                     List<AdminUserRespDTO> distributors,
                                                     List<AdminUserRespDTO> nutritionS) {
        PageResult<MemberUserRespVO> result = convertPage(pageResult);
        // 处理关联数据
        Map<Long, String> tagMap = convertMap(tags, MemberTagDO::getId, MemberTagDO::getName);
        Map<Long, String> levelMap = convertMap(levels, MemberLevelDO::getId, MemberLevelDO::getName);
        Map<Long, String> groupMap = convertMap(groups, MemberGroupDO::getId, MemberGroupDO::getName);
        Map<Long, String> meberMap = convertMap(members, MemberUserDO::getId, MemberUserDO::getNickname);
        Map<Long, String> distributorMap = convertMap(distributors, AdminUserRespDTO::getId, AdminUserRespDTO::getNickname);
        Map<Long, String> nutritionMap = convertMap(nutritionS, AdminUserRespDTO::getId, AdminUserRespDTO::getNickname);

        // 填充关联数据
        result.getList().forEach(user -> {
            user.setTagNames(convertList(user.getTagIds(), tagMap::get));
            user.setLevelName(levelMap.get(user.getLevelId()));
            user.setGroupName(groupMap.get(user.getGroupId()));
            user.setMemberNikeName(meberMap.get(user.getMemberId()));
            user.setDistributorNikeName(distributorMap.get(user.getDistributorId()));
            user.setNutritionNikeName(nutritionMap.get(user.getNutritionId()));
            user.setOpenId(user.getOpenId());
        });
        return result;
    }



}
