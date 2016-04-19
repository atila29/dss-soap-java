package brugerautorisation.transport.rmi;

import brugerautorisation.Diverse;
import brugerautorisation.data.Bruger;
import java.rmi.Naming;

public class Brugeradminklient {
	public static void main(String[] arg) throws Exception {
//		Brugeradmin ba =(Brugeradmin) Naming.lookup("rmi://localhost/brugeradmin");
		Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");

                ba.sendGlemtAdgangskodeEmail("s133974", "du er en noob");
                
    //ba.sendGlemtAdgangskodeEmail("jacno", "Dette er en test, husk at skifte kode");
		//ba.ændrAdgangskode("s144874", "kodeykqzb8", "nemkode");
		Bruger b = ba.hentBruger("s144874", "nemkode");
                
		System.out.println("Fik bruger = " + b);
		System.out.println("Data: " + Diverse.toString(b));
		ba.sendEmail("jacno", "xxx", "Hurra det virker!", "Jeg er så glad");

		ba.setEkstraFelt("s144874", "nemkode", "extra", "Hej fra Mathias :)"); // Skriv noget andet her
                
                Object ekstraFelt = ba.getEkstraFelt("s144874", "nemkode", "extra");
		System.out.println("Fik ekstraFelt = " + ekstraFelt);
            
	}
}
