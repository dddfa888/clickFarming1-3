package com.ruoyi.click.domain.vo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class WithdrawVo {

    @DecimalMin(value = "0.01", inclusive = true, message = "余额必须大于0")
    private BigDecimal amount;

    @NotBlank(message = "资金密码不能为空")
    private String fundPassword;
}
