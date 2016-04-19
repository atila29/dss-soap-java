/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brugerautorisation.server;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author atila29
 */
public class ServerBrugerProtocol {
    public static void main(String[] args) throws MalformedURLException {
                // SOAP
		URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
		QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
		Service service = Service.create(url, qname);
		Brugeradmin ba = service.getPort(Brugeradmin.class);
                
                // selve servereren
                int port = 4444;
                int maxCon = 0;
                int i = 0;
        try {
            ServerSocket listener = new ServerSocket(port);
            Socket server;
            System.out.println("Server lytter på port: " + port);
            
            while((i++ < maxCon) || (maxCon == 0)){
                BrugerProtocol connection;
                server = listener.accept();
                connection = new BrugerProtocol(server, ba);
                new Thread(connection).start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerBrugerProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
