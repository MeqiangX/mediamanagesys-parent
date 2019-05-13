package com.mingkai.movieserviceapi.model.Do.movie;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.movieserviceapi.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description: 导演
 * @author: Created by 云风 on 2019-04-02 15:58
 */
@Data
@TableName("mesys_movie_directors")
public class MovieDirectorDo extends BaseDo {

    private String directorId;

    private String directorNameEn;

    private String directorName;

    private String directorImage;

}
