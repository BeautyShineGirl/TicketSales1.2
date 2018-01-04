package cn.nvinfo.tools;

import java.util.List;

import cn.nvinfo.domain.Department;
import cn.nvinfo.domain.Power;
import cn.nvinfo.domain.Staff;

/**
 * 用户管理 	从字典表中获取数据返回页面
 * @author 杨立	2017-09-29
 *
 */
public class StaffDictionary {

	private List<Department> dept;//部门的集合
	//private List<String> role;//角色的集合
	private List<Power> power;
	private Staff staff;
	public StaffDictionary() {
		super();
	}
	public List<Department> getDept() {
		return dept;
	}
	public void setDept(List<Department> dept) {
		this.dept = dept;
	}
	/*public List<String> getRole() {
		return role;
	}
	public void setRole(List<String> role) {
		this.role = role;
	}*/
	public List<Power> getPower() {
		return power;
	}
	public void setPower(List<Power> power) {
		this.power = power;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	
}
