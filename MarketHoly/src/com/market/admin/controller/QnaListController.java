package com.market.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.market.admin.dao.QnaAdminDao;
import com.market.admin.dto.QnaAdminDto;
import com.market.page.util.PageUtil;

@WebServlet("/admin/qnaList.do")
public class QnaListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String kind = req.getParameter("kind");
		if (kind == null) {
			kind = "";
		}
		String word = req.getParameter("word");
		if (word == null) {
			word = "";
		}
		
		String spageNum = req.getParameter("pageNum");
		int pageNum = 1;
		if (spageNum != null) {
			pageNum = Integer.parseInt(spageNum);
		}
	
		QnaAdminDao dao = QnaAdminDao.getInstance();
		int totalRowCount = dao.selQnaCount(kind, word);
		PageUtil pu=new PageUtil(pageNum, totalRowCount, 6, 2);
		int startRow=pu.getStartRow()-1;
		int pageCount = pu.getTotalPageCount();
		int startPageNum = pu.getStartPageNum();
		int endPageNum = pu.getEndPageNum();
		
		ArrayList<QnaAdminDto> qnaList = dao.selQnaList(startRow, kind, word);
		
		JSONArray jsonArr = new JSONArray();
		JSONArray jarr = new JSONArray();
		for (QnaAdminDto dto : qnaList) {
			JSONObject json = new JSONObject();
			json.put("qnum", dto.getQnum());
			json.put("pname", dto.getPname());
			json.put("title", dto.getTitle());
			json.put("writer", dto.getName());
			json.put("ref", dto.getRef());
			json.put("reg_date", dto.getReg_date());
			json.put("content", dto.getContent());
			json.put("pnum", dto.getPnum());
			jarr.put(json);
		}
		

		jsonArr.put(jarr);
		jsonArr.put(startPageNum);
		jsonArr.put(endPageNum);
		jsonArr.put(pageNum);
		jsonArr.put(pageCount);
		resp.setContentType("text/plain;charset=utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print(jsonArr);
	}
}
