function handleResponse(response) {
    if (response.ok) {
        return response.json();
    } else {
        if (response.headers.get('content-type').includes('text/html')) {
            return response.text().then(text => {
                throw new Error('Server returned HTML response: ' + text);
            });
        }
        throw new Error('Network response was not ok: ' + response.statusText);
    }
}

function register() {
    var username = document.getElementById("regUsername").value;
    var password = document.getElementById("regPassword").value;
    var firstName = document.getElementById("regFirstName").value;
    var lastName = document.getElementById("regLastName").value;
    var address = document.getElementById("regAddress").value; // Optional

    var data = {
        username: username,
        password: password,
        firstName: firstName,
        lastName: lastName,
        address: address // Send as is, backend will handle it as optional
    };

    fetch('/productapp/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(handleResponse)
    .then(data => {
        if (data.id) { // Assuming successful registration returns a user object with an id
            document.getElementById("message").innerHTML = "Registration successful for username: " + username;
        } else {
            document.getElementById("message").innerHTML = "Registration failed: " + (data.message || "Unknown error");
        }
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById("message").innerHTML = "Registration failed: " + error.message;
    });
}

function login() {
    var username = document.getElementById("loginUsername").value;
    var password = document.getElementById("loginPassword").value;

    var data = {
        username: username,
        password: password
    };

    fetch('/productapp/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(handleResponse)
    .then(data => {
        if (data.id) { // Assuming successful login returns a user object with an id
            document.getElementById("message").innerHTML = "Login successful for username: " + username;
            // Optionally redirect the user or perform another action
             window.location.href = "/productapp/productlist.html";
        } else {
            document.getElementById("message").innerHTML = "Login failed: " + (data.message || "Invalid credentials");
        }
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById("message").innerHTML = "Login failed: " + error.message;
    });
}