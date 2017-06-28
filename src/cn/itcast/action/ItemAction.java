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

@Controller //������ǰ����һ��action(������)
@RequestMapping("/item")
public class ItemAction {
	
	@Resource
	ItemService itemService;
	Integer count=0;
	//@RequestMapping������: url�������Ĺ�ϵӳ��
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
		//��request.setAttributeЧ��һ��,������һ����չ�����ٽ�
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
		//�ڲ�����ת����ʾ
		return "forward:toItemEdit.action";
	}*/
	//�ϴ�ͼƬMultipartFile�ı�������Ҫ��input��nameֵ��ͬ
	@RequestMapping("/updateitem")//	
	public String itemEdit(Items items,MultipartFile pictureFile,Model model,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//1: ��ȡ�ϴ��ļ�������
		String fileName = pictureFile.getOriginalFilename();
		//2: ��ȡ�µ��ļ�����: UUID+.ԭ�еĺ�׺����    bj.png
		String newFileName=UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
		//3: ���뱾�ش���: D:\myimgfiles\�µ��ļ�����
		pictureFile.transferTo(new File("D:\\myimgfiles\\"+newFileName));
		//4: �����µ��ļ����� �������ݿ���
		items.setPic(newFileName);
		
		Integer updateItems = itemService.updateItems(items);
		//spring mvc��model������ж�������ض���,��ômodel.addAttribute�������,���Զ���������url��ƴ�Ӳ��� ����?id=1
		model.addAttribute("id", items.getId());
		//�ض�����ʾ
		//request.getRequestDispatcher("toItemEdit.action").forward(request, response);
		//response.sendRedirect("toItemEdit.action?id="+items.getId());
		return "redirect:/item/toItemEdit.action";
	}
	/**
	 * ��ʾ��װ����
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
			
		//��Ʒ�б�
		Items items_1 = new Items();
		items_1.setName("����ʼǱ�_3");
		items_1.setPrice(6000f);
		items_1.setDetail("ThinkPad T430 ����ʼǱ����ԣ�");
		
		Items items_2 = new Items();
		items_2.setName("ƻ���ֻ�");
		items_2.setPrice(5000f);
		items_2.setDetail("iphone6ƻ���ֻ���");
		
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
			//����itemService ����ɾ��
		}
		return "Success";
	}
	@RequestMapping("/updateitems")
	public String updateItems(QueryVo vo) {
		
		System.out.println(vo);
		
		return "Success";
	}
	
}
