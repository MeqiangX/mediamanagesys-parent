package com.mingkai.orderserviceapi.manager;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingkai.orderserviceapi.mapper.CinemaScreenMapper;
import com.mingkai.systemcommon.model.Do.cinema.CinemaScreenDo;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:51
 */
@Repository
public class CinemaScreenManager extends ServiceImpl<CinemaScreenMapper, CinemaScreenDo> {
}
