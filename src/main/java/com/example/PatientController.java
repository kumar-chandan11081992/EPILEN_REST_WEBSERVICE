package com.example;

import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicLong;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@RestController
public class PatientController {

	private static final String ALGO = "AES";
    private static final byte[] keyValue = 
    new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't','S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
    @RequestMapping("/patient_search")
    public Patient get_patient(@RequestParam(value="id") int id) {
    	Connection con = null;
    	Statement stmt;
    	ResultSet rs;
    	   // private final AtomicLong counter = new AtomicLong();
        String name="";
        Integer age=0;
        String disease="";
        String address="";
    	try
    	{
    	Class.forName("com.mysql.jdbc.Driver");  
    	con=DriverManager.getConnection(  
    	"jdbc:mysql://localhost:3306/DAVDB","root","password"); 
    	stmt=con.createStatement();  
    	rs=stmt.executeQuery("select * from Patient where id="+id);  
    	while(rs.next())  
    	{
    		rs.getInt("patientage");
    		name=rs.getString("patientname");
    		address=rs.getString("patientaddress");
    		disease=rs.getString("patientdisease");
    		disease=decrypt(disease);
    		break;
    		    
    	}
    	}
    	catch(Exception e)
    	{ 
    		System.out.println(e);
    	}  
    	finally
    	{
    		try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        return new Patient(id,name,age,disease,address);
    }
    
    public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
}
}