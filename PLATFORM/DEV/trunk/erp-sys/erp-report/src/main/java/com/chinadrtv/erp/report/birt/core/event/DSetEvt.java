package com.chinadrtv.erp.report.birt.core.event;

import org.eclipse.birt.report.engine.api.script.IDataSetRow;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataSetEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataSetInstance;

public class DSetEvt extends DataSetEventAdapter{

	@Override
	public void beforeOpen(IDataSetInstance dataSet,IReportContext reportContext) throws ScriptException {
		super.beforeOpen(dataSet, reportContext);
	}

	@Override
	public void afterOpen(IDataSetInstance dataSet, IReportContext reportContext)
			throws ScriptException {
		super.afterOpen(dataSet, reportContext);
	}

	@Override
	public void onFetch(IDataSetInstance dataSet, IDataSetRow row,
			IReportContext reportContext) throws ScriptException {
		super.onFetch(dataSet, row, reportContext);
	}

	@Override
	public void beforeClose(IDataSetInstance dataSet,
			IReportContext reportContext) throws ScriptException {
		super.beforeClose(dataSet, reportContext);
	}

	@Override
	public void afterClose(IReportContext reportContext) throws ScriptException {
		super.afterClose(reportContext);
	}

	
}
