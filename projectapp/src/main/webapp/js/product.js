 
function createProduct() {
    const name = document.getElementById('productName').value;
    const price = document.getElementById('productPrice').value;
    const discount = document.getElementById('productDiscount').value;
    
    const productData = {
        name: name,
        price: price,
        discount: discount // Include discount in the data sent to the server
    };

    fetch('/productapp/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
         body: JSON.stringify(productData),
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Failed to create product');
        }
    })
    .then(data => {
        console.log('Product created:', data);
        window.location.href = 'productlist.html'; // Redirect to the product list
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function updateProduct() {
    // Retrieve product details from the form
    const productId = document.getElementById('productId').value;
    const name = document.getElementById('editProductName').value;
    const price = document.getElementById('editProductPrice').value;
    const discount = document.getElementById('productDiscount').value;

    // Construct the request payload
    const productData = {
        name: name,
        price: parseFloat(price), // Ensure price is sent as a float
        discount: discount 
    };

    // Send the PUT request to update the product
    fetch(`/productapp/products/${productId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(productData)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Failed to update product');
        }
    })
    .then(updatedProduct => {
        console.log('Product updated successfully:', updatedProduct);
        // Redirect to the product list after a successful update
        window.location.href = 'productlist.html';
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error updating product.');
    });
}

// Function to list all products on productlist.html
function listProducts() {
    fetch('/productapp/products')
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Failed to fetch products');
        }
    })
    .then(products => {
        const productsTable = document.querySelector('#productsTable tbody');
        productsTable.innerHTML = ''; // Clear the table first
        products.forEach(product => {
            const row = `
                <tr>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>${product.discount}</td>
                     <td>${product.finalPrice}</td>
                    <td>
                        <button onclick="editProduct(${product.id})">Edit</button>
                        <button onclick="deleteProduct(${product.id})">Delete</button>
                    </td>
                </tr>
            `;
            productsTable.innerHTML += row;
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// Function to redirect to edit page
function editProduct(productId) {
    window.location.href = `editproduct.html?productId=${productId}`;
}

function getProductIdFromQuery() {
    const queryParams = new URLSearchParams(window.location.search);
    return queryParams.get('productId');
}

function populateFormData() {
    const productId = getProductIdFromQuery();
    if (!productId) {
        console.error('Product ID is required.');
        return;
    }

    fetch(`/productapp/products/${productId}`)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Failed to fetch product details');
            }
        })
        .then(product => {
            document.getElementById('productId').value = product.id;
            document.getElementById('editProductName').value = product.name;
            document.getElementById('editProductPrice').value = product.price;
            document.getElementById('editproductDiscount').value = product.discount;
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Function to delete a product
function deleteProduct(productId) {
    if (!confirm('Are you sure you want to delete this product?')) return;

    fetch(`/productapp/products/${productId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            console.log(`Product with ID: ${productId} deleted`);
            listProducts(); // Refresh the list after deletion
        } else {
            throw new Error('Failed to delete product');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// Example of calling listProducts on page load for productlist.html
// Ensure this script is deferred or this call is within a DOMContentLoaded listener if script is in head
if (window.location.pathname.endsWith('productlist.html')) {
    window.addEventListener('DOMContentLoaded', (event) => {
        listProducts();
    });
}