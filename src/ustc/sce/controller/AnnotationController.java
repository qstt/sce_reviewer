package ustc.sce.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.Annotation;
import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;
import ustc.sce.response.Response;
import ustc.sce.service.AnnotationService;
import ustc.sce.utils.TokenUtil;

/**
 * 批注控制层
 * 
 * @author 秋色天堂
 *
 */
@RestController
@RequestMapping("/annotation")
public class AnnotationController {

	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;
	@Resource(name = "annotationService")
	private AnnotationService annotationService;

	/**
	 * 保存批注
	 * 
	 * @param annotation
	 * @param request
	 * @return 批注信息
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = ("application/json;charset=UTF-8"))
	public String saveAnnotation(@RequestBody Annotation annotation, HttpServletRequest request) {

		annotation = annotationService.saveAnnotation(annotation);

		return JSON.toJSONString(new Response().success(annotation));
	}

	/**
	 * 删除该用户的批注
	 * 
	 * @param annotationId
	 *            批注id
	 * @param request
	 *            获取该用户
	 * @return 删除成功/失败
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String deleteAnnotation(@RequestParam("annotationId") int annotationId, HttpServletRequest request) {

		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);

		boolean flag = annotationService.deleteAnnotation(user, annotationId);

		if (flag) {
			return JSON.toJSONString(new Response().success("Annotation delete Success ..."));
		}
		return JSON.toJSONString(new Response().failure("Annotation delete Failure ..."));
	}

	/**
	 * 分页显示该用户批注
	 * @param pageNo 当前页面 默认1
	 * @param pageSize 每页显示记录条数  默认3
	 * @param request 获得该用户请求
	 * @return List<Annotation>
	 */
	@RequestMapping(value = "/user_list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String userAnnotationList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
			HttpServletRequest request) {

		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);

		int currentPage = Integer.valueOf(pageNo);

		Page page = annotationService.getForPage(user,currentPage, pageSize);
		List<Annotation> pageAnnotation = page.getList();
		if (!pageAnnotation.isEmpty()) {
			return JSON.toJSONString(new Response().success(pageAnnotation));
		}
		return JSON.toJSONString(new Response().failure("User annotation list Failure..."));
	}
	
	/**
	 * 分页显示该用户批注查找
	 * @param keyWords 查找关键字
	 * @param pageNo 当前页面  默认1
	 * @param pageSize 每页显示记录条数 默认3
	 * @param request 获取用户
	 * @return List<Annotation>
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/user_search", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String userAnnotationSearch(@RequestParam("keyWords") String keyWords,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
			HttpServletRequest request) throws UnsupportedEncodingException {

		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		int currentPage = Integer.valueOf(pageNo);
		String keyWords1 = new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		Page page = annotationService.userAnnotationSearch(user,keyWords1,currentPage, pageSize);
		List<Annotation> pageAnnotation = page.getList();
		if (!pageAnnotation.isEmpty()) {
			return JSON.toJSONString(new Response().success(pageAnnotation));
		}
		return JSON.toJSONString(new Response().failure("file annotation list Failure..."));
	}
	
	/**
	 * 不分页显示该文件所有批注
	 * @param fileId 文件id
	 * @param request
	 * @return List<Annotation>
	 */
	@RequestMapping(value = "/file_list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileAnnotationList(@RequestParam("fileId") int fileId,
			HttpServletRequest request) {

		List<Annotation> annotation = annotationService.fileAnnotationList(fileId);

		return JSON.toJSONString(new Response().success(annotation));
	}
	
	/**
	 * 不分页查找该文件中的批注
	 * @param fileId 文件id
	 * @param keyWords 查找关键字
	 * @return List<Annotation>
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/file_search", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileAnnotationSearch(@RequestParam("fileId") int fileId,@RequestParam("keyWords") String keyWords) throws UnsupportedEncodingException {

		keyWords = new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		List<Annotation> annotation = annotationService.fileAnnotationSearch(fileId,keyWords);

		return JSON.toJSONString(new Response().success(annotation));
	}
	
	
	
	
	
	

}
