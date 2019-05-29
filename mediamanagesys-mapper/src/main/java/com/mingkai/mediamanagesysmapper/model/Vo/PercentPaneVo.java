package com.mingkai.mediamanagesysmapper.model.Vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-28 0:29
 */
@Data
@ApiModel("交易额前十VO")
public class PercentPaneVo {

    private List<String> timeNames;

    private List<MapVo> histogramMap;

    private List<MapVo> paneMap;

}
