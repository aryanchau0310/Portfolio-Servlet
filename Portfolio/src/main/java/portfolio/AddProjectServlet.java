package portfolio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class AddProjectServlet
 */
@WebServlet("/AddProjectServlet")
public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title = request.getParameter("title");
	    String tech = request.getParameter("technologies");
	    String desc = request.getParameter("description");

	    Part imagePart = request.getPart("image");
	    String imageName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
	    String uploadPath = getServletContext().getRealPath("/images/") + File.separator + imageName;
	    imagePart.write(uploadPath);

	    try (Connection con = DBConnection.getConnection()) {
	      String sql = "INSERT INTO projects (title, technologies, description, image) VALUES (?, ?, ?, ?)";
	      PreparedStatement ps = con.prepareStatement(sql);
	      ps.setString(1, title);
	      ps.setString(2, tech);
	      ps.setString(3, desc);
	      ps.setString(4, "images/" + imageName);
	      ps.executeUpdate();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	    response.sendRedirect("admin.jsp");
	  }
	
	

}
