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

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;
	
	public List listArticles() throws Exception{
		List<ArticleVO> articleList = boardDAO.selectAllArtclesList();
		return articleList;
	}
	
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		return boardDAO.insertNewArticle(articleMap);
	}	
	
	@Override
	public ArticleVO viewArticle(int articleNO) throws Exception{
		return boardDAO.selectArticleNO(articleNO);
	}	
	
	@Override
	public Map listArticles(Map pagingMap) throws Exception{
		Map articlesMap = new HashMap();
		List<ArticleVO> articlesList = boardDAO.selectAllArtcles(pagingMap);
		int totArticles = boardDAO.selectTotArticles();
		articlesMap.put("articlesList", articlesList);
		articlesMap.put("totArticles", totArticles);
		return articlesMap;
	}	

}
