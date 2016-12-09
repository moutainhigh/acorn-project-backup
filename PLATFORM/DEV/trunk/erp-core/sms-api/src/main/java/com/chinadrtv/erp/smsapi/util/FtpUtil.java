package com.chinadrtv.erp.smsapi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public class FtpUtil {

	// 创建FTPClient对象
	private static FTPClient ftp = new FTPClient();
	private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 * 
	 */
	public synchronized static boolean uploadFile(String url, int port,
			String username, String password, String path, String filename,
			String local) {
		// 初始表示上传失败
		boolean success = false;
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(local));
			openConnection(url, port, username, password);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 转到指定上传目录
			ftp.changeWorkingDirectory(path);
			// 将上传文件存储到指定目录
			ftp.storeFile(filename, in);
			// 关闭输入流
			in.close();
			// 退出ftp
			ftp.logout();
			// 表示上传成功
			success = true;
		} catch (IOException e) {
			logger.error("登录FTP异常！" + filename);
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**
	 * 
	 * @Description: TODO
	 * @return void
	 * @throws
	 */
	public synchronized static boolean deleteFiles(String url, int port,
			String username, String password, String files, String path) {
		boolean success = false;
		try {
			openConnection(url, port, username, password);
			ftp.changeWorkingDirectory(path);
			ftp.deleteFile(files);
			logger.info(files + "删除成功！");
			ftp.logout();
			success = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(files + "删除异常！");
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error(files + "ftp释放异常！");
				}
			}
		}
		return success;
	}

	/**
	 * @return * ftp下载文件
	 * 
	 * @Description: TODO
	 * @param remote
	 * @param local
	 * @return
	 * @throws IOException
	 * @return boolean
	 * @throws
	 */
	public synchronized static boolean download(String url, int port,
			String username, String password, String remote, String local,
			String ftpFile, String localDir) throws IOException {
		ftp.enterLocalPassiveMode();
		boolean result = false;
		File f = new File(local);
		File x = new File(localDir);
		try {
			if (x.mkdir()) {
				logger.info("文件夹创建成功！");
			} else {
				logger.info("文件夹已存在！");
			}
			int reply;
			// 连接FTP服务器
			ftp.setDefaultTimeout(60000);
			ftp.connect(url);
			// 登录ftp
			ftp.login(username, password);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();

			ftp.changeWorkingDirectory(ftpFile);
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("登录FTP服务失败！" + ftpFile);
				return result;
			}
			logger.info(ftpFile + "ftp 登陆成功！");
			if (f.exists()) {
				OutputStream out = new FileOutputStream(f, true);
				ftp.setRestartOffset(f.length());
				result = ftp.retrieveFile(remote, out);
				out.close();
			} else {
				OutputStream out = new FileOutputStream(f);
				result = ftp.retrieveFile(remote, out);
				// out.flush();
				out.close();
			}
			ftp.logout();
			result = true;
		} catch (Exception e) {
			logger.error("退出失败" + e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error("ftp释放异常！");
				}
			}
		}
		return result;
	}

	public static HttpHeaders createHttpHeader() {
		HttpHeaders requestHeaders = new HttpHeaders();
		// Accept
		requestHeaders.set("Accept", "application/xml");
		requestHeaders.set("Accept-Charset", "utf-8");
		return requestHeaders;
	}

	public static Boolean startDown(String ftpFile, String localBaseDir) {
		File newLocalTemp = new File(localBaseDir);
		// 创建临时文件夹
		newLocalTemp.mkdir();
		Boolean flag = false;
		Date lastDate = null;
		Date nowdate = new Date();
		Calendar nowcalendar = Calendar.getInstance();
		Calendar oldcalendar = Calendar.getInstance();
		long timeNow = 0l;
		long timeOld = 0l;
		long times = 0l;
		try {
			openConnection("", 12, "", "");
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			FTPFile[] files = null;
			boolean changedir = ftp.changeWorkingDirectory(ftpFile);
			List listzie = new ArrayList();
			Long timesDo = Long.valueOf(PropertiesUtil.getTimesDo());
			if (changedir) {
				files = ftp.listFiles();
				for (int i = 0; i < files.length; i++) {
					lastDate = files[i].getTimestamp().getTime();
					nowcalendar.setTime(nowdate);
					oldcalendar.setTime(lastDate);
					timeNow = nowcalendar.getTimeInMillis();
					timeOld = oldcalendar.getTimeInMillis();
					times = (timeNow - timeOld) / (1000 * 60 * 60);// 化为小时
					logger.info("old" + timeOld + "new" + timeNow + "times"
							+ times);
					if (times > timesDo) {
						downloadFile(files[i], localBaseDir, ftpFile);
						listzie.add(i);
					}
				}
				if (listzie.size() > 0) {
					flag = true;
				}
				// for (int i = 0; i < files.length; i++) {
				// downloadFile(files[i], localBaseDir, ftpFile);
				// }
			}
			ftp.logout();
		} catch (Exception e) {
			logger.error("下载文件异常！" + e.getMessage());
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error("ftp释放异常！" + ioe.getMessage());
				}
			}
		}
		return flag;
	}

	private static void downloadFile(FTPFile ftpFile, String relativeLocalPath,
			String relativeRemotePath) {
		Date lastDate = null;
		Date nowdate = new Date();
		Calendar nowcalendar = Calendar.getInstance();
		Calendar oldcalendar = Calendar.getInstance();
		if (ftpFile.isFile()) {// down file
			if (ftpFile.getName().indexOf("?") == -1) {
				OutputStream outputStream = null;
				try {
					outputStream = new FileOutputStream(relativeLocalPath
							+ ftpFile.getName());
					ftp.retrieveFile(ftpFile.getName(), outputStream);
					// ftp.dele(ftpFile.getName());
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					System.err.println(e);
				} finally {
					try {
						if (outputStream != null)
							outputStream.close();
					} catch (IOException e) {
						System.out.println("ShowasaFile");
					}
				}
			}
		} else { // deal dirctory
			String newlocalRelatePath = relativeLocalPath + ftpFile.getName();
			String newRemote = new String(relativeRemotePath
					+ ftpFile.getName().toString());

			File fl = new File(newlocalRelatePath);
			if (!fl.exists()) {
				fl.mkdirs();
			}
			try {
				newlocalRelatePath = newlocalRelatePath + '/';
				newRemote = newRemote + "/";
				String currentWorkDir = ftpFile.getName().toString();
				// enter relative workdirectory
				boolean changedir = ftp.changeWorkingDirectory(currentWorkDir);
				long timeNow = 0l;
				long timeOld = 0l;
				long times = 0l;
				if (changedir) {
					FTPFile[] files = null;
					files = ftp.listFiles();
					for (int i = 0; i < files.length; i++) {
						lastDate = files[i].getTimestamp().getTime();
						nowcalendar.setTime(nowdate);
						oldcalendar.setTime(lastDate);
						timeNow = nowcalendar.getTimeInMillis();
						timeOld = oldcalendar.getTimeInMillis();
						times = (timeNow - timeOld) / (1000 * 60 * 60);// 化为小时
						if (times > 2l) {
							downloadFile(files[i], newlocalRelatePath,
									newRemote);
						}
					}
				}
				// return parent directory
				if (changedir)
					ftp.changeToParentDirectory();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * 打开连接
	 * 
	 * @Description: TODO
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 * @return Boolean
	 * @throws
	 */
	private static Boolean openConnection(String url, int port,
			String username, String password) {
		boolean success = false;
		int reply;
		try {
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.setDefaultTimeout(60000);
			ftp.connect(PropertiesUtil.getFtpUrl());
			// 登录ftp
			ftp.login(PropertiesUtil.getFtpName(), PropertiesUtil.getFtpPass());
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("登录FTP服务失败！");
				return success;
			}
			success = true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("登陆失败" + e.getMessage() + e.getStackTrace().toString());
		}

		return success;
	}

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 * 
	 */
	public synchronized static boolean uploadFiles(String url, int port,
			String username, String password, String path, String local,
			File[] files) {
		// 初始表示上传失败
		boolean success = false;
		FileInputStream in = null;
		String filename = "";
		try {
			openConnection(url, port, username, password);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 转到指定上传目录
			ftp.changeWorkingDirectory(path);
			for (File file : files) {
				in = new FileInputStream(new File(file.getAbsolutePath()));
				filename = file.getName();
				// 将上传文件存储到指定目录
				ftp.storeFile(filename, in);
				logger.info("文件上传成功" + filename);
				// 关闭输入流
			}
			in.close();
			// 退出ftp
			ftp.logout();
			// 表示上传成功
			success = true;
		} catch (IOException e) {
			logger.error("登录FTP异常！");
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	public static void main(String[] args) {
		try {
			// FtpUtil.startDown("/sms/status/", "d:\\acorn\\sms\\status");
			FtpUtil.uploadFile("192.168.93.111", 21, "smsftp", "smsftp@66!",
					"/sms/send/", "/sms/send/1374142564917.log",
					"d:\\acorn\\sms\\send\\20130715241245282013071524124528000000001.zip");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}