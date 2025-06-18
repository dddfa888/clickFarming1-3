package com.ruoyi.click.domain.vo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class balanceModel {

    @NotNull(message = "用户ID不能为空")
    private Long uid;

    @NotNull(message = "请选择是增加还是减少")
    private boolean increaseDecrease;

    @DecimalMin(value = "0.01", inclusive = true, message = "余额必须大于0")
    private BigDecimal balance;
}
