import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/UserManagement")
public class UserManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC connection variables
    private Connection connection;
    
    // Init method to initialize the database connection
    public void init() throws ServletException {
        try {
            // Set up connection with your database
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fraud_detection_db", "root", "password"); // Update with your database credentials
        } catch (Exception e) {
            throw new ServletException("Database connection failed.", e);
        }
    }

    // Handle all the user management functionalities
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "login"; // Default action is login
        }

        switch (action) {
            case "login":
                login(request, response);
                break;
            case "register":
                register(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            case "createAccount":
                createAccount(request, response);
                break;
            case "deleteAccount":
                deleteAccount(request, response);
                break;
            case "updateAccount":
                updateAccount(request, response);
                break;
            default:
                response.sendRedirect("login.jsp");
        }
    }

    // User Login
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("dashboard.jsp"); // Redirect to dashboard after login
            } else {
                response.sendRedirect("login.jsp?error=Invalid credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Error occurred");
        }
    }

    // User Registration
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users(username, password, email) VALUES(?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                response.sendRedirect("login.jsp?message=Registration successful. Please login.");
            } else {
                response.sendRedirect("register.jsp?error=Registration failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=Error occurred");
        }
    }

    // User Logout
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp?message=You have logged out successfully");
    }

    // Create Account (Admin)
    private void createAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users(username, password, email) VALUES(?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                response.sendRedirect("adminDashboard.jsp?message=Account created successfully");
            } else {
                response.sendRedirect("adminDashboard.jsp?error=Failed to create account");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("adminDashboard.jsp?error=Error occurred");
        }
    }

    // Delete Account (Admin)
    private void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));

        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE id=?");
            stmt.setInt(1, userId);
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                response.sendRedirect("adminDashboard.jsp?message=Account deleted successfully");
            } else {
                response.sendRedirect("adminDashboard.jsp?error=Failed to delete account");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("adminDashboard.jsp?error=Error occurred");
        }
    }

    // Update Account Information (Admin)
    private void updateAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET username=?, password=?, email=? WHERE id=?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setInt(4, userId);
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                response.sendRedirect("adminDashboard.jsp?message=Account updated successfully");
            } else {
                response.sendRedirect("adminDashboard.jsp?error=Failed to update account");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("adminDashboard.jsp?error=Error occurred");
        }
    }

    // Cleanup on shutdown
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

