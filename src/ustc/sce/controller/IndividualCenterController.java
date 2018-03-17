package ustc.sce.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.User;
import ustc.sce.response.Response;
import ustc.sce.service.IndividualCenterService;
import ustc.sce.utils.TokenUtil;

@RestController
@RequestMapping("/individual_center")
public class IndividualCenterController {
	
	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;
	@Resource(name = "individualCenterService")
	private IndividualCenterService individualCenterService;
	
	
	
////////////////////我的收藏////////////////////
	/**
	 * 我的收藏论文显示  默认每页显示5条记录    功能没有完成       数据库部分操作有些问题？？？
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String collectPaperList(@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "5") int pageSize,HttpServletRequest request) {
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		Page page = individualCenterService.getForPage(user,Integer.valueOf(pageNo), pageSize);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(paper));
		}
		return JSON.toJSONString(new Response().failure("List Failure..."));
		
	}

}
