package com.mingkai.mediamanagesyscommon.manager;

import com.mingkai.mediamanagesyscommon.mapper.MovieDirectorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 15:42
 */
@Component
public class MovieDirectorManager {

    @Autowired
    private MovieDirectorMapper movieDirectorMapper;

    public MovieDirectorMapper getMovieDirectorMapper() {
        return movieDirectorMapper;
    }
}
