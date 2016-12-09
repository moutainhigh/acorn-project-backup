/*
 * @(#)GraphController.java 1.0 2013-3-13下午2:10:19
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.util;

import java.awt.Font;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-3-13 下午2:10:19
 * 
 */
public class JfreeChartUtils {
	/**
	 * 生成折线图
	 */
	public String makeLine(String lineName, String xName, String yName,
			int width, int height, HttpServletRequest request,
			List<String[]> data, List<String[]> datas) {

		CategoryDataset linedataset = this.getDataSet(datas, data);

		JFreeChart chart = ChartFactory.createLineChart(lineName, // chart title
				xName, // domain axis label
				yName, // range axis label
				linedataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);
		setImageFont(chart);
		CategoryPlot line = chart.getCategoryPlot();
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) line
				.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		renderer.setFillPaint(java.awt.Color.white);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setItemLabelFont(new Font("黑体", Font.PLAIN, 12));
		renderer.setItemLabelsVisible(true);
		// customise the range axis...
		NumberAxis rangeAxis = (NumberAxis) line.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);
		// x轴
		CategoryAxis domainAxis = (CategoryAxis) line.getDomainAxis();
		setDomainAxis(domainAxis);
		NumberAxis numberaxis = (NumberAxis) line.getRangeAxis();
		setNumberAxis(numberaxis);
		line.setRangeAxis(rangeAxis);
		line.setDomainGridlinesVisible(true);
		line.setDrawSharedDomainAxis(true);
		String imagePath_area = null;
		try {
			// 生成图片
			String filename = ServletUtilities.saveChartAsPNG(chart, 650, 325,
					null, request.getSession());

			ServletContext servlet = request.getSession().getServletContext();

			// 得到功能路径
			imagePath_area = servlet.getContextPath()
					+ "/servlet/DisplayChart?filename=" + filename;

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("结束");
		return imagePath_area;
	}

	/**
	 * 设置X轴
	 * 
	 * @param domainAxis
	 */
	private static void setDomainAxis(CategoryAxis domainAxis) {
		// 解决x轴坐标上中文乱码
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		// 解决x轴标题中文乱码
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 14));
		// 用于显示X轴刻度
		domainAxis.setTickMarksVisible(true);
		// 设置X轴45度
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	}

	/**
	 * 设置Y轴
	 * 
	 * @param numberAxis
	 */
	private static void setNumberAxis(NumberAxis numberaxis) {
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// 是否显示零点
		numberaxis.setAutoRangeIncludesZero(true);
		numberaxis.setAutoTickUnitSelection(false);
		// 解决Y轴标题中文乱码
		numberaxis.setLabelFont(new Font("宋体", Font.PLAIN, 14));
		// numberaxis.setTickUnit(new NumberTickUnit(10000));//Y轴数据间隔
	}

	/**
	 * 解决乱码问题
	 */
	private void setImageFont(JFreeChart chart) {
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();
		ValueAxis numberaxis = plot.getRangeAxis();

		// 设置标题文字
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 12));

		// 设置X轴坐标上的文字
		domainAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 11));

		// 设置X 轴的标题文字
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));

		// 设置Y 轴坐标上的文字
		numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));

		// 设置Y 轴的标题文字
		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));

		// 设置底部文字
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
	}

	// 构建数据
	private CategoryDataset getDataSet(List<String[]> data, List<String[]> datas) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (String[] ss : data) {
			dataset.addValue(Double.valueOf(ss[0]), ss[1], ss[2]);
		}
		for (String[] ss : datas) {
			dataset.addValue(Double.valueOf(ss[0]), ss[1], ss[2]);
		}
		return dataset;
	}

}
