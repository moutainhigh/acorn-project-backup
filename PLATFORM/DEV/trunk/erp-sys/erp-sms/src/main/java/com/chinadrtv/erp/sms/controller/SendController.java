package com.chinadrtv.erp.sms.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.sms.dto.SmsSendDto;
import com.chinadrtv.erp.sms.dto.SmsTemplateDto;
import com.chinadrtv.erp.sms.jms.QueueMessageProducer;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dto.Response;
import com.chinadrtv.erp.smsapi.dto.SendRequest;
import com.chinadrtv.erp.smsapi.dto.SingleResponse;
import com.chinadrtv.erp.smsapi.dto.TimeScope;
import com.chinadrtv.erp.smsapi.dto.Variables;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;
import com.chinadrtv.erp.smsapi.service.GroupSmsSendService;
import com.chinadrtv.erp.smsapi.service.SingleSmsSendService;
import com.chinadrtv.erp.smsapi.service.SmsAnswerService;
import com.chinadrtv.erp.smsapi.service.SmsSendStatusService;
import com.chinadrtv.erp.smsapi.service.SmsTemplatesService;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.DeCompressBook;
import com.chinadrtv.erp.smsapi.util.FileUtil;
import com.chinadrtv.erp.smsapi.util.FtpUtil;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.chinadrtv.erp.smsapi.util.StringUtil;
import com.chinadrtv.erp.smsapi.util.UUidUtil;
import com.chinadrtv.sms.model.AssignXml;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/***
 * 
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-3-6 下午3:05:05
 * 
 */

@Controller
@RequestMapping("/smsService")
public class SendController {
	private static final Logger logger = LoggerFactory
			.getLogger(SendController.class);
	@Autowired
	GroupSmsSendService groupSmsSendService;
	@Autowired
	SingleSmsSendService singleSmsSendService;
	@Autowired
	SmsSendStatusService smsSendStatusService;
	@Autowired
	SmsAnswerService smsAnswerService;
	@Autowired
	SmsTemplatesService smsTemplatesService;
	@Autowired
	private QueueMessageProducer queueMessageProducer;

	@RequestMapping(value = "/batchFeedBakes", method = RequestMethod.GET)
	public String autoFeedBack() {
		String type = PropertiesUtil.getTypes();
		String files = "";
		String path = "";
		String ftpPath = "";
		Boolean downFlag = false;
		String newlocals = "" + System.currentTimeMillis();
		String zipPath = "";
		if (type.equals("1")) {
			files = PropertiesUtil.getStatusctlFile() + newlocals + "/";
			path = PropertiesUtil.getStatusBakeFile();
			ftpPath = PropertiesUtil.getStatusFtp();
			// zipPath = files + newlocals + "/";
		} else {
			files = PropertiesUtil.getFeedBackctlFile() + newlocals + "/";
			path = PropertiesUtil.getFeedbackBakeFile();
			ftpPath = PropertiesUtil.getFeedbackFile();
			// zipPath = files + newlocals + "/";

		}
		// 从ftp 上下载文件
		downFlag = FtpUtil.startDown(ftpPath, files);
		logger.info("从ftp批量下载文件成功" + downFlag);
		if (StringUtil.isNullOrBank(type)) {
			return "type  is null";
		}
		File myDir = new File(files);
		File[] contents = myDir.listFiles();
		File zipDir = null;
		File[] zipContents = null;
		String batchid = "";
		String filename = "";
		String localPath = "";
		Boolean unzipFlag = false;
		DeCompressBook dec = new DeCompressBook();
		if (downFlag == true) {
			for (File file : contents) {
				filename = file.getName();
				localPath = file.getAbsolutePath();
				try {
					zipPath = files + "" + System.currentTimeMillis() + "/";
					unzipFlag = dec.unZip(localPath, zipPath);
					if (unzipFlag == true) {
						zipDir = new File(zipPath);
						zipContents = zipDir.listFiles();
						for (File zip : zipContents) {
							batchid = FileUtil.readBatchid(zip
									.getAbsolutePath());
							filename = zip.getName();
							localPath = zip.getAbsolutePath();
							if (!StringUtil.isNullOrBank(batchid)) {
								smsSendStatusService.groupSendStatusByFile(
										localPath, batchid, type, files);
								FileUtil.deleteFile(localPath);
							}
						}
					} else {
						logger.error("解压缩文件异常" + zipPath);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					logger.error("解压缩失败" + localPath + e1);
				}
				logger.info("处理数据成功" + filename + batchid);
			}
			FtpUtil.uploadFiles("", 0, "", "", path, localPath, contents);
			logger.info("把文件上传ftp成功");
			if (unzipFlag == true) {
				for (File file : contents) {
					FileUtil.deleteFile(file.getAbsolutePath());
					logger.info("删除本地文件" + filename + batchid);
				}
			}
		}
		return "success";
	}

	/***
	 * 批量状态回复
	 * 
	 * @Description: TODO
	 * @param from
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/batchFeedBake", method = RequestMethod.POST)
	public void main(@RequestBody String xml, HttpServletResponse response)
			throws IOException {
		SingleResponse singleResponse = new SingleResponse();
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("reponse", SingleResponse.class);
		try {
			logger.info("调用状态回复接口xml" + xml);
			AssignXml assignXml = new AssignXml();
			assignXml.setXml(xml);
			queueMessageProducer.send(assignXml);
			singleResponse.setStatus("1");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			singleResponse.setErrorCode("1000");
			singleResponse.setErrorMsg("接口 成功");
			logger.info(xstream.toXML(singleResponse));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用状态回复接口xml" + xml + e);
			singleResponse.setStatus("0");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			singleResponse.setErrorCode("1001");
			singleResponse.setErrorMsg("接口 异常");
			logger.error(xstream.toXML(singleResponse) + e);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(xstream.toXML(singleResponse));
	}

	/***
	 * 单条短信回复
	 * 
	 * @Description: TODO
	 * @param user
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public void rest(@RequestBody String xml, HttpServletResponse response)
			throws IOException {
		SingleResponse singleResponse = new SingleResponse();
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("reponse", SingleResponse.class);
		smsAnswerService.singleSmsAnswer(xml);
		try {
			logger.info("调用状态回复接口xml" + xml);
			AssignXml assignXml = new AssignXml();
			assignXml.setXml(xml);
			singleResponse.setStatus("1");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			singleResponse.setErrorCode("1000");
			singleResponse.setErrorMsg("接口 成功");
			logger.info(xstream.toXML(singleResponse));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用状态回复接口xml" + xml + e);
			singleResponse.setStatus("0");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			singleResponse.setErrorCode("1001");
			singleResponse.setErrorMsg("接口 异常");
			logger.error(xstream.toXML(singleResponse) + e);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(xstream.toXML(singleResponse));
	}

	/***
	 * 单条短信状态回复
	 * 
	 * @Description: TODO
	 * @param user
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/feedBake", method = RequestMethod.POST)
	public void feedBake(@RequestBody String xml, HttpServletResponse response)
			throws IOException {
		SingleResponse singleResponse = new SingleResponse();
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("reponse", SingleResponse.class);
		logger.info("调用状态回复接口xml" + xml);
		try {
			String xmls = smsSendStatusService.singleSendStatus(xml);
			singleResponse.setStatus("1");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			singleResponse.setErrorCode("1000");
			singleResponse.setErrorMsg("接口 成功");
			logger.info(xstream.toXML(singleResponse));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用状态回复接口xml" + xml + e);
			singleResponse.setStatus("0");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			singleResponse.setErrorCode("1001");
			singleResponse.setErrorMsg("接口 异常");
			logger.error(xstream.toXML(singleResponse) + e);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(xstream.toXML(singleResponse));
	}

	/**
	 * 单条短信 发送 json 格式
	 * 
	 * @Description: TODO
	 * @return
	 * @return Map<Stirng,Object>
	 * @throws@RequestBody
	 */
	@RequestMapping(value = "/singleSmsSend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> singleJson(@RequestBody SmsSendDto smsSend,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 生成uuid
		String uuid = UUidUtil.getUUid();
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		List list = new ArrayList();
		logger.info("uuid=" + uuid + "=creator=" + smsSend.getCreator()
				+ "=customerId=" + smsSend.getCustomerId() + "=department="
				+ smsSend.getDepartment() + "=mobile=" + smsSend.getMobile()
				+ "=smsContent=" + smsSend.getSmsContent() + "=source="
				+ smsSend.getSource());
		singleSmsSendService.singleSend("Y", "", "", listmap, 9l, "N", "Y",
				"橡果国际", smsSend.getCreator(), smsSend.getSmsContent(), list,
				smsSend.getSource(), smsSend.getDepartment(),
				smsSend.getMobile(), smsSend.getCustomerId(),
				DateTimeUtil.sim3.format(new Date()), uuid, "", "", "");
		result.put("uuid", uuid);
		return result;
	}

	/***
	 * 根据uuid 查询短信
	 * 
	 * @Description: TODO
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "/getSmsByUuid/{uuid}")
	@ResponseBody
	public Map<String, Object> getSmsByUuid(@PathVariable String uuid) {
		Map<String, Object> result = new HashMap<String, Object>();
		SmsSendDetail sendDetail = null;
		try {
			sendDetail = singleSmsSendService.getByUuid(uuid);
			if (sendDetail.getReceiveStatus() != null
					&& !("10").equals(sendDetail.getReceiveStatus())) {
				if (sendDetail.getReceiveStatus().equals("1")) {
					result.put("smsStatus", "提交成功");
				} else {
					result.put("smsStatus", "提交失败");
				}
			}
			if (sendDetail.getFeedbackStatus() != null
					&& !("10").equals(sendDetail.getFeedbackStatus())) {
				if (sendDetail.getFeedbackStatus().equals("1")) {
					result.put("smsStatus", "发送成功");
				} else {
					result.put("smsStatus", "发送失败");
				}
			}
			if (sendDetail.getSmsStopStatus() != null
					&& !("10").equals(sendDetail.getSmsStopStatus())) {
				if (sendDetail.getSmsStopStatus().equals("-1")) {
					result.put("smsStatus", "短信已停止");
				} else {
					result.put("smsStatus", "短信超时未发送");
				}
			}
			if (("10").equals(sendDetail.getSmsStopStatus())
					&& ("10").equals(sendDetail.getFeedbackStatus())
					&& ("10").equals(sendDetail.getReceiveStatus())) {
				result.put("smsStatus", "无结果");
			}
			result.put("smsContent", sendDetail.getContent());
		} catch (Exception e) {
			result.put("smsContent", null);
			result.put("smsStatus", null);
			logger.error("uuid:" + uuid + "不存在" + e);
		}
		return result;
	}

	/***
	 * 根据部门和主题查询模板
	 * 
	 * @Description: TODO
	 * @param smsTemplateDto
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "/getTemplates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTemplates(
			@RequestBody SmsTemplateDto smsTemplateDto,
			DataGridModel dataGridModel) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<com.chinadrtv.erp.smsapi.model.SmsTemplate> list = smsTemplatesService
				.getSmsTemplateList(smsTemplateDto.getDeptid(),
						smsTemplateDto.getThemeTemplate());
		result.put("template", list);
		return result;
	}

	/**
	 * 单条短信 xml 格式
	 * 
	 * @Description: TODO
	 * @param xml
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/singleSend", method = RequestMethod.POST)
	public void single(@RequestBody String xml, HttpServletResponse response) {
		Response responses = new Response();
		String uuid = "";
		try {
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("sendRequest", SendRequest.class);
			// xStream.registerConverter(new ChannelConverter());
			xStream.autodetectAnnotations(true);
			SendRequest sendRequest = (SendRequest) xStream.fromXML(xml);
			List list = new ArrayList();
			List<Variables> listv = sendRequest.getMessage().getContent()
					.getVariables();
			if (listv != null && !listv.isEmpty()) {
				for (Variables temp : listv) {
					list.add(temp.getValue());
				}
			}
			String startTime = "";
			String endTime = "";
			String connectId = "";
			if (sendRequest.getSla().getDateScope() != null
					&& sendRequest.getSla().getDateScope().getStartTime() != null
					&& sendRequest.getSla().getDateScope().getEndTime() != null) {
				startTime = sendRequest.getSla().getDateScope().getStartTime();
				endTime = sendRequest.getSla().getDateScope().getEndTime();

			}
			String template = sendRequest.getMessage().getContent()
					.getTemplate();
			String toString = sendRequest.getMessage().getTo();
			String departmentString = sendRequest.getMessage().getDepartment();
			String source = sendRequest.getMessage().getSource();
			String timestamp = sendRequest.getMessage().getTimestamp();
			Long priority = Long.valueOf(sendRequest.getSla().getPriority());
			String isReply = sendRequest.getSla().getIsReply();
			String realTime = sendRequest.getSla().getRealTime();
			String signiture = sendRequest.getSla().getSigniture();
			if (sendRequest.getMessage().getConnectId() != null) {
				connectId = sendRequest.getMessage().getConnectId();
			}
			String allowChannel = sendRequest.getSla().getAllowChangeChannel();
			String creator = sendRequest.getMessage().getCreator();
			List<TimeScope> listst = new ArrayList<TimeScope>();
			List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
			if (sendRequest.getSla().getDateScope() != null) {
				listst = (List<TimeScope>) sendRequest.getSla().getDateScope()
						.getTimeScopes();
				for (int i = 0; i < listst.size(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("time", listst.get(i).getTime().toString());
					map.put("iops", listst.get(i).getIops().toString());
					listmap.add(map);
				}
			}
			// 生成uuid
			uuid = UUidUtil.getUUid();
			singleSmsSendService.singleSend(allowChannel, startTime, endTime,
					listmap, priority, isReply, realTime, signiture, creator,
					template, list, source, departmentString, toString,
					connectId, timestamp, uuid, "", "", "");
			response.setContentType("application/xml; charset=UTF-8");
			response.getWriter()
					.print("<?xml version='1.0' encoding='UTF-8'?><response><status>1</status></response>");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("单条短信发送异常 uuid " + uuid + "=e：" + e.getMessage());
			try {
				response.getWriter()
						.print("<?xml version='1.0' encoding='UTF-8'?><response><status>0</status></response>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("单条短信发送异常 uuid " + uuid + "=e：" + e1.getMessage());
			}
		}

	}

	/***
	 * 多条短信
	 * 
	 * @Description: TODO
	 * @param xml
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/groupSend", method = RequestMethod.GET)
	public ModelAndView group(String batchid) throws IOException {
		ModelAndView model = new ModelAndView("xStreamMarshallingView");
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", "01:00:00-10:30:00");
		map.put("iops", "1");
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("time", "10:30:00-10:35:00");
		maps.put("iops", "1");
		listmap.add(map);
		listmap.add(maps);
		List list = new ArrayList();
		list.add("ok");
		list.add("yes");
		Map channelmap = new HashMap();

		// groupSmsSendService.groupSend(batchid, "Y", new Date(), new Date(),
		// listmap, 2l, "Y", "Y", "橡果国际", "2", "{t1}你好{t2}", "markting",
		// channelmap);
		return model;
	}
}