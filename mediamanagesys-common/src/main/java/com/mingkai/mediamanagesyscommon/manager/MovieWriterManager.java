package com.mingkai.mediamanagesyscommon.manager;

import com.mingkai.mediamanagesyscommon.mapper.MovieWriterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 15:44
 */
@Component
public class MovieWriterManager {

    @Autowired
    private MovieWriterMapper movieWriterMapper;

    public MovieWriterMapper getMovieWriterMapper() {
        return movieWriterMapper;
    }
}
