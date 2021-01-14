package com.market.qna.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.market.db.JDBCUtil;
import com.market.qna.dto.QnaDto;
import com.market.review.dao.ReviewDao;
import com.market.review.dto.ReviewDto;

public class QnaDao {
	private static QnaDao instance = new QnaDao();

	private QnaDao() {
	}

	public static QnaDao getInstance() {
		return instance;
	}

	public int getMaxNum() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "select ifnull(max(qnum),0) maxnum from qna";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("maxnum");
			} else {
				return 0;
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int writeQna(QnaDto dto) {
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		try {
			con = JDBCUtil.getConn();
	
			String sql2 = "insert into qna values(?,?,?,?,?,?,?,-1,now(),'N',?)";
			pstmt2 = con.prepareStatement(sql2);
			pstmt2.setInt(1, dto.getPnum());
			pstmt2.setInt(2, dto.getNum());
			pstmt2.setInt(3, 0);
			pstmt2.setString(4, dto.getId());
			pstmt2.setString(5, dto.getName());
			pstmt2.setString(6, dto.getTitle());
			pstmt2.setString(7, dto.getContent());
			pstmt2.setString(8, dto.getLocker());
			pstmt2.executeUpdate();
			return 1;
		} catch (SQLException se) {
			se.printStackTrace();
			return -1;
		} finally {
			JDBCUtil.close(pstmt1);
			JDBCUtil.close(pstmt2);
			JDBCUtil.close(con);
		}
	}

	
	public int getCount(int pnums) {// 전체글의 갯수 리턴
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "select ifnull(count(qnum),0) cnt from qna where pnum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pnums);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("cnt");
			} else {
				return 0;
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public ArrayList<QnaDto> list(int startRow, int endRow, int pnums) {
		String sql = "select * from qna \r\n" + 
				"where pnum = ? and ref >= 0 and del_yn = 'N' \r\n" + 
				"order by qnum desc\r\n" + 
				"limit ?,?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pnums);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, 5);
			rs = pstmt.executeQuery();
			ArrayList<QnaDto> list = new ArrayList<QnaDto>();
			
			while (rs.next()) {
				int pnum = rs.getInt("pnum");
				int num = rs.getInt("num");
				int qnum = rs.getInt("qnum");
				String id = rs.getString("id");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int ref = rs.getInt("ref");
				Date reg_date = rs.getDate("reg_date");
				String del_yn = rs.getString("del_yn");
				String locker = rs.getString("locker");
			
				QnaDto dto = new QnaDto(pnum, num, qnum, id, name, title, content, ref, reg_date, del_yn, locker);
				list.add(dto);
			}
			
			return list;
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

}
