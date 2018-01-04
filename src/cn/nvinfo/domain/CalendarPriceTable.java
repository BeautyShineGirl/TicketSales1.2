package cn.nvinfo.domain;
/**
 * 价格日历表实体
 * @author 杨立	2017-11-17
 *
 */
public class CalendarPriceTable {

	private int id;
	private int product_id;
	private String datePrice;
	public CalendarPriceTable() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getDatePrice() {
		return datePrice;
	}
	public void setDatePrice(String datePrice) {
		this.datePrice = datePrice;
	}
	
	
}
