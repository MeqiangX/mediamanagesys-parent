package com.mingkai.movieserviceapi.model.Vo.movie;

import com.mingkai.movieserviceapi.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-19 11:19
 */
@Data
@ApiModel("花絮Vo")
public class MovieBlooperVo extends BaseVo {

    private String movieId;

    private String blooperTitle;

    private String blooperImage;

    private String blooperResourceUrl;
}
