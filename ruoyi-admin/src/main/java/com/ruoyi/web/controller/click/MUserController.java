package com.ruoyi.web.controller.click;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.click.domain.MAccountChangeRecords;
import com.ruoyi.click.domain.UserGrade;
import com.ruoyi.click.domain.vo.balanceModel;
import com.ruoyi.click.service.IMAccountChangeRecordsService;
import com.ruoyi.click.service.IMUserService;
import com.ruoyi.click.service.IUserGradeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户Controller
 *
 * @author ruoyi
 * @date 2025-06-15
 */
@RestController
@RequestMapping("/api/user")
public class MUserController extends BaseController
{
    @Autowired
    private IMUserService mUserService;

    @Autowired
    private IUserGradeService userGradeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IMAccountChangeRecordsService accountChangeRecordsService;


    @GetMapping("/userInfo")
    public AjaxResult userInfo(HttpServletRequest request) {
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();
        MUser mUser = mUserService.selectMUserByUid(userId);
        mUser.setLevelName(userGradeService.getOne(new LambdaQueryWrapper<UserGrade>()
                .eq(UserGrade::getSortNum,mUser.getLevel())).getGradeName());
        mUser.setLoginPassword("***************");
        mUser.setFundPassword("***************");
        return success(mUser);
    }

    /**
     * 修改用户余额
     * @param balanceModel
     * @return
     */
    @PostMapping("setBalance")
    public AjaxResult setBalance(HttpServletRequest request,@Validated @RequestBody balanceModel balanceModel) {
        MUser mUser = mUserService.selectMUserByUid(balanceModel.getUid());
        BigDecimal userAccountBalance = mUser.getAccountBalance();
        String userName = tokenService.getLoginUser(request).getUser().getUserName();
        // 修改余额
        HashMap<String, Object> map = mUserService.updateBalance(mUser, balanceModel);

        // 日志记录
        BigDecimal accountBalance = (BigDecimal) map.get("accountBalance");
        Integer type = (Integer) map.get("type");
        MAccountChangeRecords changeRecords = new MAccountChangeRecords();
        changeRecords.setAmount(balanceModel.getBalance());
        changeRecords.setType(type);
        changeRecords.setAccountForward(userAccountBalance);
        changeRecords.setAccountBack(accountBalance);
        changeRecords.setUid(String.valueOf(balanceModel.getUid()));
        changeRecords.setDescription(userName+"[后台修改余额]");
        changeRecords.setTransactionType(1);
        accountChangeRecordsService.insertMAccountChangeRecords(changeRecords);
        // 升级等级
        mUserService.upgrade(mUser.getUid());
        return success();
    }
    /**
     * 查询用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(MUser mUser)
    {
        startPage();
        List<MUser> list = mUserService.selectMUserList(mUser);
        TableDataInfo dataTable = getDataTable(list);
        List<MUser> rows = (List<MUser>) dataTable.getRows();
        rows.forEach(item -> {
            UserGrade userGrade = userGradeService.getOne(new LambdaQueryWrapper<UserGrade>()
                    .eq(UserGrade::getSortNum,item.getLevel()));
            if (userGrade != null) {
                item.setLevelName(userGrade.getGradeName());
            } else {
                item.setLevelName("暂无");
            }
        });
        dataTable.setRows(rows);
        return dataTable;
    }

    /**
     * 导出用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MUser mUser)
    {
        List<MUser> list = mUserService.selectMUserList(mUser);
        ExcelUtil<MUser> util = new ExcelUtil<MUser>(MUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    /**
     * 获取用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/{uid}")
    public AjaxResult getInfo(@PathVariable("uid") Long uid)
    {
        return success(mUserService.selectMUserByUid(uid));
    }

    /**
     * 获取前4级用户上级
     */
    @GetMapping("/getUpToFourLevelInviters")
    public AjaxResult getUpToFourLevelInviters(HttpServletRequest request) {
        Long uid = tokenService.getLoginUser(request).getmUser().getUid();
        MUser mUser = mUserService.selectMUserByUid(uid);
        String inviterCode = mUser.getInviterCode();
        List<MUser> inviterList = new ArrayList<>();

        String currentCode = inviterCode;
        int level = 0;

        while (currentCode != null && level < 4) {
            MUser inviter = mUserService.getOne(
                    new LambdaQueryWrapper<MUser>()
                            .eq(MUser::getInvitationCode, currentCode)
            );

            if (inviter == null) {
                break; // 上级不存在，退出循环
            }

            inviterList.add(inviter);

            // 准备下一轮：用当前上级的 inviter_code 再查上一层
            currentCode = inviter.getInviterCode();
            level++;
        }
        return success(inviterList);
    }

    /**
     * 获取所有上级
     * @param inviterCode
     * @return
     */
    @GetMapping(value = "getAllSuperiorUids")
    public AjaxResult getAllSuperiorUids(String inviterCode) {
        List<MUser> uidList = new ArrayList<>();
        String currentInviterCode = inviterCode;
        while (currentInviterCode != null) {
            MUser inviter = mUserService.getOne(
                    new LambdaQueryWrapper<MUser>()
                            .eq(MUser::getInvitationCode, currentInviterCode)
            );
            if (inviter == null) {
                break;
            }


            UserGrade userGrade = userGradeService.getOne(new LambdaQueryWrapper<UserGrade>()
                    .eq(UserGrade::getSortNum,inviter.getLevel()));
            if (userGrade != null) {
                inviter.setLevelName(userGrade.getGradeName());
            } else {
                inviter.setLevelName("暂无");
            }
            uidList.add(inviter);

            // 向上查找下一个上级
            currentInviterCode = inviter.getInviterCode();
        }

        return success(uidList);
    }




    @GetMapping(value = "editStatus/{uid}")
    public AjaxResult editStatus(@PathVariable("uid") Long uid)
    {
        MUser byId = mUserService.selectMUserByUid(uid);
        byId.setStatus(byId.getStatus() == 1 ? 0 : 1);
        return success(mUserService.updateMUser(byId));
    }


    @GetMapping(value = "editRegisterType/{uid}")
    public AjaxResult editRegisterType(@PathVariable("uid") Long uid)
    {
        MUser byId = mUserService.selectMUserByUid(uid);
        byId.setRegisterType(byId.getRegisterType().equals("0") ? "1" : "0");
        return success(mUserService.updateMUser(byId));
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MUser mUser)
    {

        return toAjax(mUserService.insertMUser(mUser));
    }



    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MUser mUser)
    {
        return toAjax(mUserService.updateMUser(mUser));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uids}")
    public AjaxResult remove(@PathVariable Long[] uids)
    {
        return toAjax(mUserService.deleteMUserByUids(uids));
    }


    /**
     * 修改用户连单数量
     */
    //@PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PutMapping("updateMultiOrderNum")
    public AjaxResult updateMultiOrderNum(@RequestBody MUser mUser)
    {
        return toAjax(mUserService.updateMultiOrderNum(mUser));
    }

}
