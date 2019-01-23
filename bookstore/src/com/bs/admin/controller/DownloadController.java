package com.bs.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
* <p>Title: DownloadController</p>  
* <p>Description: 文件下载</p>  
* @author 胡杰
* @date 2018年11月21日
 */
@Controller
public class DownloadController {

//	private String path = "D://测试IO文件/";

	// filename:.+ 参数的限定
	@GetMapping("download/{filename:.+}")
	public ResponseEntity<byte[]> downloadAction(@PathVariable("filename") String filename, HttpServletRequest req) {
		// 文件目录基于webContent根目录path
		String path = req.getServletContext().getRealPath("/");
		HttpHeaders headers = new HttpHeaders();
		String realName = filename.replace("$$", "/");
		System.out.println("filename=     " + realName);
		try {
			headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realName.substring(realName.lastIndexOf("/")+1), "UTF-8"));
			InputStream is = new FileInputStream(new File(path + realName));
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
			ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(buffer, headers, HttpStatus.OK);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
//	@GetMapping("download")
//	public void download(@RequestParam(name="filename") String filename, HttpServletResponse resp){
//		System.out.println("filename：" + filename);
//		try {
//			resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
//			InputStream is = new FileInputStream(new File(path+filename));
//			OutputStream os = resp.getOutputStream();
//			byte[] b = new byte[2048];
//			int len;
//			while((len = is.read(b)) != -1){
//				os.write(b, 0,len);
//			}
//			os.flush();
//			os.close();
//			is.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
}
