package com.ruoyi.click.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.click.domain.MMoneyInvestWithdraw;
import com.ruoyi.click.domain.UserGrade;

/**
 * 存款取款记录Service接口
 *
 * @author ruoyi
 * @date 2025-06-19
 */
public interface IMMoneyInvestWithdrawService extends IService<MMoneyInvestWithdraw>
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
     * 批量删除存款取款记录
     *
     * @param ids 需要删除的存款取款记录主键集合
     * @return 结果
     */
    public int deleteMMoneyInvestWithdrawByIds(Long[] ids);

    /**
     * 删除存款取款记录信息
     *
     * @param id 存款取款记录主键
     * @return 结果
     */
    public int deleteMMoneyInvestWithdrawById(Long id);
}
