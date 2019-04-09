package com.mingkai.mediamanagesyscommon.manager;

import com.mingkai.mediamanagesyscommon.mapper.MovieDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 15:41
 */
@Component
public class MovieDetailManager {

    @Autowired
    private MovieDetailMapper movieDetailMapper;

    public MovieDetailMapper getMovieDetailMapper() {
        return movieDetailMapper;
    }
}
