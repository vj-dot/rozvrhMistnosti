package cz.uhk.rozvrh.gui;

import cz.uhk.rozvrh.objects.RozvrhovaAkce;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RozvrhTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Předmět", "Název", "Typ akce", "Učitel", "Den", "Od", "Do"};
    private List<RozvrhovaAkce> rozvrhovaAkceList = new ArrayList<>();

    public void setList(List<RozvrhovaAkce> rozvrhovaAkceList) {
        this.rozvrhovaAkceList = rozvrhovaAkceList;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rozvrhovaAkceList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RozvrhovaAkce akce = rozvrhovaAkceList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return akce.predmet;
            case 1:
                return akce.nazev;
            case 2:
                return akce.typAkce;
            case 3:
                return akce.ucitel;
            case 4:
                return akce.den;
            case 5:
                return akce.hodinaSkutOd;
            case 6:
                return akce.hodinaSkutDo;
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
