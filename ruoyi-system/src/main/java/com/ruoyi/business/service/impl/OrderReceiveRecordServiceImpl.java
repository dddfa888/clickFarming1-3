package com.ruoyi.business.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.business.domain.MRewardRecord;
import com.ruoyi.business.domain.ProductManage;
import com.ruoyi.business.mapper.MRewardRecordMapper;
import com.ruoyi.business.mapper.ProductManageMapper;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DecimalUtil;
import com.ruoyi.click.domain.MAccountChangeRecords;
import com.ruoyi.click.domain.UserGrade;
import com.ruoyi.click.mapper.MAccountChangeRecordsMapper;
import com.ruoyi.click.mapper.MUserMapper;
import com.ruoyi.click.mapper.UserGradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.OrderReceiveRecordMapper;
import com.ruoyi.business.domain.OrderReceiveRecord;
import com.ruoyi.business.service.IOrderReceiveRecordService;

import static com.ruoyi.common.utils.SecurityUtils.getUserId;

/**
 * 订单接收记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-17
 */
@Service
public class OrderReceiveRecordServiceImpl implements IOrderReceiveRecordService
{
    @Autowired
    private OrderReceiveRecordMapper orderReceiveRecordMapper;
    @Autowired
    private ProductManageMapper productManageMapper;
    @Autowired
    private MUserMapper mUserMapper;
    @Autowired
    private UserGradeMapper userGradeMapper;
    @Autowired
    private MAccountChangeRecordsMapper mAccountChangeRecordsMapper;
    @Autowired
    private MRewardRecordMapper mRewardRecordMapper;

    /**
     * 查询订单接收记录
     *
     * @param id 订单接收记录主键
     * @return 订单接收记录
     */
    @Override
    public OrderReceiveRecord selectOrderReceiveRecordById(Long id)
    {
        return orderReceiveRecordMapper.selectOrderReceiveRecordById(id);
    }

    /**
     * 查询订单接收记录列表
     *
     * @param orderReceiveRecord 订单接收记录
     * @return 订单接收记录
     */
    @Override
    public List<OrderReceiveRecord> selectOrderReceiveRecordList(OrderReceiveRecord orderReceiveRecord)
    {
        return orderReceiveRecordMapper.selectOrderListOrderDescId(orderReceiveRecord);
    }

    /**
     * 倒序查询一个用户订单接收记录列表
     *
     * @param orderReceiveRecord 订单接收记录
     * @return 订单接收记录
     */
    @Override
    public List<OrderReceiveRecord> selectOrderListByUser(OrderReceiveRecord orderReceiveRecord)
    {
        orderReceiveRecord.setUserId(getUserId());
        return orderReceiveRecordMapper.selectListOrderDesc(orderReceiveRecord);
    }

    /**
     * 新增订单接收记录
     *
     * @param orderReceiveRecord 订单接收记录
     * @return 结果
     */
    @Override
    public int insertOrderReceiveRecord(OrderReceiveRecord orderReceiveRecord)
    {
        orderReceiveRecord.setCreateTime(DateUtils.getNowDate());
        return orderReceiveRecordMapper.insertOrderReceiveRecord(orderReceiveRecord);
    }

    /**
     * 修改订单接收记录
     *
     * @param orderReceiveRecord 订单接收记录
     * @return 结果
     */
    @Override
    public int updateOrderReceiveRecord(OrderReceiveRecord orderReceiveRecord)
    {
        orderReceiveRecord.setUpdateTime(DateUtils.getNowDate());
        return orderReceiveRecordMapper.updateOrderReceiveRecord(orderReceiveRecord);
    }

    /**
     * 批量删除订单接收记录
     *
     * @param ids 需要删除的订单接收记录主键
     * @return 结果
     */
    @Override
    public int deleteOrderReceiveRecordByIds(Long[] ids)
    {
        return orderReceiveRecordMapper.deleteOrderReceiveRecordByIds(ids);
    }

    /**
     * 删除订单接收记录信息
     *
     * @param id 订单接收记录主键
     * @return 结果
     */
    @Override
    public int deleteOrderReceiveRecordById(Long id)
    {
        return orderReceiveRecordMapper.deleteOrderReceiveRecordById(id);
    }

    /**
     * 统计一个用户当日的订单数量
     *
     * @param
     * @return 结果
     */
    @Override
    public int countNumByUserDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        Map<String,Object> param = new HashMap<>();
        param.put("userId", getUserId());
        param.put("date1", formatter.format(today));
        param.put("date2", formatter.format(today.plusDays(1)));
        return orderReceiveRecordMapper.countNumByUserDate(param);
    }


    /**
     * 前台用户点击后添加订单
     * 为了数据入库后返回id，orderReceiveRecord由Controller传过来而不是本方法内新建
     * @return 新增订单数量
     */
    @Override
    public int insertOrderByUser(OrderReceiveRecord orderReceiveRecord){

        MUser mUser = mUserMapper.selectMUserByUid(getUserId());
        OrderReceiveRecord orderParam = new OrderReceiveRecord();
        orderParam.setUserId(mUser.getUid());
        orderParam.setProcessStatus(OrderReceiveRecord.PROCESS_STATUS_WAIT);
        long unfinishedCount = orderReceiveRecordMapper.countNum(orderParam);
        if(unfinishedCount>0)
            throw new ServiceException("有订单未完成，请先付款");

        UserGrade userGrade = userGradeMapper.selectUserGradeBySortNum(mUser.getLevel());
        if(userGrade==null)
            throw new ServiceException("用户等级不存在");
        //若余额小于等级内设置的最低余额，则给出提示，下单失败
        if(mUser.getAccountBalance().compareTo(userGrade.getMinBalance())<0)
            throw new ServiceException("您的帐户尚未达到最低余额");

        int todayCount = countNumByUserDate();
        int numTarget = userGrade.getBuyProdNum();
        if(todayCount >= numTarget)
            throw new ServiceException("您已完成今天的申请");
        //原系统中当日下单次数达到设置值时提示如下：
        //  您已完成今天的申请
        //  You have completed your order today
        //  Bạn đã hoàn thành hết đơn hôm nay

        orderReceiveRecord.setUserId(mUser.getUid());
        orderReceiveRecord.setUserName(mUser.getLoginAccount());

        //无论是否连单，至少需保存一个订单
        setValueSaveProdList(orderReceiveRecord, mUser, userGrade, numTarget, ++todayCount);
        int saveOrderNum = 1;

        //检查用户表设置的值，判断是否连单，若multiOrderNum，说明需要生成多个订单
        Integer multiOrderNum = mUser.getMultiOrderNum();
        if(multiOrderNum!=null && multiOrderNum>1){
            Long firstOrderId = orderReceiveRecord.getId();
            for(int i=1; i<multiOrderNum; i++){ //上面已经保存1单，所以此处i初始值为1，而不是0
                setValueSaveProdList(orderReceiveRecord, mUser, userGrade, numTarget, ++todayCount);
            }
            saveOrderNum = multiOrderNum;
            //第1个订单的id返回到前端
            orderReceiveRecord.setId(firstOrderId);
        }
        mUserMapper.increaseBrushNumber(mUser.getUid(), saveOrderNum);
        return saveOrderNum;
    }

    /**
     * 设置一个订单的数据并保存入数据库
     */
    public void setValueSaveProdList(OrderReceiveRecord orderReceiveRecord, MUser mUser, UserGrade userGrade, int numTarget, int todayCount){
        // 数据库中随机选产品
        ProductManage product = getProductRand(mUser);
        orderReceiveRecord.setProductId(product.getId());
        orderReceiveRecord.setProductName(product.getProductName());
        orderReceiveRecord.setProductImageUrl(product.getImageUrl());
        orderReceiveRecord.setUnitPrice(product.getPrice());

        // 计算产品数量，先计算用户余额整除产品价格的商，即用户可支付范围内的最大值（最大产品数量）
        int prodNum = mUser.getAccountBalance().divide(product.getPrice(), 0, RoundingMode.DOWN).intValue();
        // 如果上面计算的prodNum是1，产品数量直接设为1。否则，假设prodNum（用户可支付范围内的最大数量）是10，生成随机数取5-10之间的整数作为本次订单实际产品数量。
        if(prodNum>1){
            int half = prodNum>>1;
            prodNum = (int)Math.floor(Math.random() * half) + (prodNum-half);
        }
        orderReceiveRecord.setNumber(prodNum);
        orderReceiveRecord.setTotalAmount(DecimalUtil.multiple(product.getPrice(), orderReceiveRecord.getNumber()));
        orderReceiveRecord.setProfit(calcProfit(userGrade, orderReceiveRecord.getTotalAmount()));
        orderReceiveRecord.setRefundAmount(DecimalUtil.add(orderReceiveRecord.getTotalAmount(), orderReceiveRecord.getProfit()));
        orderReceiveRecord.setProcessStatus(OrderReceiveRecord.PROCESS_STATUS_WAIT);
        orderReceiveRecord.setNumTarget(numTarget);
        orderReceiveRecord.setNumSeq(todayCount);
        orderReceiveRecord.setMultiType(OrderReceiveRecord.MULTI_TYPE_NO);
        orderReceiveRecord.setFreezeStatus(OrderReceiveRecord.FREEZE_STATUS_NO);
        orderReceiveRecord.setCreateTime(DateUtils.getNowDate());
        orderReceiveRecordMapper.insertOrderReceiveRecord(orderReceiveRecord);
    }

    /**
     * 从数据库中随机查询一个产品，只查询价格小于或等于用户余额的
     * @return
     */
    public ProductManage getProductRand(MUser mUser){
        Map<String,Object> paramIds = new HashMap<>();
        paramIds.put("price_Le", mUser.getAccountBalance());
        List<Long> idList = productManageMapper.getIdList(paramIds);
        if(idList==null || idList.isEmpty())
            throw new ServiceException("未查到产品信息");

        int prodIndex = (int) Math.floor(Math.random() * idList.size());
        return productManageMapper.selectProductManageById(idList.get(prodIndex));
    }

    /**
     * 计算利润
     * @return
     */
    public BigDecimal calcProfit(UserGrade userGrade, BigDecimal totalAmount){
        //最大值与最小值之差
        BigDecimal range = DecimalUtil.subtract(userGrade.getMaxBonus(), userGrade.getMinBonus());
        //最大值与最小值之差 * 随机数
        BigDecimal num = DecimalUtil.multiply(range, DecimalUtil.toBigDecimal(Math.random()));
        //最大值与最小值之差 * 随机数 + 最小值 = 最小值与最大值之间的随机数（利率）。原值用%表示，所以最后除以100后是实际利率值。
        BigDecimal ratio = num.add(userGrade.getMinBonus()).divide(DecimalUtil.toBigDecimal(100));
        //利率 * 订单总金额 = 利润  最终保留2位小数
        return DecimalUtil.multiply(ratio, totalAmount).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * 支付订单
     * @param orderId
     */
    @Override
    public int payOrder(Long orderId){
        OrderReceiveRecord orderReceiveRecord = orderReceiveRecordMapper.selectOrderReceiveRecordById(orderId);
        if(orderReceiveRecord==null)
            throw new ServiceException("订单不存在");
        if(OrderReceiveRecord.PROCESS_STATUS_SUCCESS.equals(orderReceiveRecord.getProcessStatus()))
            throw new ServiceException("订单已支付，不可重复支付");

        MUser mUser = mUserMapper.selectMUserByUid(orderReceiveRecord.getUserId());

        BigDecimal balanceBefore = mUser.getAccountBalance(); //记录变化前余额
        BigDecimal balanceChange = orderReceiveRecord.getProfit(); //新增余额
        BigDecimal balanceAfter = DecimalUtil.add(balanceBefore, balanceChange);

        Date nowDate =DateUtils.getNowDate();
        //更新用户余额
        mUser.setAccountBalance(balanceAfter);
        mUser.setUpdateTime(nowDate);
        mUserMapper.updateMUser(mUser);

        //记录余额变化详情
        MAccountChangeRecords changeRecords = new MAccountChangeRecords();
        changeRecords.setAmount(balanceChange);
        changeRecords.setType(0); //0收入 1支出
        changeRecords.setAccountForward(balanceBefore);
        changeRecords.setAccountBack(balanceAfter);
        changeRecords.setUid(String.valueOf(mUser.getUid()));
        changeRecords.setDescription(mUser.getLoginAccount()+"订单奖励");
        changeRecords.setTransactionType(3); // 3:专用于标记订单利润
        changeRecords.setCreateTime(nowDate);
        changeRecords.setRelatedId(orderId.toString());
        mAccountChangeRecordsMapper.insertMAccountChangeRecords(changeRecords);

        //添加奖励记录
        MRewardRecord mRewardRecord= new MRewardRecord();
        mRewardRecord.setUserId(mUser.getUid());
        mRewardRecord.setUserName(mUser.getLoginAccount());
        mRewardRecord.setRewardTime(nowDate);
        mRewardRecord.setRewardAmount(balanceChange);
        mRewardRecord.setBalanceBefore(balanceBefore);
        mRewardRecord.setBalanceAfter(balanceAfter);
        mRewardRecord.setDescription("订单奖励");
        mRewardRecord.setCreateTime(nowDate);
        mRewardRecordMapper.insertMRewardRecord(mRewardRecord);

        //更新值  支付状态：完成
        orderReceiveRecord.setProcessStatus(OrderReceiveRecord.PROCESS_STATUS_SUCCESS);
        orderReceiveRecord.setUpdateTime(nowDate);
        return orderReceiveRecordMapper.updateOrderReceiveRecord(orderReceiveRecord);
    }

}
