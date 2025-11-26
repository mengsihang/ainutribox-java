package com.ainutribox.module.pay.service.wallet;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.module.pay.controller.admin.wallet.vo.transaction.PayWalletTransactionPageReqVO;
import com.ainutribox.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionPageReqVO;
import com.ainutribox.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionSummaryRespVO;
import com.ainutribox.module.pay.convert.wallet.PayWalletTransactionConvert;
import com.ainutribox.module.pay.dal.dataobject.wallet.PayWalletDO;
import com.ainutribox.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import com.ainutribox.module.pay.dal.mysql.wallet.PayWalletTransactionMapper;
import com.ainutribox.module.pay.dal.redis.no.PayNoRedisDAO;
import com.ainutribox.module.pay.enums.wallet.PayWalletBizTypeEnum;
import com.ainutribox.module.pay.service.wallet.bo.WalletTransactionCreateReqBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;

import static com.ainutribox.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionPageReqVO.TYPE_EXPENSE;
import static com.ainutribox.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionPageReqVO.TYPE_INCOME;

/**
 * 钱包流水 Service 实现类
 *
 * @author jason
 */
@Service
@Slf4j
@Validated
public class PayWalletTransactionServiceImpl implements PayWalletTransactionService {

    /**
     * 钱包流水的 no 前缀
     */
    private static final String WALLET_NO_PREFIX = "W";

    @Resource
    private PayWalletService payWalletService;
    @Resource
    private PayWalletTransactionMapper payWalletTransactionMapper;
    @Resource
    private PayNoRedisDAO noRedisDAO;

    @Override
    public PageResult<PayWalletTransactionDO> getWalletTransactionPage(Long userId, Integer userType,
                                                                       AppPayWalletTransactionPageReqVO pageVO) {
        PayWalletDO wallet = payWalletService.getOrCreateWallet(userId, userType);
        return payWalletTransactionMapper.selectPage(wallet.getId(), pageVO.getType(), pageVO, pageVO.getCreateTime());
    }

    @Override
    public PageResult<PayWalletTransactionDO> getWalletTransactionPage(PayWalletTransactionPageReqVO pageVO) {
        return payWalletTransactionMapper.selectPage(pageVO.getWalletId(), null, pageVO, null);
    }

    @Override
    public PayWalletTransactionDO createWalletTransaction(WalletTransactionCreateReqBO bo) {
        PayWalletTransactionDO transaction = PayWalletTransactionConvert.INSTANCE.convert(bo)
                .setNo(noRedisDAO.generate(WALLET_NO_PREFIX));
        payWalletTransactionMapper.insert(transaction);
        return transaction;
    }

    @Override
    public PayWalletTransactionDO getWalletTransactionByNo(String no) {
        return payWalletTransactionMapper.selectByNo(no);
    }

    @Override
    public PayWalletTransactionDO getWalletTransaction(String bizId, PayWalletBizTypeEnum type) {
        return payWalletTransactionMapper.selectByBiz(bizId, type.getType());
    }

    @Override
    public AppPayWalletTransactionSummaryRespVO getWalletTransactionSummary(Long userId, Integer userType, LocalDateTime[] createTime) {
        PayWalletDO wallet = payWalletService.getOrCreateWallet(userId, userType);
        AppPayWalletTransactionSummaryRespVO summary = new AppPayWalletTransactionSummaryRespVO()
                .setTotalExpense(1).setTotalIncome(100);
        return new AppPayWalletTransactionSummaryRespVO()
                .setTotalExpense(payWalletTransactionMapper.selectPriceSum(wallet.getId(), TYPE_EXPENSE, createTime))
                .setTotalIncome(payWalletTransactionMapper.selectPriceSum(wallet.getId(), TYPE_INCOME, createTime));
    }

}
