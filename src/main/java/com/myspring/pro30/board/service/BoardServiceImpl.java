package com.myspring.pro30.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro30.board.dao.BoardDAO;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO boardDAO;

	public List listArticles() throws Exception {
		List<ArticleVO> articleList = boardDAO.selectAllArtclesList();
		return articleList;
	}

	@Override
	public int addNewArticle(Map articleMap) throws Exception {
		int articleNO = boardDAO.insertNewArticle(articleMap);
		articleMap.put("articleNO", articleNO);
		boardDAO.insertNewImage(articleMap);
		return articleNO;
	}

	/*
	 * @Override public ArticleVO viewArticle(int articleNO) throws Exception{
	 * return boardDAO.selectArticleNO(articleNO); }
	 */

	@Override
	public Map viewArticle(int articleNO) throws Exception {
		Map articleMap = new HashMap();
		ArticleVO articleVO = boardDAO.selectArticleNO(articleNO);
		List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNO);
		articleMap.put("article", articleVO);
		articleMap.put("imageFileList", imageFileList);
		return articleMap;
	}

	@Override
	public Map listArticles(Map pagingMap) throws Exception {
		Map articlesMap = new HashMap();
		List<ArticleVO> articlesList = boardDAO.selectAllArtcles(pagingMap);
		int totArticles = boardDAO.selectTotArticles();
		articlesMap.put("articlesList", articlesList);
		articlesMap.put("totArticles", totArticles);
		return articlesMap;
	}

	@Override
	public void modArticle(Map articleMap) throws Exception {
		boardDAO.modArticle(articleMap);

		List<ImageVO> imageFileList = (List<ImageVO>) articleMap.get("imageFileList");
		List<ImageVO> modAddimageFileList = (List<ImageVO>) articleMap.get("modAddimageFileList");

		if (imageFileList != null && imageFileList.size() != 0) {
			int added_img_num = Integer.parseInt((String) articleMap.get("added_img_num"));
			int pre_img_num = Integer.parseInt((String) articleMap.get("pre_img_num"));

			if (pre_img_num < added_img_num) {
				boardDAO.updateImageFile(articleMap); // 기존 이미지도 수정하고 새 이미지도 추가한 경우
				boardDAO.insertModNewImage(articleMap);
			} else {
				boardDAO.updateImageFile(articleMap); // 기존의 이미지를 수정만 한 경우
			}
		} else if (modAddimageFileList != null && modAddimageFileList.size() != 0) { // 새 이미지를 추가한 경우
			boardDAO.insertModNewImage(articleMap);
		}

	}

	@Override
	public void removeArticle(int articleNO) throws Exception {
		boardDAO.deleteArticle(articleNO);
	}

	@Override
	public int addReply(Map articleMap) throws Exception {
		return boardDAO.addReply(articleMap);
	}

}
