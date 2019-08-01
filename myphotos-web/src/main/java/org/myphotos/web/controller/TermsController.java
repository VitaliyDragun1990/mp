package org.myphotos.web.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.web.router.Router;

@WebServlet("/terms")
public class TermsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private Router router;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		router.forwardToPage("terms", req, resp);
	}
}
