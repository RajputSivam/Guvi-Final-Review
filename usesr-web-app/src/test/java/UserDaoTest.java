import com.user.dao.UserDAO;

class UserDaoTest  {
	UserDAO userDAO=new UserDAO();
	
	@Test
	void selectUser_testcase1()
	{
		User user=userDAO.selectUser(1);
		assertNotNull(user);
	}
	
	@Test
	void selectAllUsers_testcase2()
	{
		List<User> users=userDAO.selectAllUsers();
		assersTrue(users.size()>0);	
	}
	
	@Test
	void deleteUser_testcase3()
	{
		Boolean status=userDAO.deleteUser(1);
		assertFalse(status);
	}

}
