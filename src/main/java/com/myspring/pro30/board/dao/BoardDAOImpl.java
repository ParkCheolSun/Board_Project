package com.myspring.pro30.board.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List selectAllArtclesList() throws DataAccessException {
		List<ArticleVO> articleList = sqlSession.selectList("mapper.board.selectAllArticleList");
		return articleList;
	}

	@Override
	public int insertNewArticle(Map articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.board.insertNewArticle", articleMap);
		return articleNO;
	}

	@Override
	public void insertNewImage(Map articleMap) throws DataAccessException {
		List<ImageVO> imageFileList = (ArrayList) articleMap.get("imageFileList");
		int articleNO = (Integer)articleMap.get("articleNO");
		int imageFileNO = selectNewImageFileNO();
		for (ImageVO imageVO : imageFileList) {
			imageVO.setImageFileNO(++imageFileNO);
			imageVO.setArticleNO(articleNO);
		}
		sqlSession.insert("mapper.board.insertNewImage",imageFileList);
	}

	private int selectNewImageFileNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewImageFileNO");
	}

	private int selectNewArticleNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewArticleNO");
	}

	@Override
	public ArticleVO selectArticleNO(int articleNO) throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectArticle", articleNO);
	}
	
	@Override
	public List<ImageVO> selectImageFileList(int articleNO) throws DataAccessException{
		return sqlSession.selectList("mapper.board.selectImageFileList", articleNO);
	}

	@Override
	public List selectAllArtcles(Map<String, Integer> pagingMap) throws DataAccessException {
		List<ArticleVO> articleList = sqlSession.selectList("mapper.board.pageNum", pagingMap);
		{
			for(int i=0;i<articleList.size();i++) {
				ArticleVO temp = articleList.get(i);
				System.out.println("DAO title : " + temp.getTitle());
				System.out.println("DAO level : " + temp.getLvl());
			}
		}
		return articleList;
	}

	@Override
	public int selectTotArticles() throws DataAccessException {
		int totpage = sqlSession.selectOne("mapper.board.selectTotArticles");
		return totpage;
	}
	
	@Override
	public int addReply(Map articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.board.insertNewArticle", articleMap);
		return articleNO;
	}

	@Override
	public void modArticle(Map articleMap) throws DataAccessException {
		String[] imageFileNO = (String[]) articleMap.get("imageFileNO");
		int added_img_num = Integer.parseInt((String) articleMap.get("added_img_num"));
		int pre_img_num = Integer.parseInt((String) articleMap.get("pre_img_num"));
		for (int i = 0; i < added_img_num; i++) {
			if (i < pre_img_num) {
				System.out.println("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ >> imageFileNO : " + imageFileNO[i]);
			}
		}
		System.out.println("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ title >>> " + articleMap.get("title"));
		System.out.println("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ content >>> " + articleMap.get("content"));
		System.out.println("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ imageFileName >>> " + articleMap.get("imageFileName"));
		sqlSession.update("mapper.board.updateArticle", articleMap);
	}

	@Override
	public void deleteArticle(int articleNO) throws DataAccessException {
		sqlSession.delete("mapper.board.deleteArticle", articleNO);

	}
	
	@Override
	public void updateImageFile(Map articleMap) throws DataAccessException {
		List<ImageVO> imageFileList = (ArrayList)articleMap.get("imageFileList");
		int articleNO = Integer.parseInt((String)articleMap.get("articleNO"));
		
		for(int i = imageFileList.size()-1; i >= 0; i--){
			ImageVO imageVO = imageFileList.get(i);
			String imageFileName = imageVO.getImageFileName();
			if(imageFileName == null) {  //기존에 이미지를 수정하지 않는 경우 파일명이 null 이므로  수정할 필요가 없다.
				imageFileList.remove(i);     // 제거한다.
			}else {
				imageVO.setArticleNO(articleNO);
			}
		}
		if(imageFileList != null && imageFileList.size() != 0) {
			sqlSession.update("mapper.board.updateImageFile", imageFileList);  // 수정한 이미지만 갱신한다.
		}
	}
	
	@Override
	public void insertModNewImage(Map articleMap) throws DataAccessException {
		List<ImageVO> modAddimageFileList = (ArrayList<ImageVO>)articleMap.get("modAddimageFileList");
		int articleNO = Integer.parseInt((String)articleMap.get("articleNO"));
		
		int imageFileNO = selectNewImageFileNO();
		
		for(ImageVO imageVO : modAddimageFileList){
			imageVO.setArticleNO(articleNO);
			imageVO.setImageFileNO(++imageFileNO);
		}
		
     sqlSession.delete("mapper.board.insertModNewImage", modAddimageFileList );
	}

}

