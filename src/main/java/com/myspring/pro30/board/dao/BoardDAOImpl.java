package com.myspring.pro30.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.board.vo.ArticleVO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO{
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List selectAllArtclesList() throws Exception{
		List<ArticleVO> articleList = sqlSession.selectList("mapper.board.selectAllArticleList");
		return articleList;
	}
	
	@Override
	public int insertNewArticle(Map articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.board.insertNewArticle",articleMap);
		return articleNO;
	}
	
	private int selectNewArticleNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewArticleNO");
	}
	
	@Override
	public ArticleVO selectArticleNO(int articleNO) throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectArticle",articleNO);
	}
	
	@Override
	public List selectAllArtcles(Map<String, Integer> pagingMap) throws Exception{
		List<ArticleVO> articleList = sqlSession.selectList("mapper.board.pageNum",pagingMap);
		return articleList;
	}
	
	@Override
	public int selectTotArticles() throws Exception{
		int totpage = sqlSession.selectOne("mapper.board.selectTotArticles");
		return totpage;
	}
	
	@Override
	public void modArticle(Map articleMap) throws Exception{
		sqlSession.update("mapper.board.updateArticle",articleMap);
	}
	
	@Override
	public void deleteArticle(int articleNO) throws DataAccessException {
		sqlSession.delete("mapper.board.deleteArticle", articleNO);
		
	}

}
