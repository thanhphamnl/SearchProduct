package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.DBConnection;

/**
 * Servlet implementation class searchproduct
 */
@WebServlet("/searchproduct")
public class searchproduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchproduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("product_id");	
		
		if (!isInteger(id)) {		
			//	To redirect use:
			response.sendRedirect("index.html");
		}
		else {			
			try {
				String sCreateTable ="";
				PrintWriter out = response.getWriter();
				out.println( "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n");
				out.println("<html>\n");
				out.println("<style>table { width:100%;}table, th, td { border: 1px solid black; border-collapse: collapse;}" +
				"th, td { padding: 15px; text-align: left;}table#t01 tr:nth-child(even) { background-color: #eee;}"+
				"table#t01 tr:nth-child(odd) { background-color: #fff;}table#t01 th {background-color: black; color: white;}"+
				"</style>");				
				out.println("<body>");
				out.println("<a href=\"index.html\">Back To Home Page</a><br><br>");
				out.println("<h1>WELCOME TO MY PRODUCT PAGE</h1>" + "<Br>");
				
				InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
				Properties props = new Properties();
				props.load(in);
				
				DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"),
						props.getProperty("password"));
				Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);				
				ResultSet rst = stmt.executeQuery("select * from eproduct where ID = "+ id);
				
				if(rst.next()){					
					out.println("<table id="+"'"+"t01"+"'"+">" + "<tr> <th>Product Id</th> <th>Product Name</th> <th>Price</th></tr>"+
							 "<tr><td>"+ rst.getInt("ID")+"</td> <td>"+ rst.getString("name")+ "</td><td>"+ rst.getString("price")+"</td></tr></table>");
					}else {
						rst = null;
						rst = stmt.executeQuery("select * from eproduct");			
										
					/*
					 * out.println("<table id="+"'"+"t01"+"'"+">" +
					 * "<tr> <th>Product Id</th> <th>Product Name</th></tr> "+
					 * "<tr><td colspan=\"2\"><h3>"+ProNotAvailable+"</h3></td></tr>");
					 */
			
					sCreateTable ="<table id="+"'"+"t01"+"'"+ "<tr> <th>Product Id</th> <th>Product Name</th> <th>Price</th></tr>";
					while (rst.next()) {
						sCreateTable = sCreateTable +"<tr><td>"+ rst.getInt("ID")+"</td> <td>"+ rst.getString("name")+ "</td><td>"+ rst.getString("price")+"</td></tr>";
					}
					out.println ("<p style="+"font-size:25px;color:red;>Sorry! The product id = "+ id + " is not available for now! Please see the other available product below:</p>");	
					out.println(sCreateTable +"</table>");					
				}
			
				stmt.close();

				out.println("</body></html>");
				conn.closeConnection();

			} catch (ClassNotFoundException e) {			
				System.out.println("SQLException: " + e.getMessage());
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			    System.out.println("SQLState: " + e.getSQLState());
			    System.out.println("VendorError: " + e.getErrorCode());
			}
		}
	}	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	 private boolean isInteger(String s) {
	      boolean isValidInteger = false;
	      try
	      {
	         Integer.parseInt(s);
	         isValidInteger = true;
	      }
	      catch (NumberFormatException ex)
	      {
	         // s is not an integer
	      }
	 
	      return isValidInteger;
	   }

}
