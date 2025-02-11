
function detectFraud() {
    const transactionId = document.getElementById('transaction-id').value;
    const amount = parseFloat(document.getElementById('amount').value);
    const userLocation = document.getElementById('user-location').value;
    const device = document.getElementById('device').value;

    // Simple mock fraud detection logic
    let fraudDetected = false;

    // Example logic: If amount is unusually high or location is strange, flag as fraud
    if (amount > 10000 || userLocation.toLowerCase() === 'suspicious') {
        fraudDetected = true;
    }

    // Simulating AI-based logic or API request for advanced checks
    // In a real-world application, this would call an AI model or backend API
    if (fraudDetected) {
        document.getElementById('fraud-status').innerText = 'Fraudulent Activity Detected!';
        document.getElementById('fraud-status').style.color = '#d9534f'; // Red color
    } else {
        document.getElementById('fraud-status').innerText = 'No Fraud Detected';
        document.getElementById('fraud-status').style.color = '#5bc0de'; // Blue color
    }

    // Clear the form after detection
    document.getElementById('fraud-form').reset();
}
