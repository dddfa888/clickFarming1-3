package com.ruoyi.click.mapper;

import com.ruoyi.click.domain.MMoneyInvestWithdraw;

import java.util.List;

/**
 * 存款取款记录Mapper接口
 *
 * @author ruoyi
 * @date 2025-06-19
 */
public interface MMoneyInvestWithdrawMapper
{
    /**
     * 查询存款取款记录
     *
     * @param id 存款取款记录主键
     * @return 存款取款记录
     */
    public MMoneyInvestWithdraw selectMMoneyInvestWithdrawById(Long id);

    /**
     * 查询存款取款记录列表
     *
     * @param mMoneyInvestWithdraw 存款取款记录
     * @return 存款取款记录集合
     */
    public List<MMoneyInvestWithdraw> selectMMoneyInvestWithdrawList(MMoneyInvestWithdraw mMoneyInvestWithdraw);

    /**
     * 新增存款取款记录
     *
     * @param mMoneyInvestWithdraw 存款取款记录
     * @return 结果
     */
    public int insertMMoneyInvestWithdraw(MMoneyInvestWithdraw mMoneyInvestWithdraw);

    /**
     * 修改存款取款记录
     *
     * @param mMoneyInvestWithdraw 存款取款记录
     * @return 结果
     */
    public int updateMMoneyInvestWithdraw(MMoneyInvestWithdraw mMoneyInvestWithdraw);

    /**
     * 删除存款取款记录
     *
     * @param id 存款取款记录主键
     * @return 结果
     */
    public int deleteMMoneyInvestWithdrawById(Long id);

    /**
     * 批量删除存款取款记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMMoneyInvestWithdrawByIds(Long[] ids);
}
