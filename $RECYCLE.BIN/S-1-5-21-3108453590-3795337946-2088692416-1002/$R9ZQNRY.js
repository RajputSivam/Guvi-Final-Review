document.getElementById('loginForm').addEventListener('submit', function (event) {
    // Get references to form fields
    const username = document.getElementById('login-username');
    const password = document.getElementById('login-password');

    // Reset previous validation feedback
    username.classList.remove('is-invalid');
    password.classList.remove('is-invalid');

    // Flag to track if form is valid
    let isValid = true;

    // Validate username field
    if (!username.value) {
        username.classList.add('is-invalid');  // Add invalid class to the field
        isValid = false;
    }

    // Validate password field
    if (!password.value) {
        password.classList.add('is-invalid');  // Add invalid class to the field
        isValid = false;
    }

    // Prevent form submission if any field is invalid
    if (!isValid) {
        event.preventDefault(); // Stop form submission
    }
});
