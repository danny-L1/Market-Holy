package com.market.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/admin/ansGet.do")
public class QnaAnsGetController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		QnaAdminDao qnaDao = QnaAdminDao.getInstance();
		int qnum = Integer.parseInt(req.getParameter("qnum"));
		
		QnaAdminDto dto = qnaDao.getAnswer(qnum);
		JSONObject json = new JSONObject();
		json.put("title", dto.getTitle());
		json.put("content",dto.getContent());
	
		resp.setContentType("text/plain;charset=utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print(json);
	}
}
