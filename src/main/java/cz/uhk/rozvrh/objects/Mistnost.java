package cz.uhk.rozvrh.objects;

public class Mistnost {
    String cisloMistnosti;
    String zkrBudovy;
    String typ;

    public String getCisloMistnosti() {
        return cisloMistnosti;
    }

    public String getZkrBudovy() {
        return zkrBudovy;
    }

    public String getTyp() {
        return typ;
    }

    @Override
    public String toString() {
        return "Mistnost{" +
                "cisloMistnosti='" + cisloMistnosti + '\'' +
                ", zkrBudovy='" + zkrBudovy + '\'' +
                ", typ='" + typ + '\'' +
                '}';
    }
}
