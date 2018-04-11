package ustc.sce.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
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
 * 公开论文控制层  列表 查找 收藏
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
	 * 公开论文 根据论文题目进行查找
	 * @param keyWords 查找关键字 
	 * @param pageNo 当前页面    默认为1
	 * @param pageSize 每页记录条数  默认为3
	 * @return List<Paper>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String publicPaperSearch(@RequestParam(value = "keyWords",required = false) String keyWords,
			@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "3")int pageSize) throws UnsupportedEncodingException {
		
		if(keyWords != null) {
			keyWords=new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		}
		
		Page page = publicPaperService.publicPaperSearch(keyWords,Integer.valueOf(pageNo), pageSize);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(page));
		}
		return JSON.toJSONString(new Response().failure("Search Failure..."));
	}
	
	/**
	 * 收藏公开论文
	 * @param paperId 收藏论文id
	 * @param request
	 * @return 论文信息
	 */
	@RequestMapping(value = "/collect/{paperId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String publicPaperCollect(@PathVariable("paperId") int paperId, HttpServletRequest request) {
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		Paper paper = publicPaperService.publicPaperCollect(user,paperId);
		
		if(paper != null) {
			return JSON.toJSONString(new Response().success(paper));
		}
		return JSON.toJSONString(new Response().failure("Collect Failure..."));
		
	}

}
