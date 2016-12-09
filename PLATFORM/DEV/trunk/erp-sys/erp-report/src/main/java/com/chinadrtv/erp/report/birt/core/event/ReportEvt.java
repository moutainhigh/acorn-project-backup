package com.chinadrtv.erp.report.birt.core.event;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.IReportDesign;
import org.eclipse.birt.report.engine.api.script.eventadapter.ReportEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IPageInstance;

public class ReportEvt extends ReportEventAdapter{

	@Override
	public void initialize(IReportContext reportContext) throws ScriptException {
		super.initialize(reportContext);
	}

	@Override
	public void beforeFactory(IReportDesign report, IReportContext reportContext)
			throws ScriptException {
		super.beforeFactory(report, reportContext);
	}

	@Override
	public void afterFactory(IReportContext reportContext)
			throws ScriptException {
		super.afterFactory(reportContext);
	}

	@Override
	public void beforeRender(IReportContext reportContext)
			throws ScriptException {
		super.beforeRender(reportContext);
	}

	@Override
	public void afterRender(IReportContext reportContext)
			throws ScriptException {
		super.afterRender(reportContext);
	}

	@Override
	public void onPrepare(IReportContext reportContext) throws ScriptException {
		super.onPrepare(reportContext);
	}

	@Override
	public void onPageStart(IPageInstance page, IReportContext reportContext)
			throws ScriptException {
		super.onPageStart(page, reportContext);
	}

	@Override
	public void onPageEnd(IPageInstance page, IReportContext reportContext)
			throws ScriptException {
		super.onPageEnd(page, reportContext);
	}


	
}
