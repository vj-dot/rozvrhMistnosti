package cz.uhk.rozvrh.gui;

import cz.uhk.rozvrh.RozvrhReader;
import cz.uhk.rozvrh.objects.Mistnost;
import cz.uhk.rozvrh.objects.RozvrhovaAkce;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MainWindow extends JFrame {

    private JComboBox<String> comboSemestr;
    private JComboBox<String> comboBudova;
    private JComboBox<String> comboMistnost;
    private JButton buttonHledat;
    private JButton buttonRozvrh;
    private JTable table;
    private RozvrhTableModel tableModel;
    ImageIcon img = new ImageIcon("src/main/resources/VJ.png");

    private List<Mistnost> mistnosti;
    private List<RozvrhovaAkce> akceList;

    public MainWindow() {
        super("Rozvrhové hodiny");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(img.getImage());

        // lepsi vzhled GUI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initUI();

        setSize(1000, 700);
        setLocationRelativeTo(null);
        try {
            nactiBudovy();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Chyba při načítání budov: " + e.getMessage(),
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        // horni ovladaci panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        comboSemestr = createStyledComboBox(new String[]{"ZS", "LS"});
        comboBudova = createStyledComboBox(new String[]{});
        comboMistnost = createStyledComboBox(new String[]{"J1", "J2", "J3"});

        buttonHledat = new JButton("Hledat");
        styleButton(buttonHledat);
        buttonRozvrh = new JButton("Rozvrh");
        styleButton(buttonRozvrh);

        topPanel.add(new JLabel("Semestr:"));
        topPanel.add(comboSemestr);
        topPanel.add(new JLabel("Budova:"));
        topPanel.add(comboBudova);
        topPanel.add(new JLabel("Místnost:"));
        topPanel.add(comboMistnost);
        topPanel.add(buttonHledat);
        topPanel.add(buttonRozvrh);

        add(topPanel, BorderLayout.NORTH);

        // tabulka
        tableModel = new RozvrhTableModel();

        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Rozvrhové akce"));
        add(scrollPane, BorderLayout.CENTER);

        comboBudova.addActionListener(e -> aktualizujMistnosti());
        buttonHledat.addActionListener(e -> nactiData());
        buttonRozvrh.addActionListener(e -> otevriRozvrhDialog());
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
            akceList = reader.readRozvrh(semestr, budova, mistnost);

            SwingUtilities.invokeLater(() -> {
                tableModel.setList(akceList);

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

    private void nactiBudovy() throws Exception {
        RozvrhReader reader = new RozvrhReader();
        mistnosti = reader.readMistnosti();

        Set<String> budovy = new HashSet<>();
        for (Mistnost mistnost : mistnosti) {
            if (mistnost.getZkrBudovy() != null && !mistnost.getZkrBudovy().isEmpty() && mistnost.getTyp().equals("Učebna")) {
                budovy.add(mistnost.getZkrBudovy());
            }
        }
        List<String> budovyList = new ArrayList<>(budovy);
        Collections.sort(budovyList);

        comboBudova.removeAllItems();
        for (String budova : budovyList) {
            comboBudova.addItem(budova);
        }

        aktualizujMistnosti();
    }

    private void aktualizujMistnosti() {
        String budova = (String) comboBudova.getSelectedItem();
        List<Mistnost> filtrovaneMistnosti = new ArrayList<>();

        for (Mistnost m : mistnosti) {
            if (budova != null && budova.equals(m.getZkrBudovy()) && m.getTyp().equals("Učebna")) {
                filtrovaneMistnosti.add(m);
            }
        }

        List<String> mistnostiProBudovu = new ArrayList<>();
        for (Mistnost m : filtrovaneMistnosti) {
            mistnostiProBudovu.add(m.getCisloMistnosti());
        }

        mistnostiProBudovu.sort((a, b) -> {
            // rozdeleni Stringu na pismeno a cislo
            String[] partsA = a.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            String[] partsB = b.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

            // nejdriv se porovnaji pismena, pak kdyz jsou stejna tak cisla
            int result = partsA[0].compareTo(partsB[0]);
            if (result == 0 && partsA.length > 1 && partsB.length > 1) {
                Integer numA = Integer.parseInt(partsA[1]);
                Integer numB = Integer.parseInt(partsB[1]);
                return numA.compareTo(numB);
            }
            return result;
        });

        comboMistnost.setModel(new DefaultComboBoxModel<>(mistnostiProBudovu.toArray(new String[0])));
    }

    private void otevriRozvrhDialog() {
        try {
            if (akceList == null || akceList.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nejdříve vyhledejte rozvrhové akce.",
                        "Informace", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            nactiRozvrh();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Chyba při načítání rozvrhu: " + e.getMessage(),
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void nactiRozvrh() {
        String title = "Rozvrh - " + (String) comboSemestr.getSelectedItem() + " - " + (String) comboBudova.getSelectedItem() + " - " + (String) comboMistnost.getSelectedItem();
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(1400, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        String[] hodiny = {"07:25", "08:15", "09:05", "09:55", "10:45", "11:35", "12:25", "13:15", "14:05", "14:55", "15:45", "16:35", "17:25", "18:15", "19:05", "19:55"};
        String[] dny = {"Pondělí", "Úterý", "Středa", "Čtvrtek", "Pátek", "Sobota"};

        JPanel schedulePanel = new JPanel(new GridLayout(dny.length + 1, hodiny.length + 1, 0, 0));
        schedulePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        schedulePanel.add(new JLabel("Den/Hodina"));
        for (String hodina : hodiny) {
            schedulePanel.add(new JLabel(hodina , SwingConstants.CENTER));
        }

        Map<String, Map<String, JLabel>> cellMap = new HashMap<>(); // pod kazdym dnem je mapa hodin a bunek
        for (String den : dny) {
            schedulePanel.add(new JLabel(den, SwingConstants.CENTER));

            for (String hodina : hodiny) {
                JLabel cell = new JLabel("");
                cell.setPreferredSize(new Dimension(75, 20));
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                schedulePanel.add(cell);

                if (!cellMap.containsKey(den)) {
                    cellMap.put(den, new HashMap<>());
                }
                cellMap.get(den).put(hodina, cell);

//                for (RozvrhovaAkce akce : akceList) {
//                    if (akce.den.equals(den) && akce.hodinaSkutOd.toString().equals(hodina)) {
//                        cell.setText(akce.predmet);
//                        cell.setHorizontalAlignment(SwingConstants.CENTER);
//                        cell.setBackground(Color.LIGHT_GRAY);
//                        cell.setOpaque(true);
//                    }
//                }
            }
        }

        for (RozvrhovaAkce akce : akceList) {
            String den = akce.den;
            String hodinaOd = akce.hodinaSkutOd.toString();
            String typAkce = akce.typAkce;

            System.out.println("Akce: " + akce.predmet + ", Den: " + akce.den + ", Hodina od: " + akce.hodinaSkutOd + ", do: " + akce.hodinaSkutDo);

            if (cellMap.containsKey(den) && cellMap.get(den).containsKey(hodinaOd)) {
                JLabel cell = cellMap.get(den).get(hodinaOd);
                cell.setText(akce.predmet);
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setOpaque(true);

                if ("Cvičení".equals(typAkce)) {
                    cell.setBackground(new Color(208, 255, 255));
                } else if ("Přednáška".equals(typAkce)) {
                    cell.setBackground(new Color(228, 255, 222));
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(schedulePanel);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }

}