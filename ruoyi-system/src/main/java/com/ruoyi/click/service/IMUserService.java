package com.ruoyi.click.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.click.domain.vo.UserRegisterModel;
import com.ruoyi.click.domain.vo.balanceModel;

import java.util.HashMap;
import java.util.List;

/**
 * 用户Service接口
 *
 * @author ruoyi
 * @date 2025-06-15
 */
public interface IMUserService extends IService<MUser> {
    /**
     * 查询用户
     *
     * @param uid 用户主键
     * @return 用户
     */
    public MUser selectMUserByUid(Long uid);

    /**
     * 查询用户列表
     *
     * @param mUser 用户
     * @return 用户集合
     */
    public List<MUser> selectMUserList(MUser mUser);

    /**
     * 新增用户
     *
     * @param mUser 用户
     * @return 结果
     */
    public int insertMUser(MUser mUser);

    /**
     * 修改用户
     *
     * @param mUser 用户
     * @return 结果
     */
    public int updateMUser(MUser mUser);

    /**
     * 批量删除用户
     *
     * @param uids 需要删除的用户主键集合
     * @return 结果
     */
    public int deleteMUserByUids(Long[] uids);

    /**
     * 删除用户信息
     *
     * @param uid 用户主键
     * @return 结果
     */
    public int deleteMUserByUid(Long uid);

    void userRegister(UserRegisterModel model);

    MUser getUserOne(String loginAccount, String loginPassword);

    HashMap<String, Object> updateBalance(MUser mUser, balanceModel balanceModel);

    void upgrade(Long uid);



    /**
     * 用户当天刷单数量清零
     */
    void clearBrushNumber();

    /**
     * 用户当日订单数量加1
     * @param uid 用户主键
     * @return
     */
    int increaseBrushNumber(Long uid, Integer num);

}
