package cz.uhk.rozvrh.gui;

import cz.uhk.rozvrh.objects.RozvrhovaAkce;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ScheduleTableModel extends AbstractTableModel {

    private final String[] dny;
    private final String[] hodiny;
    private final String[][] data;

    public ScheduleTableModel(String[] dny, String[] hodiny, List<RozvrhovaAkce> akceList) {
        this.dny = dny;
        this.hodiny = hodiny;
        this.data = new String[dny.length][hodiny.length];

        for (RozvrhovaAkce akce : akceList) {
            int denIndex = najdiIndex(dny, akce.den);
            int hodinaIndex = najdiIndex(hodiny, akce.hodinaSkutOd.toString());

            if (denIndex >= 0 && hodinaIndex >= 0) {
                data[denIndex][hodinaIndex] = akce.predmet;
            }
        }
    }

    private int najdiIndex(String[] pole, String hodnota) {
        for (int i = 0; i < pole.length; i++) {
            if (pole[i].equals(hodnota)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getRowCount() {
        return dny.length;
    }

    @Override
    public int getColumnCount() {
        return hodiny.length + 1;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Den";
        }
        return hodiny[column - 1];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return dny[rowIndex];
        }
        return data[rowIndex][columnIndex - 1];
    }
}
