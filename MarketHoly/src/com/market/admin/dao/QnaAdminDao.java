package com.market.admin.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.market.admin.dto.QnaAdminDto;
import com.market.db.JDBCUtil;
import com.market.product.dto.ProductDto;
import com.market.qna.dto.QnaDto;

public class QnaAdminDao {
	public static QnaAdminDao instance = new QnaAdminDao();

	public static QnaAdminDao getInstance() {
		return instance;
	}

	private QnaAdminDao() {

	}

	public ArrayList<QnaAdminDto> selQnaList(int startRow, String kind, String word) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "";

			if (kind.equals("writer")) {
				sql = " select *,b.name pname from qna a \n" + 
						" inner join product b \n" + 
						" ON a.pnum = b.pnum  where ref<=0 AND a.name LIKE '%" + word + "%' and a.del_yn = 'N' \n" + 
						" order by a.reg_date desc \n" +
						" limit ?,?";
				
			} else if (kind.equals("pname")) {
				sql = " select *,b.name pname from qna a \n" + 
						" inner join product b \n" + 
						" ON a.pnum = b.pnum  where ref<=0  AND b.name LIKE '%" + word + "%' and a.del_yn = 'N' \n" + 
						" order by a.reg_date desc \n" +
						" limit ?,?";
			} else {
				sql = " select *,b.name pname from qna a \n" + 
						" inner join product b \n" + 
						" ON a.pnum = b.pnum  where ref<=0  and a.del_yn = 'N' \n" + 
						" order by a.reg_date desc "
						+ " limit ?,?";
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, 6);
			rs = pstmt.executeQuery();
			ArrayList<QnaAdminDto> list = new ArrayList<QnaAdminDto>();
			while (rs.next()) {
				int qnum = rs.getInt("qnum");
				String pname = rs.getString("pname");
				String title = rs.getString("title");
				String name = rs.getString("name");
				int ref = rs.getInt("ref");
				Date reg_date = rs.getDate("reg_date");
				String content = rs.getString("content");
				int pnum = rs.getInt("pnum");
				list.add(new QnaAdminDto(qnum, pname, title, name, ref, reg_date, content, pnum));
			}
			return list;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int insAns(QnaDto dto) {
		Connection con = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			con.setAutoCommit(false);
			String sql2 = "update qna set ref = 0 where qnum = ?";
			pstmt2 = con.prepareStatement(sql2);
			pstmt2.setInt(1, dto.getRef());
			int n = pstmt2.executeUpdate();
			if (n > 0) {
				String sql = "insert into qna values(?,?,?,?,'관리자',?,?,?,now(),'N','N')";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, dto.getPnum());
				pstmt.setInt(2, dto.getNum());
				pstmt.setInt(3, dto.getQnum());
				pstmt.setString(4, dto.getId());
				pstmt.setString(5, dto.getTitle());
				pstmt.setString(6, dto.getContent());
				pstmt.setInt(7, dto.getRef());
				return pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			return -1;
		} finally {
			try {
				con.commit();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			JDBCUtil.close(pstmt2);
			JDBCUtil.close(null, pstmt, con);
		}
		return 0;
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
			rs.next();
			return rs.getInt("maxnum");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int selQnaCount(String kind, String word) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "";
			if (kind.equals("")) {
				sql = "select ifnull(count(*),0) cnt from qna";
			} else if (kind.equals("pname")) {
				sql = "SELECT ifnull(Count(*), 0) cnt \r\n" + 
						"FROM   qna a \r\n" + 
						"       INNER JOIN product b \r\n" + 
						"               ON a.pnum = b.pnum \r\n" + 
						"WHERE  b.NAME like '%" + word + "%' ";
			} else {
				sql = "select ifnull(count(*),0) cnt from qna where name like '%" + word + "%'";
			}
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("cnt");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public ArrayList<QnaAdminDto> selUnanswerList(int startRow) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<QnaAdminDto> list = new ArrayList<QnaAdminDto>();
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT a.*, b.name pname FROM   qna a INNER JOIN product b \n" + 
					"ON a.pnum = b.pnum WHERE ref=-1 AND a.del_yn = 'N'\n" + 
					"ORDER  BY qnum DESC"+
					" limit ?,6";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int qnum = rs.getInt("qnum");
				String pname = rs.getString("pname");
				String title = rs.getString("title");
				String name = rs.getString("name");
				int ref = rs.getInt("ref");
				Date reg_date = rs.getDate("reg_date");
				String content = rs.getString("content");
				int pnum = rs.getInt("pnum");
				list.add(new QnaAdminDto(qnum, pname, title, name, ref, reg_date, content, pnum));
			}
			return list;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int selUnanswerCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT ifnull(Count(*), 0) cnt FROM qna a \n"
					+ "WHERE  a.qnum AND a.ref = -1 AND a.del_yn = 'N'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("cnt");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public ArrayList<QnaAdminDto> selQnaAnsComList(int startRow) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<QnaAdminDto> list = new ArrayList<QnaAdminDto>();
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT a.*, b.name pname FROM  qna a INNER JOIN product b \n"
					+ "ON a.pnum = b.pnum where ref = 0 AND a.del_yn = 'N'\n"
					+ "ORDER  BY qnum DESC limit ?,6";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int qnum = rs.getInt("qnum");
				String pname = rs.getString("pname");
				String title = rs.getString("title");
				String name = rs.getString("name");
				int ref = rs.getInt("ref");
				Date reg_date = rs.getDate("reg_date");
				String content = rs.getString("content");
				int pnum = rs.getInt("pnum");
				list.add(new QnaAdminDto(qnum, pname, title, name, ref, reg_date, content, pnum));
			}
			return list;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int selQnaAnsComCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT ifnull(Count(*), 0) cnt FROM  qna a INNER JOIN product b \n" + 
					"ON a.pnum = b.pnum WHERE ref = 0 AND a.del_yn = 'N'\n" + 
					"ORDER  BY qnum DESC ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("cnt");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int updAns(int qnum, String title, String content) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			con.setAutoCommit(false);
			String sql2 = "update qna set title=?,content=? where ref = ?";
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, qnum);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			return -1;
		} finally {
			try {
				con.commit();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			JDBCUtil.close(null, pstmt, con);
		}
	}
	
	public QnaAdminDto getAnswer (int qnum) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		QnaAdminDto dto = null;
		try {
			con = JDBCUtil.getConn();
			String sql ="select title, content from qna where ref =? limit 1"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qnum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				dto = new QnaAdminDto(title, content);
			}
			return dto;

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	
	}

}
