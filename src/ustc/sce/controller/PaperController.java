package ustc.sce.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.PaperReview;
import ustc.sce.domain.User;
import ustc.sce.response.Response;
import ustc.sce.service.PaperService;
import ustc.sce.utils.TokenUtil;

/**
 * 论文控制层
 * 创建论文 关联文件（文件单独上传，在创建论文时提供文件的id进行关联）
 * @author 秋色天堂
 *
 */

@RestController
@RequestMapping("/paper")
public class PaperController {

	@Resource(name = "paperService")
	private PaperService paperService;
	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;

	/**
	 * 创建论文   论文中的pdf文件是先上传成功的  传入之前的pdf文件的id  一个论文可以有多个pdf文件
	 * @param paperTitle 论文题目
	 * @param paperAuthor 论文作者
	 * @param paperOwner 论文所有者 可以不提供
	 * @param ispublic 公开/私有
	 * @param fileId 关联文件id 默认不关联
	 * @return 论文信息
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String createPaper(@RequestParam("paperTitle") String paperTitle,
			@RequestParam("paperAuthor") String paperAuthor, 
			@RequestParam(value = "paperOwner",required = false) String paperOwner,
			@RequestParam("ispublic") boolean ispublic, 
			@RequestParam(value = "fileId",required = false,defaultValue = "-1") Integer fileId) throws UnsupportedEncodingException {
		
		String paperTitle1=new String(paperTitle.getBytes("iso-8859-1"), "utf-8");

		PaperReview paperReview = paperService.createPaper(paperTitle1, paperAuthor, paperOwner, ispublic, fileId);
			
		return JSON.toJSONString(new Response().success(paperReview));
	}
	
	/**
	 * 创建论文
	 * @param paper 论文实体
	 * @param request
	 * @return 论文信息
	 */
	@RequestMapping(value = "/create1", method = RequestMethod.POST,produces=("application/json;charset=UTF-8"))
	public String createPaper(@RequestBody Paper paper,HttpServletRequest request) {
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		paper.setPaperOwner(user.getUserName());
		PaperReview paperReview = paperService.savePaper(paper);
		
		return JSON.toJSONString(new Response().success(paperReview));
		
	}
	
	/**
	 * 给论文增加文件
	 * @param paperId 论文id
	 * @param fileId 文件id
	 * @return 文件信息
	 */
	@RequestMapping(value = "/addPDF", method = RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String addPDF(@RequestParam("paperId") int paperId,@RequestParam("fileId") int fileId) {
		FileEntity fileEntity = paperService.addPDF(paperId,fileId);
		return JSON.toJSONString(new Response().success(fileEntity));
		
	}
	
	/**
	 * 删除论文 将论文中所有文件都删除
	 * @param paperId 论文id
	 * @param request
	 * @return 删除成功/失败
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String paperDelete(@RequestParam("paperId") int paperId,HttpServletRequest request) {
		boolean flag = paperService.paperDelete(paperId,request);
		if (flag) {
			return JSON.toJSONString(new Response().success("paperDelete Success..."));
		}
		return JSON.toJSONString(new Response().failure("paperDelete Failure..."));
	}
	
	/**
	 * 论文列表
	 * @param pageNo 当前页面 默认为1
	 * @param pageSize 每页显示记录条数  默认为3
	 * @param ispublic 公开/私有   公开 1  私有 0
	 * @return List<Paper>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String paperList(@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "3") int pageSize,
			@RequestParam(value = "ispublic") int ispublic) {
		
		int currentPage = Integer.valueOf(pageNo);
		
		Page page = paperService.getForPage(currentPage, pageSize,ispublic);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(paper));
		}
		return JSON.toJSONString(new Response().failure("List Failure..."));
		
	}
	
	/**
	 * 根据论文题目进行查询
	 * @param keyWords 查询关键字
	 * @param pageNo 当前页面 默认为1
	 * @param pageSize 每页记录条数 默认为3
	 * @param ispublic 公开 1/私有 0
	 * @return List<Paper>
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String paperSearch(@RequestParam("keyWords") String keyWords,
			@RequestParam(value = "pageNo",required = false,defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize",required = false,defaultValue = "3") int pageSize,
			@RequestParam(value = "ispublic") int ispublic) {
		
		int currentPage = Integer.valueOf(pageNo);
		
		Page page = paperService.paperSearch(keyWords,currentPage, pageSize,ispublic);
		List<Paper> paper = page.getList();
		if (!paper.isEmpty()) {
			return JSON.toJSONString(new Response().success(paper));
		}
		return JSON.toJSONString(new Response().failure("Search Failure..."));
		
	}
	
	
	

}
