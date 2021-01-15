package com.market.review.controller;

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
import com.market.review.dao.ReviewDao;
import com.market.review.dto.ReviewDto;

@WebServlet("/review/listReview.do")
public class ListReviewController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		
		ReviewDao dao = ReviewDao.getInstance();
		int totalRowCount= dao.getCount();
		PageUtil pu=new PageUtil(pageNum, totalRowCount, 6, 2);
		int startRow=pu.getStartRow()-1;
		ArrayList<ReviewDto> list = dao.listReview(startRow,pnum);
		
		int pageCount = pu.getTotalPageCount();
		int startPageNum = pu.getStartPageNum(); 
		int endPageNum = pu.getEndPageNum();
		
		HttpSession session=req.getSession();
		String id=(String)session.getAttribute("id");
		
		ProductDao pdao=new ProductDao();
		ProductDto dto=pdao.getDetail(pnum);

		req.setAttribute("pnum", pnum);
		req.setAttribute("dto",dto);
		req.setAttribute("id",id);
		req.setAttribute("list", list);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("startPage", startPageNum);
		req.setAttribute("endPage", endPageNum);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("plag", 2);

		
		//System.out.println(list.get(0).getId());
		req.getRequestDispatcher("/index.jsp?page=product/detail.jsp&tabpage=review/reviewList.jsp").forward(req, resp);
		//"/index.jsp?page=/member/joinResult.jsp"
	}
}



