# mediamanagesys-parent
mediamanagesys-parent 电影院管理系统

# 跟新日志

1. 2019年4月11日10:15:59
    1. 新增省市三个维表加上影院信息总共四个维表，后期添加地区的api 获取当地的影院，前端提供区域选择，查看区域下的影院
    2. 增加定位api

2. 2019年4月19日18:14:41
    1. 豆瓣api 返回的json 中含有 想看的人(wish_count) 和 看过的人(collect_count) 可以在后期 如果有时间可以在页面加上，并且 还有一个最受期待榜，根据想看的人来排序可以加上
    2. 另外考虑到 电影详情处太过空白，后期可能要加上电影的基本信息  导演 主演 编导 国家 地区 等，如果还有时间可以加上图集()
    3. 地区选择Select控件完成，优化在之后 目前只能选择市区县 无法向上精确

3. 2019年5月9日22:54:29
    1. 项目进度跟新：后端：权限(时间不足可能不弄) 还剩下个定时模块完善， 现在只有个数据跟新，还要添加个定时清除过期订单 以及 排片记录 (每10分钟？) 然后还要添加可以操控数据和清除的定时任务，从管理后台可以操作，以及记录每次执行的信息和状态和时长--> 记录表 ;
    2. 前端 前台还有个取消/退款 看时间 可能不做
    3. 前端 后台还有个定时任务模块 

4. 2019年5月19日00:59:45 
    1. 项目进度跟新： 毕设论文完成，将毕设代码修补完，补充功能：
    2. 定时任务模块
        1. 新增执行任务类型维表 执行任务类型 任务名称 
        2. 新增执行任务表 字段 执行任务类型，执行任务时间，任务消耗时间，成功与否 执行人
        3. 增加切面方法，对于不同的任务方法 执行不同的切面方法
        4. 定时任务添加，定时清理过期排片信息，和坐席信息
    3. 电影详情
        1. 添加用户想看的收集，后台进行统计展现
    4. 放映厅相关
        1. 限制放映厅不能共享
    5. 支付
        1. 支付测试完善
        2. 加入取消订单(开场后无法取消)
        
5. 2019年5月26日00:32:31跟新

    > 项目的一个结尾，加密密码，页脚完善，迁移微服务，还有几点要优化
    
    1. 后台控制台的统计时间太长，看不见效果，考虑换成最近一个月
    2. 统计中加上中国地图板块，显示各个省份的昨日影院数和订单数和交易额,定时任务加上一个每日统计全国地图数据
    
