package com.myspring.pro30.member.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberVO> selectAllMemberList() throws DataAccessException {
		List<MemberVO> membersList = null;
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}
	
	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException{
		int result = sqlSession.insert("mapper.member.insertMember",memberVO);
		return result;
		
	}
	
	@Override
	public int deleteMember(String id) throws DataAccessException{
		int result = sqlSession.insert("mapper.member.deleteMember",id);
		return result;
	}
	
	@Override
	public MemberVO modMember(String id) throws DataAccessException{
		MemberVO memberVO;
		memberVO = (MemberVO)sqlSession.selectOne("mapper.member.selectMemberById",id);
		return memberVO;
		
	}
	
	@Override
	public int updateMember(MemberVO memberVO) throws DataAccessException{
		int result = sqlSession.update("mapper.member.updateMember", memberVO);
		return result;
	}
	
	@Override
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException{
		MemberVO vo = sqlSession.selectOne("mapper.member.loginById", memberVO);
		return vo;
	}
}
