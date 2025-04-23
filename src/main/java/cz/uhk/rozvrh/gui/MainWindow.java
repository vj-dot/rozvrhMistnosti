package cz.uhk.rozvrh.gui;

import cz.uhk.rozvrh.RozvrhReader;
import cz.uhk.rozvrh.objects.RozvrhovaAkce;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {

    private JComboBox<String> comboSemestr;
    private JComboBox<String> comboBudova;
    private JComboBox<String> comboMistnost;
    private JButton buttonHledat;
    private JTable table;
    private DefaultTableModel tableModel;
    ImageIcon img = new ImageIcon("src/main/resources/VJ.png");

    public MainWindow() {
        super("Rozvrhové hodiny");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(img.getImage());

        // Pro lepsi vzhled GUI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initUI();

        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        // horni ovladaci panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        comboSemestr = createStyledComboBox(new String[]{"ZS", "LS"});
        comboBudova = createStyledComboBox(new String[]{"J", "H", "B"});
        comboMistnost = createStyledComboBox(new String[]{"J1", "J2", "J3"});

        buttonHledat = new JButton("Hledat");
        styleButton(buttonHledat);

        topPanel.add(new JLabel("Semestr:"));
        topPanel.add(comboSemestr);
        topPanel.add(new JLabel("Budova:"));
        topPanel.add(comboBudova);
        topPanel.add(new JLabel("Místnost:"));
        topPanel.add(comboMistnost);
        topPanel.add(buttonHledat);

        add(topPanel, BorderLayout.NORTH);

        // tabulka
        tableModel = new DefaultTableModel(new String[]{"Předmět", "Název", "Typ akce", "Učitel", "Den", "Od", "Do"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Rozvrhové akce"));
        add(scrollPane, BorderLayout.CENTER);

        buttonHledat.addActionListener(e -> nactiData());
    }


    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(Color.WHITE);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setFocusable(false);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return comboBox;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // SteelBlue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setForeground(Color.BLACK);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setRowHeight(25);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(5, 0));
        table.setSelectionBackground(new Color(220, 240, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setFillsViewportHeight(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);  // Předmět
        table.getColumnModel().getColumn(1).setPreferredWidth(250); // Název
        table.getColumnModel().getColumn(2).setPreferredWidth(40); // Typ akce
        table.getColumnModel().getColumn(3).setPreferredWidth(200); // Učitel
        table.getColumnModel().getColumn(4).setPreferredWidth(40); // Den
        table.getColumnModel().getColumn(5).setPreferredWidth(40); // Od
        table.getColumnModel().getColumn(5).setPreferredWidth(40); // Do
        table.getTableHeader().setReorderingAllowed(false);
    }

    private void nactiData() {
        try {
            RozvrhReader reader = new RozvrhReader();
            String semestr = (String) comboSemestr.getSelectedItem();
            String budova = (String) comboBudova.getSelectedItem();
            String mistnost = (String) comboMistnost.getSelectedItem();
            List<RozvrhovaAkce> akceList = reader.readRozvrh(semestr, budova, mistnost);

            SwingUtilities.invokeLater(() -> {
                tableModel.setRowCount(0); // vymazani dat z tabulky

                for (RozvrhovaAkce akce : akceList) {
                    tableModel.addRow(new Object[]{
                            akce.predmet,
                            akce.nazev,
                            akce.typAkce,
                            akce.ucitel,
                            akce.den,
                            akce.hodinaSkutOd,
                            akce.hodinaSkutDo
                    });
                    System.out.println(akce);
                }

                if (akceList.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Nebyly nalezeny žádné rozvrhové akce pro zadané kritéria.",
                            "Informace", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Chyba při načítání dat: " + e.getMessage(),
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }
}