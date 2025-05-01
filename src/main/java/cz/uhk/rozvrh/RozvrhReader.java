package cz.uhk.rozvrh;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import cz.uhk.rozvrh.objects.Mistnost;
import cz.uhk.rozvrh.objects.RozvrhovaAkce;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RozvrhReader {

    public List<RozvrhovaAkce> readRozvrh(String semestr, String budova, String mistnost) throws Exception {
        String jsonUrl = "https://stag-demo.uhk.cz/ws/services/rest2/rozvrhy/getRozvrhByMistnost?outputFormat=JSON";

        if (semestr != null) {
            jsonUrl += "&semestr=" + semestr;
        }
        if (budova != null) {
            jsonUrl += "&budova=" + budova;
        }
        if (mistnost != null) {
            jsonUrl += "&mistnost=" + mistnost;
        }

        HttpURLConnection connection = (HttpURLConnection) new URL(jsonUrl).openConnection();
        connection.setRequestMethod("GET");

        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");

        Gson gson = new GsonBuilder().create();

        // ziskani JSON objektu
        JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

        // Získání seznamu rozvrhových akcí z JSON objektu
        Type listType = new TypeToken<List<RozvrhovaAkce>>() {}.getType();
        List<RozvrhovaAkce> akceList = gson.fromJson(root.get("rozvrhovaAkce"), listType);

        List<RozvrhovaAkce> filteredAkceList = new ArrayList<>();
        for (RozvrhovaAkce akce : akceList) {
            if ("Cvičení".equals(akce.typAkce) || "Přednáška".equals(akce.typAkce) || "Seminář".equals(akce.typAkce)) {
                filteredAkceList.add(akce);
            }
        }

        return filteredAkceList;
    }

    public List<Mistnost> readMistnosti() throws Exception {
        String jsonUrl = "https://stag-demo.uhk.cz/ws/services/rest2/mistnost/getMistnostiInfo?outputFormat=JSON";

        HttpURLConnection connection = (HttpURLConnection) new URL(jsonUrl).openConnection();
        connection.setRequestMethod("GET");
        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");

        Gson gson = new GsonBuilder().create();
        JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
        Type listType = new TypeToken<List<Mistnost>>() {}.getType();
        List<Mistnost> mistnostiList = gson.fromJson(root.get("mistnostInfo"), listType);

        return mistnostiList;
    }

}
