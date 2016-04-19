/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brugerautorisation.server;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.soap.SOAPFaultException;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author atila29
 */
public class BrugerProtocol implements Runnable{
    private Socket server;
    private Brugeradmin ba;
    
    public BrugerProtocol(Socket server, Brugeradmin ba) {
        this.server = server;
        this.ba = ba;
    }
    
    private void cmd(List<String> msg) {
        OutputStreamWriter out = null;
        Writer writer = null;
        try {
            out = new OutputStreamWriter(server.getOutputStream());
            writer = new BufferedWriter(out);
            switch(msg.get(0).toLowerCase().replace("\n", "")){
                case ("hello"):
                    break;
                case("login"):
                    System.out.println("v");
                    JSONObject json = new JSONObject(msg.get(1));
                     JSONObject json_return = new JSONObject();
                    try{
                        Bruger b = ba.hentBruger(json.getString("username"), json.getString("password"));
                        json_return.put("username", b.brugernavn);
                    } catch (SOAPFaultException e) {
                        json_return.put("error", e.getMessage());
                    }
                    writer.write(json_return.toString());
                    writer.flush();
                    //out.flush();
                    break;
                case("forgot_pass"):
                    break;
                default:
                    System.out.println("ikke fundet");
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(BrugerProtocol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(BrugerProtocol.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(BrugerProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @Override
    public void run() {
        try {
            //DataInputStream in = new DataInputStream(server.getInputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            String line = in.readLine();
            System.out.println(line);
            List<String> input = new ArrayList<>();
            input.add(line);
            while((line = in.readLine())!= null) {
                input.add(new String(line));
                System.out.println(line);
            }
            this.cmd(input);
            in.close();
            
        } catch (IOException ex) {
            Logger.getLogger(BrugerProtocol.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
    }
    
}
