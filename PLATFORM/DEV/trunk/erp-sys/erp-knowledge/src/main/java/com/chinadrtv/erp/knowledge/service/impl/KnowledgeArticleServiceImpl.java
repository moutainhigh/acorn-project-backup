/*
 * @(#)KnowledgeArticleServiceImpl.java 1.0 2013-11-6下午1:55:27
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.knowledge.dao.KnowledgeArticleDao;
import com.chinadrtv.erp.knowledge.dao.KnowledgePictureDao;
import com.chinadrtv.erp.knowledge.dao.KnowledgeRelationshipDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;
import com.chinadrtv.erp.knowledge.model.KnowledgePicture;
import com.chinadrtv.erp.knowledge.model.KnowledgeRelationship;
import com.chinadrtv.erp.knowledge.service.KnowledgeArticleService;
import com.chinadrtv.erp.knowledge.service.KnowledgeRelationshipService;
import com.chinadrtv.erp.knowledge.service.LuceneIndexService;
import com.chinadrtv.erp.knowledge.util.HtmlUtil;
import com.chinadrtv.erp.knowledge.util.PropertiesUtils;
import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-11-6 下午1:55:27
 * 
 */
@Service("knowledgeArticleService")
public class KnowledgeArticleServiceImpl implements KnowledgeArticleService {
	@Autowired
	private KnowledgeArticleDao knowledgeArticleDao;
	@Autowired
	private KnowledgePictureDao knowledgePictureDao;
	@Autowired
	private KnowledgeRelationshipDao knowledgeRelationshipDao;
	@Autowired
	private KnowledgeRelationshipService knowledgeRelationshipService;
	private static final Logger logger = LoggerFactory
			.getLogger(KnowledgeArticleServiceImpl.class);
	@Autowired
	private LuceneIndexService luceneIndexService;

	@Override
	public Map<String, Object> saveKnowledgeArticle(
			KnowledgeArticle knowledgeArticle, HttpServletRequest request,
			String groupType, String userId) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		knowledgeArticle.setDepartType(groupType.toUpperCase());
		KnowledgeArticle knowledgeArticle2 = null;
		Boolean flag = false;
		// 图片数组
		String[] imgDiscourse = null;
		String[] imgProduct = null;
		String imgDiscourses = "";
		String imgProducts = "";
		Long seq = null;
		imgProducts = HtmlUtil.getImgStr(knowledgeArticle.getContent());
		imgDiscourses = HtmlUtil.getImgStr(knowledgeArticle.getDiscouse());
		if (imgDiscourses != null && !"".equals(imgDiscourses)) {
			imgDiscourse = imgDiscourses.split(",");
		}
		if (imgProducts != null && !"".equals(imgProducts)) {
			imgProduct = imgProducts.split(",");
		}
		try {
			// 产品按照产品简码查询，其他按照id查询
			if (knowledgeArticle.getType().equals("1")) {
				knowledgeArticle2 = knowledgeArticleDao
						.getByProductCode(knowledgeArticle);
			} else {
				if (knowledgeArticle.getId() != null) {
					knowledgeArticle2 = knowledgeArticleDao
							.get(knowledgeArticle.getId());
				}
			}
			if (knowledgeArticle2 != null) {
				knowledgeArticle.setId(knowledgeArticle2.getId());
			}
			String relationsId = "";
			if (knowledgeArticle2 != null) {
				knowledgeArticle.setUpdateDate(new Date());
				knowledgeArticle.setUpdateUser(userId);
				relationsId = knowledgeArticle2.getRelationshipIds();
				// 只有为产品的时候才会保存知识点
				if (knowledgeArticle.getType().equals("1")) {
					knowledgeArticle.setPath(HtmlUtil.newHtml(
							knowledgeArticle.getContentHtml(),
							PropertiesUtils.getValue("html.file"),
							PropertiesUtils.getValue("html.url"),
							knowledgeArticle2.getPath()));
				}
				knowledgeArticle.setDiscoursePath(HtmlUtil.newHtml(
						knowledgeArticle.getDiscourseHtml(),
						PropertiesUtils.getValue("html.file"),
						PropertiesUtils.getValue("html.url"),
						knowledgeArticle2.getDiscoursePath()));
				knowledgeArticle.setCreateDate(knowledgeArticle2
						.getCreateDate());
				knowledgeArticle.setCreateUser(knowledgeArticle2
						.getCreateUser());
				knowledgeArticle.setStatus(knowledgeArticle2.getStatus());
				// 获得知识点id
				seq = knowledgeArticle.getId();

				knowledgeArticleDao.merge(knowledgeArticle);
				luceneIndexService.updateIndex(knowledgeArticle);
				// 更新关联知识点
				Map<String, Object> map = new HashMap<String, Object>();
				if (!StringUtil.isNullOrBank(relationsId)
						&& !StringUtil.isNullOrBank(knowledgeArticle
								.getRelationshipIds())) {
					updateRelationShips(relationsId,
							knowledgeArticle.getRelationshipIds(),
							knowledgeArticle);
				} else {
					if (StringUtil.isNullOrBank(relationsId)
							&& !StringUtil.isNullOrBank(knowledgeArticle
									.getRelationshipIds())) {
						savesRelationShip(
								knowledgeArticle.getRelationshipIds(),
								knowledgeArticle);

					}
					if (StringUtil.isNullOrBank(knowledgeArticle
							.getRelationshipIds())
							&& !StringUtil.isNullOrBank(relationsId)) {

						String[] ids1 = relationsId.split(",");
						for (int i = 0; i < ids1.length; i++) {
							knowledgeRelationshipService.delete(
									knowledgeArticle.getId(),
									Long.valueOf(ids1[i]));
						}

					}
				}

			} else {
				knowledgeArticle.setCreateDate(new Date());
				knowledgeArticle.setCreateUser(userId);
				// 获得知识点id
				seq = knowledgeArticleDao.getSeq();
				knowledgeArticle.setId(seq);
				// 只有为产品的时候才会保存知识点
				knowledgeArticle.setDiscoursePath(HtmlUtil.newHtml(
						knowledgeArticle.getDiscourseHtml(),
						PropertiesUtils.getValue("html.file"),
						PropertiesUtils.getValue("html.url"), ""));
				if (knowledgeArticle.getType().equals("1")) {
					knowledgeArticle.setPath(HtmlUtil.newHtml(
							knowledgeArticle.getContentHtml(),
							PropertiesUtils.getValue("html.file"),
							PropertiesUtils.getValue("html.url"), ""));
				}

				knowledgeArticle.setStatus("1");
				// 先保存知识点
				knowledgeArticleDao.save(knowledgeArticle);
				luceneIndexService.singleIndex(knowledgeArticle);
				// 标签文章关系 保存
				if (!StringUtil.isNullOrBank(knowledgeArticle
						.getRelationshipIds())) {
					savesRelationShip(knowledgeArticle.getRelationshipIds(),
							knowledgeArticle);
				}
			}
			// 保存产品图片
			if (imgProduct != null && imgProduct.length > 0) {
				for (int i = 0; i < imgProduct.length; i++) {
					KnowledgePicture knowledgePicture = new KnowledgePicture();
					knowledgePicture.setCreateDate(new Date());
					knowledgePicture.setCreateUser(userId);
					knowledgePicture.setArticleId(seq);
					knowledgePicture.setPictureType("1");
					knowledgePicture.setUrl(imgProduct[i]);
					knowledgePictureDao.save(knowledgePicture);
				}
			}
			// 话术的图片
			if (imgDiscourse != null && imgDiscourse.length > 0) {
				for (int i = 0; i < imgDiscourse.length; i++) {
					KnowledgePicture knowledgePicture = new KnowledgePicture();
					knowledgePicture.setCreateDate(new Date());
					knowledgePicture.setCreateUser(userId);
					knowledgePicture.setArticleId(seq);
					knowledgePicture.setPictureType("2");
					knowledgePicture.setUrl(imgDiscourse[i]);
					knowledgePictureDao.save(knowledgePicture);
				}
			}

			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			result.put("result", "保存失败");
			logger.error("保存失败" + e.getMessage());
		}
		if (flag == true) {
			result.put("result", "保存成功");
			result.put("id", seq);
		} else {
			result.put("result", "保存失败");
		}
		return result;
	}

	/**
	 * 
	 * @Description: 保存关联知识点
	 * @param id
	 * @param knowledgeArticle
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean savesRelationShip(String id,
			KnowledgeArticle knowledgeArticle) {
		Boolean result = false;
		try {
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				KnowledgeRelationship knowledgeRelationship = new KnowledgeRelationship();
				knowledgeRelationship.setArticleId(knowledgeArticle.getId());
				knowledgeRelationship.setStatus("1");
				knowledgeRelationship.setTagId(Long.valueOf(ids[i]));
				knowledgeRelationship.setCreateDate(new Date());
				knowledgeRelationship.setCreateUser(knowledgeArticle
						.getCreateUser());
				knowledgeRelationshipService.saves(knowledgeRelationship);
			}
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 
	 * @Description: 处理关联知识点
	 * @param id1
	 * @param id2
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	Boolean updateRelationShips(String id1, String id2,
			KnowledgeArticle knowledgeArticle) {
		Map<String, Object> maps = new HashMap<String, Object>();
		String[] ids1 = id1.split(",");
		Boolean result = false;
		try {
			for (int i = 0; i < ids1.length; i++) {
				knowledgeRelationshipService.delete(knowledgeArticle.getId(),
						Long.valueOf(ids1[i]));
			}
			savesRelationShip(id2, knowledgeArticle);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Boolean deleteKnowledgeArticle(KnowledgeArticle knowledgeArticle) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		try {
			if (knowledgeArticle.getType().equals("4")) {
				knowledgeArticle.setType("2");
			} else {
				knowledgeArticle.setType("1");
			}
			knowledgeArticle.setStatus("0");
			knowledgeArticleDao.merge(knowledgeArticle);
			luceneIndexService.deleteIndex(knowledgeArticle);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("更新知识点失败" + e.getStackTrace());
		}
		return flag;
	}

	@Override
	public KnowledgeArticle getByProductCode(KnowledgeArticle knowledgeArticle) {
		// TODO Auto-generated method stub

		return knowledgeArticleDao.getByProductCode(knowledgeArticle);
	}

	public KnowledgeArticle getById(Long Id) {
		// TODO Auto-generated method stub
		return knowledgeArticleDao.get(Id);
	}

	@Override
	public List<KnowledgeArticle> getTrees(Long cid, String depType) {
		// TODO Auto-generated method stub
		return knowledgeArticleDao.queryList(cid, depType);
	}

	@Override
	public Map<String, Object> query(KnowledgeArticle knowledgeArticle,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<KnowledgeArticle> list = knowledgeArticleDao.query(
				knowledgeArticle, dataModel);
		Integer total = knowledgeArticleDao.queryCounts(knowledgeArticle);
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	@Override
	public Boolean isExist(Long cid) {
		// TODO Auto-generated method stub
		return knowledgeArticleDao.isExist(cid);
	}

	@Override
	public List<KnowledgeArticle> getNewAdds(Long nums, String dtype) {
		// TODO Auto-generated method stub
		return knowledgeArticleDao.getNewAdds(nums, dtype);
	}

}
