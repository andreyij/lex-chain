package chatsegmenter.paronimchainer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class ParoCheck {
	public static boolean areParonims(String word1,String word2) throws Exception
	{
		Statement stmt;
		Class.forName("com.mysql.jdbc.Driver");

	      String url = "jdbc:mysql://localhost:3306/mysql";

	      Connection con =  DriverManager.getConnection(url,"admin", "");

	      stmt = con.createStatement();
	      String sql = new String("select b.cuvant from englezaalg3gr2.cuvinte as a, englezaalg3gr2.cuvinte as b," +
	      		"englezaalg3gr2.liste as c,englezaalg3gr2.cuvantlista as d  where a.cuvant ='"+word1+
	      		"' and a.IDcuvant = d.IDcuvant and d.IDLista = c.IDLista and c.IDcuvant = b.IDcuvant and b.cuvant = '"+
	      		word2+"';");
	      ResultSet rez = stmt.executeQuery(sql);
	      rez.last();
	      if (rez.getRow()==1)
	      {
	    	  return true;
	      }
	      return false;
	      
		
	}
	//creez baza de date cu paronimele de gradul 1
	
	/*public static void main(String args[]) throws Exception
	{
		Statement stmt;
		Class.forName("com.mysql.jdbc.Driver");

	      String url = "jdbc:mysql://localhost:3306/mysql";

	      Connection con =  DriverManager.getConnection(url,"admin", "");

	      stmt = con.createStatement();
	      Statement stmt2 = con.createStatement();
	      Statement stmt3 = con.createStatement();
	      String sql = new String("select distinct a.cuvant as 'cuvant', c.cuvant as 'paronim' from englezaalg3gr2.cuvinte as a , englezaalg3gr2.liste as b," +
	      		"englezaalg3gr2.cuvinte as c , englezaalg3gr2.liste as d where a.IDCuvant = b.IDCuvant and b.IDLista = d.IDLista and c.IDCuvant = d.IDCuvant;");
	      ResultSet rez = stmt.executeQuery(sql);
	      int i = 1;
	      while (rez.next())
	      {
	    	  String cuv = rez.getString("cuvant");
	    	  String paro = rez.getString("paronim");
	    	  System.out.println(rez.getRow());
	    	  stmt2.executeUpdate("insert into englezagr1.paronime values('"+cuv+"','"+paro+"');");
	    	  
	    	  
	      }
	}*/

}
