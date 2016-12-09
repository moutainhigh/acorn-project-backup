package com.chinadrtv.erp.knowledge.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;
import com.chinadrtv.erp.knowledge.model.KnowledgeAttachment;
import com.chinadrtv.erp.knowledge.model.KnowledgeCategory;
import com.chinadrtv.erp.knowledge.service.AttachmentService;
import com.chinadrtv.erp.knowledge.service.CategoryService;
import com.chinadrtv.erp.knowledge.service.KnowledgeArticleService;
import com.chinadrtv.erp.user.util.SecurityHelper;

@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController extends BaseController implements
		InitializingBean {

	private static final String CONTENT_TYPE = "application/octet-stream; charset=utf-8";
	private static final String CONTENT_DISPOSITION_KEY = "Content-Disposition";
	private static final String CONTENT_DISPOSITION_VALUE = "attachment; filename=%s";

	@Value("${base.upload.attach.path}")
	private String baseUploadPath;

	@Resource
	private KnowledgeArticleService articleService;

	@Resource
	private CategoryService categoryService;

	@Resource
	private AttachmentService attachmentService;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(baseUploadPath, "Base upload path not be null. ");
		File file = new File(baseUploadPath);
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
				file.mkdir();
			}
		} else {
			file.mkdirs();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public KnowledgeAttachment uploadAttachment(
			@RequestParam(value = "articleId", required = true) Long articleId,
			@RequestParam(value = "file", required = true) MultipartFile file) {

		KnowledgeArticle article = articleService.getById(articleId);
		KnowledgeCategory category = categoryService.getCategoryById(article
				.getCategoryId());

		File newFile = createNewFile(
				baseUploadPath + File.separator + category.getFullPath(),
				file.getOriginalFilename());

		KnowledgeAttachment attachment = createUnavailableAttach(articleId,
				file.getSize(), 0, file.getOriginalFilename(),
				newFile.getPath());

		if (newFile.exists()) {
			try {
				copyFile(file.getInputStream(), newFile);
				updateAvailableAttach(attachment.getId());
				return attachment;
			} catch (IOException e) {
			}
		}

		attachmentService.remove(attachment.getId());
		return new KnowledgeAttachment();
	}

	@ResponseBody
	@RequestMapping(value = "/{id}/count")
	public Integer countAttachment(@PathVariable("id") Long articleId) {
		return attachmentService.countByArticleId(articleId);
	}

	@ResponseBody
	@RequestMapping(value = "/{id}/list")
	public List<KnowledgeAttachment> listAttachments(
			@PathVariable("id") Long articleId) {
		return attachmentService.listByArticleId(articleId);
	}

	@ResponseBody
	@RequestMapping(value = "/remove/{id}")
	public String removeAttachment(@PathVariable("id") Long attachmentId) {
		KnowledgeAttachment attachment = attachmentService.get(attachmentId);
		if (attachment == null) {
			return Boolean.FALSE.toString();
		}

		boolean isDel = false;
		File file = new File(attachment.getUrl());
		if (file.exists()) {
			isDel = file.delete();
		}

		if (isDel) {
			attachmentService.remove(attachmentId);
		}

		return String.valueOf(isDel);
	}

	@RequestMapping(value = "/download/{id}")
	public void downloadAttachment(@PathVariable("id") Long attachmentId,
			HttpServletResponse response) {
		KnowledgeAttachment attachment = attachmentService.get(attachmentId);
		if (attachment == null) {
			return;
		}

		attachment.setDownloadCount(attachment.getDownloadCount() + 1);
		attachmentService.update(attachment);
		InputStream is = null;
		OutputStream os = null;

		File file = new File(attachment.getUrl());
		if (file.exists()) {
			try {
				response.reset();
				response.setContentType(CONTENT_TYPE);
				response.setHeader(CONTENT_DISPOSITION_KEY, String.format(
						CONTENT_DISPOSITION_VALUE, file.getName()));
				os = response.getOutputStream();
				os.write(FileUtils.readFileToByteArray(file));
				os.flush();
			} catch (Exception e) {

			} finally {
				close(is, os);
			}
		}
	}

	private KnowledgeAttachment createUnavailableAttach(Long articleId,
			Long size, Integer count, String name, String path) {
		KnowledgeAttachment attachment = new KnowledgeAttachment();
		attachment.setArticleId(articleId);
		attachment.setDownloadCount(0);
		attachment.setFileSize(size);
		attachment.setName(name);
		attachment.setUrl(path);
		attachment.setStatus("N");
		attachment.setCreateUser(SecurityHelper.getLoginUser().getUserId());
		attachment.setCreateDate(new Date());
		attachment = attachmentService.save(attachment);
		return attachment;
	}

	private void updateAvailableAttach(Long attachmentId) {
		KnowledgeAttachment attachment = attachmentService.get(attachmentId);
		attachment.setStatus("Y");
		attachmentService.update(attachment);
	}

	private File createNewFile(String filePath, String fileName) {
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(filePath + File.separator + fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
		}
		return file;
	}

	/**
	 * 写文件到本地
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	private void copyFile(InputStream is, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[16 * 1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			fos.write(buffer, 0, len);
			fos.flush();
		}
		try {
			fos.close();
		} catch (Exception e) {
		}
		try {
			is.close();
		} catch (Exception e) {
		}
	}

	private void close(InputStream is, OutputStream os) {
		if (is != null) {
			try {
				is.close();
			} catch (Exception e) {
			}
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
			}
		}
	}

}
