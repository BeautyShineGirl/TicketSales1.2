package cn.nvinfo.service;

import java.util.List;

import javax.swing.text.View;

import cn.nvinfo.domain.Custom;
import cn.nvinfo.domain.Order;
import cn.nvinfo.domain.Product;
import cn.nvinfo.domain.ViewMessage;
import cn.nvinfo.tools.OrderList;
import cn.nvinfo.utils.Pager;



public interface UserService {

	/*
	 * 登陆  杨立  2017-10-16
	 */
	Custom getUser(String userName, String password);
	/*
	 * 添加订单到数据库	YANGLI 2017-10-18
	 */
	int addOrder(Order order);
	//修改订单状态为为0，支付成功，添加微信订单号到order表	杨立	2017-10-26
	int updateOrder(Order order);
	//在本地数据库中查询，若查询状态不是orderState为3，则发起向微信退款查询（refundQueryServlet）
	int queryRefund(Integer id);
	//向微信发出查询退款申请返回退款成功
	void updateOrderState(String id);
	/*
	 * 查询提交的订单 是否已支付，如未支付orderState=1，则向微信发出支付请求	杨立	2017-10-26
	 */
	int selectOrderState(String id);
	/*
	 * 修改状态为0，支付成功
	 */
	void editState(String id);
	/*
	 * 轮播图	返回优先级0到4的产品及每个产品所对应的景区照片所对应的集合
	 */
	List<Product> getProduct();
	/*
	 * 优先级最高的产品	优先级为5	杨立	2017-11-1
	 */
	List<Product> getExpeciallyProduct();
	/*
	 * 景点游	优先级为6的景区 按优先级排列   从打到小的顺序	杨立	2017-11-1	
	 */
	List<Product> getViewTravalPro();
	/*
	 * sort为0的景区	杨立	2017-11-1
	
	ViewMessage getScopeView();
	 */
	/*
	 * 周边游	优先级为7的景区  且不是西安的景区的产品	杨立	2017-11-1
	 */
	List<Product> getRimTravalPro();
	/*
	 * 国内游	优先级为8的景区  	杨立	2017-11-1
	 */
	List<Product> getNationalTravalPro();
	/*
	 * 点击更多时出现所有产品	按优先级顺序排列  	杨立	2017-11-1
	 */
	List<Product> getExpeciallyMore();
	/*
	 * 根据id返回产品详情  杨立  2017-09-25
	 */
	Product getById(Integer id);
	/*
	 * 分销商页面搜索框  杨立  2017-11-3
	 */
	List<Product> getKeyWordProduct(String keyWord);
	//先从订单表中把该支付成功的订单查出来	2017-11-07
	Order getOrderSuccess(String outTradeNo);
	/*
	 * 景点游点击更多  	优先级为6  	杨立	2017-11-07
	 */
	List<Product> viewTravalMore();
	/*
	 * 周边游点击更多 	优先级为7  	杨立	2017-11-07
	 */
	List<Product> getRimTraval();
	//国内游点击更多   优先级为8	杨立	2017-11-07
	List<Product> getNationalTravalMore();
	////获得sort为1的景区的集合	杨立	2017-11-08
	List<ViewMessage> getExpeciallyView();
	//获得sort为2的景区的集合	杨立	2017-11-08
	List<ViewMessage> getViewTravalView();
	//周边游景区信息	获得sort为3的景区的集合	杨立	2017-11-08
	List<ViewMessage> getRimTravalView();
	//国内游景区信息	获得sort为4的景区的集合	杨立	2017-11-08
	List<ViewMessage> getNationalTravalView();
	//查询根据景区id返回产品List详情	yangli	2017-11-08
	List<Product> getByIdList(Integer id);
	//在本地数据库中查询此订单是否已经存在	2017-11-09
	List<String> getByOrderId(String orderId);
	/*
	 * 查询分销商成功支付或者退款的订单
	 * 支付成功订单查询 state=0
	 * 退款成功订单state=3	杨立	2017-11-13	
	 */
	List<Order> getStateOerder(Integer userId, Integer state);
	//分销商用户删除已退款订单 state=3	2017-11-14
	int deleteRefund(Integer id);
	//获取订单的出发日期	2017-11-14
	String queryUseDate(String id);
	//根据产品编号，查询第三方的产品编号 	yangli 2017-12-08
	int getProductNo(Integer productId);
	//获取order_id,order_money,order_state,保存到数据库	2017-12-08
	int updateAnotherOrder(Order order1);
	//将第三方给客户返回的短信验证码保存到orderCode中	yangli 2017-12-11
	int insertCode(Order order3);
	//根据供应商id得到供应商的名字	yangli	2017-12-11
	String getSupplierName(int supplierId);
	int updateProductNum(Integer newNum, Integer productId);
	//退款成功，更新SupplierOrderState
	int updateSuplierState(Order order3);
}	
