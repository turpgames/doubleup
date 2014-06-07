package com.turpgames.doubleup.servlet2;

import javax.servlet.annotation.WebServlet;

import com.turpgames.servlet.TurpServlet;

@WebServlet("/v2")
public class DoubleupServlet extends TurpServlet {
	private static final long serialVersionUID = -1966323018621072378L;

	public DoubleupServlet() {
		super(new DoubleupServletProvider());
	}
}