package cn.itcast.pojo;

import java.util.List;

public class QueryVo {
	
	//�û�����
	//��������
	//��Ʒ����
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
