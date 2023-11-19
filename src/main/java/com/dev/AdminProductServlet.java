package com.dev;

import java.io.IOException;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class AdminProductServlet
 */
@MultipartConfig
@WebServlet("/Admin")

public class AdminProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProductServlet() {
    	// TODO Auto-generated method stub
    			
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
       
             request.getRequestDispatcher("Ecommerce.jsp").forward(request, response);
         
     }
    



    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String Section = request.getParameter("section");
	    int prix = Integer.parseInt(request.getParameter("Prix"));
	    String name_produit = request.getParameter("name_produit");
	    // Handle file upload (if needed)
	    Part filePart = request.getPart("image");
	    
	    produis p=new produis(prix, Section, name_produit, filePart);
	    HttpSession session = request.getSession();
        session.setAttribute("Section", Section);	    
        String url="mongodb://localhost:27017";
        String d="Admin_Database";
        ConnectionMongo m1=new ConnectionMongo(url,d);
        MongoClient m=m1.Connection() ;
        MongoCollection<Document> col=m1.mongoclient( p.get_Section());	
        Document doc=m1.store_data(p.getName_produit(),p.getPrix(), filePart, p.get_pay_or_not());
        col.insertOne(doc);
        m1.mongoClient.close();
        System.out.print("hello");	    
	    
		
		
		
		
		
	}}

    
