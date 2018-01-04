package cn.nvinfo.domain;
/**
 * 优先级实体   
 * @author 杨立  2017-09-18
 *
 */
public class Priority {

	private int id;//编号
	private String name;//优先级分类名称
	private int priority;//优先级
	
	
	public Priority() {
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
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
}
