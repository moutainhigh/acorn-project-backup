package com.chinadrtv.acorn.service;

import com.chinadrtv.acorn.bean.CheckResult;
import com.chinadrtv.acorn.bean.FeedbackInfo;
import com.chinadrtv.model.iagent.MailStatusHis;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderLogisticsFeedbackService {
    CheckResult checkFeedbackData(FeedbackInfo feedbackInfo);
    List<MailStatusHis> parseFeedbackData(FeedbackInfo feedbackInfo);
}
