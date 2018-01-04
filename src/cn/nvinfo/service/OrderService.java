package cn.nvinfo.service;


import cn.nvinfo.tools.OrderList;
import cn.nvinfo.utils.Pager;

/**
 * 订单管理
 * @author 杨立   2017-09-28
 *
 */
public interface OrderService {
	/*
	 * 分页查询	杨立  2017-09-28	修改日期	2017-11-1	二次修改	2017-11-3
	 */
	Pager<OrderList> getPager(Integer pageIndex, Integer pageSize,
			String orderId, String product, String view, Integer state,
			String custom, String supplier, Integer power_id, Integer staff_id, String createDate, String useDate, String verDate, String createDateEnd, String useDateEnd);
	/*
	 * 未支付账单查询 state=1
	 * 已核销订单state=2	杨立	2017-09-28
	 *出游日期	 startTime	 startTimeEnd
	 * 下单日期	endTime endTimeEnd
	 */
	Pager<OrderList> getStateOerder(Integer pageIndex, Integer pageSize,
			String startTime, String endTime, Integer state, Integer power_id, Integer staff_id,String endTimeEnd,String startTimeEnd);

}
