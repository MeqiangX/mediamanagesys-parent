package com.mingkai.mappermodule.manager;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingkai.mappermodule.mapper.CinemaMapper;
import com.mingkai.mappermodule.model.Do.cinema.CinemaDo;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:16
 */
@Repository
public class CinemaManager extends ServiceImpl<CinemaMapper, CinemaDo> {
}
