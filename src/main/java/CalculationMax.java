import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

public class CalculationMax {
    private Map<String, Integer> postServer = new HashMap<>();
    private List<Marcet> basket = new ArrayList<>();

    ////////////////////////////////////////////////метод создания корзины///////////////////////////////////
    public void addMarcet(BufferedReader in) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Marcet marcet = gson.fromJson(in,Marcet.class);
        basket.add(marcet);// добавляем в массив корзины сообщения с клиента
    }

    /////////////////////////////////////метод, который переводит файл TSV в List, проводит расчеты Max и выдает пару из Map
    public Map loadFromTSV(File file) throws IOException {

        List<String[]> categories = new ArrayList<>();
        //Marcet marcet = new Marcet(title, date, sum);
        Map<String, Integer> postServer = new HashMap<>();
        categories = Files.lines(file.toPath())
                .map(line -> line.split("\t"))
                .collect(Collectors.toList());
        Map<String, String> resultsMap = new HashMap<String, String>();
        for (String[] s : categories) {
            resultsMap.put((String) s[0], (String) s[1]);
        }

        for (Marcet index : basket) {
            if (!resultsMap.containsKey(index.title)) {
                if (postServer.isEmpty()) {
                    postServer.put("другое", index.sum);
                } else {
                    int sum = postServer.get("другое");
                    sum += index.sum;
                    postServer.put("другое", sum);
                }
            }
            if (resultsMap.containsKey(index.title)) {
                if (postServer.isEmpty()) {
                    postServer.put(resultsMap.get(index.title), index.sum);
                } else {
                    int sum = postServer.containsKey(resultsMap.get(index.title)) ? postServer.get(resultsMap.get(index.title)) : 0;
                    sum += index.sum;
                    postServer.put(resultsMap.get(index.title), sum);
                }
            }
        }
        String maxFinCategory = Collections.max(postServer.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();
        int maxFinSum = postServer.get(maxFinCategory);
        JSONObject jsonMaxSum = new JSONObject();
        jsonMaxSum.put("categories", maxFinCategory);
        jsonMaxSum.put("sum", maxFinSum);
        return jsonMaxSum;
    }
}