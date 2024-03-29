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
    var pincodeElement = document.getElementById("regPincode");
    var pincode = pincodeElement ? pincodeElement.value : "";
    var data = {
        username: username,
        password: password,
        firstName: firstName,
        lastName: lastName,
        address: address, // Send as is, backend will handle it as optional
        pincode:pincode
    };

    fetch('/productapp/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            // If the response is not ok, attempt to parse it as JSON to get detailed error information
            return response.json().then(err => {
                // Constructing an informative error message
                const errorMsg = `Error ${response.status}: ${err.error || "An error occurred"} - ${err.message || "Unknown error"}`;
                document.getElementById("errorMessage").innerText = errorMsg;
                // Since we're handling the error here, we stop the promise chain by returning a rejected promise
                return Promise.reject(new Error(errorMsg));
            });
        }
    })
    .then(data => {
        if (data.id) { // Assuming successful registration returns a user object with an id
            //document.getElementById("message").innerHTML = "Registration successful for username: " + username;
            window.location.href = "/productapp/productlist.html";
        }  
    })
    .catch(error => {
        // This catch will handle any network errors and errors thrown from within the first then block
        console.error('Error:', error);
        // Optionally, you could also update the UI with this error message here, if not already set
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