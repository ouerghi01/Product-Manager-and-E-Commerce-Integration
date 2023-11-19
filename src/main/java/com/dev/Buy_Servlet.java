package com.dev;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;




/**
 * Servlet implementation class Buy_Servlet
 */
@MultipartConfig
@WebServlet("/Buy")

public class Buy_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Buy_Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Get the JSON data from the request body
	    int c = 0;
	    String url = "mongodb://localhost:27017";
	    String databaseName = "Admin_Database";

	    ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, List<String>> data = objectMapper.readValue(request.getReader(), Map.class);
	    List<String> idProducts = data.get("id_products");

	    ConnectionMongo mongo = new ConnectionMongo(url, databaseName);
	    MongoClient connectionMongo = mongo.Connection();
	    List<String> allCollection = mongo.getCollectionNames();

	    for (String collectionName : allCollection) {
	        MongoCollection<Document> collection = mongo.mongoclient_get(collectionName);

	        for (String idProduct : idProducts) {
	            ObjectId id = new ObjectId(idProduct);
	            Document query = new Document("_id", id);
	            // Create an update to set the "pay or not" field to "True"
	            Document update = new Document("$set", new Document("pay or not", true));
	            collection.updateOne(query, update);
	            c++; // Increment the counter for each updated document
	        }
	    }

	    // You can send a response to the client if needed.
	    response.setContentType("application/json");
	    response.getWriter().write("Updated " + c + " documents.");
	}

}


