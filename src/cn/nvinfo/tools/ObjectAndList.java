package cn.nvinfo.tools;

import java.util.List;

import cn.nvinfo.domain.Custom;
import cn.nvinfo.domain.CustomType;

/**
 * 分销商管理时，修改的数据回显
 * @author admin
 *
 */
public class ObjectAndList {

	private Custom custom;
	private List<CustomType> customType;
	private List<String> level;
	public ObjectAndList() {
		super();
	}
	public Custom getCustom() {
		return custom;
	}
	public void setCustom(Custom custom) {
		this.custom = custom;
	}
	public List<CustomType> getCustomType() {
		return customType;
	}
	public void setCustomType(List<CustomType> customType) {
		this.customType = customType;
	}
	public List<String> getLevel() {
		return level;
	}
	public void setLevel(List<String> level) {
		this.level = level;
	}
	
}
