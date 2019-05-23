package com.mingkai.mappermodule.model.Vo.movie;


import com.mingkai.mappermodule.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-19 11:18
 */
@Data
@ApiModel("预告片VO")
public class MovieTrailerVo extends BaseVo {
    private String movieId;

    private String trailerTitle;

    private String trailerImage;

    private String trailerResourceUrl;
}
