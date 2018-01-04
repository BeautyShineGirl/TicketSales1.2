package cn.nvinfo.controller;


import javax.annotation.Resource;


import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import cn.nvinfo.service.OrderService;
import cn.nvinfo.tools.OrderList;
import cn.nvinfo.utils.CheckUtil;
import cn.nvinfo.utils.Pager;
import cn.nvinfo.utils.Result;

/**
 * 订单管理
 * @author 杨立   2017-09-28
 *
 */

@Controller
@Scope("prototype")
@RequestMapping("order")
public class OrderAction {
	private static Logger log=Logger.getLogger(OrderAction.class);
	@Resource
	private OrderService orderService;
	private Integer pageSize=10;	
    
	/**
	 *  分页查询	杨立  2017-09-28	二次修改	2017-11-3
	 * @param pageIndex
	 * @param orderId
	 * @param product
	 * @param view
	 * @param state
	 * @param custom
	 * @param supplier
	 * @return
	 */
	@RequestMapping("/findPageData.action")
	public @ResponseBody Object findPageData(Integer pageIndex,String orderId,String product,
			String view,Integer state,String custom,String supplier,Integer power_id,Integer staff_id,
			String createDate,String useDate,String verDate,String createDateEnd,String useDateEnd){
		//验证参数
		if(!"".equals(CheckUtil.checkArgsNotNull(pageIndex,power_id,staff_id))){
			log.info(new Result(0,"参数错误"));
			return new Result(0,"参数错误");
		}
		log.info("findPageData分页查询提交条件：pageIndex="+pageIndex+", orderId="+orderId+", product="+product+", view="+view+", state="+state+", custom="+custom+", supplier="+supplier+", power_id="+power_id+", staff_id="+staff_id);
		Pager<OrderList> pager=orderService.getPager(pageIndex,pageSize,orderId,product,view,state,custom,supplier,power_id,staff_id,createDate,useDate,verDate,createDateEnd,useDateEnd);
		if(pager.getDatas().size()!=0){
			log.info(new Result(1,"查询成功",pager));
			return new Result(1,"查询成功",pager);
		}else{
			log.info(new Result(0,"暂无数据",pager));
			return new Result(0,"暂无数据",pager);
		}
	}
	/**
	 * 未支付账单查询 state=1
	 * 已核销订单state=2	杨立	2017-09-28	待优化	二次修改	2017-11-3
	 * @param pageIndex
	 * @param satrtTime
	 * @param endTime
	 * @return
	 * 出游日期	 startTime	 startTimeEnd
	 * 下单日期	endTime endTimeEnd
	 */
	@RequestMapping("/stateOerder.action")
	public @ResponseBody Object stateOerder(Integer pageIndex,String startTime,String endTime,Integer state, Integer power_id,
			Integer staff_id,String endTimeEnd,String startTimeEnd){
		//验证参数
		if(!"".equals(CheckUtil.checkArgsNotNull(pageIndex,state,power_id,staff_id))){
			log.info(new Result(0,"参数错误"));
			return new Result(0,"参数错误");
		}
		log.info("stateOerder查询提交条件：pageIndex="+","+pageIndex+", startTime="+startTime+", endTime="+endTime+", state="+state+", power_id="+power_id+", staff_id="+staff_id);
		Pager<OrderList> pager=orderService.getStateOerder(pageIndex,pageSize,startTime,endTime,state,power_id,staff_id,endTimeEnd,startTimeEnd);
		if(pager.getDatas().size()!=0){
			log.info(new Result(1,"查询成功",pager));
			return new Result(1,"查询成功",pager);
		}else{
			log.info(new Result(0,"暂无数据",pager));
			return new Result(0,"暂无数据",pager);
		}
	}
}
