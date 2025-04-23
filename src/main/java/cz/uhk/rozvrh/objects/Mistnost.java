package cz.uhk.rozvrh.objects;

public class Mistnost {
    String cislo;
    String budova;

    public String getCislo() {
        return cislo;
    }

    public String getBudova() {
        return budova;
    }

    @Override
    public String toString() {
        return "Mistnost{" +
                "cislo='" + cislo + '\'' +
                ", budova='" + budova + '\'' +
                '}';
    }
}
