package com.myspring.pro30.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;

public interface BoardDAO {
	public List selectAllArtclesList() throws DataAccessException;
	public int insertNewArticle(Map articleMap) throws DataAccessException;
	public ArticleVO selectArticleNO(int articleNO) throws DataAccessException;
	public List selectAllArtcles(Map<String, Integer> pagingMap) throws DataAccessException;
	public int selectTotArticles() throws DataAccessException;
	public void modArticle(Map articleMap) throws DataAccessException;
	public void deleteArticle(int articleNO) throws DataAccessException;
	public void insertNewImage(Map articleMap) throws DataAccessException;
	public List<ImageVO> selectImageFileList(int articleNO) throws DataAccessException;
	public int addReply(Map articleMap) throws DataAccessException;
}
