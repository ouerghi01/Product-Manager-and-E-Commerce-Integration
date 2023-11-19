package com.dev;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.bson.Document;
import org.json.simple.JSONObject;



import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import jakarta.servlet.http.Part;

public class ConnectionMongo {
	

    public String uri ;
    public String database_name;
    public String Collection_name; 
    public MongoClient mongoClient;
    public ConnectionMongo(String u,String d) {
    	
    	uri=u;
    	database_name=d;
    	
    	
    	
    }
    public Document store_data_Users(String p,String e,String t,int b) throws IOException {
  	  
  	  
  	  
  	  
	    JSONObject imageWithMetadata = new JSONObject();
	    imageWithMetadata.put("username", p);
	    imageWithMetadata.put("password", e);
	    imageWithMetadata.put("email",t);
	    imageWithMetadata.put("solde",b);
	   
	    //imageWithMetadata.put("date", getCurrentDate()); // You can create a method to get the current date
	    
	    Document mongoDocument = Document.parse(imageWithMetadata.toString());
	    return mongoDocument;

}
    public BufferedImage decodeBase64ToImage(String base64) throws IOException {
	    byte[] bytes = Base64.getDecoder().decode(base64);
	    InputStream inputStream = new ByteArrayInputStream(bytes);
	    return ImageIO.read(inputStream);
	}
    public static String displayImageFromBase64(String imageBase64) {
        return "<img src=\"data:image/png;base64," + imageBase64 + "\" alt=\"Image\">";
    }
    public List<String> getCollectionNames() {
    	MongoClient mongoClient=this.Connection();
        
        MongoDatabase database = mongoClient.getDatabase(this.database_name);
        List<String> collectionNames = new ArrayList<>();
        MongoIterable<String> collectionNamesIterable = database.listCollectionNames();
        for (String name : collectionNamesIterable) {
            collectionNames.add(name);
        }
        return collectionNames;
    }
   public  MongoClient  Connection( ) 
    
    
    
    { 
    
    
	     this.mongoClient = MongoClients.create(this.uri);
	     return this.mongoClient;
     
    	}
    public  MongoCollection<Document> mongoclient(String Collection_name) {
    	
    	MongoClient m=this.Connection();
        
    MongoDatabase database = mongoClient.getDatabase(this.database_name);
   database.createCollection(Collection_name);
    return database.getCollection(Collection_name);

    }
public  MongoCollection<Document> mongoclient_get(String Collection_name) {
    	
    	MongoClient mongoClient=this.Connection();
        
    MongoDatabase database = mongoClient.getDatabase(this.database_name);
    MongoCollection<Document>d= database.getCollection(Collection_name);
    return d;

    }
    // encode image en base64
    public String encodeImageToBase64(Part filePart) throws IOException {
	    InputStream fileInputStream = filePart.getInputStream();
	    byte[] bytes = new byte[fileInputStream.available()];
	    fileInputStream.read(bytes);
	    return Base64.getEncoder().encodeToString(bytes);
	}
    public Document store_data(String e,int c,Part filePart,Boolean pay) throws IOException {
	    JSONObject imageWithMetadata = new JSONObject();
	  
	    imageWithMetadata.put("prix", c);
	    imageWithMetadata.put("name_produit", e);
	    imageWithMetadata.put("description", "This is an image of the product.");
	    //imageWithMetadata.put("date", getCurrentDate()); // You can create a method to get the current date
	    imageWithMetadata.put("imageData", this.encodeImageToBase64(filePart));
	    imageWithMetadata.put("pay or not ", pay);
	    Document mongoDocument = Document.parse(imageWithMetadata.toString());
	    return mongoDocument;

  }
    
}