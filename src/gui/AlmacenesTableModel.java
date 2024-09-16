package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import data.Almacen;

public class AlmacenesTableModel extends AbstractTableModel{
	private  List<Almacen> _aData;
	
	public AlmacenesTableModel(List<Almacen> aData) { 
		_aData = aData;
	}
	
	@Override
	public int getRowCount() {
		return _aData.size();
	}

	@Override
	public int getColumnCount() {
		return 1;	// Muestra nombre, distrito
	}

	@Override
	public Object getValueAt(int iRow, int iColumn) throws IllegalArgumentException {
		switch(iColumn) {
			case 0: return _aData.get(iRow).GetName();
			default: throw new IllegalArgumentException("No existe la columna " + iColumn + " de la fila " + iRow);
		}
	}

	public Almacen getData(int iRow) throws SQLException, IOException{
		return _aData.get(iRow);
	}
}