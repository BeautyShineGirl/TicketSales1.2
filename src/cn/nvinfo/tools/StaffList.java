package cn.nvinfo.tools;
/**
 * 用户管理	显示表格实体
 * @author 杨立	2017-09-29
 *
 */
public class StaffList {

	private int id;//编号
	private String loginName;//用户名
	private String name;//姓名
	private String department;//部门
	private String role;//角色
	public StaffList() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	 
	
}
