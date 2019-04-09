package com.mingkai.mediamanagesyscommon.manager;

import com.mingkai.mediamanagesyscommon.mapper.MovieTrailerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 15:43
 */
@Component
public class MovieTrailerManager {

    @Autowired
    private MovieTrailerMapper movieTrailerMapper;

    public MovieTrailerMapper getMovieTrailerMapper() {
        return movieTrailerMapper;
    }
}
