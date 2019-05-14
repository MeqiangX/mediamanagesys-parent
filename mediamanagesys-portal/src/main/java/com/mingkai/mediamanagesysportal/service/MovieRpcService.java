package com.mingkai.mediamanagesysportal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesysportal.feigns.MovieRpcFeign;
import com.mingkai.systemcommon.model.Po.movie.MoviePagePo;
import com.mingkai.systemcommon.model.Po.movie.MovieRankPagePo;
import com.mingkai.systemcommon.model.Po.movie.MovieSearchPo;
import com.mingkai.systemcommon.model.Vo.movie.MovieBlooperVo;
import com.mingkai.systemcommon.model.Vo.movie.MovieTrailerVo;
import com.mingkai.systemcommon.model.Vo.movie.MovieVo;
import com.mingkai.systemcommon.utils.check.CheckOfR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-13 21:58
 */
@Service
public class MovieRpcService {

    @Autowired
    private MovieRpcFeign movieRpcFeign;

    public Boolean portalTest(){
       return CheckOfR.check(movieRpcFeign.portalTest());
    }


    public MovieVo movieDetailsById(String id){
        return CheckOfR.check(movieRpcFeign.movieDetailsById(id));
    }


    public Page<MovieVo> movieRank(MovieRankPagePo movieRankPagePo){
        return CheckOfR.check(movieRpcFeign.movieRank(movieRankPagePo));
    }


    public Page<MovieVo> moviePage(MoviePagePo moviePagePo){
        return CheckOfR.check(movieRpcFeign.moviePage(moviePagePo));
    }


    public List<MovieTrailerVo> movieTrailers(String movieId){
        return CheckOfR.check(movieRpcFeign.movieTrailers(movieId));
    }


    public List<MovieBlooperVo> movieBloopers(String movieId){
        return CheckOfR.check(movieRpcFeign.movieBloopers(movieId));
    }


    public Page<MovieVo> searchMovies(MovieSearchPo movieSearchPo){
        return CheckOfR.check(movieRpcFeign.searchMovies(movieSearchPo));

    }

}
