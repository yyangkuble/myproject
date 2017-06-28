package cn.itcast.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.dao.ItemsMapper;
import cn.itcast.exception.CustomException;
import cn.itcast.pojo.Items;
import cn.itcast.pojo.ItemsExample;
import cn.itcast.pojo.ItemsExample.Criteria;
import cn.itcast.pojo.QueryVo;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Resource
	ItemsMapper itemsMapper;
	
	@Override
	public List<Items> findItems() {
		// TODO Auto-generated method stub
		List<Items> list = itemsMapper.selectByExampleWithBLOBs(null);
		return list;
	}

	@Override
	public Items findItemById(Integer id) {
		// TODO Auto-generated method stub
		Items items = itemsMapper.selectByPrimaryKey(id);
		return items;
	}

	@Override
	public Integer updateItems(Items items) {
		// TODO Auto-generated method stub
		
		int result = itemsMapper.updateByPrimaryKeySelective(items);
		
		return result;
	}

	@Override
	public List<Items> findItemsByQueryVo(QueryVo vo) {
		// TODO Auto-generated method stub
		
		ItemsExample example=new ItemsExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameLike(vo.getItems().getName());
		criteria.andPriceEqualTo(vo.getItems().getPrice());
		List<Items> list = itemsMapper.selectByExampleWithBLOBs(example);
		
		return list;
	}

}
