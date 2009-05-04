package be.simulation.utilitaires;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.FileAppender;

/**
 * Utilisé pour sauvegarder les logs à chaque fois dans un fichier différent.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class CustomFileAppender extends FileAppender {

	@Override
	public void setFile(String file) {
		Date date = new Date();
		StringBuffer dateStr = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
		dateStr = sdf.format(date, dateStr, new FieldPosition(0));
		String logFileName = file + "." + dateStr + ".log";
		super.setFile(logFileName);
	}
	
}
