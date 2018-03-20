package ustc.sce.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.PaperReview;
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
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String createPaper(@RequestParam("paperTitle") String paperTitle,
			@RequestParam("paperAuthor") String paperAuthor, 
			@RequestParam(value = "paperOwner",required = false) String paperOwner,
			@RequestParam("ispublic") boolean ispublic, 
			@RequestParam(value = "fileId",required = false,defaultValue = "-1") Integer fileId,
			HttpServletResponse response, HttpServletRequest request) {

		PaperReview paperReview = paperService.createPaper(paperTitle, paperAuthor, paperOwner, ispublic, fileId);
			
		return JSON.toJSONString(new Response().success(paperReview));
	}
	
	/**
	 * 给论文增加文件pdf
	 * @param paperId
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value = "/addPDF", method = RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String addPDF(@RequestParam("paperId") int paperId,@RequestParam("fileId") int fileId) {
		FileEntity fileEntity = paperService.addPDF(paperId,fileId);
		return JSON.toJSONString(new Response().success(fileEntity));
		
	}
	
	/**
	 * 删除文件
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String paperDelete(@RequestParam("paperId") int paperId,HttpServletRequest request) {
		boolean flag = paperService.paperDelete(paperId,request);
		if (flag) {
			return JSON.toJSONString(new Response().success("paperDelete Success..."));
		}
		return JSON.toJSONString(new Response().failure("paperDelete Failure..."));
	}
	

}
