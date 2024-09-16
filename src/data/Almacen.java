package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import util.Database;

public class Almacen {
	private Integer _iId;
	
	public Integer GetId(){
		return _iId;
	}
	
	private String _sName;
	
	public String GetName(){
		return _sName;
	}
	
	public void SetName(String sName) throws IllegalArgumentException{
		if(sName == "" || sName == null)
			throw new IllegalArgumentException("El nombre no puede ser nulo.");
		_sName = sName;
	}
	
	private Date _dtDeletedAt;

    public Date getDeletedAt(){
        return _dtDeletedAt;
    }
	
	public Almacen(String sName) throws SQLException, IOException {
		this(null, sName);
	}
	
	private Almacen(Integer iId, String sName) throws SQLException, IOException {
		SetName(sName);
		_iId = iId;
	}
	
	public static Almacen Get(int iId) throws SQLException, IOException{
		Connection con = null;
        ResultSet rs = null;
		try {
	        con = Database.Connection();
	        String sConsulta = "SELECT nombre FROM almacen WHERE id = " + iId;
	        rs = con.createStatement().executeQuery(sConsulta);
	        if(rs.next())
	        	return new Almacen(iId, rs.getString("nombre"));
	        return null;
		}
        catch(SQLException e) { throw e; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}		
    }
	
	@Override
	public String toString() {
		return _sName;
	}
	
	public void Save() throws SQLException, IOException {
		if(_dtDeletedAt == null) {
			Connection con = Database.Connection();
	        ResultSet rs = null;
	        PreparedStatement stmt = null;
			try {
				String sConsulta = "SELECT id FROM almacen WHERE id = " + _iId;
		        rs = con.createStatement().executeQuery(sConsulta);
	
				if(!rs.next()) { //Inserta en la base de datos
				 	sConsulta = "INSERT INTO almacen (nombre) VALUES (?);";
					stmt = con.prepareStatement(sConsulta);
					stmt.setString(1, _sName);
			        stmt.executeUpdate();
			        
			        _iId = Database.LastId(con);
				}
				else { //Actualiza los datos
					sConsulta="UPDATE almacen SET nombre = " + Database.String2Sql(_sName, true, false) + " WHERE id = " + _iId;
					con.createStatement().executeUpdate(sConsulta);
				}
			}finally {
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
				if (con != null) con.close();
			}
		}
	}

    public void Delete() throws SQLException, IOException {
    	if(_dtDeletedAt != null) {
	        Connection con = null;
	        try {
	        	con = Database.Connection();
	        	con.createStatement().executeUpdate("DELETE FROM almacen WHERE id = " + _iId);
	            _dtDeletedAt = new Date();
	        } finally{
	            if (con != null) con.close();
	        }
    	}
    }

    private static String Where(String sName) {
    	if(sName != null)
    		return " WHERE nombre LIKE " + Database.String2Sql(sName, true, true);
    	return "";
    }
    	
    public static ArrayList<Almacen> Search(String sName) throws SQLException, IllegalStateException, IOException {
        ArrayList<Almacen> aAlmacen = new ArrayList<>();
        Connection con = null;
		ResultSet rs = null;
        try {
        	con = Database.Connection();
            String sConsulta = "SELECT id, nombre FROM almacen";
            sConsulta += Where(sName) + " ORDER BY nombre ASC";
            rs = con.createStatement().executeQuery(sConsulta);
           
            while(rs.next())
                aAlmacen.add(new Almacen(rs.getInt("id"), rs.getString("nombre")));
        
            return aAlmacen;
        }
        catch(SQLException ex){ throw ex; }
        finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
    }
}
