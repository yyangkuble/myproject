package cn.itcast.service;

import java.util.List;

import cn.itcast.exception.CustomException;
import cn.itcast.pojo.Items;
import cn.itcast.pojo.QueryVo;

public interface ItemService {
	
	List<Items> findItems();
	Items findItemById(Integer id);
	
	Integer updateItems(Items items);
	
	List<Items> findItemsByQueryVo(QueryVo vo);
	
	
	
}
