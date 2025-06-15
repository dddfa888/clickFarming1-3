package com.ruoyi.system.domain.click;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 用户等级对象 user_grade
 *
 * @author ruoyi
 * @date 2025-06-15
 */
public class UserGrade extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 序号 */
    @Excel(name = "序号")
    private Long sortNum;

    /** 等级名称 */
    @Excel(name = "等级名称")
    private String gradeName;

    /** 参加费 */
    @Excel(name = "参加费")
    private String joinCost;

    /** 最低余额 */
    @Excel(name = "最低余额")
    private BigDecimal minBalance;

    /** 每天购买的产品数量 */
    @Excel(name = "每天购买的产品数量")
    private Long buyProdNum;

    /** 利润（%） */
    @Excel(name = "最小奖金", readConverterExp = "%=")
    private BigDecimal minBonus;

    /** 最大奖金（%） */
    @Excel(name = "最大奖金", readConverterExp = "%=")
    private String maxBonus;


    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
        this.minBalance = minBalance;
    }


    public BigDecimal getMinBonus() {
        return minBonus;
    }

    public void setMinBonus(BigDecimal minBonus) {
        this.minBonus = minBonus;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setSortNum(Long sortNum)
    {
        this.sortNum = sortNum;
    }

    public Long getSortNum()
    {
        return sortNum;
    }

    public void setGradeName(String gradeName)
    {
        this.gradeName = gradeName;
    }

    public String getGradeName()
    {
        return gradeName;
    }

    public void setJoinCost(String joinCost)
    {
        this.joinCost = joinCost;
    }

    public String getJoinCost()
    {
        return joinCost;
    }



    public void setBuyProdNum(Long buyProdNum)
    {
        this.buyProdNum = buyProdNum;
    }

    public Long getBuyProdNum()
    {
        return buyProdNum;
    }




    public void setMaxBonus(String maxBonus)
    {
        this.maxBonus = maxBonus;
    }

    public String getMaxBonus()
    {
        return maxBonus;
    }


}
