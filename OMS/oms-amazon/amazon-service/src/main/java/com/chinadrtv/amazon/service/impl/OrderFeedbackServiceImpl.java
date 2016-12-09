package com.chinadrtv.amazon.service.impl;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.*;
import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.common.dal.model.TradeFeedback;
import com.chinadrtv.amazon.model.TradeResultList;
import com.chinadrtv.amazon.service.OrderFeedbackService;
import org.milyn.Smooks;
import org.milyn.payload.JavaResult;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderFeedbackServiceImpl implements OrderFeedbackService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderFeedbackServiceImpl.class);

    private Smooks smooks;

    @PostConstruct
    private void init() throws Exception{
        smooks = new Smooks("smooks/feedback-smooks-config.xml");
    }

    @Override
    public boolean updateTradeStatus(AmazonOrderConfig amazonOrderConfig, List<TradeFeedback> tradefeedbackList, StringBuffer feedSubmissionId) {
        boolean b = false;
        String requestStr = getOrderFulfillmentMsg(amazonOrderConfig, tradefeedbackList);

        MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
        config.setServiceURL(amazonOrderConfig.getFeedbackUrl());
        MarketplaceWebService service = new MarketplaceWebServiceClient(
                amazonOrderConfig.getAccessKeyId(),amazonOrderConfig.getSecretAccessKey(),amazonOrderConfig.getApplicationName(),amazonOrderConfig.getApplicationVersion(),config);
                // appKey, appSecret, appName, appVersion, config);
        SubmitFeedRequest request = new SubmitFeedRequest();
        request.setMerchant(amazonOrderConfig.getSellerId());// this.merchantId);
        IdList marketplaces = new IdList(Arrays.asList(amazonOrderConfig.getMarketplaceId()));//this.marketplaces));
        request.setMarketplaceIdList(marketplaces);
        request.setFeedType("_POST_ORDER_FULFILLMENT_DATA_");

        String md5Str = computeContentMD5Header(new ByteArrayInputStream(requestStr.getBytes()));
        request.setFeedContent(new ByteArrayInputStream(requestStr.getBytes()));
        request.setContentMD5(md5Str);

        try {
            SubmitFeedResponse response = service.submitFeed(request);
            if (response.isSetSubmitFeedResult()) {
                SubmitFeedResult submitFeedResult = response.getSubmitFeedResult();
                if (submitFeedResult.isSetFeedSubmissionInfo()) {
                    FeedSubmissionInfo feedSubmissionInfo = submitFeedResult.getFeedSubmissionInfo();
                    if (feedSubmissionInfo.isSetFeedSubmissionId()) {
                        feedSubmissionId.append(feedSubmissionInfo.getFeedSubmissionId());
                    }
                }
                b = true;
            }
        } catch (MarketplaceWebServiceException e) {
            logger.error("feed back error:",e);
        }
        return b;
    }

    @Override
    public TradeResultList getTradeResultInfo(AmazonOrderConfig amazonOrderConfig, String feedSubmissionId) {
        MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
        config.setServiceURL(amazonOrderConfig.getFeedbackUrl());
        MarketplaceWebService service = new MarketplaceWebServiceClient(
                amazonOrderConfig.getAccessKeyId(),amazonOrderConfig.getSecretAccessKey(),amazonOrderConfig.getApplicationName(),amazonOrderConfig.getApplicationVersion(),config);
                //appKey, appSecret, appName, appVersion, config);

        GetFeedSubmissionResultRequest request = new GetFeedSubmissionResultRequest();
        request.setMerchant(amazonOrderConfig.getSellerId());// this.merchantId);
        request.setFeedSubmissionId(feedSubmissionId);

        ByteArrayOutputStream processingResult = new ByteArrayOutputStream();
        request.setFeedSubmissionResultOutputStream( processingResult );

        StringBuffer stringBuffer=new StringBuffer();
        try {
            GetFeedSubmissionResultResponse response = service.getFeedSubmissionResult(request);
            stringBuffer.append(new String(processingResult.toByteArray(),"utf-8"));
        } catch (MarketplaceWebServiceException e) {
            logger.error("feed back result id:"+ feedSubmissionId);
            logger.error("get feed back result error:", e);
            return null;
        } catch (UnsupportedEncodingException unExp)
        {
            logger.error("unsupported exp:",unExp);
        }
        catch (Exception exp)
        {
            logger.error("exp error:"+exp.getMessage(), exp);
            return null;
        }

        //transform
        try{
            return transformXMLToPOJO(stringBuffer);
        }catch (Exception exp)
        {
            logger.error("transform feed back result error id:"+feedSubmissionId);
            logger.error("result data:"+stringBuffer);
            logger.error("exp info:"+exp.getMessage(), exp);
            return null;
        }

    }

    public String getOrderFulfillmentMsg(AmazonOrderConfig amazonOrderConfig,List<TradeFeedback> tradefeedbacks) {
        StringBuffer sb = new StringBuffer();
        int i = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String formatted = "";

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<AmazonEnvelope xsi:noNamespaceSchemaLocation=\"amzn-envelope.xsd\"\n");
        sb.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n");
        sb.append("<Header>");
        sb.append("<DocumentVersion>1.02</DocumentVersion>");
        sb.append(String.format("<MerchantIdentifier>%s</MerchantIdentifier>\n", amazonOrderConfig.getSellerId()));//  merchantId));
        sb.append("</Header>");
        sb.append("<MessageType>OrderFulfillment</MessageType>");
        for (TradeFeedback tradeFeedback : tradefeedbacks) {
            sb.append("<Message>");
            sb.append(String.format("<MessageID>%d</MessageID>", i));
            sb.append("<OperationType>Update</OperationType>");
            sb.append("<OrderFulfillment>");
            sb.append(String.format("<AmazonOrderID>%s</AmazonOrderID>", tradeFeedback.getTradeId()));
            formatted = sdf.format(tradeFeedback.getOutDate());
            formatted = formatted.substring(0, 22) + ":" + formatted.substring(22);
            sb.append(String.format("<FulfillmentDate>%s</FulfillmentDate>", formatted));
            sb.append("<FulfillmentData>");
            sb.append(String.format("<CarrierName>%s</CarrierName>", tradeFeedback.getCompanyName()));
            sb.append("<ShippingMethod>快速</ShippingMethod>");
            sb.append(String.format("<ShipperTrackingNumber>%s</ShipperTrackingNumber>", tradeFeedback.getMailId()));
            sb.append("</FulfillmentData>");
            sb.append("</OrderFulfillment>");
            sb.append("</Message>");
            i++ ;
        }
        sb.append("</AmazonEnvelope>");
        return sb.toString();
    }

    public String computeContentMD5Header(InputStream inputStream) {
        // Consume the stream to compute the MD5 as a side effect.
        DigestInputStream s;
        try {
            s = new DigestInputStream(inputStream, MessageDigest.getInstance("MD5"));
            // drain the buffer, as the digest is computed as a side-effect
            byte[] buffer = new byte[8192];
            while (s.read(buffer) > 0)
                ;
            return new String(
                    org.apache.commons.codec.binary.Base64.encodeBase64(s
                            .getMessageDigest().digest()), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            logger.error("md5 algorithm error:",e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("md5 I/O error:",e);
            throw new RuntimeException(e);
        }
    }


    public TradeResultList transformXMLToPOJO(StringBuffer stringBuffer){
        Source source = new StreamSource(new StringReader(stringBuffer.toString()));

        JavaResult javaResult = new JavaResult();

        smooks.filterSource(source, javaResult);

        return (TradeResultList) javaResult.getBean("tradeResultList");
    }

}
