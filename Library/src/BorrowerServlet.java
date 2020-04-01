
/*
 * 
 * @uthor: Anupama 
 * 
 * To add a borrower to the database
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
@WebServlet("/BorrowerServlet")
public class BorrowerServlet extends HttpServlet {
	static Connection conn = null;
			
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerServlet() {
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
		
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String address=request.getParameter("address");

		long cardno= (long)0;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		/* Create a connection to the local MySQL server */
		conn = DriverManager.getConnection(url, user, password);

		/* Create a SQL statement object and execute the query */
		
		//to get the card no
		PreparedStatement ps2 = conn.prepareStatement("select max(card_no) from Borrower");
		ResultSet rs2 = ps2.executeQuery();
		while (rs2.next())
		{
			cardno = rs2.getLong("max(card_no)");
		}
		
		
		//to check whether it already exists
		PreparedStatement ps = conn.prepareStatement("select * from Borrower where fname=? and lname= ? and address = ?");
		ps.setString(1, fname);
		ps.setString(2, lname);
		ps.setString(3, address);
		ResultSet rs = ps.executeQuery();
		System.out.println(ps);
		
		if(!rs.next())
		{
			PreparedStatement ps1 = conn.prepareStatement("Insert into Borrower (card_no, Fname, Lname, Address)values (?,?,?,?)");
			ps1.setLong(1, ++cardno);
			ps1.setString(2, fname);
			ps1.setString(3, lname);
			ps1.setString(4, address);
			ps1.executeUpdate();
			System.out.println(ps1);
			request.setAttribute("message", "Your information is updated!!!");
			
		}
		
		else
		{
		 request.setAttribute("message", "You are already a member!!!");	
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Borrower.jsp");
		dispatcher.forward(request,response);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
