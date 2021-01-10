package com.market.product.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.market.page.util.PageUtil;
import com.market.product.dao.ProductDao;
import com.market.product.dto.ProductDto;
@WebServlet("/product/nbs.do")
public class NBSController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.최근 일주일 등록 상품
		//2.인기상품
		//3.세일상품
		String filter=req.getParameter("filter");		
		String spageNum = req.getParameter("pageNum");
		int pageNum = 1;
		if (spageNum != null) {
			pageNum = Integer.parseInt(spageNum);
		}
		ProductDao dao = new ProductDao();
		int totalRowCount=dao.getNBSCount(filter);
		PageUtil pu=new PageUtil(pageNum, totalRowCount, 6, 2);
		int startRow = pu.getStartRow()-1;
		ArrayList<ProductDto> list = dao.getNBSList(startRow, filter);
	

		
		req.setAttribute("filter", filter);
		req.setAttribute("pu", pu);
		req.setAttribute("list", list);
		req.setAttribute("pageNum", pageNum);
		req.getRequestDispatcher("/index.jsp?page=product/nbs_list.jsp").forward(req, resp);
	}
}
