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
import ustc.sce.service.IndividualCenterService;
import ustc.sce.utils.TokenUtil;

/**
 * 个人中心控制层   我的论文  我的批注  我的收藏
 * @author 秋色天堂
 *
 */
@RestController
@RequestMapping("/collection")
public class MyCollectionController {
	
	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;
	@Resource(name = "individualCenterService")
	private IndividualCenterService individualCenterService;
	
	/**
	 * 论文收藏  列表显示
	 * @param pageNo 当前页面  默认1
	 * @param pageSize 每页记录条数  默认3
	 * @param request
	 * @return List<Paper>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String collectPaperList(@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "3") int pageSize,HttpServletRequest request) {
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		Page page = individualCenterService.getForPage(user,Integer.valueOf(pageNo), pageSize);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(page));
		}
		return JSON.toJSONString(new Response().failure("List Failure..."));
		
	}

	/**
	 * 取消收藏
	 * @param paperId 论文id
	 * @param request 获得用户
	 * @return 取消收藏成功/失败
	 */
	@RequestMapping(value = "/cancel/{paperId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String cancelCollection(@PathVariable("paperId") int paperId,HttpServletRequest request) {
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		boolean flag = individualCenterService.cancelCollection(user,paperId);
		if (flag) {
			return JSON.toJSONString(new Response().success("cancelCollection Success ..."));
		}
		return JSON.toJSONString(new Response().failure("cancelCollection Failure..."));
	}
	
	/**
	 * 收藏论文   根据论文题目进行查找
	 * @param keyWords 查找关键字
	 * @param pageNo 当前页面   默认为1
	 * @param pageSize 每页记录条数  默认为3
	 * @param request  获取用户
	 * @return List<Paper>
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String searchCollection(@RequestParam("keyWords") String keyWords,
			@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "3")int pageSize,
			HttpServletRequest request) throws UnsupportedEncodingException {
				
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		keyWords=new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		if(keyWords.isEmpty()) {
			return JSON.toJSONString(new Response().failure("请输论文名..."));
		}
		
		int currentPage = Integer.valueOf(pageNo);
		Page page = individualCenterService.searchCollection(user,keyWords,currentPage, pageSize);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(page));
		}
		return JSON.toJSONString(new Response().failure("Search Failure..."));
	}
	
}
