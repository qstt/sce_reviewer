package ustc.sce.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import org.springframework.web.bind.annotation.PathVariable;
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

/**
 * 文件控制层   文件上传  删除文件  文件下载
 * @author 秋色天堂
 *
 */
@RestController
@RequestMapping("/file")
public class FileController {

	@Resource(name = "fileService")
	private FileService fileService;
	@Resource(name = "tokenUtil")
	private TokenUtil tokenUtil;

	/**
	 * 文件上传
	 * @param file 上传的文件
	 * @param request
	 * @return 文件信息/上传失败
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String fileUplod(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request)
			throws Exception{
		
		if(file == null) {
			return JSON.toJSONString(new Response().failure("FileUpload Failure..."));
		}
		
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
//			fileUpload.setCreateTime(new Timestamp(System.currentTimeMillis()));
//			fileUpload.setUpdateTime(new Timestamp(System.currentTimeMillis()));

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
	 * @param fileId  文件id
	 * @param request
	 * @return 删除成功/失败
	 */
	@RequestMapping(value = "/delete/{fileId}", method = RequestMethod.POST,produces = "text/html;charset=utf-8")
	public String fileDelete(@PathVariable("fileId") int fileId,HttpServletRequest request) {
		boolean flag = fileService.fileDelete(fileId,request);
		if (flag) {
			return JSON.toJSONString(new Response().success("FileDelect Success..."));
		}
		return JSON.toJSONString(new Response().failure("FileDelect Failure..."));
	}
	
	/**
	 * 文件下载
	 * @param fileId  文件id
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/download/{fileId}",method = RequestMethod.GET,produces = "text/html;charset=utf-8")
	public void fileDownload(@PathVariable("fileId") int fileId, HttpServletResponse response,HttpServletRequest request)
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
	 * 分页显示该用户上传的文件
	 * @param pageNo 当前页面  默认1
	 * @param pageSize 每页显示记录条数  默认3
	 * @param request
	 * @return page
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/user/list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileUserList(@RequestParam(value = "keyWords",required = false) String keyWords,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
			HttpServletRequest request) throws UnsupportedEncodingException {
		
		if(keyWords != null) {
			keyWords=new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		}
		
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);

		Page page = fileService.getForPage(keyWords,Integer.valueOf(pageNo), pageSize,user);
		List<FileEntity> pageFileEntity = page.getList();
		if (!pageFileEntity.isEmpty()) {
			for (int i = 0; i < pageFileEntity.size(); i++) {
				String filePath = pageFileEntity.get(i).getFilePath();
				filePath = request.getSession().getServletContext().getRealPath("\\") + filePath;
				pageFileEntity.get(i).setFilePath(filePath);
			}
			return JSON.toJSONString(new Response().success(page));
		}
		return JSON.toJSONString(new Response().failure("FileList Failure..."));

	}
	
	/**
	 * 不分页显示该用户上传的文件
	 * @param request
	 * @return  page
	 */
	@RequestMapping(value = "/nopage_list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileNoPageList(HttpServletRequest request) {
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		List<FileEntity> fileEntitys = fileService.fileNoPageList(user);
		if(fileEntitys == null) {
			return JSON.toJSONString(new Response().failure("该用户没有上传的文件..."));
		}
		
		return JSON.toJSONString(new Response().success(fileEntitys));
	}
	
	
	/**
	 * 分页显示所有文件列表
	 * @param pageNo 当前页面  默认为1
	 * @param pageSize 每页显示记录条数  默认为3
	 * @param request
	 * @return List<FileEntity>
	 */
	@RequestMapping(value = "/all_list", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileAllList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
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
			return JSON.toJSONString(new Response().success(page));
		}
		return JSON.toJSONString(new Response().failure("FileList Failure..."));

	}
	
	/**
	 * 文件详情 显示成网页版进行批注
	 * @param fileId  文件id
	 * @param request
	 * @return file 使用filePath
	 * @throws Exception 
	 */
	@RequestMapping(value = "/pdf/{fileId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileShow(@PathVariable("fileId") int fileId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		FileEntity file = fileService.getFile(fileId);
		if (file != null) {
			String filePath = file.getFilePath();
			filePath = request.getSession().getServletContext().getRealPath("\\") + filePath;
			File file2 = new File(filePath);
			byte[] data = null;
			FileInputStream input = new FileInputStream(file2);
            data = new byte[input.available()];
            input.read(data);
            response.getOutputStream().write(data);
			file.setFilePath(filePath);
			return null;
			
//			return JSON.toJSONString(new Response().success(file));
		}
		return JSON.toJSONString(new Response().failure("fileShow Failure..."));

	}
	
	/**
	 * 按文件名查找所有文件 分页显示
	 * @param keyWords 关键字
	 * @param pageNo 当前页面
	 * @param pageSize 每页显示记录条数
	 * @param request
	 * @return List<FileEntity>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/all_search", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileAllSearch(@RequestParam(value = "keyWords") String keyWords,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
			HttpServletRequest request) throws UnsupportedEncodingException {
		keyWords=new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		if(keyWords.isEmpty()) {
			return JSON.toJSONString(new Response().failure("请输入文件名..."));
		}
		
		int currentPage = Integer.valueOf(pageNo);
		Page page = fileService.getForPage(currentPage, pageSize,keyWords);
		List<FileEntity> pageFileEntity = page.getList();
		if (!pageFileEntity.isEmpty()) {
			for (int i = 0; i < pageFileEntity.size(); i++) {
				String filePath = pageFileEntity.get(i).getFilePath();
				filePath = request.getSession().getServletContext().getRealPath("\\") + filePath;
				pageFileEntity.get(i).setFilePath(filePath);
			}
			return JSON.toJSONString(new Response().success(page));
		}
		return JSON.toJSONString(new Response().failure("FileList Failure..."));

	}
	
	/**
	 * 按文件名查找该用户上传文件  不用分页显示
	 * @param keyWords 查询关键字
	 * @param request
	 * @return List<FileEntity>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/user_search", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String fileUserSearch(@RequestParam("keyWords") String keyWords,HttpServletRequest request) throws UnsupportedEncodingException {
		String header = request.getHeader("X-Token");
		User user = tokenUtil.getUser(header);
		
		if(keyWords.isEmpty()) {
			return JSON.toJSONString(new Response().failure("请输入文件名..."));
		}
		
		keyWords=new String(keyWords.getBytes("iso-8859-1"), "utf-8");
		List<FileEntity> fileEntitys = fileService.fileNoPageList(user,keyWords);
		
		if(fileEntitys == null) {
			return JSON.toJSONString(new Response().failure("没有该文件..."));
		}
		
		return JSON.toJSONString(new Response().success(fileEntitys));   
		
	}
	

	
/////////////////////////////下面的方法都不使用，测试中保留后面会删除//////////////////////////////////////////////////
	
	/**
	 * 下载文件第2种方法   项目中没有使用
	 * @param fileName 文件名
	 * @param HttpServletRequest request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/download2")
	public ResponseEntity<byte[]> download(@RequestParam("fileName") String fileName, HttpServletRequest request)
			throws IOException {

		FileEntity fe = fileService.getFile(fileName);

		if (fe == null) {
			return null;
		}
		String fileType = fe.getFileType();
		String filePath = fe.getFilePath();
		String realPath = request.getSession().getServletContext().getRealPath("\\") + filePath;
		File file = new File(realPath);
		fileName = fileName + "." + fileType;
		String dfileName = new String(fileName.getBytes("UTF-8"), "iso8859-1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", dfileName);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

}
