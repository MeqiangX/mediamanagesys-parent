package com.mingkai.mediamanagesysmapper.model.Po.movie;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 22:13
 */
@Data
@ApiModel("后台排片记录PO")
public class MovieArgBackPo extends Page {

    private String arrangeDate;

    private String cinemaName;

    private String movieName;

}
