package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.table.AbstractTableModel;

import data.Almacen;
import data.Material;

public class AlmacenListModel extends javax.swing.AbstractListModel<Almacen>
							  implements ComboBoxModel<Almacen>
	{
	
	private List<Almacen> _aData;
	private Object _oSelectedItem = null;
	
	public AlmacenListModel(List<Almacen> aData) { 
		_aData = aData;
	}

	@Override
	public int getSize() {
		return _aData.size();
	}

	@Override
	public Almacen getElementAt(int iIndex) {
		return _aData.get(iIndex);
	}
	
	@Override
	public void setSelectedItem(Object iItem) {
		_oSelectedItem= iItem;
	}

	@Override
	public Object getSelectedItem() {
		return _oSelectedItem;
	}
}
