/**
 * 
 */
let products_id = {
    id_product: [],
    product_names: [],
    price_products: [],
    price_total: 0,
};

function addToCart() {
    let buyButtons = document.querySelectorAll(".product-button");
    buyButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            let productElement = this.closest(".product-card");
            let id_product = productElement.getAttribute("data-product-id");
            console.log(id_product);
             // Utilisez getAttribute sur l'élément lui-même pour obtenir l'attribut
            let product_name = productElement.querySelector(".product-title").textContent;
            let product_price = parseFloat(productElement.querySelector(".product-price").textContent.replace('$', ''));

            products_id.id_product.push(id_product);
            products_id.product_names.push(product_name);
            products_id.price_products.push(product_price);

            products_id.price_total += product_price;

            updateCartView();
        });
    });
}
console.log(products_id);
function updateCartView() {
    let cartItems = document.getElementById("cart-items");
    cartItems.innerHTML = "";

    let totalAmount = 0;

    for (let i = 0; i < products_id.id_product.length; i++) {
        const cartItem = document.createElement("div");
        cartItem.className = "cart-item";
        cartItem.innerHTML = `
            <h3>${products_id.product_names[i]} - Price: $${products_id.price_products[i]}</h3>
        `;
        cartItems.appendChild(cartItem);

        totalAmount += products_id.price_products[i];
    }

    let totalAmountElement = document.getElementById("total-amount");
    totalAmountElement.textContent = "Total: $" + totalAmount.toFixed(2);

    const br = document.createElement("br");
    const purchase = document.createElement("button");
    purchase.textContent = "Purchase";
    purchase.addEventListener("click", function () {
        // Call testConnectionToServlet when the "Purchase" button is clicked
        testConnectionToServlet();
    });
    
    totalAmountElement.appendChild(br);
    totalAmountElement.appendChild(purchase);
}

function testConnectionToServlet() {
    // Get the id_products data
    const id_products = products_id.id_product;

    // Create an object to send as JSON
    const dataToSend = {
        id_products: id_products
    };

    // Make a POST request to the servlet URL
    fetch('registration', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Set the content type to JSON
        },
        body: JSON.stringify(dataToSend), // Convert the data to JSON string
    })
    .then(response => {
        if (response.ok) {
            console.log("ok"); // Display "ok" when the request is successful
             console.log(dataToSend);
        } else {
            throw new Error('Request failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
   
}




// Call addToCart to initialize the event listeners
addToCart();

// Add an event listener to display the content of the products_id object
document.getElementById("displayProducts").addEventListener("click", function () {
    console.log(products_id);
});
