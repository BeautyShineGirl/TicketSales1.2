package cn.nvinfo.domain;
/**
 * 分销商类别实体
 * @author 杨立   2017-09-18
 *
 */
public class CustomType {

	private int id;//分销商编号
	private String name;//分销商类别  
	private double condPercent;//加价率
	private double condYuan;//加价金额
	
	public CustomType() {
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

	public double getCondPercent() {
		return condPercent;
	}

	public void setCondPercent(double condPercent) {
		this.condPercent = condPercent;
	}

	public double getCondYuan() {
		return condYuan;
	}

	public void setCondYuan(double condYuan) {
		this.condYuan = condYuan;
	}

	
}
