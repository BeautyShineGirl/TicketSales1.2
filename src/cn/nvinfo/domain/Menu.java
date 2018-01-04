package cn.nvinfo.domain;
/**
 * 给前台登陆时返回
 * @author yangli	2017-10-26
 *
 */
public class Menu {
	private String path;
	private String meta;
	private String component;
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}
	
	
}
