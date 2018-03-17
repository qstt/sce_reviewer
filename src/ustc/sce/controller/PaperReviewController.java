package ustc.sce.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
	 * 未评审状态0
	 * @param paperStatus
	 * @param paperTitle
	 * @return
	 */
	@RequestMapping(value = "/not-review",method = RequestMethod.POST)
	public String notReview(@RequestParam("paperStatus") int paperStatus,@RequestParam("paperId") int paperId,HttpServletRequest request) {
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		PaperReview paperReview = paperReviewService.notReview(paperStatus,paperId,user);
		
		if (paperReview != null) {
			return JSON.toJSONString(new Response().success(paperReview));
		}
		return JSON.toJSONString(new Response().failure("notReview Failure..."));
		
	}
	
	/**
	 * 状态改变  未评审到正在评审  正在评审到已定稿
	 * @param paperStatus
	 * @param paperTitle
	 * @return
	 */
	@RequestMapping(value = "/change-review",method = RequestMethod.POST)
	public String changeReview(@RequestParam("paperStatus") int paperStatus,@RequestParam("paperTitle") String paperTitle) {
		
		PaperReview paperReview = paperReviewService.changeReview(paperStatus,paperTitle);
		
		if (paperReview != null) {
			return JSON.toJSONString(new Response().success(paperReview));
		}
		return JSON.toJSONString(new Response().failure("changeReview Failure..."));
		
	}

}
