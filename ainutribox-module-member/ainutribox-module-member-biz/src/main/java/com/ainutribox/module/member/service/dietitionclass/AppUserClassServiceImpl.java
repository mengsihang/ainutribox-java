package com.ainutribox.module.member.service.dietitionclass;

import cn.hutool.core.util.ObjectUtil;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.util.MyBatisUtils;
import com.ainutribox.module.member.controller.admin.dietition.vo.DietitionInfoRespVO;
import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.DietitionClassChildRespVO;
import com.ainutribox.module.member.controller.app.dietitionclass.dto.*;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppClassChildVO;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppDietitionInfoVo;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppUserClassVO;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.DietitianProductVO;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscomment.DietitionClassCommentDO;
import com.ainutribox.module.member.dal.dataobject.joinclass.JoinClassDO;
import com.ainutribox.module.member.dal.dataobject.spotclass.SpotClassDO;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.dal.mysql.dietitionclass.DietitionClassMapper;
import com.ainutribox.module.member.dal.mysql.dietitionclasschild.DietitionClassChildMapper;
import com.ainutribox.module.member.dal.mysql.dietitionclasscomment.DietitionClassCommentMapper;
import com.ainutribox.module.member.dal.mysql.joinclass.JoinClassMapper;
import com.ainutribox.module.member.dal.mysql.spotclass.SpotClassMapper;
import com.ainutribox.module.member.enums.TryLockConstants;
import com.ainutribox.module.member.service.dietition.DietitionInfoService;
import com.ainutribox.module.member.service.user.MemberUserService;
import com.ainutribox.module.member.util.RedisUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * AppUserClassServiceImpl
 *
 * @author lucifer
 * @date 2024-06-29 11:44
 */
@Service
public class AppUserClassServiceImpl implements  AppUserClassService{

    @Resource
    private DietitionClassMapper dietitionClassMapper;

    @Resource
    private MemberUserService userService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private JoinClassMapper joinClassMapper;

    @Resource
    private AppDietitionClassService appdietitionClassService;
    @Resource
    private DietitionClassChildMapper classChildMapper;

    @Resource
    private SpotClassMapper spotClassMapper;

    @Resource
    private DietitionClassCommentMapper classCommentMapper;

    @Resource
    private DietitionInfoService dietitionInfoService;




    @Override
    public PageResult<AppUserClassVO> getAllClassPage(AppUserClassDTO appUserClassDTO) {
        IPage<AppUserClassVO> classPageVo = dietitionClassMapper.selectUserClassPage(MyBatisUtils.buildPage(appUserClassDTO),appUserClassDTO);
        return new PageResult<>(classPageVo.getRecords(),classPageVo.getTotal());

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void joinClass(Long userId, AppJoinOrCancelClassDTO joinOrCancelClassDTO) {
        //操作类型不对
        if(joinOrCancelClassDTO.getType() != 1){
            throw exception(CLASS_TYPE_ERROR);
        }
        //频繁操作
        if(!redisUtils.tryLock(userId+TryLockConstants.JOIN_CLASS_LOCK+joinOrCancelClassDTO.getClassId(),TryLockConstants.DEFAULT_LOCK_VALUE,TryLockConstants.DEFAULT_LOCK_TIME, TryLockConstants.DEFAULT_LOCK_TIMEUNIT)){
            throw exception(CLASS_FREQUENTLY_ERROR);
        }

        if(appdietitionClassService.isClassExpired(joinOrCancelClassDTO.getClassId())== 0){
            throw exception(PAY_CLASS_NOT_EXISTS);
        }


        //判断是否已经加入
        Integer joinStatus = userService.isMemberJoinClassExpired(userId,joinOrCancelClassDTO.getClassId());

        if(joinStatus == 1){
            throw exception(JOIN_CLASS_EXISTS);
        }

        JoinClassDO joinClassDO = new JoinClassDO();
        joinClassDO.setClassId(joinOrCancelClassDTO.getClassId());
        joinClassDO.setMemberId(userId);

        joinClassMapper.insert(joinClassDO);

        appdietitionClassService.updateClassPeople(joinOrCancelClassDTO.getClassId(),1);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelClass(Long userId, AppJoinOrCancelClassDTO joinOrCancelClassDTO) {

        //操作类型不对
        if(joinOrCancelClassDTO.getType() != 2){
            throw exception(CLASS_TYPE_ERROR);
        }
        //频繁操作
        if(!redisUtils.tryLock(userId+TryLockConstants.CANCEL_CLASS_LOCK+joinOrCancelClassDTO.getClassId(),TryLockConstants.DEFAULT_LOCK_VALUE,TryLockConstants.DEFAULT_LOCK_TIME,TryLockConstants.DEFAULT_LOCK_TIMEUNIT)){
            throw exception(CLASS_FREQUENTLY_ERROR);
        }

        if(appdietitionClassService.isClassExpired(joinOrCancelClassDTO.getClassId())== 0){
            throw exception(PAY_CLASS_NOT_EXISTS);
        }

        //判断是否已经加入
        Integer joinStatus = userService.isMemberJoinClassExpired(userId,joinOrCancelClassDTO.getClassId());

        if(joinStatus != 1){
            throw exception(NOT_JOIN_CLASS_EXISTS);
        }
        //因为是中间表直接物理删除
        joinClassMapper.deleteByUserIdAndClassId(userId,joinOrCancelClassDTO.getClassId());

        appdietitionClassService.updateClassPeople(joinOrCancelClassDTO.getClassId(),-1);

    }

    @Override
    public List<AppUserClassVO> selectUserJoinClassList(Long userId) {
        return dietitionClassMapper.selectUserJoinClassList(userId);
    }

    @Override
    public List<AppUserClassVO> selectUserBuyClassList(Long userId) {
        return dietitionClassMapper.selectUserBuyClassList(userId);
    }

    @Override
    public AppClassChildVO getClassChileVO(Long userId, Long classId) {

        AppClassChildVO appClassChildVO = new AppClassChildVO();

        AppUserClassVO appUserClassVO = dietitionClassMapper.selectClassVo(classId);
        appUserClassVO.setScore(appUserClassVO.getScore());
        appClassChildVO.setAppUserClassVO(appUserClassVO);

        //获得评论数
        Long commentNum = classCommentMapper.selectCount("class_id",classId);

        appClassChildVO.setCommentNum(commentNum);


        //判断是否已购买课程
       Integer payStatus =  userService.isMemberPayClassExpired(userId,classId);
       //判断是否已加入
       Integer joinStatus = userService.isMemberJoinClassExpired(userId,classId);
       //判断是否是会员
       Integer vipStatus = userService.isMemberVipExpired(userId);
       //点在状态
        Integer spotStatus = userService.isSpotExpired(userId,classId);
        appClassChildVO.setSpotStatus(spotStatus);
        appClassChildVO.setJoinStatus(joinStatus);
        appClassChildVO.setPayStatus(payStatus);

       LambdaQueryWrapperX<DietitionClassChildDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
       lambdaQueryWrapperX.eq(DietitionClassChildDO::getClassId,classId);
       List<DietitionClassChildDO> classChildDOList = classChildMapper.selectList(lambdaQueryWrapperX);
       if(payStatus == 1){
           return  appClassChildVO.setClassChildDOList(BeanUtils.toBean(classChildDOList, DietitionClassChildRespVO.class));
       }
       if(vipStatus == 1){
           classChildDOList.stream()
                   .filter(item -> item.getChildType() == 2)
                   .forEach(item -> item.setVideoUrl(""));

           return  appClassChildVO.setClassChildDOList(BeanUtils.toBean(classChildDOList, DietitionClassChildRespVO.class));
       }

        classChildDOList.stream()
                .filter(item -> item.getChildType() == 2 || item.getChildType() == 1)
                .forEach(item -> item.setVideoUrl(""));

        return  appClassChildVO.setClassChildDOList(BeanUtils.toBean(classChildDOList, DietitionClassChildRespVO.class));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void userSpotClass(Long userId, AppSpotOrCancelClassDTO spotOrCancelClassDTO){

        //操作类型不对
        if(spotOrCancelClassDTO.getType() != 1){
            throw exception(CLASS_TYPE_ERROR);
        }

        Long classId = spotOrCancelClassDTO.getClassId();

        //频繁操作
        if(!redisUtils.tryLock(userId+TryLockConstants.SPOT_CLASS_LOCK+classId,TryLockConstants.DEFAULT_LOCK_VALUE,TryLockConstants.DEFAULT_LOCK_TIME,TryLockConstants.DEFAULT_LOCK_TIMEUNIT)){
            throw exception(CLASS_FREQUENTLY_ERROR);
        }

        if(appdietitionClassService.isClassExpired(classId)== 0){
            throw exception(PAY_CLASS_NOT_EXISTS);
        }


        //判断是否已经点赞
        Integer spotStatus = userService.isSpotExpired(userId,classId);
        if(spotStatus == 1){
            throw exception(SPOT_CLASS_EXISTS);
        }

        SpotClassDO spotClassDO = new SpotClassDO();
        spotClassDO.setClassId(classId);
        spotClassDO.setUserId(userId);

        spotClassMapper.insert(spotClassDO);

        //更新课程点赞数
        appdietitionClassService.updateClassSpot(classId,1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void userCreateComment(Long userId, AppClassCommentDTO appClassCommentDTO) {

        Long classId = appClassCommentDTO.getClassId();

        //频繁操作
        if(!redisUtils.tryLock(userId+TryLockConstants.COMMENT_CLASS_LOCK+classId,TryLockConstants.DEFAULT_LOCK_VALUE,TryLockConstants.COMMENT_LOCK_TIME,TryLockConstants.DEFAULT_LOCK_TIMEUNIT)){
            throw exception(CLASS_FREQUENTLY_ERROR);
        }

        if(appdietitionClassService.isClassExpired(classId)== 0){
            throw exception(PAY_CLASS_NOT_EXISTS);
        }

        DietitionClassCommentDO dietitionClassCommentDO = BeanUtils.toBean(appClassCommentDTO,DietitionClassCommentDO.class);

        //获取用户信息
        MemberUserDO userDO = userService.getUser(userId);
        dietitionClassCommentDO.setUserId(userId);
        dietitionClassCommentDO.setUserAvatar(userDO.getAvatar());
        dietitionClassCommentDO.setUserNickname(userDO.getNickname());

        classCommentMapper.insert(dietitionClassCommentDO);


        //获取用户课程评分
        Integer scoresAvg = dietitionClassMapper.getClassAverageScore(classId);
        appdietitionClassService.updateClassAvg(classId,scoresAvg);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dietitionReplyComment(Long userId, AppClassReplyCommentDTO appClassReplyCommentDTO) {

        Long commentId = appClassReplyCommentDTO.getCommentId();

        //频繁操作
        if(!redisUtils.tryLock(userId+TryLockConstants.COMMENT_CLASS_LOCK+commentId,TryLockConstants.DEFAULT_LOCK_VALUE,TryLockConstants.COMMENT_LOCK_TIME,TryLockConstants.DEFAULT_LOCK_TIMEUNIT)){
            throw exception(CLASS_FREQUENTLY_ERROR);
        }

        DietitionClassCommentDO dietitionClassCommentDO = BeanUtils.toBean(appClassReplyCommentDTO,DietitionClassCommentDO.class);
        dietitionClassCommentDO.setId(appClassReplyCommentDTO.getCommentId());

        //获取导师信息
        DietitionInfoDO dietitionInfoDO = dietitionInfoService.getDietitionByUserId(userId);
        dietitionClassCommentDO.setDietitionId(userId);
        dietitionClassCommentDO.setUserAvatar(dietitionInfoDO.getInfoPic());
        dietitionClassCommentDO.setUserNickname(dietitionInfoDO.getNickName());
        dietitionClassCommentDO.setReplyTime(LocalDateTime.now());
        dietitionClassCommentDO.setReplyStatus(true);

        classCommentMapper.updateById(dietitionClassCommentDO);

    }

    @Override
    public AppDietitionInfoVo getDietitionInfoData(AppDietitionInfoDTO appDietitionInfoDTO) {

        if(ObjectUtil.isEmpty(appDietitionInfoDTO.getDietitionId())){
            throw exception(DIETITION_NOT_ID_EXISTS);
        }

        AppDietitionInfoVo appDietitionInfoVo = new AppDietitionInfoVo();

        DietitionInfoDO dietitionInfoDO =  dietitionInfoService.getDietitionByUserId(appDietitionInfoDTO.getDietitionId());
        List<AppUserClassVO> dietitionClassList = dietitionClassMapper.selectDietitionClassList(appDietitionInfoDTO);
        List<DietitianProductVO> dietitianProductList = dietitionClassMapper.selectDietitionProductList(appDietitionInfoDTO.getDietitionId());

        appDietitionInfoVo.setDietitionInfoDO(dietitionInfoDO);
        appDietitionInfoVo.setDietitionClassList(dietitionClassList);
        appDietitionInfoVo.setDietitianProductList(dietitianProductList);

        return  appDietitionInfoVo;
    }

    @Override
    public PageResult<DietitionInfoRespVO> getDietitionInfoList(AppUserClassDTO appUserClassDTO) {
        IPage<DietitionInfoRespVO> infoRespVOIPage = dietitionClassMapper.getDietitionInfoList(MyBatisUtils.buildPage(appUserClassDTO),appUserClassDTO);
        return new PageResult<>(infoRespVOIPage.getRecords(),infoRespVOIPage.getTotal());
    }


}
