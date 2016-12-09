/*
 * @(#)LuceneIndex.java 1.0 2013-12-23下午1:32:30
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;
import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-23 下午1:32:30
 * 
 */
@Component("luceneIndex")
public class LuceneIndex {

	private Directory directory;
	private Analyzer analyzer;

	/**
	 * @return the directory
	 */
	public Directory getDirectory() {
		return directory;
	}

	/**
	 * @param directory
	 *            the directory to set
	 */
	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	/**
	 * @return the analyzer
	 */
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	/**
	 * @param analyzer
	 *            the analyzer to set
	 */
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	/**
	 * 创建索引 Description：
	 * 
	 * @author dennisit@163.com Apr 3, 2013
	 * @throws Exception
	 */
	public void createIndex(List<KnowledgeArticle> list, String indexFilePath)
			throws Exception {
		directory = FSDirectory.open(new File(indexFilePath));
		analyzer = new IKAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				Version.LUCENE_35, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		indexWriter.deleteAll();
		for (int i = 0; i < list.size(); i++) {
			KnowledgeArticle knowledgeArticle = list.get(i);
			Document document = addDocument(knowledgeArticle, indexFilePath);
			indexWriter.addDocument(document);
		}
		indexWriter.close();
	}

	/**
	 * 
	 * Description： 更新索引
	 * 
	 * 
	 * @param id
	 * @param title
	 * @param content
	 */
	public void update(KnowledgeArticle knowledgeArticle, String indexFilePath) {
		try {
			directory = FSDirectory.open(new File(indexFilePath));
			analyzer = new IKAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					Version.LUCENE_35, analyzer);
			IndexWriter indexWriter = new IndexWriter(directory,
					indexWriterConfig);
			Document document = addDocument(knowledgeArticle, indexFilePath);
			Term term = new Term("id", String.valueOf(knowledgeArticle.getId()));
			indexWriter.updateDocument(term, document);
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description: 根据id删除索引
	 * @param id
	 * @param indexFilePath
	 * @return void
	 * @throws
	 */
	public void delete(Long id, String indexFilePath) {
		try {
			directory = FSDirectory.open(new File(indexFilePath));
			analyzer = new IKAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					Version.LUCENE_35, analyzer);
			IndexWriter indexWriter = new IndexWriter(directory,
					indexWriterConfig);
			Term term = new Term("id", String.valueOf(id));
			indexWriter.deleteDocuments(term);
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Description：
	 * 
	 * @author 新增索引
	 * @param id
	 * @param title
	 * @param content
	 * @return
	 */
	public Document addDocument(KnowledgeArticle knowledgeArticle,
			String indexFilePath) {
		try {
			directory = FSDirectory.open(new File(indexFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		analyzer = new IKAnalyzer();
		Document doc = new Document();
		// Field.Index.NO 表示不索引
		// Field.Index.ANALYZED 表示分词且索引
		// Field.Index.NOT_ANALYZED 表示不分词且索引
		Field ids = null;
		Field productName = null;
		Field content = null;
		Field discourse = null;
		Field shortPinyin = null;
		Field prodcutCode = null;
		Field title = null;
		Field createDate = null;
		Field updateDate = null;
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		ids = new Field("id", String.valueOf(knowledgeArticle.getId()),
				Field.Store.YES, Field.Index.NOT_ANALYZED);
		if (!StringUtil.isNullOrBank(knowledgeArticle.getDiscouse())) {
			discourse = new Field("discourse", knowledgeArticle.getDiscouse()
					.toString(), Field.Store.YES, Field.Index.ANALYZED);
			doc.add(discourse);
		}
		if (knowledgeArticle.getUpdateDate() != null) {
			updateDate = new Field("createDate", sfd.format(knowledgeArticle
					.getUpdateDate()), Field.Store.YES,
					Field.Index.NOT_ANALYZED);
			doc.add(updateDate);
		}
		createDate = new Field("createDate", sfd.format(knowledgeArticle
				.getCreateDate()), Field.Store.YES, Field.Index.NOT_ANALYZED);
		doc.add(createDate);
		// Field
		doc.add(ids);
		if (knowledgeArticle.getType().equals("1")) {
			if (!StringUtil.isNullOrBank(knowledgeArticle.getProductName())) {
				productName = new Field("productName", knowledgeArticle
						.getProductName().toString(), Field.Store.YES,
						Field.Index.ANALYZED);
				// 对域加权限 加重对主题的操作
				productName.setBoost(2.0F);
				doc.add(productName);

			}
			if (!StringUtil.isNullOrBank(knowledgeArticle.getContent())) {
				content = new Field("content", knowledgeArticle.getContent()
						.toString(), Field.Store.YES, Field.Index.ANALYZED);
				doc.add(content);

			}
			if (!StringUtil.isNullOrBank(knowledgeArticle.getShortPinyin())) {
				shortPinyin = new Field("shortPinyin", knowledgeArticle
						.getShortPinyin().toString(), Field.Store.YES,
						Field.Index.ANALYZED);
				doc.add(shortPinyin);

			}
			if (!StringUtil.isNullOrBank(knowledgeArticle.getProductCode())) {
				prodcutCode = new Field("prodcutCode", knowledgeArticle
						.getProductCode().toString(), Field.Store.YES,
						Field.Index.ANALYZED);
				doc.add(prodcutCode);

			}

		} else {
			title = new Field("title", knowledgeArticle.getTitle().toString(),
					Field.Store.YES, Field.Index.ANALYZED);
			doc.add(title);
			title.setBoost(2.0F);
		}
		System.out.println(title);
		System.out.println(productName);

		return doc;
	}
}
