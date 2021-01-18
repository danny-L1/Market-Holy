package com.market.admin.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.market.admin.dto.StatDayDto;
import com.market.admin.dto.StatGenderDto;
import com.market.admin.dto.StatMonDto;
import com.market.db.JDBCUtil;

public class StatDao {
	public static StatDao instance = new StatDao();

	public static StatDao getInstance() {
		return instance;
	}

	private StatDao() {
	}

	public StatMonDto selMonSell(Date startDate, Date endDate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT \n"
					+ "    COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '01' then '1월'\n"
					+ "        END\n"
					+ "    ) AS '1월',\n"
					+ "     COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '02' then '2월'\n"
					+ "        END\n"
					+ "    ) AS '2월',\n"
					+ "    COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '03' then '3월'\n"
					+ "        END\n"
					+ "    ) AS '3월',\n"
					+ "    COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '04' then '4월'\n"
					+ "        END\n"
					+ "    ) AS '4월',\n"
					+ "    COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '05' then '5월'\n"
					+ "        END\n"
					+ "    ) AS '5월',\n"
					+ "      COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '06' then '6월'\n"
					+ "        END\n"
					+ "    ) AS '6월',\n"
					+ "      COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '07' then '7월'\n"
					+ "        END\n"
					+ "    ) AS '7월',\n"
					+ "      COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '08' then '8월'\n"
					+ "        END\n"
					+ "    ) AS '8월',\n"
					+ "      COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '09' then '9월'\n"
					+ "        END\n"
					+ "    ) AS '9월',\n"
					+ "      COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '10' then '10월'\n"
					+ "        END\n"
					+ "    ) AS '10월',\n"
					+ "      COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '11' then '11월'\n"
					+ "        END\n"
					+ "    ) AS '11월',\n"
					+ "     COUNT(\n"
					+ "        CASE (DATE_FORMAT(reg_date,'%m'))\n"
					+ "            when '12' then '12월'\n"
					+ "        END\n"
					+ "    ) AS '12월' \n"
					+ "FROM  orders \n"
					+ "WHERE  status = 5 AND reg_date >= ? AND reg_date <= ?\n";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, startDate);
			pstmt.setDate(2, endDate);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int jan = rs.getInt("1월");
				int feb = rs.getInt("2월");
				int mar = rs.getInt("3월");
				int apr = rs.getInt("4월");
				int may = rs.getInt("5월");
				int jun = rs.getInt("6월");
				int jul = rs.getInt("7월");
				int aug = rs.getInt("8월");
				int sep = rs.getInt("9월");
				int oct = rs.getInt("10월");
				int nov = rs.getInt("11월");
				int dec = rs.getInt("12월");
				return new StatMonDto(jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec);
			}
			return null;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public StatMonDto selMonAmount(Date startDate, Date endDate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT \n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='01',price * 0.0001,0)) AS '1월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='02',price * 0.0001,0)) AS '2월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='03',price * 0.0001,0)) AS '3월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='04',price * 0.0001,0)) AS '4월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='05',price * 0.0001,0)) AS '5월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='06',price * 0.0001,0)) AS '6월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='07',price * 0.0001,0)) AS '7월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='08',price * 0.0001,0)) AS '8월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='09',price * 0.0001,0)) AS '9월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='10',price * 0.0001,0))AS '10월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='11',price * 0.0001,0)) AS '11월',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%m')='12',price * 0.0001,0)) AS '12월'\n"
					+ "FROM  orders \n"
					+ " WHERE  status = 5 AND reg_date >= ? AND reg_date <= ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, startDate);
			pstmt.setDate(2, endDate);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int jan = rs.getInt("1월");
				int feb = rs.getInt("2월");
				int mar = rs.getInt("3월");
				int apr = rs.getInt("4월");
				int may = rs.getInt("5월");
				int jun = rs.getInt("6월");
				int jul = rs.getInt("7월");
				int aug = rs.getInt("8월");
				int sep = rs.getInt("9월");
				int oct = rs.getInt("10월");
				int nov = rs.getInt("11월");
				int dec = rs.getInt("12월");
				return new StatMonDto(jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec);
			}
			return null;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public StatDayDto selDaySell(Date startDate, Date endDate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();

			String sql = "SELECT \n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '01' then '1일' END  ) AS '1일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '02' then '2일' END  ) AS '2일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '03' then '3일' END  ) AS '3일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '04' then '4일' END  ) AS '4일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '05' then '5일' END  ) AS '5일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '06' then '6일' END  ) AS '6일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '07' then '7일' END  ) AS '7일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '08' then '8일' END  ) AS '8일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '09' then '9일' END  ) AS '9일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '10' then '10일' END  ) AS '10일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '11' then '11일' END  ) AS '11일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '12' then '12일' END  ) AS '12일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '13' then '13일' END  ) AS '13일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '14' then '14일' END  ) AS '14일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '15' then '15일' END  ) AS '15일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '16' then '16일' END  ) AS '16일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '17' then '17일' END  ) AS '17일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '18' then '18일' END  ) AS '18일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '19' then '19일' END  ) AS '19일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '20' then '20일' END  ) AS '20일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '21' then '21일' END  ) AS '21일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '22' then '22일' END  ) AS '22일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '23' then '23일' END  ) AS '23일',\n"
					+ "    COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '24' then '24일' END  ) AS '24일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '25' then '25일' END  ) AS '25일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '26' then '26일' END  ) AS '26일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '27' then '27일' END  ) AS '27일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '28' then '28일' END  ) AS '28일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '29' then '29일' END  ) AS '29일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '30' then '30일' END  ) AS '30일',\n"
					+ "	COUNT(\n"
					+ "		CASE (DATE_FORMAT(reg_date,'%d'))\n"
					+ "		when '31' then '31일' END  ) AS '31일'\n"
					+ "	\n"
					+ "	\n"
					+ "FROM  orders\n"
					+ "WHERE  status = 5 AND reg_date >= ? AND reg_date <= ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, startDate);
			pstmt.setDate(2, endDate);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int day1 = rs.getInt("1일");
				int day2 = rs.getInt("2일");
				int day3 = rs.getInt("3일");
				int day4 = rs.getInt("4일");
				int day5 = rs.getInt("5일");
				int day6 = rs.getInt("6일");
				int day7 = rs.getInt("7일");
				int day8 = rs.getInt("8일");
				int day9 = rs.getInt("9일");
				int day10 = rs.getInt("10일");
				int day11 = rs.getInt("11일");
				int day12 = rs.getInt("12일");
				int day13 = rs.getInt("13일");
				int day14 = rs.getInt("14일");
				int day15 = rs.getInt("15일");
				int day16 = rs.getInt("16일");
				int day17 = rs.getInt("17일");
				int day18 = rs.getInt("18일");
				int day19 = rs.getInt("19일");
				int day20 = rs.getInt("20일");
				int day21 = rs.getInt("21일");
				int day22 = rs.getInt("22일");
				int day23 = rs.getInt("23일");
				int day24 = rs.getInt("24일");
				int day25 = rs.getInt("25일");
				int day26 = rs.getInt("26일");
				int day27 = rs.getInt("27일");
				int day28 = rs.getInt("28일");
				int day29 = rs.getInt("29일");
				int day30 = rs.getInt("30일");
				int day31 = rs.getInt("31일");
				return new StatDayDto(day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15, day16, day17,
						day18, day19, day20, day21, day22, day23, day24, day25, day26, day27, day28, day29, day30, day31);
			}
			return null;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public StatDayDto selDayAmount(Date startDate, Date endDate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT \n"
					+ "	sum(if(DATE_FORMAT(reg_date,'%d')='01',price * 0.0001,0)) AS '1일',\n"
					+ "	sum(if(DATE_FORMAT(reg_date,'%d')='02',price * 0.0001,0)) AS '2일',\n"
					+ "	sum(if(DATE_FORMAT(reg_date,'%d')='03',price * 0.0001,0)) AS '3일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='04',price * 0.0001,0)) AS '4일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='05',price * 0.0001,0)) AS '5일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='06',price * 0.0001,0)) AS '6일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='07',price * 0.0001,0)) AS '7일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='08',price * 0.0001,0)) AS '8일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='09',price * 0.0001,0)) AS '9일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='10',price * 0.0001,0)) AS '10일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='11',price * 0.0001,0)) AS '11일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='12', price * 0.0001,0)) AS '12일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='13',price * 0.0001,0)) AS '13일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='14',price * 0.0001,0)) AS '14일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='15',price * 0.0001,0)) AS '15일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='16',price * 0.0001,0)) AS '16일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='17',price * 0.0001,0)) AS '17일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='18',price * 0.0001,0)) AS '18일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='19',price * 0.0001,0)) AS '19일',\n"
					+ "	sum(if(DATE_FORMAT(reg_date,'%d')='20',price * 0.0001,0)) AS '20일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='21',price * 0.0001,0)) AS '21일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='22',price * 0.0001,0)) AS '22일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='23',price * 0.0001,0)) AS '23일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='24',price * 0.0001,0)) AS '24일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='25',price * 0.0001,0)) AS '25일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='26',price * 0.0001,0)) AS '26일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='27',price * 0.0001,0)) AS '27일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='28',price * 0.0001,0)) AS '28일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='29',price * 0.0001,0)) AS '29일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='30',price * 0.0001,0)) AS '30일',\n"
					+ "    sum(if(DATE_FORMAT(reg_date,'%d')='31',price * 0.0001,0)) AS '31일'\n"
					+ "FROM  orders \n"
					+ "WHERE  status = 5 AND reg_date >= ? AND reg_date <= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, startDate);
			pstmt.setDate(2, endDate);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int day1 = rs.getInt("1일");
				int day2 = rs.getInt("2일");
				int day3 = rs.getInt("3일");
				int day4 = rs.getInt("4일");
				int day5 = rs.getInt("5일");
				int day6 = rs.getInt("6일");
				int day7 = rs.getInt("7일");
				int day8 = rs.getInt("8일");
				int day9 = rs.getInt("9일");
				int day10 = rs.getInt("10일");
				int day11 = rs.getInt("11일");
				int day12 = rs.getInt("12일");
				int day13 = rs.getInt("13일");
				int day14 = rs.getInt("14일");
				int day15 = rs.getInt("15일");
				int day16 = rs.getInt("16일");
				int day17 = rs.getInt("17일");
				int day18 = rs.getInt("18일");
				int day19 = rs.getInt("19일");
				int day20 = rs.getInt("20일");
				int day21 = rs.getInt("21일");
				int day22 = rs.getInt("22일");
				int day23 = rs.getInt("23일");
				int day24 = rs.getInt("24일");
				int day25 = rs.getInt("25일");
				int day26 = rs.getInt("26일");
				int day27 = rs.getInt("27일");
				int day28 = rs.getInt("28일");
				int day29 = rs.getInt("29일");
				int day30 = rs.getInt("30일");
				int day31 = rs.getInt("31일");
				return new StatDayDto(day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15, day16, day17,
						day18, day19, day20, day21, day22, day23, day24, day25, day26, day27, day28, day29, day30, day31);
			}
			return null;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public ArrayList<StatGenderDto> SelGender() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<StatGenderDto> list = new ArrayList<StatGenderDto>();
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT if(m.gender= 1,'남자', '여자') as 'gender', Count(o.onum) cnt \n"
					+ "FROM  orders o left outer join member m on o.num = m.num \n"
					+ "WHERE m.num IN(SELECT o.num\n"
					+ "FROM  orders   WHERE  status = 5)\n"
					+ "GROUP  BY gender ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int cnt = rs.getInt("cnt");
				String gender = rs.getString("gender");
				list.add(new StatGenderDto(cnt,gender));
			}
			return list;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}
}
