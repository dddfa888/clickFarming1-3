package com.ruoyi.system.service.click;

import com.ruoyi.system.domain.click.MAccountChangeRecords;

import java.util.List;

/**
 * 账变记录Service接口
 *
 * @author ruoyi
 * @date 2025-06-16
 */
public interface IMAccountChangeRecordsService
{
    /**
     * 查询账变记录
     *
     * @param uuid 账变记录主键
     * @return 账变记录
     */
    public MAccountChangeRecords selectMAccountChangeRecordsByUuid(String uuid);

    /**
     * 查询账变记录列表
     *
     * @param mAccountChangeRecords 账变记录
     * @return 账变记录集合
     */
    public List<MAccountChangeRecords> selectMAccountChangeRecordsList(MAccountChangeRecords mAccountChangeRecords);

    /**
     * 新增账变记录
     *
     * @param mAccountChangeRecords 账变记录
     * @return 结果
     */
    public int insertMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords);

    /**
     * 修改账变记录
     *
     * @param mAccountChangeRecords 账变记录
     * @return 结果
     */
    public int updateMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords);

    /**
     * 批量删除账变记录
     *
     * @param uuids 需要删除的账变记录主键集合
     * @return 结果
     */
    public int deleteMAccountChangeRecordsByUuids(String[] uuids);

    /**
     * 删除账变记录信息
     *
     * @param uuid 账变记录主键
     * @return 结果
     */
    public int deleteMAccountChangeRecordsByUuid(String uuid);
}
