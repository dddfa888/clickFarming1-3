package com.ruoyi.system.domain.click;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户等级对象 user_grade
 *
 * @author ruoyi
 * @date 2025-06-15
 */
@Data
public class UserGrade
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
    private BigDecimal maxBonus;

    private Date updateTime;
    private Date createTime;
}
