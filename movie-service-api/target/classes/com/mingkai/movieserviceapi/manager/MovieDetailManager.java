package com.mingkai.movieserviceapi.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingkai.movieserviceapi.mapper.MovieDetailMapper;
import com.mingkai.movieserviceapi.model.Do.movie.MovieDetailDo;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 15:41
 */
@Repository
public class MovieDetailManager extends ServiceImpl<MovieDetailMapper, MovieDetailDo> {

}
