package com.ruoyi.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ruoyi.business.domain.OrderReceiveRecord;
import com.ruoyi.business.service.IOrderReceiveRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单接收记录Controller
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@RestController
@RequestMapping("/api/order")
public class OrderReceiveRecordController extends BaseController
{
    @Autowired
    private IOrderReceiveRecordService orderReceiveRecordService;

    /**
     * 查询订单接收记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderReceiveRecord orderReceiveRecord)
    {
        startPage();
        List<OrderReceiveRecord> list = orderReceiveRecordService.selectOrderReceiveRecordList(orderReceiveRecord);
        return getDataTable(list);
    }

    /**
     * 导出订单接收记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "订单接收记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderReceiveRecord orderReceiveRecord)
    {
        List<OrderReceiveRecord> list = orderReceiveRecordService.selectOrderReceiveRecordList(orderReceiveRecord);
        ExcelUtil<OrderReceiveRecord> util = new ExcelUtil<OrderReceiveRecord>(OrderReceiveRecord.class);
        util.exportExcel(response, list, "订单接收记录数据");
    }

    /**
     * 获取订单接收记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderReceiveRecordService.selectOrderReceiveRecordById(id));
    }

    /**
     * 新增订单接收记录
     */
    @PreAuthorize("@ss.hasPermi('system:order:add')")
    @Log(title = "订单接收记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderReceiveRecord orderReceiveRecord)
    {
        return toAjax(orderReceiveRecordService.insertOrderReceiveRecord(orderReceiveRecord));
    }

    /**
     * 修改订单接收记录
     */
    @PreAuthorize("@ss.hasPermi('system:order:edit')")
    @Log(title = "订单接收记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderReceiveRecord orderReceiveRecord)
    {
        return toAjax(orderReceiveRecordService.updateOrderReceiveRecord(orderReceiveRecord));
    }

    /**
     * 删除订单接收记录
     */
    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @Log(title = "订单接收记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderReceiveRecordService.deleteOrderReceiveRecordByIds(ids));
    }

    /**
     * 前台用户点击后添加订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:insertOrderByUser')")
    @Log(title = "前台用户点击后添加订单", businessType = BusinessType.INSERT)
    @PostMapping("/insertOrderByUser")
    public AjaxResult insertOrderByUser()
    {
        OrderReceiveRecord orderReceiveRecord = new OrderReceiveRecord();
        return toAjax(orderReceiveRecordService.insertOrderByUser(orderReceiveRecord))
                .put("orderId", orderReceiveRecord.getId());
    }

    /**
     * 支付订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:payOrder')")
    @Log(title = "支付订单", businessType = BusinessType.UPDATE)
    @PutMapping("/payOrder/{id}")
    public AjaxResult payOrder(@PathVariable("id") Long id)
    {
        return toAjax(orderReceiveRecordService.payOrder(id));
    }

}
