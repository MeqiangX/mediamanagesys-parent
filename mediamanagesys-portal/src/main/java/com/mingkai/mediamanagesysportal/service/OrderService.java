package com.mingkai.mediamanagesysportal.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesyscommon.manager.ClassDicManager;
import com.mingkai.mediamanagesyscommon.manager.ClassUserRelManager;
import com.mingkai.mediamanagesyscommon.manager.ScreenSeatManager;
import com.mingkai.mediamanagesyscommon.manager.TicketDetailManager;
import com.mingkai.mediamanagesyscommon.mapper.ScreenArrangeMapper;
import com.mingkai.mediamanagesyscommon.model.Do.order.ClassDicDo;
import com.mingkai.mediamanagesyscommon.model.Do.order.ClassUserRelDo;
import com.mingkai.mediamanagesyscommon.model.Do.order.TicketDetailDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenArrangeDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenSeatDo;
import com.mingkai.mediamanagesyscommon.utils.string.onlyID.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 15:16
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private ScreenSeatManager screenSeatManager;

    @Autowired
    private ScreenArrangeMapper screenArrangeMapper;

    @Autowired
    private ClassDicManager classDicManager;

    @Autowired
    private ClassUserRelManager classUserRelManager;

    @Autowired
    private TicketDetailManager ticketDetailManager;

    /**
     * 购票
     * @param seatId
     * @return
     */
    @Transactional
    public Boolean seatBooking(Integer seatId,Integer userId){

        // 查看当前的座位状态是否可用 是否已经被购买
        ScreenSeatDo screenSeat = screenSeatManager.getById(seatId);

        if (Objects.isNull(screenSeat)){
            throw new BizException("该坐席不存在："+ seatId);
        }

        // 查看状态
        if (screenSeat.getStatus().equals(Integer.valueOf(1))){
            throw new BizException("该座位已损坏，请重新选择");
        }

        if (screenSeat.getIsPurchased().equals(Integer.valueOf(1))){
            throw new BizException("该座位已经被预定，请重新选择");
        }

        // 正常预定  mybatisPlus 自带的wrapper 的跟新会默认忽视 null 刚好符合
        boolean update = screenSeatManager.update(new UpdateWrapper<ScreenSeatDo>()
                .eq("id", seatId)
                .set("is_purchased", 1));

        // 成功之后  生成订单信息  订单id 用户id  座位id 会员等级 折扣价格
        TicketDetailDo ticketDetailDo = new TicketDetailDo();
        ticketDetailDo.setOrderId(SnowFlakeUtil.getId());
        ticketDetailDo.setUserId(userId);
        ticketDetailDo.setSeatId(seatId);

        ClassUserRelDo classUserRel = classUserRelManager.getOne(new QueryWrapper<ClassUserRelDo>()
                .eq("user_id", userId));

        //通过seatId 拿到 Price
        ScreenArrangeDo screenArrangeDo = screenArrangeMapper.selectById(screenSeat.getScreenArrangeId());


        // 原始价格
        BigDecimal oringinalPrice
                = screenArrangeDo.getPrice();

        if (Objects.isNull(classUserRel)){
            // 非会员
            ticketDetailDo.setPrice(oringinalPrice);
        }else{
            //会员
            ClassDicDo classId = classDicManager.getById(classUserRel.getClassId());

            if (Objects.isNull(classId)){
                log.info("未找到相关的会员记录，按照默认价格："+classUserRel.getClassId());
                ticketDetailDo.setPrice(oringinalPrice);
            }else{
                ticketDetailDo.setPrice(classId.getDiscount().multiply(oringinalPrice));
            }
        }

        // 插入表中
        return ticketDetailManager.save(ticketDetailDo);
    }

}
