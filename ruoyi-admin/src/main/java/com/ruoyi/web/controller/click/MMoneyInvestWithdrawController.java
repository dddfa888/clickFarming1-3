package com.ruoyi.web.controller.click;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.business.domain.OrderReceiveRecord;
import com.ruoyi.business.mapper.OrderReceiveRecordMapper;
import com.ruoyi.click.domain.MAccountChangeRecords;
import com.ruoyi.click.domain.MMoneyInvestWithdraw;
import com.ruoyi.click.domain.vo.BackOperateVo;
import com.ruoyi.click.domain.vo.WithdrawVo;
import com.ruoyi.click.service.IMAccountChangeRecordsService;
import com.ruoyi.click.service.IMMoneyInvestWithdrawService;
import com.ruoyi.click.service.IMUserService;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DecimalUtil;
import com.ruoyi.common.utils.EncoderUtil;
import com.ruoyi.common.utils.RandomUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 存款取款记录Controller
 *
 * @author ruoyi
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/api/withdraw")
public class MMoneyInvestWithdrawController extends BaseController
{
    @Autowired
    private IMMoneyInvestWithdrawService mMoneyInvestWithdrawService;


    @Autowired
    private IMUserService mUserService;

    @Autowired
    private TokenService tokenService;


    @Autowired
    private IMAccountChangeRecordsService mAccountChangeRecordsService;

    @Autowired
    private OrderReceiveRecordMapper orderReceiveRecordMapper;


    /**
     * 获取个人的提现记录
     * @param request
     * @return
     */
    @GetMapping("/userList")
    public TableDataInfo userList(HttpServletRequest request) {
        startPage();
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();
        MMoneyInvestWithdraw withdraw = new MMoneyInvestWithdraw();
        withdraw.setUserId(userId);
        List<MMoneyInvestWithdraw> list = mMoneyInvestWithdrawService.selectMMoneyInvestWithdrawList(withdraw);
        return getDataTable(list);
    }

    /**
     * 查询存款取款记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MMoneyInvestWithdraw mMoneyInvestWithdraw)
    {
        startPage();
        List<MMoneyInvestWithdraw> list = mMoneyInvestWithdrawService.selectMMoneyInvestWithdrawList(mMoneyInvestWithdraw);
        return getDataTable(list);
    }

    /**
     * 导出存款取款记录列表
     */
    @Log(title = "存款取款记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MMoneyInvestWithdraw mMoneyInvestWithdraw)
    {
        List<MMoneyInvestWithdraw> list = mMoneyInvestWithdrawService.selectMMoneyInvestWithdrawList(mMoneyInvestWithdraw);
        ExcelUtil<MMoneyInvestWithdraw> util = new ExcelUtil<MMoneyInvestWithdraw>(MMoneyInvestWithdraw.class);
        util.exportExcel(response, list, "存款取款记录数据");
    }

    /**
     * 获取存款取款记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mMoneyInvestWithdrawService.selectMMoneyInvestWithdrawById(id));
    }

    /**
     * 同意
     * @param backOperateVo
     * @return
     */
    @PostMapping(value = "/agree")
    public AjaxResult agree(@Validated @RequestBody BackOperateVo  backOperateVo) {
        MMoneyInvestWithdraw withdraw = mMoneyInvestWithdrawService.selectMMoneyInvestWithdrawById(backOperateVo.getId());
        if (withdraw == null) {
            return error("提现订单不存在");
        }
        withdraw.setStatus(1);
        mMoneyInvestWithdrawService.updateMMoneyInvestWithdraw( withdraw);
        return success();
    }

    /**
     * 驳回
     * @param backOperateVo
     * @return
     */
    @PostMapping(value = "/reject")
    public AjaxResult reject(@Validated @RequestBody BackOperateVo  backOperateVo) {
        MMoneyInvestWithdraw withdraw = mMoneyInvestWithdrawService.selectMMoneyInvestWithdrawById(backOperateVo.getId());
        if (withdraw == null) {
            return error("提现订单不存在");
        }
        withdraw.setReasonForRejection(backOperateVo.getNotes());
        withdraw.setStatus(2);
        mMoneyInvestWithdrawService.updateMMoneyInvestWithdraw( withdraw);

        // 驳回金额返还
        MUser mUser = mUserService.selectMUserByUid(withdraw.getUserId());
        BigDecimal accountForward = mUser.getAccountBalance();
        BigDecimal accountBack = DecimalUtil.add(accountForward, withdraw.getAmount());
        mUser.setAccountBalance(accountBack);
        mUserService.updateMUser(mUser);

        // 日志
        MAccountChangeRecords mAccountChangeRecords = new MAccountChangeRecords();
        mAccountChangeRecords.setLoginAccount(mUser.getLoginAccount());
        mAccountChangeRecords.setUid(String.valueOf(mUser.getUid()));
        mAccountChangeRecords.setAmount(withdraw.getAmount());
        mAccountChangeRecords.setAccountBack(accountBack);
        mAccountChangeRecords.setAccountForward(accountForward);
        mAccountChangeRecords.setType(0);
        mAccountChangeRecords.setTransactionType(4);
        mAccountChangeRecords.setDescription("提现订单["+withdraw.getOrderId()+"] 驳回");
        mAccountChangeRecordsService.insertMAccountChangeRecords(mAccountChangeRecords);
        return success();
    }

    /**
     * 新增存款取款记录
     */
    @Log(title = "提现", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(HttpServletRequest request,@Validated @RequestBody WithdrawVo withdrawVo) {
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();

        MUser mUser = mUserService.selectMUserByUid(userId);
        boolean matches = EncoderUtil.matches(withdrawVo.getFundPassword(), mUser.getFundPassword());
        if(!matches){
            throw new ServiceException("资金密码错误");

        }
        BigDecimal accountForward = mUser.getAccountBalance();
        if (accountForward.compareTo(withdrawVo.getAmount()) < 0) {
            return AjaxResult.error("余额不足");
        }
        OrderReceiveRecord orderParam = new OrderReceiveRecord();
        orderParam.setUserId(mUser.getUid());
        orderParam.setProcessStatus(OrderReceiveRecord.PROCESS_STATUS_WAIT);
        long unfinishedCount = orderReceiveRecordMapper.countNum(orderParam);
        if(unfinishedCount>0){
            throw new ServiceException("有订单未完成，不可提现");
        }
        checkBank(mUser);

        BigDecimal accountBack = DecimalUtil.subtract(accountForward, withdrawVo.getAmount());
        mUser.setAccountBalance(accountBack);
        mUserService.updateMUser(mUser);


        MMoneyInvestWithdraw mMoneyInvestWithdraw = new MMoneyInvestWithdraw();
        mMoneyInvestWithdraw.setAmount(withdrawVo.getAmount());
        mMoneyInvestWithdraw.setUserId(mUser.getUid());
        mMoneyInvestWithdraw.setUserName(mUser.getLoginAccount());
        mMoneyInvestWithdraw.setBankName(mUser.getBankName());
        mMoneyInvestWithdraw.setBankAccountName(mUser.getBankAccountName());
        mMoneyInvestWithdraw.setBankAccountNumber(mUser.getBankAccountNumber());
        mMoneyInvestWithdraw.setOrderId(RandomUtil.generateRandomNumber(16));
        mMoneyInvestWithdraw.setType("0");
        mMoneyInvestWithdraw.setStatus(0);
        mMoneyInvestWithdraw.setAccountForward(accountForward);
        mMoneyInvestWithdraw.setAccountBack(accountBack);
        mMoneyInvestWithdrawService.insertMMoneyInvestWithdraw(mMoneyInvestWithdraw);


        MAccountChangeRecords mAccountChangeRecords = new MAccountChangeRecords();
        mAccountChangeRecords.setUid(String.valueOf(mUser.getUid()));
        mAccountChangeRecords.setAmount(withdrawVo.getAmount());
        mAccountChangeRecords.setAccountBack(accountBack);
        mAccountChangeRecords.setAccountForward(accountForward);
        mAccountChangeRecords.setType(1);
        mAccountChangeRecords.setTransactionType(2);
        mAccountChangeRecords.setDescription("发起提现订单["+mMoneyInvestWithdraw.getOrderId()+"]");
        mAccountChangeRecordsService.insertMAccountChangeRecords(mAccountChangeRecords);
        return success();
    }

    private void checkBank(MUser mUser) {
        if (StringUtils.isAnyBlank(
                mUser.getBankName(),
                mUser.getBankAccountName(),
                mUser.getBankAccountNumber())) {
            throw new ServiceException("银行信息不完整，请填写银行名称、账户名称和账号");
        }
    }

    /**
     * 修改存款取款记录
     */
    @Log(title = "存款取款记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MMoneyInvestWithdraw mMoneyInvestWithdraw)
    {
        return toAjax(mMoneyInvestWithdrawService.updateMMoneyInvestWithdraw(mMoneyInvestWithdraw));
    }

    /**
     * 删除存款取款记录
     */
    @Log(title = "存款取款记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mMoneyInvestWithdrawService.deleteMMoneyInvestWithdrawByIds(ids));
    }
}
