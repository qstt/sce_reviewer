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
import ustc.sce.service.PublicPaperService;
import ustc.sce.utils.TokenUtil;

/**
 * 公开论文  列表 查找 收藏
 * @author 秋色天堂
 *
 */
@RestController
@RequestMapping("/public_paper")
public class PublicPaperController {
	
	@Resource(name = "publicPaperService")
	private PublicPaperService publicPaperService;
	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;
	
	/**
	 * 公开论文列表
	 * @param pageNo 当前页面  默认 1
	 * @param pageSize 每页记录条数  默认 3
	 * @return 论文信息
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String publicPaperList(@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "3") int pageSize) {
				
		Page page = publicPaperService.getForPage(Integer.valueOf(pageNo), pageSize);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(paper));
		}
		return JSON.toJSONString(new Response().failure("List Failure..."));
	}
	
	/**
	 * 公开论文 根据论文题目进行查找
	 * @param keyWords 查找关键字
	 * @param pageNo 当前页面
	 * @param pageSize 每页记录条数
	 * @return List<Paper>
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String publicPaperSearch(@RequestParam("keyWords") String keyWords,
			@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "5")int pageSize) {
		
		Page page = publicPaperService.publicPaperSearch(keyWords,Integer.valueOf(pageNo), pageSize);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(paper));
		}
		return JSON.toJSONString(new Response().failure("Search Failure..."));
	}
	
	/**
	 * 收藏公开论文
	 * @param paperId 收藏论文id
	 * @param request
	 * @return 论文信息
	 */
	@RequestMapping(value = "/collect", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String publicPaperCollect(@RequestParam("paperId") int paperId, HttpServletRequest request) {
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		Paper paper = publicPaperService.publicPaperCollect(user,paperId);
		
		if(paper != null) {
			return JSON.toJSONString(new Response().success(paper));
		}
		return JSON.toJSONString(new Response().failure("Collect Failure..."));
		
	}

}
