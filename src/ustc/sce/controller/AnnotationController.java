package ustc.sce.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.Annotation;
import ustc.sce.domain.User;
import ustc.sce.response.Response;
import ustc.sce.service.AnnotationService;
import ustc.sce.utils.TokenUtil;

/**
 * 批注
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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveAnnotation(@RequestParam("annotationSelect") String annotationSelect,
			@RequestParam("annotationContent") String annotationContent,
			@RequestParam("fileId") int fileId,
			HttpServletResponse response, HttpServletRequest request) {

		Annotation annotation = annotationService.saveAnnotation(annotationSelect, annotationContent, fileId);

		return JSON.toJSONString(new Response().success(annotation));

	}

}
