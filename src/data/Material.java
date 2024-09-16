package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import util.Database;

public class Material {
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
	
	private String _sDescription;
	
	public String GetDescription(){
		return _sDescription;
	}
	
	public void SetDescription(String sDescription) {
		_sDescription = sDescription;
	}
	
	private int _iStock;
	
	public int GetStock(){
		return _iStock;
	}
	
	public void SetStock(int iStock) {
		_iStock= iStock;
	}
	
	private Date _dtDeletedAt;

	public Date getDeletedAt(){
		return _dtDeletedAt;
	}
	
	private Almacen _almacen;
	
	public Almacen GetAlmacen() {
		return _almacen;    
	}
	
	public void SetAlmacen(Almacen almacen) {
		if(almacen == null)
			throw new IllegalArgumentException("El almacen no puede ser nulo.");
		_almacen = almacen;
	}
	
	public Material(Almacen almacen, String sName, String sDescription, int iStock) throws SQLException, IOException {
		this(null, almacen, sName, sDescription, iStock);
	}
	
	private Material(Integer iId, Almacen almacen, String sName, String sDescription, int iStock) throws SQLException, IOException {
		SetAlmacen(almacen);
		SetName(sName);
		_sDescription = sDescription;
		_iStock = iStock;
		_iId = iId;
	}
	
	public static Material Get(int iId) throws SQLException, IOException{
		Connection con = null;
        ResultSet rs = null;
		try {
	        con = Database.Connection();
	        
	        String sConsulta = "SELECT almacen_id, nombre, descripcion, stock FROM material WHERE id = " + iId;
	        rs = con.createStatement().executeQuery(sConsulta);
	        if(rs.next())
	        	return new Material(iId, Almacen.Get(rs.getInt("almacen_id")), rs.getString("nombre"),
	        										 rs.getString("descripcion"), rs.getInt("stock"));
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
		return super.toString() + _iId + ":" + _sName + ":" + _sDescription + ":" + _iStock;
	}
	
	public void Save() throws SQLException, IOException {
		if(_dtDeletedAt == null) {
			Connection con = Database.Connection();
			ResultSet rs = null;
			PreparedStatement stmt = null;
			try {
				String sConsulta = "SELECT id FROM material WHERE id = " + _iId;
		        rs = con.createStatement().executeQuery(sConsulta);
	
				if(!rs.next()) { //Inserta en la base de datos
				 	sConsulta = "INSERT INTO material (almacen_id, nombre, descripcion, stock) VALUES (?,?,?,?);";
					stmt = con.prepareStatement(sConsulta);
					stmt.setInt(1, _almacen.GetId());
					stmt.setString(2, _sName);
					stmt.setString(3, _sDescription);
					stmt.setInt(4, _iStock);
					stmt.executeUpdate();
	
					_iId = Database.LastId(con);
				}
				else { //Actualiza los datos
					sConsulta="UPDATE material SET nombre = " + Database.String2Sql(_sName, true, false) + ", almacen_id = " + _almacen.GetId()
							+ ", descripcion = " + Database.String2Sql(_sDescription, true, false) + ", stock = " + _iStock + " WHERE id = " + _iId;
					con.createStatement().executeUpdate(sConsulta);
				}
			} finally {
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
	        	con.createStatement().executeUpdate("DELETE FROM material WHERE id = " + _iId);
	        	_dtDeletedAt = new Date();
	        } finally{
	            if (con != null) con.close();
	        }
    	}
    }

    private static String Where(String sName, String sAlmacen) {
    	String sWhere = "";
    	if(sName != null)
    		sWhere += "material.nombre LIKE " + Database.String2Sql(sName, true, true) + " AND ";
    	if(sAlmacen != null)
    		sWhere += "almacen.nombre LIKE " + Database.String2Sql(sAlmacen, true, true) + " AND ";
    	if(sWhere != "")
    		return "WHERE " + sWhere.substring(0, sWhere.length() - 5); // Devuelve sWhere quitandole el ultimo AND
    	return sWhere;
    }
    	
    public static ArrayList<Material> Search(String sName, String sAlmacen) throws SQLException, IllegalStateException, IOException {
        ArrayList<Material> aMaterial = new ArrayList<>();
        Connection con = null;
		ResultSet rs = null;
        try {
        	con = Database.Connection();
            String sConsulta = "SELECT material.id, almacen_id, material.nombre, descripcion, stock FROM material INNER JOIN almacen ON material.almacen_id = almacen.id ";
            sConsulta += Where(sName, sAlmacen) + " ORDER BY nombre ASC";
            rs = con.createStatement().executeQuery(sConsulta);
           
            while(rs.next())
                aMaterial.add(new Material(rs.getInt("material.id"), Almacen.Get(rs.getInt("almacen_id")), rs.getString("material.nombre"),
                						   rs.getString("descripcion"), rs.getInt("stock")));
        
            return aMaterial;
        }
        catch(SQLException ex){ throw ex; }
        finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
    }
}
