package com.mingkai.systemcommon.model.Do.movie;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.systemcommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description: 电影主演
 * @author: Created by 云风 on 2019-04-02 15:50
 */
@TableName("mesys_movie_casts")
@Data
public class MovieCastDo extends BaseDo {

    private String actorId;

    private String actorNameEn;

    private String actorName;

    private String actorImage;


}
