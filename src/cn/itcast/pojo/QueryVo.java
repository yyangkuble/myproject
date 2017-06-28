package cn.itcast.pojo;

import java.util.List;

public class QueryVo {
	
	//用户对象
	//订单对象
	//商品对象
	Items items;
	List<Items> itemsList;
	
	public List<Items> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<Items> itemsList) {
		this.itemsList = itemsList;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}
	
	
}
