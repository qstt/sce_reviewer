package ustc.sce.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.Paper;
import ustc.sce.response.Response;
import ustc.sce.service.PaperService;

/**
 * 创建论文 关联文件（文件单独上传，在创建论文时提供文件的id进行关联）
 * 
 * @author 秋色天堂
 *
 */

@RestController
@RequestMapping("/paper")
public class PaperController {

	@Resource(name = "paperService")
	private PaperService paperService;

	/**
	 * 创建论文   论文中的pdf文件是先上传成功的  传入之前的pdf文件的id  一个论文可以有多个pdf文件
	 * @param paperTitle   论文题目
	 * @param paperAuthor   论文作者
	 * @param paperOwner   论文所有者
	 * @param ispublic   是否公开，默认私有
	 * @param fileId   关联的pdf文件
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,produces = "text/html;charset=utf-8")
	public String createPaper(@RequestParam("paperTitle") String paperTitle,
			@RequestParam("paperAuthor") String paperAuthor, 
			@RequestParam(value = "paperOwner",required = false) String paperOwner,
			@RequestParam("ispublic") boolean ispublic, 
			@RequestParam(value = "fileId",required = false,defaultValue = "-1") Integer fileId,
			HttpServletResponse response, HttpServletRequest request) {

		Paper paper = paperService.create(paperTitle, paperAuthor, paperOwner, ispublic, fileId);
			
		return JSON.toJSONString(new Response().success(paper));
	}
	
	//给论文增加文件pdf
	@RequestMapping(value = "/addPDF", method = RequestMethod.POST,produces = "text/html;charset=utf-8")
	public String addPDF(@RequestParam("paperId") int paperId,@RequestParam("fileId") int fileId) {
		Paper paper = paperService.addPDF(paperId,fileId);
		return JSON.toJSONString(new Response().success(paper));
		
	}
	

}
