package data.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import data.Material;
import data.Almacen;

class Materialtest {

	@Test
	void testConstructor() throws IOException, SQLException {
		Material material = new Material(Almacen.Get(2), "Mochila", "Mochila pequeña", 3);
		
		assertEquals(Almacen.Get(2).GetId(),	material.GetAlmacen().GetId());
		assertEquals("Mochila",					material.GetName());
		assertEquals("Mochila pequeña", 		material.GetDescription());
		assertEquals(3,							material.GetStock());
	}
	
	@Test
	void testSets() throws IOException, IllegalArgumentException, SQLException {
		Material material = new Material(Almacen.Get(2), "Mochila", "Mochila pequeña", 3);
		
		material.SetAlmacen(Almacen.Get(1));
		material.SetName("Mesa");
		material.SetDescription("Tablero con cuatro patas.");
		material.SetStock(10);
		
		assertEquals(Almacen.Get(1).GetId(),		material.GetAlmacen().GetId());
		assertEquals("Mesa", 						material.GetName());
		assertEquals("Tablero con cuatro patas.", 	material.GetDescription());
		assertEquals(10, 							material.GetStock());
	}
	
	@Test
	void testGet() throws IOException, SQLException {
		Material material = Material.Get(2);
		
		assertEquals(Almacen.Get(1).GetId(),		material.GetAlmacen().GetId());
		assertEquals("Cartucho Ak-47", 				material.GetName());
		assertEquals("Cartucho de balas de ak-47", 	material.GetDescription());
		assertEquals(5, 							material.GetStock());
	}
	
	@Test
	void testSaveDelete() throws SQLException, IOException {
		Material material = new Material(Almacen.Get(2), "O'Connel", "prueba", 40);
		
		material.Save();	// Guarda en la BD
		material = Material.Get(material.GetId());
		assertEquals(Almacen.Get(2).GetId(),	material.GetAlmacen().GetId());
		assertEquals("O'Connel", 				material.GetName());
		assertEquals("prueba", 					material.GetDescription());
		assertEquals(40, 						material.GetStock());
		
		material.SetAlmacen(Almacen.Get(1));
		material.SetName("actualizado");
		material.SetDescription("nuevo objeto");
		material.SetStock(2);
		material.Save();	// Actualiza el objeto en la BD
		
		material = Material.Get(material.GetId());
		assertEquals(Almacen.Get(1).GetId(),	material.GetAlmacen().GetId());
		assertEquals("actualizado", 			material.GetName());
		assertEquals("nuevo objeto", 			material.GetDescription());
		assertEquals(2, 						material.GetStock());
			
		material.Delete();
		assertNull(Material.Get(material.GetId()));
		assertNotNull(material.getDeletedAt());
	}
	
	@Test
	void testSearch() throws IllegalStateException, SQLException, IOException {
		List<Material> aMaterial = Material.Search(null, null);
		
		assertEquals(3,						aMaterial.size());
		
		assertEquals(4,						aMaterial.get(0).GetId());
		assertEquals(1,						aMaterial.get(1).GetId());
		assertEquals(2,						aMaterial.get(2).GetId());
		assertEquals("Abrigo",				aMaterial.get(0).GetName());
		assertEquals("Botella de agua 1L",	aMaterial.get(1).GetName());
		assertEquals("Cartucho Ak-47",		aMaterial.get(2).GetName());
		assertEquals(Almacen.Get(2).GetId(), aMaterial.get(0).GetAlmacen().GetId());
		assertEquals(Almacen.Get(2).GetId(), aMaterial.get(1).GetAlmacen().GetId());
		assertEquals(Almacen.Get(1).GetId(), aMaterial.get(2).GetAlmacen().GetId());
		
		aMaterial = Material.Search("u", null);
		assertEquals("Botella de agua 1L",	aMaterial.get(0).GetName());
		assertEquals("Cartucho Ak-47", 		aMaterial.get(1).GetName());
		
		aMaterial = Material.Search("Abrigo", null);
		assertEquals("Abrigo", 	aMaterial.get(0).GetName());
		
		aMaterial = Material.Search("Coche", null);
		assertEquals(0,			aMaterial.size());
		
		aMaterial = Material.Search(null, "a");
		assertEquals("Abrigo",				aMaterial.get(0).GetName());
		assertEquals("Botella de agua 1L", 	aMaterial.get(1).GetName());
		
		aMaterial = Material.Search(null, "Moscu");
		assertEquals("Cartucho Ak-47", aMaterial.get(0).GetName());
		
		aMaterial = Material.Search(null, "Coche");
		assertEquals(0, aMaterial.size());
		
	}
}
