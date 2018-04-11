package ustc.sce.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.PaperReview;
import ustc.sce.domain.User;
import ustc.sce.response.Response;
import ustc.sce.service.PaperReviewService;
import ustc.sce.utils.TokenUtil;


/**
 * 论文评审状态   0 未评审  1 正在评审  2已定稿
 * @author 秋色天堂
 *
 */
@RestController
@RequestMapping("/paper_review")
public class PaperReviewController {
	
	@Resource(name="paperReviewService")
	private PaperReviewService paperReviewService;
	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;
	
	/**
	 * 提交论文
	 * @param paperStatus
	 * @param paperTitle
	 * @return
	 */
	@RequestMapping(value = "/submit_paper/{paperId}/{teacherId}",method = RequestMethod.POST,produces="text/html;charset=utf-8")
	public String submitPaper(@PathVariable("paperId") int paperId,@PathVariable("teacherId") int teacherId) {
		
		PaperReview paperReview = paperReviewService.submitPaper(paperId,teacherId);
		
		if (paperReview != null) {
			return JSON.toJSONString(new Response().success(paperReview));
		}
		return JSON.toJSONString(new Response().failure("Submit Paper Failure..."));
	}
	
	/**
	 * 获取所有注册的老师
	 * @return
	 */
	@RequestMapping(value = "/teachers",method = RequestMethod.GET,produces="text/html;charset=utf-8")
	public String getTeachers() {
		List<User> teachers = paperReviewService.getTeachers();
		if (teachers != null) {
			return JSON.toJSONString(new Response().success(teachers));
		}
		return JSON.toJSONString(new Response().failure("没有老师..."));
	}
	
	/**
	 * 评审中老师和学生的交互
	 * @param paperId
	 * @param teacherStatus
	 * @return
	 */
	@RequestMapping(value = "/reviewing/{paperId}/{teacherStatus}",method = RequestMethod.GET,produces="text/html;charset=utf-8")
	public String reviewing(@PathVariable("paperId") int paperId,@PathVariable("teacherStatus") int teacherStatus) {
		
		PaperReview paperReview = paperReviewService.reviewing(paperId,teacherStatus);
		if (paperReview != null) {
			return JSON.toJSONString(new Response().success(paperReview));
		}
		return JSON.toJSONString(new Response().failure("Submit Paper Failure..."));
	}
	
	@RequestMapping(value = "/final_version/{paperId}",method = RequestMethod.GET,produces="text/html;charset=utf-8")
	public String finalVersion(@PathVariable("paperId") int paperId) {
		PaperReview paperReview = paperReviewService.finalVersion(paperId);
		if (paperReview != null) {
			return JSON.toJSONString(new Response().success(paperReview));
		}
		return JSON.toJSONString(new Response().failure("Submit Paper Failure..."));
	}
	

}
