package cn.nvinfo.domain;

import java.util.List;

import net.sf.json.JSONObject;

import cn.nvinfo.tools.CalendarPrice;

/**
 * 产品实体  
 * @author 杨立   2017-09-18
 *
 */
public class Product {

	private int id;//产品编号
	private String name;//票名 
	
	private int viewId;//景区编号
	private String viewName;
	private int supplierId;//供应商
	private String supplierName;
	private double endPrice; //结算价
	private double marketPrice;//门市价
	private String salePrice;//销售价	新加字段	2017-11-1
	private String isSale;//是否销售  数据字典
	private String type;//门票类型    单门票，套票，亲子票  数据字典
	private int ticketType;// 票型   数据字典   平价票，普通票，活动票
	private String startTime;//开始时间 	
	private String endTime;//结束时间
	private int dailySale;//当天可售数量
	private int num;//总数量
	private String orderTime;//提前预订时间
	private String isCancel;//是否可退    否，是  数据字典
	private String notice;//入园须知
	private String costInside;//费用包含
	private String costOutside;//费用不包含
	private String remark;//备注
	private String userType;//使用者类别开关   0禁用 ，1不禁用
	
	private String priorityType;//显示优先级分类  自由类，活动类。。对应优先级为0,1.。
	private int priorityId;//优先级
	private String customName;//分销商
	private List<String> custom;
	private String method;//入园方式
	private int customId;//分销商字符串
	private String ticketTypeName;
	private String picture;//该产品所对应的景区图片
	private List<Images> images;//分销商页面返回产品所对应的图片
	private String datePrice;//价格日历表的字符串
	private List<CalendarPrice> calendar;
	private String pro_img;//产品的主图
	private String viewRemark;//景区简介的字段
	private String random_calendar;//用于在自己添加产品时，根据此参数添加该产品的价格日历 表
	private String partPrice;//个别日期销售价的修改
	
	//对接骏图是新加字段
	private int juntuPId;//骏图的产品id
	private int juntuTType;//骏图产品所对应的门票资源类型，0-不限，1-单票，2-套票
	private String max_min_buy;//单次最大购买数,单次最小购买数,以","隔开
	private int validityDays;//validityType为2时必填，兑换凭证后相对有效天数
	private int validityType;//门票有效期类0不限，1 绝对有效期 ，2 相对有效期
	private String refundRule;//退改规则
	private int visitorMode;//0-不需要游玩人信息，1-每单仅需一名游玩人信息，2-每张票需要一名游玩人信息
	public Product() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public double getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(double endPrice) {
		this.endPrice = endPrice;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getIsSale() {
		return isSale;
	}

	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public int getTicketType() {
		return ticketType;
	}

	public void setTicketType(int ticketType) {
		this.ticketType = ticketType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getDailySale() {
		return dailySale;
	}

	public void setDailySale(int dailySale) {
		this.dailySale = dailySale;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getCostInside() {
		return costInside;
	}

	public void setCostInside(String costInside) {
		this.costInside = costInside;
	}

	public String getCostOutside() {
		return costOutside;
	}

	public void setCostOutside(String costOutside) {
		this.costOutside = costOutside;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPriorityType() {
		return priorityType;
	}

	public void setPriorityType(String priorityType) {
		this.priorityType = priorityType;
	}

	

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getViewId() {
		return viewId;
	}

	public void setViewId(int viewId) {
		this.viewId = viewId;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public int getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(int priorityId) {
		this.priorityId = priorityId;
	}
	


	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public List<String> getCustom() {
		return custom;
	}

	public void setCustom(List<String> custom) {
		this.custom = custom;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public int getCustomId() {
		return customId;
	}

	public void setCustomId(int customId) {
		this.customId = customId;
	}

	
	public List<Images> getImages() {
		return images;
	}

	public void setImages(List<Images> images) {
		this.images = images;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDatePrice() {
		return datePrice;
	}

	public void setDatePrice(String datePrice) {
		this.datePrice = datePrice;
	}

	public List<CalendarPrice> getCalendar() {
		return calendar;
	}

	public void setCalendar(List<CalendarPrice> calendar) {
		this.calendar = calendar;
	}

	public String getPro_img() {
		return pro_img;
	}

	public void setPro_img(String pro_img) {
		this.pro_img = pro_img;
	}

	public String getViewRemark() {
		return viewRemark;
	}

	public void setViewRemark(String viewRemark) {
		this.viewRemark = viewRemark;
	}

	public String getRandom_calendar() {
		return random_calendar;
	}

	public void setRandom_calendar(String random_calendar) {
		this.random_calendar = random_calendar;
	}

	public String getPartPrice() {
		return partPrice;
	}

	public void setPartPrice(String partPrice) {
		this.partPrice = partPrice;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	

	public int getJuntuPId() {
		return juntuPId;
	}

	public void setJuntuPId(int juntuPId) {
		this.juntuPId = juntuPId;
	}

	public int getJuntuTType() {
		return juntuTType;
	}

	public void setJuntuTType(int juntuTType) {
		this.juntuTType = juntuTType;
	}

	public String getMax_min_buy() {
		return max_min_buy;
	}

	public void setMax_min_buy(String max_min_buy) {
		this.max_min_buy = max_min_buy;
	}

	public int getValidityDays() {
		return validityDays;
	}

	public void setValidityDays(int validityDays) {
		this.validityDays = validityDays;
	}

	public int getValidityType() {
		return validityType;
	}

	public void setValidityType(int validityType) {
		this.validityType = validityType;
	}

	public String getRefundRule() {
		return refundRule;
	}

	public void setRefundRule(String refundRule) {
		this.refundRule = refundRule;
	}

	public int getVisitorMode() {
		return visitorMode;
	}

	public void setVisitorMode(int visitorMode) {
		this.visitorMode = visitorMode;
	}
	
	
}
