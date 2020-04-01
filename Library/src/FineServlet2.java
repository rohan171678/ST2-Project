
/*
 * 
 * @uthor: Anupama 
 * 
 * To calculate the fine for a checked in book
 * 
 */
import java.io.IOException;
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
 * Servlet implementation class LoansServlet
 */
@WebServlet("/FineServlet2")
public class FineServlet2 extends HttpServlet {
	static Connection conn = null;
			
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FineServlet2() {
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
	
		if(!request.getParameter("book_radio").equals(""))
		cardno=Long.parseLong(request.getParameter("book_radio"));
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		/* Create a connection to the local MySQL server */
		conn = DriverManager.getConnection(url, user, password);

		/* Create a SQL statement object and execute the query */
		PreparedStatement ps = conn.prepareStatement("update fine set paid=1 where paid=0 and loan_id in (select loan_id from book_loans where card_no=? and Date_in is not null);");
		ps.setLong(1, cardno);
		System.out.println(ps+" is the query");
		
		ps.executeUpdate();
		request.setAttribute("message", "Paid!!");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Fine.jsp");
		dispatcher.forward(request,response);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
