package masterSpringMvc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PictureUploadController {
	public static final Resource PICTURES_DIR = new FileSystemResource("./pictures");

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
		File tempFile = File.createTempFile("pic", getFileExtension(filename), PICTURES_DIR.getFile());
		try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}

		return "profile/uploadPage";
	}

	private static String getFileExtension(String name) {
		return name.substring(name.lastIndexOf("."));
	}
	
	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
		}

	

}
