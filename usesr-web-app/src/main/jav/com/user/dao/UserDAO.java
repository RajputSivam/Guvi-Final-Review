package com.user.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.sun.jndi.ldap.Connection;
import com.user.model.User;

public class UserDAO {
	private String jdbcURL="jdbc:mysl://localhost:30006/userappdb";
	private String jdbcUserName="root";
	private String jdbcPassword="root";
	
	private static final String INSERT_USER_SQL="INSERT  INTO uses"+"(username,email,country,password) VALUES "+" (?,?,?,?)";
	private static final String SELECT_USER_BY_ID="SELECT * FROM USERS WHERE ID=? ";
	private static final String DELETE_USERS_SQL="DELETE FROM USERS WHERE ID=?;";
	private static final String UPDATE_USERS_SQL="UPDATE SET UNAME=?,EMAIL=? , COUNTRY=?,PASSWORS=? where  ID=? ";
	public UserDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Connection getConnection()
	{
		Connection Connection=null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(jdbcURL,jdbcUserName,jdbcPassword);
				
		}
		catch(SQLException | ClassNotFoundException e)
		{
			e.pritnStackTrace();
		}
		catch(Exception e)
		{
			e.pritnStackTrace();
		}
		return connection;
		}
	
	public void insertUser(User user)
	{
		UserDAO dao=new UserDAO;
		try (Connection connectin=dao.getConnection())
		{
			PreparedStatement preparedStatement=connectionStatment (INSERT_USER_SQL);
			preparedStatement.setString(1,user.getName(2,user.getEmail());
			preparedStatement.setString(3, user.getCountry);
			preparedStatement.setString(4,user.getPassword));
			
			preparedStatement.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public User selectUser(int id)
	{
		User user=new User ();
		UserDAO dao=new UserDAO();
		
		try(Connection connection=dao.getConneciton())
		{
			preparedStatement preparedStatment=connection.preparedStatment (
					SELECT_USER_BY_ID);
			ResultSet resultSet=preparedStatment.executeQuery();
			
			while (resultSet.next())
			{
				user.setId(id);
				user.setName(resultSet.getString("uname"))
				user.setEmail(resultSet.getString("email"));
				user.setCountry(resultSet.getString("country"));
				user.setPassword(resultSet.getString("password"));
				
			}
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return user;
		}
	public List <Users> selectAllUsers()
	{
		List <User> users=new ArrayList<Users>();
		UserDAD dao=new UserDAD();
		try(Connection connection =dao.getConnection() )
		{
			PreparedStatement preparedStatment=connection.preparedStatement(SELECT_ALL_USERS);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				int id=resultSet.getInt("id");
				String uname=resultSet.getString("uname");
				String email=resultSet.getString("email");
				String country=resultSet.getString("country");
				String password=resultSet.getString("passwd");
				
				users.add(new User(id,uname, email,country,password));
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return users;
	}
	public boolean deleteUser(int id)
	{
		boolean status=false;
		UserDAO dao=new  UserDAO();
		
		try(Connection connection=dao.getConnection )
		{
			PreparedStatement preparedstatement=connection.preparedStatement (DELETE_USERS_SQL);
			preparedStatement.setInt(1,id);
			
			status=preparedStatement.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	
	}
	

}
