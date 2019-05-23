package com.mingkai.mediamanagesysmapper.model.Do.movie;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesysmapper.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description: 电影编剧
 * @author: Created by 云风 on 2019-04-02 15:56
 */
@Data
@TableName("mesys_movie_writers")
public class MovieWriterDo extends BaseDo {

    private String writerId;

    private String writerNameEn;

    private String writerName;

    private String writerImage;

}
