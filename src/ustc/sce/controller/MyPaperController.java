package ustc.sce.controller;

import java.io.UnsupportedEncodingException;
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
import ustc.sce.service.MyPaperService;
import ustc.sce.utils.TokenUtil;

/**
 * 我的论文控制层
 * @author 秋色天堂
 *
 */
@RestController
@RequestMapping("/mypaper")
public class MyPaperController {
	
	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;
	@Resource(name = "myPaperService")
	private MyPaperService myPaperService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String myPpaperSearch(@RequestParam(value = "keyWords",required = false) String keyWords,
			@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "3")int pageSize,
			HttpServletRequest request) throws UnsupportedEncodingException {
				
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		if(keyWords != null) {
			keyWords=new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		}
		
		int currentPage = Integer.valueOf(pageNo);
		Page page = myPaperService.myPpaperSearch(user,keyWords,currentPage, pageSize);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(page));
		}
		return JSON.toJSONString(new Response().failure("Search Failure..."));
	}
	
	
	
	

}
