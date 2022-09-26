package com.myspring.pro30.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.board.vo.ArticleVO;

public interface BoardDAO {
	public List selectAllArtclesList() throws Exception;
	public int insertNewArticle(Map articleMap) throws DataAccessException;
	public ArticleVO selectArticleNO(int articleNO) throws DataAccessException;
	public List selectAllArtcles(Map<String, Integer> pagingMap) throws Exception;
	public int selectTotArticles() throws Exception;
	public void modArticle(Map articleMap) throws Exception;
	public void deleteArticle(int articleNO) throws DataAccessException;
}
