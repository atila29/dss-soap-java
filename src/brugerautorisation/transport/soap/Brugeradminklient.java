package brugerautorisation.transport.soap;

import brugerautorisation.Diverse;
import brugerautorisation.data.Bruger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author j
 */
public class Brugeradminklient {
	public static void main(String[] args) throws MalformedURLException {
//		URL url = new URL("http://localhost:9901/brugeradmin?wsdl");
		URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
		QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
		Service service = Service.create(url, qname);
		Brugeradmin ba = service.getPort(Brugeradmin.class);

    //ba.sendGlemtAdgangskodeEmail("jacno", "Dette er en test, husk at skifte kode");
		//ba.ændrAdgangskode("jacno", "kode3stljl", "xxx");
		Bruger b = ba.hentBruger(args[0], args[1]);
		System.out.println("{\"username\" : " + "\"" + b.brugernavn + "\"}");
		//System.out.println("Data: " + Diverse.toString(b));
		// ba.sendEmail("jacno", "xxx", "Hurra det virker!", "Jeg er så glad");
		//ba.setEkstraFelt("jacno", "xxx", "s123456_testfelt", "Jeg er så glad");
		//Object ekstraFelt = ba.getEkstraFelt("jacno", "xxx", "s123456_testfelt");
		//System.out.println("Fik ekstraFelt = " + ekstraFelt);
            
	}
}