package com.mingkai.mappermodule.model.Do.movie;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mappermodule.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description: 电影花絮
 * @author: Created by 云风 on 2019-04-02 15:52
 */
@Data
@TableName("mesys_movie_blooper")
public class MovieBlooperDo extends BaseDo {

    private String movieId;

    private String blooperTitle;

    private String blooperImage;

    private String blooperResourceUrl;

}
