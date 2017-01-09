package cn.easyproject.easyee.sh.sys.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EasyUI Tree Object <br/>
 * 可以封装菜单的相关属性
 * @author easyproject.cn
 *
 */
public class EasyUITreeEntity {
	private String id;  //1, An identity value bind to the node.
	private String text;  //EasyUI Demo, Text to be showed.
	private String url; //
	private String state;  //open, he node state, 'open' or 'closed'.
	private String iconCls;  //icon-application, The css class to display icon.
	private boolean checked=false;  //true, Whether the node is checked.
	private Map<String, Object> attributes=new HashMap<String, Object>();  //Custom attributes bind to the node.
	private List<EasyUITreeEntity> children=new ArrayList<EasyUITreeEntity>();
	private String remark; //附加字段：备注
	private String type; //附加字段：节点类型（菜单权限节点、操作权限节点）
	
	public static final String MENU_PERMISSION="menu";
	public static final String OPERATION_PERMISSION="operation";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<EasyUITreeEntity> getChildren() {
		return children;
	}
	public void setChildren(List<EasyUITreeEntity> children) {
		this.children = children;
	}

	public EasyUITreeEntity(String id, String text, String state,
			List<EasyUITreeEntity> children) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
		this.children = children;
	}
	public EasyUITreeEntity() {
		super();
	}
	public EasyUITreeEntity(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	public EasyUITreeEntity(String id, String text, String url, String state,
			String iconCls, boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.state = state;
		this.iconCls = iconCls;
		this.checked = checked;
	}
	public EasyUITreeEntity(String id, String text, String url, String state, String iconCls) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.state = state;
		this.iconCls = iconCls;
	}
	public EasyUITreeEntity(String id, String text, String url, String state) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.state = state;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public EasyUITreeEntity(String id, String text, String url) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
	}
	@Override
	public String toString() {
		return "\n EasyUITree [id=" + id + ", text=" + text + ", url=" + url
				+ ", state=" + state + ", iconCls=" + iconCls + ", checked="
				+ checked + ", attributes=" + attributes + ", children="
				+ children + "] \n";
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
