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

import com.market.admin.dao.OrderAdminDao;
import com.market.admin.dto.OrderAdminDto;
import com.market.page.util.PageUtil;

@WebServlet("/admin/orderList.do")
public class OrderListController extends HttpServlet {
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
		String status = req.getParameter("status");
		if (status == null) {
			status = "";
		}

		String spageNum = req.getParameter("pageNum");
		int pageNum = 1;
		if (spageNum != null) {
			pageNum = Integer.parseInt(spageNum);
		}
	
		OrderAdminDao ordDao = OrderAdminDao.getInstance();
		int totalRowCount=ordDao.selOrdCnt(kind, word, status) ;
		PageUtil pu=new PageUtil(pageNum, totalRowCount, 6, 2);
		int startRow=pu.getStartRow()-1;
		ArrayList<OrderAdminDto> ordList = ordDao.selOrdList(startRow, kind, word, status);
		int pageCount = pu.getTotalPageCount();
		int startPageNum = pu.getStartPageNum();
		int endPageNum = pu.getEndPageNum();
		
		JSONArray jsonArr = new JSONArray();
		JSONArray jarr = new JSONArray();
		for (OrderAdminDto dto : ordList) {
			JSONObject json = new JSONObject();
			json.put("onum", dto.getOnum());
			json.put("id", dto.getId());
			json.put("statusName", dto.getStatusName());
			json.put("prodName", dto.getProdName());
			json.put("pay_yn", dto.getPay_yn());
			json.put("price", dto.getPrice());
			json.put("addr", dto.getAddr());
			json.put("pay_wayName", dto.getPay_wayName());
			json.put("use_point", dto.getUse_point());
			json.put("reg_date", dto.getReg_date());
			
			json.put("num", dto.getNum());
			json.put("rating", dto.getRating());
			json.put("status", dto.getStatus());
			
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
