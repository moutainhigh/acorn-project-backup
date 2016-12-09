1.IDE中install过程中test出现乱码的解决方法。
	 使用配置方式启动install，在common中设置encogind为GBK

2.部署到tomcat中，出现中乱码的解决方法。
	方法一：
	修改server.xml中的Connector
	<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" URIEncoding="UTF-8"/>
	这里指定了get时候的数据编码 
	方法二：
	如果使用了拦截器，则乱码只会出现在get方法中，那么就要规范get发送数据时不能有中文或者进行转码，
	或者使用post方法提交。