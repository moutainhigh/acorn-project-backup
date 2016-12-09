package com.chinadrtv.erp.report.birt.core.event;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataSourceEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataSourceInstance;

public class DSEvt extends DataSourceEventAdapter{

	@Override
	public void beforeOpen(IDataSourceInstance dataSource,
			IReportContext reportContext) throws ScriptException {
		super.beforeOpen(dataSource, reportContext);
	}

	@Override
	public void afterOpen(IDataSourceInstance dataSource,
			IReportContext reportContext) throws ScriptException {
		super.afterOpen(dataSource, reportContext);
	}

	@Override
	public void beforeClose(IDataSourceInstance dataSource,
			IReportContext reportContext) throws ScriptException {
		super.beforeClose(dataSource, reportContext);
	}

	@Override
	public void afterClose(IReportContext reportContext) throws ScriptException {
		super.afterClose(reportContext);
	}


	
}
