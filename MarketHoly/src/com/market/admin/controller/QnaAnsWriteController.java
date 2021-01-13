package com.market.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.market.admin.dao.QnaAdminDao;
import com.market.member.dto.MemberDto;
import com.market.qna.dto.QnaDto;

@WebServlet("/admin/qnaAnsWrite.do")
public class QnaAnsWriteController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("memberDto");

		QnaAdminDao dao = QnaAdminDao.getInstance();
		//int qnum = dao.getMaxNum()+1;
		int pnum = Integer.parseInt(req.getParameter("pnum"));
		int num = memberDto.getNum();
		int ref = Integer.parseInt(req.getParameter("qnum")); //답변할 글의 글번호를 답변의 ref에 넣어주기
		String id = memberDto.getId();
		String name = memberDto.getName();
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		
	
		int n = dao.insAns(new QnaDto(pnum, num, 0, id, name, title, content, ref, null, "N", "N"));
		JSONObject json = new JSONObject();
		json.put("n", n);

		resp.setContentType("text/plain;charset=utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print(json);
	}
}
