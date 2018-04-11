package ustc.sce.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.FileEntity;
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
	 * 创建论文
	 * @param paper 论文实体
	 * @param request 获得用户
	 * @param fileId 所关联文件id
	 * @return 论文信息
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,produces=("application/json;charset=UTF-8"))
	public String createPaper(@RequestBody Paper paper,
			@RequestParam(value = "fileId",required = false,defaultValue = "-1") Integer fileId,
			HttpServletRequest request) throws UnsupportedEncodingException {
		
//		@RequestParam(value = "paperTitle",required = false) String paperTitle,
//		@RequestParam(value = "paperAuthor",required = false,defaultValue = "-1") String paperAuthor,
//		@RequestParam(value = "ispublic",required = false) boolean ispublic,
//		
//		paperTitle=new String(paperTitle.getBytes("iso-8859-1"), "utf-8");
//		Paper paper = new Paper();
//		paper.setPaperTitle(paperTitle);
//		paper.setPaperAuthor(paperAuthor);
//		paper.setIspublic(ispublic);
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		paper.setPaperOwner(user.getUserName());
		PaperReview paperReview = paperService.createPaper(paper,fileId);
		
		return JSON.toJSONString(new Response().success(paperReview));
		
	}
	
	/**
	 * 给论文增加文件
	 * @param paperId 论文id
	 * @param fileId 文件id
	 * @return 文件信息
	 */
	@RequestMapping(value = "/addPDF/{paperId}/{fileId}", method = RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String addPDF(@PathVariable("paperId") int paperId,@PathVariable("fileId") int fileId) {
		Paper paper = paperService.addPDF(paperId,fileId);
		if(paper == null) {
			return JSON.toJSONString(new Response().failure("addPDF Failure..."));
		}
		return JSON.toJSONString(new Response().success(paper));
		
	}
	
	/**
	 * 删除论文 将论文中所有文件都删除
	 * @param paperId 论文id
	 * @param request
	 * @return 删除成功/失败
	 */
	@RequestMapping(value = "/delete/{paperId}", method = RequestMethod.POST,produces = "text/html;charset=utf-8")
	public String paperDelete(@PathVariable("paperId") int paperId,HttpServletRequest request) {
		boolean flag = paperService.paperDelete(paperId,request);
		if (flag) {
			return JSON.toJSONString(new Response().success("paperDelete Success..."));
		}
		return JSON.toJSONString(new Response().failure("paperDelete Failure..."));
	}
	
	/**
	 * 获取该论文所有文件
	 * @param paperId 论文id
	 * @param request
	 * @return List<FileEntity>
	 */
	@RequestMapping(value = "/files/{paperId}", method = RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String paperFile(@PathVariable("paperId") int paperId,HttpServletRequest request) {
		List<FileEntity> fileEntity = paperService.paperFile(paperId);
		if (!fileEntity.isEmpty()) {
			for (int i = 0; i < fileEntity.size(); i++) {
				String filePath = fileEntity.get(i).getFilePath();
				filePath = request.getSession().getServletContext().getRealPath("\\") + filePath;
				fileEntity.get(i).setFilePath(filePath);
			}
			return JSON.toJSONString(new Response().success(fileEntity));
		}
		return JSON.toJSONString(new Response().failure("paper no file ..."));
		
	}
	
	
	

}
