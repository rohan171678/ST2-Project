

/*
 * 
 * @uthor: Anupama 
 * 
 * To check out a book
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
@WebServlet("/CheckInServlet")
public class CheckInServlet extends HttpServlet {
	static Connection conn = null;
			
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckInServlet() {
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
		
		String bookid =request.getParameter("bookid");
		String borrower_name=request.getParameter("borrower_name");
		
		if(!request.getParameter("cardno").equals(""))
		cardno=Long.parseLong(request.getParameter("cardno"));
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		/* Create a connection to the local MySQL server */
		conn = DriverManager.getConnection(url, user, password);

		/* Create a SQL statement object and execute the query */
		PreparedStatement ps = conn.prepareStatement(" select Loan_id,card_no,concat(fname,' ',lname) as Name,book_id,title,Branch_id from book_loans natural join borrower natural join book where ((fname=? or lname=?) or card_no= ? or Book_id=? )and date_in is null ;");
		ps.setString(1, borrower_name);
		ps.setString(2, borrower_name);
		ps.setLong(3, cardno);
		ps.setString(4, bookid);
		System.out.println(ps+" is the query");
		
		ResultSet rs = ps.executeQuery();
		request.setAttribute("loans", rs);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CheckIn.jsp");
		dispatcher.forward(request,response);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
