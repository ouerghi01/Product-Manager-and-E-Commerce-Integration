package com.dev;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

/**
 * Servlet implementation class Login_Registre
 */

@MultipartConfig
@WebServlet("/LOGIN_")
public class Login_Registre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login_Registre() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String pswd = request.getParameter("pswd");
        
        String url = "mongodb://localhost:27017";
        String dbName = "Factorisation";
        String collectionName = "Users";
        
        Document query = new Document("email", email);
        
        try {
        	ConnectionMongo m1 = new ConnectionMongo(url, dbName);
            MongoClient settings = m1.Connection();
            MongoCollection<Document> col = m1.mongoclient_get(collectionName);

            FindIterable<Document> result = col.find(
                    new Document("$or", Arrays.asList(
                        new Document("email", email),
                        new Document("password", pswd)
                    ))
                );
            Document userDocument = result.first();
            if (userDocument!=null) {
                // A document with the provided username and password was found
                /*HttpSession session = request.getSession();
                session.setAttribute("authenticatedUser", email);*/
                
                
                ObjectId userId = userDocument.getObjectId("_id"); // Extract the ObjectId
                String User_name=userDocument.getString("username");
                 // You can store the ObjectId in the session for later use
                 HttpSession session = request.getSession();
                 session.setAttribute("userId", userId);
                 session.setAttribute("username", User_name);

                 
                 response.sendRedirect("Ecommerce.jsp");
                 System.out.println("Login successful!");
            } else {
                // No matching document was found
                System.out.println("Login failed. Please check your username and password.");
            }
            
            m1.mongoClient.close();
        } catch (Exception e) {
            // Handle any exceptions (e.g., database connection issues)
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to an error page
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String username = request.getParameter("txt");
        String pswd = request.getParameter("pswd");
        String email = request.getParameter("email");
        User user = new User(username, pswd, email);

        String url = "mongodb://localhost:27017";
        String databaseName = "Factorisation";
        String collectionName = "Users";

        ConnectionMongo connectionMongo = new ConnectionMongo(url, databaseName);
        MongoClient mongoClient = connectionMongo.Connection();
        MongoCollection<Document> collection = connectionMongo.mongoclient_get(collectionName);

        FindIterable<Document> result = collection.find(
                new Document("$or", Arrays.asList(
                    new Document("username", username),
                    new Document("email", email),new Document("pswd",pswd)
                ))
            );        Document existingUser = result.first();

        if (existingUser != null) {
            response.getWriter().println("Email or pswd or username already exists. Please use a different username,pswd,email.");
        } else {
            // Email is unique, proceed to insert the new user
           // String hashedPassword = BCrypt.hashpw(user.get_p(), BCrypt.gensalt());

            Document userDocument = connectionMongo.store_data_Users(user.get_u(), user.get_p(), user.get_e(), user.get_balance());
            collection.insertOne(userDocument);
            response.getWriter().println("User registered successfully!");
        }

        mongoClient.close();
    }

	

}
