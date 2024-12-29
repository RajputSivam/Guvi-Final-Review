const UserDAO = require('./UserDAO'); // Adjust this path to where your UserDAO is located
const { User } = require('./UserModel'); // Adjust this path to where your User model is located

describe('UserDAO Tests', () => {
    let userDAO;

    beforeEach(() => {
        userDAO = new UserDAO();
    });

    // Test case 1: selectUser - Fetching a user by ID
    test('selectUser_testcase1', async () => {
        const user = await userDAO.selectUser(1);
        expect(user).toBeTruthy(); // Should not be null or undefined
    });

    // Test case 2: selectAllUsers - Fetching all users
    test('selectAllUsers_testcase2', async () => {
        const users = await userDAO.selectAllUsers();
        expect(users.length).toBeGreaterThan(0); // Should have more than 0 users
    });

    // Test case 3: deleteUser - Deleting a user by ID
    test('deleteUser_testcase3', async () => {
        const status = await userDAO.deleteUser(1);
        expect(status).toBe(false); // Should return false after a deletion attempt
    });

    // Test case 4: insertUser - Inserting a new user
    test('insertUser_testcase4', async () => {
        const newUser = new User('testuser', 'testuser@example.com', 'Test Country', 'password123');
        await userDAO.insertUser(newUser);

        const insertedUser = await userDAO.selectUser(1); // Assuming the ID is auto-incremented
        expect(insertedUser).toBeTruthy();
        expect(insertedUser.name).toBe('testuser');
        expect(insertedUser.email).toBe('testuser@example.com');
        expect(insertedUser.country).toBe('Test Country');
        expect(insertedUser.password).toBe('password123');
    });

    // Test case 5: updateUser - Updating a user by ID
    test('updateUser_testcase5', async () => {
        const updatedUser = new User('updateduser', 'updateduser@example.com', 'Updated Country', 'newpassword123');
        await userDAO.updateUser(1, updatedUser); // Assuming user with ID 1 exists

        const fetchedUser = await userDAO.selectUser(1);
        expect(fetchedUser.name).toBe('updateduser');
        expect(fetchedUser.email).toBe('updateduser@example.com');
        expect(fetchedUser.country).toBe('Updated Country');
        expect(fetchedUser.password).toBe('newpassword123');
    });

    // Test case 6: selectUser - Non-existing user ID
    test('selectUser_nonExistingUser_testcase6', async () => {
        const user = await userDAO.selectUser(9999); // Assuming ID 9999 does not exist
        expect(user).toBeNull(); // Should return null if user is not found
    });

    // Test case 7: selectAllUsers - No users in the database
    test('selectAllUsers_noUsers_testcase7', async () => {
        const users = await userDAO.selectAllUsers();
        expect(users).toHaveLength(0); // Should return an empty array if there are no users
    });

    // Test case 8: deleteUser - Deleting a non-existing user
    test('deleteUser_nonExistingUser_testcase8', async () => {
        const status = await userDAO.deleteUser(9999); // Assuming ID 9999 doesn't exist
        expect(status).toBe(false); // Should return false if the user doesn't exist
    });

    // Test case 9: insertUser - Inserting a user with missing fields
    test('insertUser_missingFields_testcase9', async () => {
        const newUser = new User('testuser', '', 'Test Country', ''); // Missing email and password
        try {
            await userDAO.insertUser(newUser);
        } catch (e) {
            expect(e).toBeDefined(); // Expect an error due to invalid data
        }
    });

    // Test case 10: updateUser - Updating non-existing user
    test('updateUser_nonExistingUser_testcase10', async () => {
        const nonExistingUser = new User('nonexistent', 'nonexistent@example.com', 'Nowhere', 'password123');
        const result = await userDAO.updateUser(9999, nonExistingUser); // ID 9999 does not exist
        expect(result).toBe(false); // Should return false as user doesn't exist
    });

    // Test case 11: selectAllUsers - Multiple users
    test('selectAllUsers_multipleUsers_testcase11', async () => {
        const users = await userDAO.selectAllUsers();
        expect(users.length).toBeGreaterThan(1); // Should return a list with more than one user if multiple users exist
    });

    // Test case 12: insertUser - Handling SQL error (simulating a database failure)
    test('insertUser_sqlError_testcase12', async () => {
        const newUser = new User('erroruser', 'erroruser@example.com', 'Error Country', 'errorpass');
        // Simulating a database error by forcing a bad connection or wrong SQL query
        userDAO.getConnection = jest.fn().mockRejectedValue(new Error('Database connection error'));

        try {
            await userDAO.insertUser(newUser);
        } catch (e) {
            expect(e.message).toBe('Database connection error'); // Should throw an error
        }
    });
});


