package ustc.sce.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/paper-review")
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
	@RequestMapping(value = "/submit_paper/{paperId}/{teacherId}",method = RequestMethod.POST)
	public String submitPaper(@PathVariable("paperId") int paperId,@PathVariable("teacherId") int teacherId) {
		
		PaperReview paperReview = paperReviewService.submitPaper(paperId,teacherId);
		
		if (paperReview != null) {
			return JSON.toJSONString(new Response().success(paperReview));
		}
		return JSON.toJSONString(new Response().failure("Submit Paper Failure..."));
	}
	
	
	
	

}
