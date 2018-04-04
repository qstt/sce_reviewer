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

import ustc.sce.domain.Annotation;
import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
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

		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		annotation.setAnnotator(user.getUserName());
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
	@RequestMapping(value = "/delete/{annotationId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String deleteAnnotation(@PathVariable("annotationId") int annotationId, HttpServletRequest request) {

		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);

		boolean flag = annotationService.deleteAnnotation(user, annotationId);

		if (flag) {
			return JSON.toJSONString(new Response().success("Annotation delete Success ..."));
		}
		return JSON.toJSONString(new Response().failure("Annotation delete Failure ..."));
	}
	
	/**
	 * 不分页显示该文件所有批注
	 * @param fileId 文件id
	 * @param request
	 * @return List<Annotation>
	 */
	@RequestMapping(value = "/file_list/{fileId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileAnnotationList(@PathVariable("fileId") int fileId,
			HttpServletRequest request) {

		List<Annotation> annotation = annotationService.fileAnnotationList(fileId);
		if(annotation == null) {
			return JSON.toJSONString(new Response().failure("该文件没有批注..."));
		}

		return JSON.toJSONString(new Response().success(annotation));
	}
	
	/**
	 * 不分页查找该文件中的批注
	 * @param fileId 文件id
	 * @param keyWords 查找关键字
	 * @return List<Annotation>
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/file_search/{fileId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileAnnotationSearch(@PathVariable("fileId") int fileId,@RequestParam("keyWords") String keyWords) throws UnsupportedEncodingException {

		keyWords=new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		if(keyWords.isEmpty()) {
			return JSON.toJSONString(new Response().failure("请输入查找内容..."));
		}
		List<Annotation> annotation = annotationService.fileAnnotationSearch(fileId,keyWords);
		if(annotation == null) {
			return JSON.toJSONString(new Response().failure("没有该查找内容..."));
		}
		return JSON.toJSONString(new Response().success(annotation));
	}
	
	/**
	 * 显示该文件该用户的所有批注
	 * @param fileId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_list/{fileId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String userAnnotationList(@PathVariable("fileId") int fileId,
			HttpServletRequest request){
			
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		List<Annotation> annotation = annotationService.userAnnotationList(user,fileId);
		if(annotation == null) {
			return JSON.toJSONString(new Response().failure("该用户没有批注..."));
		}
		return JSON.toJSONString(new Response().success(annotation));
	}
	
	/**
	 * 查找该文件该用户的批注
	 * @param fileId
	 * @param keyWords
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/user_search/{fileId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String userAnnotationSearch(@PathVariable("fileId") int fileId,@RequestParam("keyWords") String keyWords,HttpServletRequest request) throws UnsupportedEncodingException {

		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		keyWords=new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		if(keyWords.isEmpty()) {
			return JSON.toJSONString(new Response().failure("请输入查找内容..."));
		}
		List<Annotation> annotation = annotationService.userAnnotationSearch(user,fileId,keyWords);
		if(annotation == null) {
			return JSON.toJSONString(new Response().failure("没有该查找内容..."));
		}
		return JSON.toJSONString(new Response().success(annotation));
	}
	
	/**
	 * 我的批注列表 不分页
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String myAnnotationList(HttpServletRequest request) {
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		List<FileEntity> fileEntitys = annotationService.myAnnotationList(user);
		
		if (!fileEntitys.isEmpty()) {
			return JSON.toJSONString(new Response().success(fileEntitys));
		}
		return JSON.toJSONString(new Response().failure("List Failure..."));
	
	}
	
	/**
	 * 删除该用户对该论文所有的批注
	 * @param fileId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/all_delete/{fileId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String myAnnotationDelete(@PathVariable("fileId") int fileId,HttpServletRequest request) {
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		boolean flag = annotationService.myAnnotationDelete(user,fileId);
		
		if (flag) {
			return JSON.toJSONString(new Response().success("DETELE Success..."));
		}
		return JSON.toJSONString(new Response().failure("DETELE Failure..."));
	
	}
				

	
	
	
	
	

}
