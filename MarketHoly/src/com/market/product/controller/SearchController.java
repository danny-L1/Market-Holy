package com.market.product.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.market.admin.dao.CategoryDao;
import com.market.admin.dto.CategoryDto;
import com.market.member.dto.MemberDto;
import com.market.page.util.PageUtil;
import com.market.product.dao.ProductDao;
import com.market.product.dto.ProductDto;
@WebServlet("/product/search.do")
public class SearchController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("memberDto");
		String id="";
		if (memberDto != null) {
			id=memberDto.getId();
		}
		String keyword=req.getParameter("keyword");
	
		String spageNum = req.getParameter("pageNum");
		int pageNum = 1;
		if (spageNum != null) {
			pageNum = Integer.parseInt(spageNum);
		}
		ProductDao dao = new ProductDao();
		int totalRowCount=dao.getCount(0, 0, keyword);
		PageUtil pu=new PageUtil(pageNum, totalRowCount, 6, 2);
		int startRow = pu.getStartRow()-1;
		ArrayList<ProductDto> list = dao.getSearchList(startRow, keyword);
		
		
		req.setAttribute("keyword", keyword);
		req.setAttribute("id", id);
		req.setAttribute("list", list);
		req.setAttribute("pu", pu);
		req.setAttribute("pageNum", pageNum);
		
		req.getRequestDispatcher("/index.jsp?page=product/search_list.jsp").forward(req, resp);
	}
}
