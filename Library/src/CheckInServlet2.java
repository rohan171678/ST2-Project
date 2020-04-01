
/*
 * 
 * @uthor: Anupama 
 * 
 * To check in a book
 * 
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckInServlet2
 */
@WebServlet("/CheckInServlet2")
public class CheckInServlet2 extends HttpServlet {
	static Connection conn = null;
			
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckInServlet2() {
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
		int branchid=0;
		String bookradio = request.getParameter("book_radio");
		String str[] = bookradio.split(",");
		str[2] = str[2].substring(0, str[2].length());
		Date d = new Date();		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		/* Create a connection to the local MySQL server */
		conn = DriverManager.getConnection(url, user, password);

		/* Create a SQL statement object and execute the query */
		PreparedStatement ps = conn.prepareStatement("Update book_copies set no_of_copies= no_of_copies+1 where book_id=? and branch_id=?;");
		PreparedStatement ps1 = conn.prepareStatement("Update book_loans set Date_in=? where book_id=? and branch_id=? and card_no=? and loan_id=?;");
		ps.setString(1, str[0]);
		ps.setInt(2, Integer.parseInt(str[1]));
		
		
		ps.executeUpdate();
		ps1.setString(1, sdf.format(d));
		ps1.setString(2, str[0]);
		ps1.setInt(3, Integer.parseInt(str[1]));
		ps1.setLong(4, Long.parseLong(str[2]));
		ps1.setLong(5, Long.parseLong(str[3]));
		ps1.executeUpdate();
		request.setAttribute("message", "BOOK CHECKED IN!!!");
		System.out.println(ps + "\n"+ps1);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CheckIn.jsp");
		dispatcher.forward(request,response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
