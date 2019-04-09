package com.mingkai.mediamanagesyscommon.manager;

import com.mingkai.mediamanagesyscommon.mapper.MovieCastMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 15:40
 */
@Component
public class MovieCastManager {

    @Autowired
    private MovieCastMapper movieCastMapper;

    public MovieCastMapper getMovieCastMapper() {
        return movieCastMapper;
    }
}
