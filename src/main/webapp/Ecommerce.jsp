<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.bson.Document" %>
<%@ page import="com.mongodb.client.MongoCollection" %>
<%@ page import="com.mongodb.client.FindIterable" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.dev.ConnectionMongo" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="org.bson.types.ObjectId" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.dev.ConnectionMongo" %>



<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Display User Data</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <style >body {
  margin: 0;
  padding: 0;
  font-family: 'Arial', sans-serif;
  background-color: #f3f3f3;
}

* {
  box-sizing: border-box;
}

/* Style the header */
#Signup {
  float: right;
  padding: 15px 15px;
  background-color: #2196F3;
}

.topnav {
  overflow: hidden;
  background-color: #e9e9e9;
}

.topnav a {
  float: left;
  display: block;
  color: brown;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}

.topnav a.active {
  background-color: #2196F3;
  color: white;
}

.topnav .search-container {
  float: right;
}

.topnav input[type=text] {
  padding: 6px;
  margin-top: 50px;
  font-size: 17px;
  border: none;
}

.topnav .search-container button {
  float: right;
  padding: 6px 10px;
  margin-top: 50px;
  margin-right: 15px;
  background-color: bisque;
  font-size: 17px;
  border: none;
  cursor: pointer;
}

.topnav .search-container button:hover {
  background-color: #2196F3;
}

@media screen and (max-width: 600px) {
  .topnav .search-container {
    float: none;
  }
  .topnav a,
  .topnav input[type=text],
  .topnav .search-container button {
    float: none;
    display: block;
    text-align: left;
    width: 100%;
    margin: 0;
    padding: 14px;
  }
  .topnav input[type=text] {
    border: 1px solid #ccc;
  }
}

button {
  display: block;
  margin-top: 10px;
  padding: 10px;
  background-color: #4caf50;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #45a049;
}

/* Style the main content container */
.main {
  max-width: 1000px;
  margin: 20px auto;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  text-align: right;
}

/* Style the product grid */
.product-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.product-card {
  width: 30%;
  margin: 20px;
  background-color: rosybrown;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 20px;
  text-align: center;
  border-radius: 10px;
  transition: transform 0.2s;
}

.product-card:hover {
  transform: scale(1.03);
}

.product-image {
  max-width: 100%;
  max-height: 200px;
  object-fit: contain;
  border-radius: 10px 10px 0 0;
}

.product-title {
  font-weight: bold;
  margin: 10px 0;
  font-size: 1.2rem;
  text-transform: capitalize;
}

.product-price {
  color: #e44d26;
  font-weight: bold;
}

section {
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto;
  padding: 20px;
  gap: 20px;
  align-items: stretch;
  width: 110%;
  border: 1px solid #ccc;
  border-radius: 10px;
  margin: 10px 0;
  overflow: auto;
}

section p {
  flex: 0 0 auto;
  margin: 0 10px 10px 0;
}

.product-button {
  background-color: #4caf50;
  color: rebeccapurple;
  border: none;
  padding: 10px 16px;
  margin-top: 10px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

#main-content {
  float: left;
  margin-left: 50px;
  padding: 20px;
}

.product-button:hover {
  background-color: #45a049;
}
#title_panier{
  border-radius: 8px;
  padding:20px;
background-color:red;
  font-weight: bold;


}

.purchase_button{
color:#a65959;
}
#panier{
  background-color: #4caf50;
  font-weight: bold;

float:right;
margin-right:20px;
margin-left:-5px;
padding:15px 15px;
margin:100px 20px;
transition: background-color 0.3s;
  border-radius: 5px;
}
/* Media queries for responsiveness */
@media (max-width: 768px) {
  .product-card {
    width: 45%;
  }
}

@media (max-width: 480px) {
  .product-card {
    width: 100%;
  }
}
    
    </style>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body>
<div class="topnav">
  <a class="active" href="#home">Home</a>
  <a href="#about">About</a>
  <% 
    if (session.getAttribute("userId") != null) {
      String name = (String) session.getAttribute("username");
      if (name != null) {
  %>
         <a href="Profile.jsp"><%= name %></a>
  <%
      }
    }
  %>
  <a id="Signup" href="#contact">Logout</a>
  <div class="search-container">
    <form >
      <input type="text" placeholder="Search.." name="search">
      <button type="submit"><i class="fa fa-search"></i>Search</button>
    </form>
  </div>
</div>


<div style="padding-left:16px">
  
</div>
<main id="main-content" class="container">
<%
String url = "mongodb://localhost:27017";
String dbName = "Admin_Database";
ConnectionMongo connectionMongo = new ConnectionMongo(url, dbName);
List<String> collectionNames = connectionMongo.getCollectionNames();
for (String collectionName : collectionNames) {
    MongoCollection<Document> collection = connectionMongo.mongoclient_get(collectionName);
    FindIterable<Document> documents = collection.find();
    Iterator<Document> iterator = documents.iterator();
%>
    <section class="<%= collectionName %>">
        <p><%= collectionName %></p>
        <%
        while (iterator.hasNext()) {
            Document productDocument = iterator.next();
       	    ObjectId userId = productDocument.getObjectId("_id"); // Extract the ObjectId
       	    
            System.out.println(userId.toString());
            String productName = productDocument.getString("name_produit");
            int price = productDocument.getInteger("prix");
            String imagedata = productDocument.getString("imageData");
        %>

        <div class="product-card">
           <h1 class="id_product" data-product-id="<%= userId.toString() %>"></h1>

            <img src="data:image/png;base64,<%= imagedata %>" alt="Image" class="product-image">
            <h3 class="product-title"><%= productName %></h3>
            <p class="product-price">$<%= price %></p>
            <button id="buyButton" class="product-button">BUY</button>
        </div>
        <%
        }
        %>
    </section>
<%
}
%>

<button id="displayProducts">displayProducts</button>
</main>
<main id="panier">
<h3 id="title_panier">Panier</h3>
<div id ="cart-items">
        <!-- This is where the cart items will be displayed -->
    </div>
    <div id="total-amount"></div>
    
</main>

   
</body>
    <script src="script.js"></script>
    <script>
    const logoutLink = document.getElementById("Signup");
    logoutLink.addEventListener('click', function (event) {
      
      window.location.href = "Login.jsp";
      event.preventDefault();
    });
    </script>

</html>

