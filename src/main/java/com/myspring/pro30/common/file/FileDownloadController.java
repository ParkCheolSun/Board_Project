package com.myspring.pro30.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


//@Controller
public class FileDownloadController {
	private static String ARTICLE_IMAGE_REPO = "c:\\board\\article_image";
	private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);
	@RequestMapping("/download.do")
	public void download(@RequestParam("imageFileName") String imageFileName,@RequestParam("articleNO") String articleNO , HttpServletResponse response) throws Exception{
		OutputStream out = response.getOutputStream();
		String downFile = ARTICLE_IMAGE_REPO + "\\" + articleNO + "\\" + imageFileName;
		logger.debug("debug : imageFileName" + imageFileName);
		File file = new File(downFile);
		
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName);
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024*8];
		
		while(true) {
			int count = in.read(buffer);
			if(count == -1) break;
			out.write(buffer,0,count);
		}
		in.close();
		out.close();
	}
}