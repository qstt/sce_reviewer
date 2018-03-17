package ustc.sce.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.Collection;
import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.User;
import ustc.sce.response.Response;
import ustc.sce.service.PublicPaperService;
import ustc.sce.utils.TokenUtil;

@RestController
@RequestMapping("/public_paper")
public class PublicPaperController {
	
	@Resource(name = "publicPaperService")
	private PublicPaperService publicPaperService;
	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;
	
	/**
	 * 公开论文显示   默认每页5条记录
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String publicPaperList(@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "5") int pageSize) {
				
		Page page = publicPaperService.getForPage(Integer.valueOf(pageNo), pageSize);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(paper));
		}
		return JSON.toJSONString(new Response().failure("List Failure..."));
	}
	
	/**
	 * 根据论文题目进行搜索
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String publicPaperSearch(@RequestParam("KeyWords") String KeyWords,
			@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "5")int pageSize) {
		
		Page page = publicPaperService.publicPaperSearch(KeyWords,Integer.valueOf(pageNo), pageSize);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(paper));
		}
		return JSON.toJSONString(new Response().failure("Search Failure..."));
	}
	
	/**
	 * 收藏论文
	 * @param collectPaperId
	 * @return
	 */
	@RequestMapping(value = "/collect", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String publicPaperCollect(@RequestParam("collectPaperId") int collectPaperId, HttpServletRequest request) {
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		Collection collection = publicPaperService.publicPaperCollect(user,collectPaperId);
		
		if(collection != null) {
			return JSON.toJSONString(new Response().success(collection));
		}
		return JSON.toJSONString(new Response().failure("Collect Failure..."));
		
	}

}
