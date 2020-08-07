package com.market.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.market.admin.dao.CategoryDao;

@WebServlet("/admin/catAdd.do")
public class CatAddController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String catName = req.getParameter("catName");
		CategoryDao dao = CategoryDao.getInstance();
		int n = dao.insCategory(catName);
		
		JSONObject json = new JSONObject();
		json.put("n", n);
		resp.setContentType("text/plain;charset=utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print(json);
		
//		if (n > 0) {
//			resp.sendRedirect(req.getContextPath() + "/admin/category.do");
//		} else {
//			resp.sendRedirect(req.getContextPath() + "/error.do");
//		}
	}
}
