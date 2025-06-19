package com.ruoyi.click.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.click.domain.MAccountChangeRecords;
import com.ruoyi.click.mapper.MAccountChangeRecordsMapper;
import com.ruoyi.click.service.IMAccountChangeRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账变记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-16
 */
@Service
public class MAccountChangeRecordsServiceImpl implements IMAccountChangeRecordsService
{
    @Autowired
    private MAccountChangeRecordsMapper mAccountChangeRecordsMapper;

    /**
     * 查询账变记录
     *
     * @param uuid 账变记录主键
     * @return 账变记录
     */
    @Override
    public MAccountChangeRecords selectMAccountChangeRecordsByUuid(String uuid)
    {
        return mAccountChangeRecordsMapper.selectMAccountChangeRecordsByUuid(uuid);
    }

    /**
     * 查询账变记录列表
     *
     * @param mAccountChangeRecords 账变记录
     * @return 账变记录
     */
    @Override
    public List<MAccountChangeRecords> selectMAccountChangeRecordsList(MAccountChangeRecords mAccountChangeRecords)
    {
        return mAccountChangeRecordsMapper.selectMAccountChangeRecordsList(mAccountChangeRecords);
    }

    /**
     * 新增账变记录
     *
     * @param mAccountChangeRecords 账变记录
     * @return 结果
     */
    @Override
    public int insertMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords)
    {
        mAccountChangeRecords.setCreateTime(DateUtils.getNowDate());
        return mAccountChangeRecordsMapper.insertMAccountChangeRecords(mAccountChangeRecords);
    }

    /**
     * 修改账变记录
     *
     * @param mAccountChangeRecords 账变记录
     * @return 结果
     */
    @Override
    public int updateMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords)
    {
        mAccountChangeRecords.setUpdateTime(DateUtils.getNowDate());
        return mAccountChangeRecordsMapper.updateMAccountChangeRecords(mAccountChangeRecords);
    }

    /**
     * 批量删除账变记录
     *
     * @param uuids 需要删除的账变记录主键
     * @return 结果
     */
    @Override
    public int deleteMAccountChangeRecordsByUuids(String[] uuids)
    {
        return mAccountChangeRecordsMapper.deleteMAccountChangeRecordsByUuids(uuids);
    }

    /**
     * 删除账变记录信息
     *
     * @param uuid 账变记录主键
     * @return 结果
     */
    @Override
    public int deleteMAccountChangeRecordsByUuid(String uuid)
    {
        return mAccountChangeRecordsMapper.deleteMAccountChangeRecordsByUuid(uuid);
    }
}
