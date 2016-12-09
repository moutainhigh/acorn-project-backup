package com.chinadrtv.erp.task.util;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.web.filter.GenericFilterBean;

/**
 * 类扫描工具
 * @author zhangguosheng
 */
public class ClassScanUtils {
	
	private static final String CLASS_EXT = ".class";
	private static final String JAR_FILE_EXT = ".jar";
	private static final String ERROR_MESSAGE = "packageName can't be null";
	private static final String DOT = ".";
	private static final String ZIP_SLASH = "/";
	private static final String BLACK = "";
	private static final ClassFilter NULL_CLASS_FILTER = null;
	
	/**(1) 文件过滤器，过滤掉不需要的文件**/
	private static FileFilter fileFilter = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return isClass(pathname.getName()) || isDirectory(pathname) || isJarFile(pathname.getName());
		}
	};
	
	/**
	 * 如果packageName为空，就抛出空指针异常。</br>
	 * (本工具类有一个bug，如果扫描文件时需要一个包路径为截取字符串的条件，现在还没有修复,所以加上该条件)
	 * @param packageName
	 */
	private static void ckeckNullPackageName(String packageName) {
		if (packageName == null || packageName.trim().length() == 0)
			throw new NullPointerException(ERROR_MESSAGE);
	}

	/**
	 * 扫面包路径下满足class过滤器条件的所有class文件，</br> 如果包路径为 com.abs + A.class 
	 * 但是输入 abs 会产生classNotFoundException</br> 因为className 应该为 com.abs.A 现在却成为
	 * abs.A,此工具类对该异常进行忽略处理,有可能是一个不完善的地方，以后需要进行修改</br>
	 * @param packageName  包路径 com | com. | com.abs | com.abs.
	 * @param classFilter  class过滤器，过滤掉不需要的class
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static Set<Class<?>> scanPackage(String packageName,ClassFilter classFilter){
		ckeckNullPackageName(packageName);
		Set<Class<?>> classes = new HashSet<Class<?>>();
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> resources = null;
		try {
			resources = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(resources!=null){
			while (resources.hasMoreElements()){
				URL url = resources.nextElement();
				classes.addAll(fillClasses(url, packageName, classFilter));
			}
		}
		return classes;
	}

	/**
	 * 扫面改包路径下所有class文件
	 * @param packageName 包路径 com | com. | com.abs | com.abs.
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static Set<Class<?>> scanPackage(String packageName) throws IOException, ClassNotFoundException {
		return scanPackage(packageName, NULL_CLASS_FILTER);
	}

	/**
	 * 填充满足条件的class 填充到 classes
	 * @param file          类路径下的文件
	 * @param packageName   需要扫面的包名
	 * @param classFilter   class过滤器
	 * @param classes       List 集合
	 * @throws ClassNotFoundException 
	 */
	private static Set<Class<?>> fillClasses(URL url, String packageName, ClassFilter classFilter) {
		Set<Class<?>> clazzes = new HashSet<Class<?>>();
        String protocol = url.getProtocol();
        File file = null;
        if ("file".equals(protocol)) {
        	file = new File(url.getFile());
        	clazzes.addAll(processDirectory(file, packageName, classFilter));
        } else if ("jar".equals(protocol)) {
        	String path = url.getFile();
        	if(path.startsWith("file:/")){
        		path = path.substring(6);
        	}
        	int index = path.indexOf(JAR_FILE_EXT);
        	file = new File(path.substring(0, index) + JAR_FILE_EXT);
        	clazzes.addAll(processJarFile(file, packageName, classFilter));
        }
        return clazzes;
	}

	/**
	 * 处理如果为目录的情况,需要递归调用 fillClasses方法
	 * @param directory
	 * @param packageName
	 * @param classFilter
	 * @param classes
	 * @throws ClassNotFoundException 
	 */
	private static Set<Class<?>> processDirectory(File directory, String packageName, ClassFilter classFilter) {
		Set<Class<?>> clazzes = new HashSet<Class<?>>();
		for (File file : directory.listFiles(fileFilter)) {
			if(file.isDirectory()){
				clazzes.addAll(processDirectory(file, packageName, classFilter));
			}else if(file.isFile()){
				Class<?> clazz = processClassFile(file, packageName, classFilter);
				if(clazz!=null){
					clazzes.add(clazz);
				}
			}
		}
		return clazzes;
	}

	/**
	 * 处理为class文件的情况,填充满足条件的class 到 classes
	 * @param file
	 * @param packageName
	 * @param classFilter
	 * @param classes
	 * @throws ClassNotFoundException 
	 */
	private static Class<?> processClassFile(File file, String packageName, ClassFilter classFilter) {
		Class<?> clazz = null;
		String filePathWithDot = file.getAbsolutePath().replace(File.separator, DOT);
		int subIndex = -1;
		if ((subIndex = filePathWithDot.indexOf(packageName)) != -1) {
			String className = filePathWithDot.substring(subIndex).replace(CLASS_EXT, BLACK);
			clazz = filterClass(className, classFilter);
		}
		return clazz;
	}

	/**
	 * 处理为jar文件的情况，填充满足条件的class 到 classes
	 * @param file
	 * @param packageName
	 * @param classFilter
	 * @param classes
	 */
	private static Set<Class<?>> processJarFile(File file, String packageName, ClassFilter classFilter) {
		Set<Class<?>> clazzes = new HashSet<Class<?>>();
		try {
			for (ZipEntry entry : Collections.list(new ZipFile(file).entries())) {
				if (isClass(entry.getName())) {
					String className = entry.getName();
					className = className.substring(0, className.length()-6);
					className = className.replace(ZIP_SLASH, DOT);
					Class<?> clazz = filterClass(className, classFilter);
					if(clazz!=null){
						clazzes.add(clazz);
					}
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return clazzes;
	}

	/**
	 * 填充class 到 classes
	 * @param className
	 * @param packageName
	 * @param classes
	 * @param classFilter
	 * @return 
	 * @throws ClassNotFoundException 
	 */
	private static Class<?> filterClass(String className, ClassFilter classFilter) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className, Boolean.FALSE, ClassScanUtils.class.getClassLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (clazz!=null && (classFilter == NULL_CLASS_FILTER || classFilter.accept(clazz))) {
			return clazz;
		}
		return null;
	}

	private static boolean isClass(String fileName) {
		return fileName.endsWith(CLASS_EXT);
	}

	private static boolean isDirectory(File file) {
		return file.isDirectory();
	}

	private static boolean isJarFile(String fileName) {
		return fileName.endsWith(JAR_FILE_EXT);
	}
	
	public static void main(String[] args){
		ClassFilter filter = new ClassFilter() {
			@Override
			public boolean accept(Class<?> clazz) {
				return !Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()) && Modifier.isPublic(clazz.getModifiers()) && !Modifier.isStatic(clazz.getModifiers()) && GenericFilterBean.class.isAssignableFrom(clazz);
			}
		};
		for (Class<?> clazz : ClassScanUtils.scanPackage("org.springframework.security.web", filter)) {
			System.out.println(clazz);
		}
	}

}
