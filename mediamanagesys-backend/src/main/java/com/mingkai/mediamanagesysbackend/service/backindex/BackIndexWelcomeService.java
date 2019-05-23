package com.mingkai.mediamanagesysbackend.service.backindex;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mingkai.mediamanagesysbackend.model.PO.EchartsPo;
import com.mingkai.mediamanagesysbackend.model.VO.EchartsVo;
import com.mingkai.mediamanagesysbackend.model.VO.PaneDataVo;
import com.mingkai.mediamanagesysmapper.manager.CinemaManager;
import com.mingkai.mediamanagesysmapper.manager.MovieDetailManager;
import com.mingkai.mediamanagesysmapper.manager.TicketDetailManager;
import com.mingkai.mediamanagesysmapper.manager.UserAdminManager;
import com.mingkai.mediamanagesysmapper.mapper.ScreenRoomMapper;
import com.mingkai.mediamanagesysmapper.mapper.UserMapper;
import com.mingkai.mediamanagesysmapper.model.Do.EchartsDo;
import com.mingkai.mediamanagesysmapper.model.Do.UserAdminDo;
import com.mingkai.mediamanagesysmapper.model.Do.order.TicketDetailDo;
import com.mingkai.mediamanagesysmapper.model.Do.uc.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-04 20:07
 */
@Service
@Slf4j
public class BackIndexWelcomeService {

    @Autowired
    private CinemaManager cinemaManager;

    @Autowired
    private ScreenRoomMapper screenRoomMapper;

    @Autowired
    private MovieDetailManager movieDetailManager;

    @Autowired
    private TicketDetailManager ticketDetailManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAdminManager userAdminManager;

    /**
     * 面板数据
     * @return
     */
    public List<PaneDataVo> paneData(){

        List<PaneDataVo> list = Lists.newArrayList();

        //影院总数
        int cinemaCount = cinemaManager.count();
        list.add(new PaneDataVo("影院总数",cinemaCount));



        // 放映厅总数
        Integer screenCount = screenRoomMapper.selectCount(null);
        list.add(new PaneDataVo("放映厅总数",screenCount));

        // 电影总数
        int movieCount = movieDetailManager.count();
        list.add(new PaneDataVo("电影总数",movieCount));

        // 订单总数 已经支付 并且已经使用了
        int orderCount = ticketDetailManager.count(new QueryWrapper<TicketDetailDo>()
                .eq("status", 1)
                .eq("is_deleted", 1));
        list.add(new PaneDataVo("订单总数",orderCount));

        // 用户总数
        Integer userCount = userMapper.selectCount(new QueryWrapper<UserDO>()
                .eq("status", 0));
        list.add(new PaneDataVo("用户总数",userCount));


        // 管理员总数
        int adminCount = userAdminManager.count(new QueryWrapper<UserAdminDo>()
                .eq("status", 0));
        list.add(new PaneDataVo("管理员总数",adminCount));

        return list;

    }


    /**
     * 图表数据 0 电影 1 影院 2 用户 3 订单
     * @param echartsPo
     * @return
     */
    public List<EchartsVo> echartsOption(EchartsPo echartsPo){

        List<EchartsVo> list = Lists.newArrayList();
        List<EchartsDo> echartsDos = Lists.newArrayList();

        if (echartsPo.getOption().equals(Integer.valueOf(0))){

            // 电影
            echartsDos = movieDetailManager.getBaseMapper().movieEchartsData(echartsPo.getStartYear(), echartsPo.getStartMonth(), echartsPo.getEndYear(), echartsPo.getEndMonth());


        }else if (echartsPo.getOption().equals(Integer.valueOf(1))){

            // 影院
            echartsDos = cinemaManager.getBaseMapper().cinemaEchartsData(echartsPo.getStartYear(), echartsPo.getStartMonth(), echartsPo.getEndYear(), echartsPo.getEndMonth());

        }else if (echartsPo.getOption().equals(Integer.valueOf(2))){

            // 用户
            echartsDos = userMapper.userEchartsData(echartsPo.getStartYear(), echartsPo.getStartMonth(), echartsPo.getEndYear(), echartsPo.getEndMonth());

        }else{

            // 订单
            echartsDos = ticketDetailManager.getBaseMapper().orderEchartsData(echartsPo.getStartYear(), echartsPo.getStartMonth(), echartsPo.getEndYear(), echartsPo.getEndMonth());

        }

        // 转化成 yyyyMM 的形式  的map
        Map<String,Integer> countDateMap = Maps.newHashMap();

        for (EchartsDo echartsDo : echartsDos) {
            countDateMap.put(String.format("%d%02d",echartsDo.getYear(),echartsDo.getMonth()),echartsDo.getCount());
        }

        // list 添加日期
        int year = echartsPo.getStartYear();
        int month = echartsPo.getStartMonth();

        while (1 != String.format("%d%02d",year,month).compareTo(String.format("%d%02d",echartsPo.getEndYear(),echartsPo.getEndMonth()))){

            EchartsVo echartsVo = new EchartsVo();
            echartsVo.setDate(String.format("%d%02d",year,month));
            echartsVo.setCount(0);
            list.add(echartsVo);

            if (month < 12){
                month++;
            }else{
                month = 1;
                year++;
            }



        }

        // 遍历list map取 日期 没有的补0
        for (EchartsVo echartsVo : list) {

            Integer count = countDateMap.get(echartsVo.getDate());

            if (Objects.nonNull(count)){
                echartsVo.setCount(count);
            }

        }

        return list;

    }

}
