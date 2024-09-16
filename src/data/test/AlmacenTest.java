package data.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import data.Almacen;

class AlmacenTest {

	@Test
	void testConstructor() throws IOException, SQLException {
		Almacen almacen = new Almacen("Moscu");
		
		assertEquals("Moscu",	almacen.GetName());
	}
	
	@Test
	void testSets() throws IOException, IllegalArgumentException, SQLException {
		Almacen almacen = new Almacen("Moscu");
		
		almacen.SetName("Madrid");
		
		assertEquals("Madrid", 	almacen.GetName());
	}
	
	@Test
	void testGet() throws IOException, SQLException {
		Almacen almacen = Almacen.Get(2);
		
		assertEquals("Kazan", 	almacen.GetName());
	}
	
	@Test
	void testSaveDelete() throws SQLException, IOException {
		Almacen almacen = new Almacen("Madrid");
		
		almacen.Save();	// Guarda en la BD
		almacen = Almacen.Get(almacen.GetId());
		assertEquals("Madrid", 	almacen.GetName());
		
		almacen.SetName("actualizado");
		almacen.Save();	// Actualiza el objeto en la BD
		
		almacen = Almacen.Get(almacen.GetId());
		assertEquals("actualizado", 	almacen.GetName());
			
		almacen.Delete();
		assertNull(Almacen.Get(almacen.GetId()));
		assertNotNull(almacen.getDeletedAt());
	}
	
	@Test
	void testSearch() throws IllegalStateException, SQLException, IOException {
		List<Almacen> aAlmacen = Almacen.Search(null);
		
		assertEquals(3,				aAlmacen.size());
		
		assertEquals(2,				aAlmacen.get(0).GetId());
		assertEquals(1,				aAlmacen.get(1).GetId());
		assertEquals(3,				aAlmacen.get(2).GetId());
		assertEquals("Kazan",		aAlmacen.get(0).GetName());
		assertEquals("Moscu",		aAlmacen.get(1).GetName());
		assertEquals("Omsk",		aAlmacen.get(2).GetName());
		
		aAlmacen = Almacen.Search("u");
		assertEquals("Moscu",	aAlmacen.get(0).GetName());
		
		aAlmacen = Almacen.Search("Kazan");
		assertEquals("Kazan", 	aAlmacen.get(0).GetName());
		
		aAlmacen = Almacen.Search("Cadiz");
		assertEquals(0,			aAlmacen.size());
	}

}
