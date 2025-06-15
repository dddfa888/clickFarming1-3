package com.ruoyi.system.service.click.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.system.domain.click.vo.UserRegisterModel;
import com.ruoyi.system.mapper.click.MUserMapper;
import com.ruoyi.system.mapper.click.UserGradeMapper;
import com.ruoyi.system.service.click.IMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-15
 */
@Service
public class MUserServiceImpl extends ServiceImpl<MUserMapper, MUser>  implements IMUserService {
    @Autowired
    private MUserMapper mUserMapper;

    @Autowired
    private UserGradeMapper userGradeMapper;

    /**
     * 查询用户
     *
     * @param uid 用户主键
     * @return 用户
     */
    @Override
    public MUser selectMUserByUid(Long uid)
    {
        return mUserMapper.selectMUserByUid(uid);
    }

    /**
     * 查询用户列表
     *
     * @param mUser 用户
     * @return 用户
     */
    @Override
    public List<MUser> selectMUserList(MUser mUser)
    {
        return mUserMapper.selectMUserList(mUser);
    }
    /**
     * 新增用户
     *
     * @param mUser 用户
     * @return 结果
     */
    @Override
    public int insertMUser(MUser mUser) {
        if (StringUtils.isNotBlank(mUser.getInviterCode())) {
            MUser inviterUser = this.getOne(
                    new LambdaQueryWrapper<MUser>()
                            .eq(MUser::getInvitationCode, mUser.getInviterCode())
            );

            if (inviterUser == null) {
                throw new ServiceException("邀请码无效");
            }

            mUser.setInviter(String.valueOf(inviterUser.getUid()));
            mUser.setInviterName(inviterUser.getLoginAccount());
            mUser.setInviterCode(inviterUser.getInvitationCode());

            StringBuilder builder = new StringBuilder();
            String higherUid = inviterUser.getHigherUid();
            if (higherUid != null) {
                builder.append(higherUid).append(",");
            }
            builder.append(inviterUser.getUid());
            mUser.setHigherUid(builder.toString());
        }

        // 校验账号是否重复
        String loginAccount = mUser.getLoginAccount();
        if (StringUtils.isNotBlank(loginAccount) &&
                this.getOne(new LambdaQueryWrapper<MUser>().eq(MUser::getLoginAccount, loginAccount)) != null) {
            throw new ServiceException("账号已存在");
        }

        mUser.setBrushNumber(0L);
        mUser.setAccountBalance(DecimalUtil.toBigDecimal(0));
        mUser.setLoginPassword(EncoderUtil.encoder(mUser.getLoginPassword()));
        mUser.setFundPassword(EncoderUtil.encoder(mUser.getFundPassword()));
        mUser.setInvitationCode(getRandomNumber());
        mUser.setCreateTime(DateUtils.getNowDate());
        mUser.setStatus(1);
        return mUserMapper.insertMUser(mUser);
    }

    public String getRandomNumber(){
        String randomNumber = RandomUtil.generateRandomNumber(6);
        long count = this.count(new LambdaQueryWrapper<MUser>().eq(MUser::getInvitationCode, randomNumber));
        if(count>0){
            getRandomNumber();
        }
        return randomNumber;
    }

    /**
     * 修改用户
     *
     * @param mUser 用户
     * @return 结果
     */
    @Override
    public int updateMUser(MUser mUser)
    {
        mUser.setUpdateTime(DateUtils.getNowDate());

        MUser user = mUserMapper.selectMUserByUid(mUser.getUid());
        if(!user.getLoginPassword().equals(mUser.getLoginPassword())){
            mUser.setLoginPassword(EncoderUtil.encoder(mUser.getLoginPassword()));

        }
        if(!user.getFundPassword().equals(mUser.getFundPassword())){
            mUser.setFundPassword(EncoderUtil.encoder(mUser.getFundPassword()));

        }
        return mUserMapper.updateMUser(mUser);
    }

    /**
     * 批量删除用户
     *
     * @param uids 需要删除的用户主键
     * @return 结果
     */
    @Override
    public int deleteMUserByUids(Long[] uids)
    {
        return mUserMapper.deleteMUserByUids(uids);
    }

    /**
     * 删除用户信息
     *
     * @param uid 用户主键
     * @return 结果
     */
    @Override
    public int deleteMUserByUid(Long uid)
    {
        return mUserMapper.deleteMUserByUid(uid);
    }

    @Override
    public void userRegister(UserRegisterModel model) {
        MUser inviterUser = this.getOne(
                new LambdaQueryWrapper<MUser>()
                        .eq(MUser::getInvitationCode, model.getInvitationCode())
        );

        if (inviterUser == null) {
            throw new ServiceException("邀请码无效");
        }

        MUser mUser = new MUser();
        mUser.setInviter(String.valueOf(inviterUser.getUid()));
        mUser.setInviterName(inviterUser.getLoginAccount());
        mUser.setInviterCode(inviterUser.getInvitationCode());

        StringBuilder builder = new StringBuilder();
        String higherUid = inviterUser.getHigherUid();
        if (higherUid != null) {
            builder.append(higherUid).append(",");
        }
        builder.append(inviterUser.getUid());
        mUser.setHigherUid(builder.toString());

        // 校验账号是否重复
        String loginAccount = model.getLoginAccount();
        if (StringUtils.isNotBlank(loginAccount) &&
                this.getOne(new LambdaQueryWrapper<MUser>().eq(MUser::getLoginAccount, loginAccount)) != null) {
            throw new ServiceException("账号已存在");
        }
        mUser.setLoginAccount(loginAccount);
        mUser.setBrushNumber(0L);
        mUser.setAccountBalance(DecimalUtil.toBigDecimal(0));
        mUser.setLoginPassword(EncoderUtil.encoder(model.getLoginPassword()));
        mUser.setFundPassword(EncoderUtil.encoder(model.getFundPassword()));
        mUser.setInvitationCode(getRandomNumber());
        mUser.setCreateTime(DateUtils.getNowDate());
        mUser.setRegisterType("1");
        mUser.setPhoneNumber(model.getPhone());
        mUser.setStatus(1);
        // TODO 此处需要思考
        mUser.setLevelId(1);
        mUserMapper.insertMUser(mUser);
    }

    @Override
    public MUser getUserOne(String loginAccount, String loginPassword) {
        LambdaQueryWrapper<MUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MUser::getLoginAccount, loginAccount);
        MUser user = this.getOne(wrapper);
        if (user == null) {
            throw new ServiceException("没有该用户");
        }

        boolean matches = EncoderUtil.matches(loginPassword, user.getLoginPassword());
        if (!matches) {
            throw new ServiceException("密码错误");
        }
        return user;
    }
}
