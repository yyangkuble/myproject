package cn.itcast.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.exception.CustomException;
import cn.itcast.pojo.Items;
import cn.itcast.pojo.QueryVo;
import cn.itcast.service.ItemService;

@Controller //声明当前类是一个action(控制器)
@RequestMapping("/item")
public class ItemAction {
	
	@Resource
	ItemService itemService;
	Integer count=0;
	//@RequestMapping的作用: url到方法的关系映射
	@RequestMapping("/itemList")
	public String showItems(HttpServletRequest request,Integer id,Items items,Model model) throws CustomException {
		System.out.println(id);
		System.out.println(items.getId());
		System.out.println(++count);
		List<Items> itemList = itemService.findItems();
		/*
		int i=1/0;
		System.out.println(i);*/
		
		/*if (true) {
			CustomException customException = new CustomException();
			customException.setMessage("Unknown column 'idd' in 'field list'");
			customException.setCode(1054);
			throw customException;
		}*/
		
		//request.setAttribute("itemList", itemList);
		//跟request.setAttribute效果一样,但是有一点扩展后面再讲
		model.addAttribute("itemList", itemList);
		return "itemList";
	}
	
	@RequestMapping("/toItemEdit")//	url:/item/toItemEdit
	public ModelAndView toItemEdit(Integer id) {
		Items items = itemService.findItemById(id);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("item", items);
		modelAndView.setViewName("editItem");
		return modelAndView;
	}
	
	/*@RequestMapping("/updateitem")
	public String itemEdit(Items items,Model model) {
		Integer updateItems = itemService.updateItems(items);
		//model.addAttribute("id", items.getId());
		//内部请求转发演示
		return "forward:toItemEdit.action";
	}*/
	//上传图片MultipartFile的变量名称要与input的name值相同
	@RequestMapping("/updateitem")//	
	public String itemEdit(Items items,MultipartFile pictureFile,Model model,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//1: 获取上传文件的名字
		String fileName = pictureFile.getOriginalFilename();
		//2: 获取新的文件名称: UUID+.原有的后缀名称    bj.png
		String newFileName=UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
		//3: 存入本地磁盘: D:\myimgfiles\新的文件名称
		pictureFile.transferTo(new File("D:\\myimgfiles\\"+newFileName));
		//4: 将新新的文件名称 存入数据库中
		items.setPic(newFileName);
		
		Integer updateItems = itemService.updateItems(items);
		//spring mvc的model对象会判断如果是重定向,那么model.addAttribute这个方法,会自动给我们在url上拼接参数 例如?id=1
		model.addAttribute("id", items.getId());
		//重定向演示
		//request.getRequestDispatcher("toItemEdit.action").forward(request, response);
		//response.sendRedirect("toItemEdit.action?id="+items.getId());
		return "redirect:/item/toItemEdit.action";
	}
	/**
	 * 演示包装类型
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryitem")
	public String searchItems(QueryVo vo,Model model) {
		System.out.println(vo);
		List<Items> list = itemService.findItemsByQueryVo(vo);
		model.addAttribute("itemList", list);
		return "itemList";
	}
	
	@RequestMapping("/showItemsJson")
	public void showItemsJson(HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("GBK");
		System.out.println(request.getParameter("id"));
		
		List<Items> itemList = new ArrayList<>();
			
		//商品列表
		Items items_1 = new Items();
		items_1.setName("联想笔记本_3");
		items_1.setPrice(6000f);
		items_1.setDetail("ThinkPad T430 联想笔记本电脑！");
		
		Items items_2 = new Items();
		items_2.setName("苹果手机");
		items_2.setPrice(5000f);
		items_2.setDetail("iphone6苹果手机！");
		
		itemList.add(items_1);
		itemList.add(items_2);

		
		try {
			response.getWriter().print(itemList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/deleteitem")
	public String deleteitem(Integer[]  ids) {
		System.out.println(ids);
		for (Integer id : ids) {
			//调用itemService 批量删除
		}
		return "Success";
	}
	@RequestMapping("/updateitems")
	public String updateItems(QueryVo vo) {
		
		System.out.println(vo);
		
		return "Success";
	}
	
}
