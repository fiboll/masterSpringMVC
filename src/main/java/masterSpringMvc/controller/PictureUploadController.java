package masterSpringMvc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import masterSpringMvc.config.PictureUploadProperties;

@Controller
public class PictureUploadController {	
	private final Resource picturesDir;
	private final Resource anonymousPicture;
	
	@Autowired
	public PictureUploadController(PictureUploadProperties uploadProperties) {
		picturesDir = uploadProperties.getUploadPath();
		anonymousPicture = uploadProperties.getAnonymousPicture();
	}


	@RequestMapping("upload")
	public String uploadPage() {
		return "profile/uploadPage";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs) throws IOException {
		
		if (file.isEmpty() || !isImage(file)) {
			redirectAttrs.addFlashAttribute("error","Niewłaściwy plik. Załaduj plik z obrazem.");
			return "redirect:/upload";
		}

		String filename = file.getOriginalFilename();
		File tempFile = File.createTempFile("pic", getFileExtension(filename), picturesDir.getFile());
		try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}

		return "profile/uploadPage";
	}
	
	@RequestMapping(value = "/uploadedPicture")
	public void getUploadedPicture(HttpServletResponse response) throws IOException {
		response.setHeader("Content-Type",	URLConnection.guessContentTypeFromName(anonymousPicture.getFilename()));
		IOUtils.copy(anonymousPicture.getInputStream(), response.getOutputStream());
	}

	private Resource copyFileToPictures(MultipartFile file) throws IOException {

		String fileExtension = getFileExtension(file.getOriginalFilename());
		File tempFile = File.createTempFile("pic", fileExtension,
		picturesDir.getFile());
		try (InputStream in = file.getInputStream();
		OutputStream out = new FileOutputStream(tempFile)) {
		IOUtils.copy(in, out);
		}
		return new FileSystemResource(tempFile);
		}


	private static String getFileExtension(String name) {

		return name.substring(name.lastIndexOf("."));
	}
	
	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
		}

	

}