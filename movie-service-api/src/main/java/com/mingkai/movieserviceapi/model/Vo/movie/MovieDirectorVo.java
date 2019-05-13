package com.mingkai.movieserviceapi.model.Vo.movie;

import com.mingkai.movieserviceapi.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-19 11:17
 */
@Data
@ApiModel("导演Vo")
public class MovieDirectorVo extends BaseVo {
    private String directorId;

    private String directorNameEn;

    private String directorName;

    private String directorImage;
}
