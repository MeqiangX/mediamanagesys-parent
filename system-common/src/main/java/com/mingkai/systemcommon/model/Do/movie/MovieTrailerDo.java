package com.mingkai.systemcommon.model.Do.movie;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.systemcommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description: 电影预告
 * @author: Created by 云风 on 2019-04-02 15:53
 */
@Data
@TableName("mesys_movie_trailer")
public class MovieTrailerDo extends BaseDo {

    private String movieId;

    private String trailerTitle;

    private String trailerImage;

    private String trailerResourceUrl;

}
