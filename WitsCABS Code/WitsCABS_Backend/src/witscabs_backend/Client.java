/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package witscabs_backend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class Client extends Thread
{
    private Socket c;
    public Client(Socket client)
    {
        c = client;
    }
    
    @Override
    public void run()
    {
        try 
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            DataOutputStream out = new DataOutputStream(c.getOutputStream());
            String function = in.readLine();
            if(function.equalsIgnoreCase("newCustomer"))
            {
                out.writeBytes("OK\n");
                String JSON = in.readLine();
                JSON_Handler temp = new JSON_Handler(JSON);
                String sql = "INSERT INTO CUSTOMER(Customer_Name, Customer_Description, Customer_PhoneNumber, Customer_StartNumber,"
                        + "Customer_StartStreet, Customer_StartSuburb, Customer_EndNumber, Customer_EndStreet, Customer_EndSuburb)"
                        + "VALUES("+temp.getWholeValue()+");";
                System.out.println(sql);
                WitsCABS_Backend.sendSQLQuery(sql);
                out.writeBytes("OK\n");
            }
            else if(function.equalsIgnoreCase("newDriver"))
            {
                out.writeBytes("OK\n");
                String JSON = in.readLine();
                System.out.println(JSON);
                out.writeBytes("OK\n");
            }
            else
            {
                out.writeBytes("NOT UNDERSTOOD\n");
            }
            
            in.close();
            out.close();
            c.close();
            System.out.println("Client Closed!");
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
