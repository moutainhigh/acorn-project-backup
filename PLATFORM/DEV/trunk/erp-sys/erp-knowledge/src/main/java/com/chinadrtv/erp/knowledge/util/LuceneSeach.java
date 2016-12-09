/*
 * @(#)LuceneSeach.java 1.0 2013-12-25下午1:22:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.chinadrtv.erp.knowledge.dto.KnowledgeArticleDto;
import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-25 下午1:22:31
 * 
 */
@Component
public class LuceneSeach {

	public LuceneSeach() {

	}

	/**
	 * 
	 * Description：查询
	 * 
	 * @author cuiming
	 * @param where
	 *            查询条件
	 * @param scoreDoc
	 *            分页时用
	 */
	public Map<String, Object> search(String[] fields, String keyword,
			String indexFilePath, DataModel dataModel, String searchType) {
		Directory directory = null;
		Analyzer analyzer = new IKAnalyzer();
		IndexSearcher indexSearcher = null;
		List<KnowledgeArticleDto> result = new ArrayList<KnowledgeArticleDto>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> maps = new HashMap<String, Object>();
		try {
			directory = FSDirectory.open(new File(indexFilePath));
			// 创建索引搜索器,且只读
			IndexReader indexReader = IndexReader.open(directory, true);
			indexSearcher = new IndexSearcher(indexReader);
			MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
					Version.LUCENE_35, fields, analyzer);
			Query query = queryParser.parse(keyword);
			// 返回前number条记录
			TopDocs topDocs = indexSearcher.search(query, 100);
			int totalCount = topDocs.totalHits; // 搜索结果总数量
			mapResult = highLights(topDocs, analyzer, indexSearcher, query,
					searchType, dataModel);
			result = (List<KnowledgeArticleDto>) mapResult.get("result");
			maps.put("total", topDocs.totalHits);
			maps.put("rows", result);
			maps.put("size", mapResult.get("size").toString());
			System.out.println("共检索出 " + totalCount + " 条记录");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return maps;
	}

	/**
	 * @throws InvalidTokenOffsetsException
	 * @throws IOException
	 * @throws CorruptIndexException
	 *             *
	 * 
	 * @Description: 对结果集进行高亮显示
	 * @param topDocs
	 * @return
	 * @return List<KnowledgeArticle>
	 * @throws
	 */
	public Map<String, Object> highLights(TopDocs topDocs, Analyzer analyzer,
			IndexSearcher indexSearcher, Query query, String searchType,
			DataModel dataModel) throws CorruptIndexException, IOException,
			InvalidTokenOffsetsException {
		Map<String, Object> maps = new HashMap<String, Object>();
		List<KnowledgeArticleDto> result = new ArrayList<KnowledgeArticleDto>();
		// 高亮显示
		/*
		 * 创建高亮器,使搜索的结果高亮显示 SimpleHTMLFormatter：用来控制你要加亮的关键字的高亮方式 此类有2个构造方法
		 * 1：SimpleHTMLFormatter()默认的构造方法.加亮方式：<B>关键字</B>
		 * 2：SimpleHTMLFormatter(String preTag, String
		 * postTag).加亮方式：preTag关键字postTag
		 */
		Formatter formatter = new SimpleHTMLFormatter("<font color='red'>",
				"</font>");
		/*
		 * QueryScorer QueryScorer
		 * 是内置的计分器。计分器的工作首先是将片段排序。QueryScorer使用的项是从用户输入的查询中得到的；
		 * 它会从原始输入的单词、词组和布尔查询中提取项，并且基于相应的加权因子（boost factor）给它们加权。
		 * 为了便于QueryScoere使用，还必须对查询的原始形式进行重写。 比如，带通配符查询、模糊查询、前缀查询以及范围查询
		 * 等，都被重写为BoolenaQuery中所使用的项。
		 * 在将Query实例传递到QueryScorer之前，可以调用Query.rewrite (IndexReader)方法来重写Query对象
		 */
		Scorer fragmentScorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
		Fragmenter fragmenter = new SimpleFragmenter(1000);
		/*
		 * Highlighter利用Fragmenter将原始文本分割成多个片段。
		 * 内置的SimpleFragmenter将原始文本分割成相同大小的片段，片段默认的大小为100个字符。这个大小是可控制的。
		 */
		highlighter.setTextFragmenter(fragmenter);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		String productName = "";
		Long id = 0l;
		String content = "";
		String discourse = "";
		String shortPinyin = "";
		String prodcutCode = "";
		String title = "";
		String lighter = "";
		String lightercontent = "";
		String lighterdiscourse = "";
		String lightershortPinyin = "";
		String lighterprodcutCode = "";
		String lightertitle = "";
		String lighterproductName = "";
		String createDate = "";
		String updateDate = "";
		KnowledgeArticleDto k1 = null;
		// 查询起始记录位置
		int begin = dataModel.getPageSize() * (dataModel.getCurrentPage() - 1);
		// 查询终止记录位置
		int end = Math.min(begin + dataModel.getPageSize(), scoreDocs.length);
		maps.put("size", end - begin);
		for (int i = begin; i < end; i++) {
			k1 = new KnowledgeArticleDto();
			Document document = indexSearcher.doc(scoreDocs[i].doc);
			id = Long.parseLong(document.get("id"));
			k1.setId(id);
			discourse = document.get("discourse");
			discourse = HtmlUtil.splitAndFilterString(discourse, 50);
			lighterdiscourse = highlighter.getBestFragment(analyzer,
					"discourse", discourse);
			if (null == lighterdiscourse) {
				lighterdiscourse = discourse;
			}
			updateDate = document.get("createDate");
			if (StringUtil.isNullOrBank(updateDate)) {
				createDate = document.get("createDate");
			} else {
				createDate = updateDate;
			}

			k1.setCreateDate(createDate);
			k1.setDiscouse(lighterdiscourse);
			if (searchType.equals("2")) {
				title = document.get("title");
				lightertitle = highlighter.getBestFragment(analyzer, "title",
						title);
				if (null == lightertitle) {
					lightertitle = title;
				}
				k1.setTitle(lightertitle);
			} else {
				productName = document.get("productName");
				lighterproductName = highlighter.getBestFragment(analyzer,
						"productName", productName);
				if (null == lighterproductName) {
					lighterproductName = productName;
				}
				k1.setProductName(lighterproductName);
				shortPinyin = document.get("shortPinyin");
				lightershortPinyin = highlighter.getBestFragment(analyzer,
						"shortPinyin", shortPinyin);
				if (null == lightershortPinyin) {
					lightershortPinyin = shortPinyin;
				}
				k1.setShortPinyin(lightershortPinyin);
				prodcutCode = document.get("prodcutCode");
				lighterprodcutCode = highlighter.getBestFragment(analyzer,
						"prodcutCode", prodcutCode);
				if (null == lighterprodcutCode) {
					lighterprodcutCode = prodcutCode;
				}
				k1.setProductCode(lighterprodcutCode);
				content = document.get("content");
				if (!StringUtil.isNullOrBank(content)) {
					content = HtmlUtil.splitAndFilterString(content, 50);
					lightercontent = highlighter.getBestFragment(analyzer,
							"content", content);
					if (null == lightercontent) {
						lightercontent = content;
					}
					k1.setContent(lightercontent);
				}
				result.add(k1);
				System.out.println(k1.getProductName());
				System.out.println(k1.getId());
				System.out.println(k1.getTitle());
			}
		}
		maps.put("result", result);
		return maps;
	}

}
