package ustc.sce.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;
import ustc.sce.response.Response;
import ustc.sce.service.FileService;
import ustc.sce.utils.TokenUtil;

@RestController
@RequestMapping("/file")
public class FileController {

	@Resource(name = "fileService")
	private FileService fileService;
	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;

	/**
	 * 文件上传
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String fileUplod(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception, Exception {

		FileEntity fileUpload = new FileEntity();

		if (!file.isEmpty()) {
			// 文件保存路径 获得项目的路径 ServletContext sc = request.getSession().getServletContext();
			String filePath = request.getSession().getServletContext().getRealPath("\\") + "upload";
			if (!new File(filePath).exists()) {
				new File(filePath).mkdir();
			}
			
			System.out.println(filePath);

			// 绝对路径
//			 String filePath = "J:\\eclipse\\apache-tomacat-7.0.47\\webapps\\upload\\reviewer";

			String fileName = file.getOriginalFilename();
			// 文件的扩展名
			String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);

			// 日期 201803201154
			Date dt = new Date();
			DateFormat dFormat3 = new SimpleDateFormat("yyyyMMddHHmmss");
			String formatDate = dFormat3.format(dt);
			// 去掉文件后缀名 加上时间戳
			fileName = fileName.substring(0, fileName.lastIndexOf(".")) + formatDate;
			// 加上文件扩展名
			String fileName2 = fileName + "." + extensionName;

			filePath = filePath + "\\" + fileName2;
			// 转存文件
			file.transferTo(new File(filePath));

			// 数据库中保存部分路径 保证安全 返回时再加上前面的路径
			//String filePath1 = "reviewer\\" + fileName2;   //绝对路径使用
			String filePath1 = "upload\\" + fileName2;   //相对路径使用

			String header = request.getHeader("X-Token");
			User user = tokenUtil.getUser(header);

			fileUpload.setFileName(fileName);
			fileUpload.setFileType(extensionName);
			fileUpload.setFilePath(filePath1);
			fileUpload.setUser(user);

			fileService.FileSave(fileUpload);
			// 直接返回file的JSON格式，只有txt文件可以上传成功，
			// pdf和doc文件可以上传到内存中也可以将数据保存在数据库中，但是返回会报500错误
			// 在配置中加上p:maxInMemorySize="54000000"
			return JSON.toJSONString(new Response().success(fileUpload));
		}
		return JSON.toJSONString(new Response().failure("FileUpload Failure..."));
	}

	/**
	 * 删除文件
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String fileDelete(@RequestParam("fileId") int fileId,HttpServletRequest request) {
		boolean flag = fileService.fileDelete(fileId,request);
		if (flag) {
			return JSON.toJSONString(new Response().success("FileDelect Success..."));
		}
		return JSON.toJSONString(new Response().failure("FileDelect Failure..."));
	}
	
	/**
	 * 文件下载
	 */
	@RequestMapping(value = "/download",method = RequestMethod.GET)
	public void fileDownload(@RequestParam("fileId") int fileId, HttpServletResponse response,HttpServletRequest request)
			throws IOException {

		// 得到filePath 拼接真实路径
		FileEntity fe = fileService.getFile(fileId);

		if (fe != null) {

			String fileName = fe.getFileName();
			String fileType = fe.getFileType();
			String filePath = fe.getFilePath();
			String realPath = request.getSession().getServletContext().getRealPath("\\") + filePath;
			
			File file = new File(realPath);
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			fileName = fileName + "." + fileType;
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1")); // 指定下载的文件名

			os.write(FileUtils.readFileToByteArray(file));
			os.flush();
			os.close();
		}

	}
	
	/**
	 * 分页显示该用户上传的文件  默认每页3条记录
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileSearch(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
			HttpServletRequest request) {
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);

		Page page = fileService.getForPage(Integer.valueOf(pageNo), pageSize,user);
		List<FileEntity> pageFileEntity = page.getList();
		if (!pageFileEntity.isEmpty()) {
			for (int i = 0; i < pageFileEntity.size(); i++) {
				String filePath = pageFileEntity.get(i).getFilePath();
				filePath = request.getSession().getServletContext().getRealPath("\\") + filePath;
				pageFileEntity.get(i).setFilePath(filePath);
			}
			return JSON.toJSONString(new Response().success(pageFileEntity));
		}
		return JSON.toJSONString(new Response().failure("FileList Failure..."));

	}
	
	/**
	 * 分页显示文件列表 默认每页3条记录
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
			HttpServletRequest request) {

		Page page = fileService.getForPage(Integer.valueOf(pageNo), pageSize);
		List<FileEntity> pageFileEntity = page.getList();
		if (!pageFileEntity.isEmpty()) {
			for (int i = 0; i < pageFileEntity.size(); i++) {
				String filePath = pageFileEntity.get(i).getFilePath();
				filePath = request.getSession().getServletContext().getRealPath("\\") + filePath;
				pageFileEntity.get(i).setFilePath(filePath);
			}
			return JSON.toJSONString(new Response().success(pageFileEntity));
		}
		return JSON.toJSONString(new Response().failure("FileList Failure..."));

	}
	
	/**
	 * 文件详情 显示成网页版进行批注 传入fileId 返回file用file中的filePath
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileShow(@RequestParam("fileId") int fileId) {

		FileEntity file = fileService.fielShow(fileId);

		if (file != null) {
			String filePath = file.getFilePath();
			filePath = "J:\\eclipse\\apache-tomacat-7.0.47\\webapps\\upload\\" + filePath;
			file.setFilePath(filePath);
			return JSON.toJSONString(new Response().success(file));
		}
		return JSON.toJSONString(new Response().failure("fileShow Failure..."));

	}
	

	
/////////////////////////////下面的方法都不使用，测试中保留后面会删除//////////////////////////////////////////////////
	
	// 文件下载 第二种方法
	@RequestMapping(value = "/download2")
	public ResponseEntity<byte[]> download(@RequestParam("fileName") String fileName, HttpServletResponse response)
			throws IOException {

		FileEntity fe = fileService.getFile(fileName);

		if (fe == null) {
			return null;
		}
		String fileType = fe.getFileType();
		String filePath = fe.getFilePath();
		String realPath = "J:\\eclipse\\apache-tomacat-7.0.47\\webapps\\upload\\" + filePath;
		File file = new File(realPath);
		fileName = fileName + "." + fileType;
		String dfileName = new String(fileName.getBytes("UTF-8"), "iso8859-1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", dfileName);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

}
