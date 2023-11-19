package com.dev;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import jakarta.servlet.http.Part;

public class produis {
private int prix ;
private String Section;
private String name_produit;
private Part image_produit;
private boolean pay_or_not;
public produis(int  p,String sec,String name_pro,Part image) {
	this.image_produit=image;
	this.name_produit=name_pro;
	this.prix=p;
	this.Section=sec;
	this.pay_or_not=false;
	
}

public int getPrix() {
    return prix;
}
public Boolean get_pay_or_not() {
    return this.pay_or_not;
}
public void set_pay_or_no(Boolean b) {
	this.pay_or_not=b;
}

public void setPrix(int prix) {
    this.prix = prix;
}

public String getName_produit() {
    return name_produit;
}

public void setName_produit(String name_produit) {
    this.name_produit = name_produit;
}
public String get_Section() {
    return this.Section;
}

public void set_Section(String S) {
    this.Section = S;
}

public Part getImage_produit() {
    return image_produit;
}

public void setImage_produit(Part image_produit) {
    this.image_produit = image_produit;
}


}
