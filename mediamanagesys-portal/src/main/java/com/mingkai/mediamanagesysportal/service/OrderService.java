package com.mingkai.mediamanagesysportal.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.google.common.collect.Lists;
import com.mingkai.mediamanagesyscommon.common.TimeConstant;
import com.mingkai.mediamanagesyscommon.manager.*;
import com.mingkai.mediamanagesyscommon.mapper.ScreenArrangeMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenRoomMapper;
import com.mingkai.mediamanagesyscommon.model.Do.cinema.CinemaDo;
import com.mingkai.mediamanagesyscommon.model.Do.cinema.CinemaScreenDo;
import com.mingkai.mediamanagesyscommon.model.Do.movie.MovieDetailDo;
import com.mingkai.mediamanagesyscommon.model.Do.order.ClassDicDo;
import com.mingkai.mediamanagesyscommon.model.Do.order.ClassUserRelDo;
import com.mingkai.mediamanagesyscommon.model.Do.order.TicketDetailDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenArrangeDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenRoomDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenSeatDo;
import com.mingkai.mediamanagesyscommon.model.Po.order.CoordinatePo;
import com.mingkai.mediamanagesyscommon.model.Po.order.OrderPagePo;
import com.mingkai.mediamanagesyscommon.model.Po.order.SeatPo;
import com.mingkai.mediamanagesyscommon.model.Vo.order.OrderSimpleVo;
import com.mingkai.mediamanagesyscommon.utils.convert.ConvertUtil;
import com.mingkai.mediamanagesyscommon.utils.page.PageUtils;
import com.mingkai.mediamanagesyscommon.utils.redis.RedisUtil;
import com.mingkai.mediamanagesyscommon.utils.string.onlyID.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    private MovieDetailManager movieDetailManager;

    @Autowired
    private CinemaScreenManager cinemaScreenManager;

    @Autowired
    private CinemaManager cinemaManager;

    @Autowired
    private ScreenRoomMapper screenRoomMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页查询用户订单
     * @param orderPagePo
     * @return
     */
    public Page<OrderSimpleVo> userOrders(OrderPagePo orderPagePo){

        Page<TicketDetailDo> ticketDetailDoPage = new Page<>();

        // 如果都是空 则是全部
        if (Strings.isBlank(orderPagePo.getOrderId()) && Strings.isBlank(orderPagePo.getUserId())){

            ticketDetailDoPage = (Page)ticketDetailManager.page(orderPagePo, new QueryWrapper<TicketDetailDo>());

        }else if (Objects.isNull(orderPagePo.getOrderId())){

            // 发送的是 用户下的订单 不涉及订单号的查询

            // 根据用户id 去查询 分页订单
            ticketDetailDoPage = (Page)ticketDetailManager.page(orderPagePo, new QueryWrapper<TicketDetailDo>()
                    .eq("user_id", orderPagePo.getUserId()));

        }else{

            // 订单号 或者 用户id 来查询
            // 根据用户id 去查询 分页订单
            ticketDetailDoPage = (Page)ticketDetailManager.page(orderPagePo, new QueryWrapper<TicketDetailDo>()
                    .eq("user_id", orderPagePo.getUserId())
                    .or().eq("order_id",orderPagePo.getOrderId()));


        }


        if (Objects.isNull(ticketDetailDoPage) || ticketDetailDoPage.getRecords().size() == 0){
            return PageUtils.emptyPage(orderPagePo,new Page());
        }

        List<TicketDetailDo> records = ticketDetailDoPage.getRecords();

        List<OrderSimpleVo> resultList = Lists.newArrayList();

        for (TicketDetailDo record : records) {

            OrderSimpleVo orderSimpleVo = orderDetail(record.getOrderId());
            resultList.add(orderSimpleVo);
        }


        return PageUtils.page(resultList,orderPagePo);

    }


    /**
     * 购票  有个支付 时间 将订购完成 还未支付的 订单放入redis 同时创建订单 状态为未支付， 未支付的订单 应该在redis 中存储记录
     * 如果没有，则说明 这个订单 已经超时 失效了， 关闭
     * @param seatIds
     * @return
     */
    @Transactional
    public String seatBooking(List<Integer> seatIds, Integer userId){

        // 查看当前的座位状态是否可用 是否已经被购买
        List<ScreenSeatDo> screenSeatLists = screenSeatManager.list(new QueryWrapper<ScreenSeatDo>()
                .in("id", seatIds)
        .eq("is_purchased",0));


        if (Objects.isNull(screenSeatLists)){
            throw new BizException("该坐席不存在："+ seatIds);
        }

        if (screenSeatLists.size() != seatIds.size()){

            List<Integer> noList = Lists.newArrayList();

            List<Integer> idLists = screenSeatLists.stream().map(e -> e.getId()).collect(Collectors.toList());

            for (Integer seatId : seatIds) {

                if (!idLists.contains(seatId)){
                    noList.add(seatId);
                }

            }

            throw new BizException("以下坐席id没有记录或者已经被预定 -->  " + noList);

        }



        // 正常预定  mybatisPlus 自带的wrapper 的跟新会默认忽视 null 刚好符合
        boolean update = screenSeatManager.update(new UpdateWrapper<ScreenSeatDo>()
                .in("id", seatIds)
                .set("is_purchased", 1));

        // 成功之后  生成订单信息  订单id 用户id  座位ids 会员等级 折扣价格
        TicketDetailDo ticketDetailDo = new TicketDetailDo();
        ticketDetailDo.setOrderId(SnowFlakeUtil.getId());
        ticketDetailDo.setUserId(userId);

        //seatIds  转换成,分割的坐席ids

        String ids = seatIds.stream().map(String::valueOf).collect(Collectors.joining(","));

        ticketDetailDo.setSeatIds(ids);


        ClassUserRelDo classUserRel = classUserRelManager.getOne(new QueryWrapper<ClassUserRelDo>()
                .eq("user_id", userId));

        //通过seatId 拿到 Price

        ScreenSeatDo screenSeat = screenSeatManager.getById(seatIds.get(0));

        ScreenArrangeDo screenArrangeDo = screenArrangeMapper.selectById(screenSeat.getScreenArrangeId());


        // 原始价格
        BigDecimal oringinalPrice
                = screenArrangeDo.getPrice();

        if (Objects.isNull(classUserRel)){
            // 非会员
            ticketDetailDo.setPrice(oringinalPrice.multiply(new BigDecimal(seatIds.size())));
        }else{
            //会员
            ClassDicDo classId = classDicManager.getById(classUserRel.getClassId());

            if (Objects.isNull(classId)){
                log.info("未找到相关的会员记录，按照默认价格："+classUserRel.getClassId());
                ticketDetailDo.setPrice(oringinalPrice.multiply(new BigDecimal(seatIds.size())));
            }else{
                ticketDetailDo.setPrice((classId.getDiscount().multiply(oringinalPrice)).multiply(new BigDecimal(seatIds.size())));
            }
        }

        // 插入表中
        boolean saveData = ticketDetailManager.save(ticketDetailDo);
        // 订购成功插入表中的同时 插入到Redis 并设置过期时间 15分钟
        redisUtil.set(""+ticketDetailDo.getOrderId(),""+userId, TimeConstant.EXPIRE_TIME_ORDER);
        return true == saveData ? ticketDetailDo.getOrderId() : "";
    }


    /**
     * 支付界面的未支付订单  by 订单id
     * @param orderId
     * @return
     */
    public OrderSimpleVo orderDetail(String orderId){

        // 根据订单id 去查找
        TicketDetailDo ticketDetailDo = ticketDetailManager.getOne(new QueryWrapper<TicketDetailDo>()
                .eq("order_id", orderId));

        if (Objects.isNull(ticketDetailDo)){
            throw new BizException("订单不存在 - " + orderId);
        }

        OrderSimpleVo orderSimpleVo = ConvertUtil.convert(ticketDetailDo,OrderSimpleVo.class);


        // 查找电影名 开始时间 影院 放映厅 座位

        String seatIds = ticketDetailDo.getSeatIds();

        String[] seatIdsArray = seatIds.split(",");


        // 通过座位id 来找 排片id  但是同时要找座位  所以一起找
        List<ScreenSeatDo> screenSeatDos = (List)screenSeatManager.listByIds(Arrays.asList(seatIdsArray));


        // 座位有了

        //找影院和放映厅 以及电影
        ScreenArrangeDo screenArrangeDo = screenArrangeMapper.selectById(screenSeatDos.get(0).getScreenArrangeId());


        //电影
        MovieDetailDo movie = movieDetailManager.getOne(new QueryWrapper<MovieDetailDo>()
                .eq("movie_id", screenArrangeDo.getMovieId()));

        // 影院 放映厅


        CinemaScreenDo cinemaAndScreenDo = cinemaScreenManager.getById(screenArrangeDo.getCinemaScreenId());


        // 影院
        CinemaDo cinema = cinemaManager.getById(cinemaAndScreenDo.getCinemaId());

        //放映厅
        ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaAndScreenDo.getScreenHallId());


        orderSimpleVo.setCinemaName(cinema.getCinemaName());
        orderSimpleVo.setCinemaAddr(cinema.getCinemaFullAddress());
        orderSimpleVo.setCinemaPhone(cinema.getPhone());
        orderSimpleVo.setScreeningHallName(screenRoomDo.getScreeningHallName());

        orderSimpleVo.setMovieName(movie.getMovieName());
        orderSimpleVo.setTimeScopeStart(screenArrangeDo.getTimeScopeStart());

        // 座位
        List<String> seats = Lists.newArrayList();
        for (ScreenSeatDo screenSeatDo : screenSeatDos) {
            seats.add(screenSeatDo.getScreeningHallX() + "排" + screenSeatDo.getScreeningHallY() + "座");
        }

        orderSimpleVo.setSeats(seats);

        return orderSimpleVo;
    }


    /**
     * 得到未支付订单的剩余支付时间
     * @param orderId
     * @return
     */
    public Long getUnPayOrderTime(String orderId){

        Object restTime = redisUtil.get(orderId);

        if (Objects.isNull(restTime)){
            return 0L;
        }

        return  redisUtil.getExpire(orderId);

    }


    /**
     * 超时订单的处理
     * @param orderId
     * @return
     */
    @Transactional
    public Boolean timeoutOrderCheck(String orderId){

        // 删除订单
        Long unPayOrderTime = getUnPayOrderTime(orderId);
        if (0 == unPayOrderTime){

            // 查找订单相关的座位记录
            TicketDetailDo ticketDetailDo = ticketDetailManager.getOne(new QueryWrapper<TicketDetailDo>()
                    .eq("order_id", orderId));

            String seatIds = ticketDetailDo.getSeatIds();

            boolean delOrder = ticketDetailManager.remove(new QueryWrapper<TicketDetailDo>()
                    .eq("order_id", orderId));

            if (!delOrder){
                throw new BizException("删除订单异常");
            }

            // 正常删除订单 将订单相关的座位记录 重置为可购买状态

            String[] seats = seatIds.split(",");

            boolean update = screenSeatManager.update(new UpdateWrapper<ScreenSeatDo>()
                    .in("id", seats)
                    .set("is_purchased", 0));

            if (update){
                return Boolean.TRUE;
            }

            throw new BizException("座位重置异常");

        }
        // 将订单关联的座位的状态is_purchased 更改为0

        throw new BizException("当前订单剩余时间还有 - " + unPayOrderTime + "秒 无法删除");

    }


    /**
     *  下单第一步检验 当前用户在当前排片arrangeId 下 是否已经买满了
     * @param userId
     * @param arrangeId
     * @return
     */
    public Integer boughtCountsArrangeId(Integer userId,String arrangeId){

        // 根据userId 查询用户订单 包括未支付 支付
        List<TicketDetailDo> ticketDetailDos = ticketDetailManager.list(new QueryWrapper<TicketDetailDo>()
                .eq("user_id", userId));

        // 根据订单的seats 来查询 坐席对应的arrangeId
        if (Objects.isNull(ticketDetailDos) || ticketDetailDos.size() == 0){
            return 0;
        }


        // 取出 和 arrangeId 相同的 id 数量

        // 返回
        Integer num = 0;

        for (TicketDetailDo ticketDetailDo : ticketDetailDos) {

            // 更改 可能多个订单号都是同一场的-->

            String[] seats = ticketDetailDo.getSeatIds().split(",");

            ScreenSeatDo screenSeatDo = screenSeatManager.getById(seats[0]);
            num += seats.length;

        }

        return num;

    }


    /**
     * 下单前的校验，当前所选坐席是否可买
     */
    public List<Integer> isAllowPurchased(List<Integer> seats){

        List<ScreenSeatDo> list = screenSeatManager.list(new QueryWrapper<ScreenSeatDo>()
                .eq("is_purchased", 0)
                .in("id", seats));

        if (Objects.isNull(list)){
            // 全部都被预定了
            return seats;
        }else if (list.size() == seats.size()){
            // 都没有被预定
            return Lists.newArrayList();
        }else{
            // 有一部分已经被预定了
            List<Integer> ticketingList = Lists.newArrayList();
            for (Integer seat : seats) {

                if (!list.contains(seat)){
                    ticketingList.add(seat);
                }
            }

            return ticketingList;
        }

    }


    /**
     * 通过 坐席List 和 arrangeId 查询 坐席ids
     * @param coordinatePo
     * @return
     */
    public List<Integer> seatIdsByCoordinate(CoordinatePo coordinatePo){

        List<Integer> result = Lists.newArrayList();

        List<SeatPo> seats = coordinatePo.getSeats();



        for (SeatPo seat : seats) {
            ScreenSeatDo screenSeatDo = screenSeatManager.getOne(new QueryWrapper<ScreenSeatDo>()
                    .eq("screen_arrange_id", coordinatePo.getArrangeId())
                    .eq("screening_hall_x", seat.getRow())
                    .eq("screening_hall_y", seat.getCol()));

            result.add(screenSeatDo.getId());
        }

        return result;
    }

    /**
     * 取消订单(0 未支付 1 已经支付)
     * @param orderId
     * @return
     */
    public Boolean cancelOrder(String orderId){

        // 通过后台查询订单状态

        // status 0 未支付

        // 未支付的订单 会在redis 存在  删除 订单的同时 删除redis
        TicketDetailDo ticketDetailDo = ticketDetailManager.getOne(new QueryWrapper<TicketDetailDo>()
                .eq("order_id", orderId));


        // 清除redis 订单记录
        redisUtil.del(orderId);


        // 得到seatIds
        String seatIds = ticketDetailDo.getSeatIds();

        // 删除订单
        ticketDetailManager.removeById(ticketDetailDo.getId());

        // 恢复座位
        String[] seats = seatIds.split(",");

        boolean update = screenSeatManager.update(new UpdateWrapper<ScreenSeatDo>()
                .in("id", seats)
                .set("is_purchased", 0));


        // status 1 已经支付 步骤相同 只是对于已经支付的 要多一个退款操作

        if (ticketDetailDo.getStatus() == 1){

            // 退款操作

        }

        return update;

    }


    /**
     *  删除用户订单
     * @param userId
     * @return
     */
    @Transactional
    public Boolean deleteUserOrders(Integer userId){

        List<TicketDetailDo> ticketDetailDoList = ticketDetailManager.list(new QueryWrapper<TicketDetailDo>()
                .eq("user_id", userId));

        if (Objects.nonNull(ticketDetailDoList) && ticketDetailDoList.size() != 0){

            // 删除订单
            for (TicketDetailDo ticketDetailDo : ticketDetailDoList) {
                this.cancelOrder(ticketDetailDo.getOrderId());
            }

            return Boolean.TRUE;

        }else{
            return Boolean.TRUE;
        }

    }


    /**
     *  通过orderId 得到订单
     * @param orderId
     * @return
     */
    public TicketDetailDo getOrderByOrderId(String orderId){
        TicketDetailDo ticketDetailDo = ticketDetailManager.getOne(new QueryWrapper<TicketDetailDo>()
                .eq("order_id", orderId));

        return ticketDetailDo;

    }


    /**
     * 支付成功  更新支付状态  后续有时间可以添加支付记录
     * @return
     */
    public Boolean paySuccess(String orderId){

        boolean update = ticketDetailManager.update(new UpdateWrapper<TicketDetailDo>()
                .eq("order_id", orderId)
                .set("status", 1));

        return update;
    }

}
