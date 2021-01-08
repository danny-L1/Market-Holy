package com.market.qna.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.market.page.util.PageUtil;
import com.market.product.dao.ProductDao;
import com.market.product.dto.ProductDto;
import com.market.qna.dao.QnaDao;
import com.market.qna.dto.QnaDto;

@WebServlet("/qna/qnaList.do")
public class QnaListController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String sPnum=req.getParameter("pnum");
		int pnum=1;
		if(sPnum!=null) {
			pnum=Integer.parseInt(req.getParameter("pnum"));
		}
		
	
		
		String spageNum=req.getParameter("pageNum");
		int pageNum=1;
		if(spageNum!=null) {
			pageNum=Integer.parseInt(spageNum);
		}
		

		QnaDao dao = QnaDao.getInstance();
		int totalRowCount=dao.getCount(pnum);
		PageUtil pu=new PageUtil(pageNum, totalRowCount, 4, 5);
		int startRow=pu.getStartRow()-1;
		int endRow=startRow+4;
		ArrayList<QnaDto> list=dao.list(startRow, endRow, pnum);
	
		
		
		//페이지에 해당하는 글목록 가져오기

		
		int pageCount=pu.getTotalPageCount();
		int startPageNum=pu.getStartPageNum();
		int endPageNum=pu.getEndPageNum();
		if(pageCount<endPageNum) {
			endPageNum=pageCount;
		}
		
		HttpSession session=req.getSession();
		String id=(String)session.getAttribute("id");
		
	
		ProductDao pdao=new ProductDao();
		ProductDto dto=pdao.getDetail(pnum);

		req.setAttribute("count", totalRowCount);
		req.setAttribute("pnum", pnum);
		req.setAttribute("dto",dto);
		req.setAttribute("id",id);
		req.setAttribute("list",list);
		req.setAttribute("pageCount",pageCount);
		req.setAttribute("startPage",startPageNum);
		req.setAttribute("endPage",endPageNum);
		req.setAttribute("pageNum",pageNum);
		req.setAttribute("plag", 3);
		
		//req.getRequestDispatcher("/product/detail.jsp?page=/qna/listQna.jsp").forward(req, resp);
		req.getRequestDispatcher("/index.jsp?page=product/detail.jsp&tabpage=qna/listQna.jsp").forward(req, resp);
		
	}
}
