/*
 * 
 * @uthor: Anupama 
 * 
 * To search for a book in the database
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
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	static Connection conn = null;
			
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        
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
		
		if(!request.getParameter("bookid").equals(""))
		bookid=request.getParameter("bookid");
		
		String booktitle = request.getParameter("booktitle");
		String author = request.getParameter("author");
		
		String bookname=null;
		String query="select * from Book_authors natural join book where book_id=? and title like ? or author_name like ?; ";
		
		int flag1 =0, flag2=0, flag3=0;
		
		if(request.getParameter("bookid").equals(""))
			 flag1 =0;
			else
			  flag1 =1;
			if(request.getParameter("booktitle").equals(""))
			 flag2=0;
			else
			 flag2=1;
			if(request.getParameter("author").equals(""))
			 flag3 =0;
			else
			 flag3=1;
			 
			 int a= flag1 * 4 + flag2 * 2 + flag3 * 1;
			 
			/** 1 2 3  bookid-booktitle-author
			 000 0
			 001 1
			 010 2
			 011 3
			 100 4
			 101 5
			 110 6
			 111 7 **/
			 System.out.println("case  " + a);
			 
			 switch(a)
			 {
			 case 0 :
				  query= "(select * from Book_authors natural join book natural join book_copies natural join book_count where book_id=? and title= ? and author_name like ?) UNION (select * from Book_authors natural join book natural join book_copies natural join book_count where book_id=0000 and title like 'xxxxxx' and author_name like 'xxxxxxxx'); ";
				  break;
				  
				 case 1 :
				  query= "(select * from Book_authors natural join book natural join book_copies natural join book_count where book_id=? and title like ? and book_id=0000000) UNION(select * from Book_authors natural join book natural join book_copies natural join book_count where author_name like ?);";
				  break;
				 
				 case 2 :
				 query= "(select * from Book_authors natural join book natural join book_copies natural join book_count where book_id=? and book_id=0000000) UNION(select * from Book_authors natural join book natural join book_copies natural join book_count where title like ? ) UNION (select * from Book_authors natural join book natural join book_copies natural join book_count where author_name like ? and book_id=0000000) ;";
				  break;
				  
				case 3 :
				  query= "(select * from Book_authors natural join book natural join book_copies natural join book_count where book_id=? and book_id=0000000) UNION(select * from Book_authors natural join book natural join book_copies natural join book_count where title like ? and author_name like ?  );";
				  break;
				  
				case 4 :
				  query= "(select * from Book_authors natural join book natural join book_copies natural join book_count where book_id= ?  )UNION(select * from Book_authors natural join book natural join book_copies natural join book_count where title like ? and title like ? and book_id=0000000) ;";
				  break;  
				 
				 case 5 :
				  query= "select * from Book_authors natural join book natural join book_copies natural join book_count where book_id=? and title not in (?) and author_name like ?;";
				  break;
				  
				 case 6 :
				  query= "(select * from Book_authors natural join book natural join book_copies natural join book_count where book_id=? and title like ? ) UNION(select * from Book_authors natural join book natural join book_copies natural join book_count where author_name like ? and book_id=0000000);";
				  break;
				  
				 case 7 :
				  query= "select * from Book_authors natural join book natural join book_copies natural join book_count where book_id=? and title like ? and author_name like ? ; ";
				  break;
			 }
			 
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		/* Create a connection to the local MySQL server */
		conn = DriverManager.getConnection(url, user, password);

		/* Create a SQL statement object and execute the query */
		PreparedStatement ps1 = conn.prepareStatement(query );
		/*to give the values which is entered by the user */
		ps1.setString(1,bookid);
		ps1.setString(2,"%"+booktitle+"%");
		ps1.setString(3,"%"+author+"%");
		System.out.println(ps1);
		System.out.println( bookid + "id");
		
		//when book_id is given
		ResultSet rs1 = ps1.executeQuery();
		request.setAttribute("result", rs1);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Search.jsp");
		dispatcher.forward(request,response);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
