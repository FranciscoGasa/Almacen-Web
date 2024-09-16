package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import data.Material;

public class MaterialesTableModel extends AbstractTableModel{
	private  List<Material> _aData;
	
	public MaterialesTableModel(List<Material> aData) { 
		_aData = aData;
	}
	
	@Override
	public int getRowCount() {
		return _aData.size();
	}

	@Override
	public int getColumnCount() {
		return 4;	// Muestra nombre, descripcion, stock
	}

	@Override
	public Object getValueAt(int iRow, int iColumn) throws IllegalArgumentException {
		switch(iColumn) {
			case 0: return _aData.get(iRow).GetName();
			case 1: return _aData.get(iRow).GetDescription();
			case 2: return _aData.get(iRow).GetStock();
			case 3: return _aData.get(iRow).GetAlmacen().GetName();
			default: throw new IllegalArgumentException("No existe la columna " + iColumn + " de la fila " + iRow);
		}
	}

	public Material getData(int iRow) throws SQLException, IOException{
		return _aData.get(iRow);
	}
}
