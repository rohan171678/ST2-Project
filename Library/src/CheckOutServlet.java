
/*
 * 
 * @uthor: Anupama 
 * 
 * To checkout a book
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
 * Servlet implementation class LoansServlet
 */
@WebServlet("/CheckOutServlet")
public class CheckOutServlet extends HttpServlet {
	static Connection conn = null;
			
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOutServlet() {
        super();
        System.out.println("Search Servlet");
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
		String bookid = null; 
		Long cardno=(long)0;
		String branchid=null;
		
		if(!request.getParameter("bookid").equals(""))
			bookid=request.getParameter("bookid");
		
		if(!request.getParameter("branchid").equals(""))
			branchid=request.getParameter("branchid");
		if(!request.getParameter("cardno").equals(""))
			cardno=Long.parseLong(request.getParameter("cardno"));
		
		Date d = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		/* Create a connection to the local MySQL server */
		conn = DriverManager.getConnection(url, user, password);

		/* Create a SQL statement object and execute the query */
		PreparedStatement ps1 = conn.prepareStatement("Select count(*)  from book_copies where book_id=? and branch_id=? and no_of_copies >0;");
		ps1.setString(1, bookid);
		ps1.setString(2, branchid);
		ResultSet rs1 = ps1.executeQuery();
		rs1.next();
		
		PreparedStatement ps2 = conn.prepareStatement("select count(*)  from book_loans where card_no=? group by card_no;");
		ps2.setLong(1, cardno);
		ResultSet rs2 = ps2.executeQuery();
		
		
		PreparedStatement ps = null, ps3 = null ;
		//System.out.println(rs2.getInt(1));
		//System.out.println(rs1.getInt(1));
		int a1= rs1.getInt(1);
		int a2=-1;
		if(rs2.next())
		{a2=rs2.getInt(1);
		rs2.first();
		}
		
		System.out.println(a1 + "  SDFGH  "+ a2);
		if (a1 > 0 && a2 <3)
		{
			 System.out.println("hiiii");
			ps= conn.prepareStatement("Insert into book_loans (Book_id, Branch_id, card_no, Date_out, Due_date)values (?,?,?,?,DATE_ADD(?,INTERVAL 14 DAY))");
			ps3 = conn.prepareStatement("Update book_copies set no_of_copies= no_of_copies-1 where book_id=? and branch_id=?;");
		    ps.setString(1,bookid);
			ps.setString(2, branchid);
			ps.setLong(3, cardno);
			ps.setString(4, sdf.format(d));
			ps.setString(5,sdf.format(d));
			ps3.setString(1, bookid);
			ps3.setString(2, branchid);
			ps.executeUpdate();
			ps3.executeUpdate();
			request.setAttribute("message", "BOOK CHECKED OUT!!!");	
		}
		else
		{
			request.setAttribute("message", "This check out is not possible!!!");
		}
		System.out.println(ps1 +  "\n"+ps2+"\n"+ ps+ "\n"+ps3);
		//System.out.println(ps2);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CheckOut.jsp");
		dispatcher.forward(request,response);
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
