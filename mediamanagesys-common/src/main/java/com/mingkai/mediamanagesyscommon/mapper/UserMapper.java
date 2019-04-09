package com.mingkai.mediamanagesyscommon.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesyscommon.model.Do.uc.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-03-30 17:50
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    @Select("select * from mesys_user where (user_name = #{account} or phone = #{account} or email = #{account}) and user_password = #{password} and status = 0 and is_deleted = 0")
    UserDO login(@Param("account") String account, @Param("password") String password);


}
