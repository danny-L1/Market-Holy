package com.market.admin.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.market.common.dao.CommonDao;
import com.market.common.dto.CommonDto;

@WebServlet("/admin/qna.do")
public class QnaController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CommonDao dao = CommonDao.getInstance();
		ArrayList<CommonDto> comList = dao.selComList("qna검색 combobox");
		req.setAttribute("comList", comList);
		req.getRequestDispatcher("/index.jsp?page=/admin/qna.jsp").forward(req, resp);
	}
}
