package com.chinadrtv.oms.paipai.oauth;

import java.io.IOException;

import org.junit.Test;

public class TestOpenApiV3 {

	public static void main(String args[]) {
		TestOpenApiV3 v3 = new TestOpenApiV3();
		v3.testInvoke();
	}
	
	@Test
	public void testInvoke() {

		PaiPaiOpenApiOauth api = PaiPaiOpenApiOauth.createInvokeApi("/item/modifyItemPic.xhtml");

		api.setAppOAuthID("700042973");
		api.setAppOAuthkey("hdUMwmU4P5jQtHpC");
		api.setAccessToken("ad39b7dbd59b87cda827223c0e520d6f");
		api.setUin(1280863473);
		api.setHost("http://api.paipai.com");

		FileItem file = new FileItem("D:/temp/Black_And_White.jpg");
		api.addFileParam("pic", file);

		api.addParam("itemCode", "DA0B40910000000004010000302B9D0335");
		api.addParam("sellerUin", "1280863473");
		try {
			PaiPaiOpenApiOauth.invoke(api);
		} catch (OpenApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
