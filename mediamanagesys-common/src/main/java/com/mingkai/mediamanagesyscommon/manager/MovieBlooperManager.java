package com.mingkai.mediamanagesyscommon.manager;

import com.mingkai.mediamanagesyscommon.mapper.MovieBlooperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 15:38
 */
@Component
public class MovieBlooperManager {

    @Autowired
    private MovieBlooperMapper movieBlooperMapper;

    public MovieBlooperMapper getMovieBlooperMapper() {
        return movieBlooperMapper;
    }
}
