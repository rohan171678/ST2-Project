/*
 * 
 * @uthor: Anupama 
 * 
 * To calculate the fine for a checked in book
 * 
 */

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FineServlet
 */
@WebServlet("/FineServlet")
public class FineServlet extends HttpServlet {
	static Connection conn = null;
			
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FineServlet() {
        super();
        //System.out.println("Search Servlet");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String url = "jdbc:mysql://localhost:3306/Library";
		String user = "root";
		String password = "asd";
		long cardno=(long)0;
		
		if(!request.getParameter("cardno").equals(""))
		cardno=Long.parseLong(request.getParameter("cardno"));
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		/* Create a connection to the local MySQL server */
		conn = DriverManager.getConnection(url, user, password);
		
		//stored procedure
		CallableStatement cs = conn.prepareCall("{call REFRESH()}");
		cs.execute();
				

		/* Create a SQL statement object and execute the query */
		PreparedStatement ps = conn.prepareStatement(" select sum(fine_amt) as fine_amt,card_no from book_loans natural join fine where card_no=? and paid=0 group by card_no;");
		ps.setLong(1, cardno);
		
		ResultSet rs = ps.executeQuery();
		request.setAttribute("fine", rs);
		
		PreparedStatement ps1 = conn.prepareStatement(" select sum(fine_amt) as fine_amt,card_no from book_loans natural join fine where card_no=? and paid=1 group by card_no;");
		ps1.setLong(1, cardno);
		
		ResultSet rs1 = ps1.executeQuery();
		request.setAttribute("paid", rs1);
		System.out.println(ps+ "/n"+ ps1);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Fine.jsp");
		dispatcher.forward(request,response);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
