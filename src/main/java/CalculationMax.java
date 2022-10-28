import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

public class CalculationMax {
    private Map<String, Integer> postServer = new HashMap<>();

    /////////////////////////////////////метод, который переводит файл TSV в List, проводит расчеты Max и выдает пару из Map
    public static Map loadFromTSV(File file) throws IOException {

        List<String[]> categories = new ArrayList<>();
        Marcet marcet = new Marcet();
        Map<String, Integer> postServer = new HashMap<>();
        categories = Files.lines(file.toPath())
                .map(line -> line.split("\t"))
                .collect(Collectors.toList());
        Map<String, String> resultsMap = new HashMap<String, String>();
        for (String[] s : categories) {
            resultsMap.put((String) s[0], (String) s[1]);
        }
        for (String key : resultsMap.keySet()) {
            for (Marcet index : Main.basket) {
                if (!resultsMap.containsKey(index.title)) {
                    postServer.put("другое", index.sum);
                }
                if (index.title.equals(key)) {
                    if (postServer.isEmpty()) {
                        postServer.put(resultsMap.get(key), index.sum);
                    } else {
                        for (String i : postServer.keySet()) {
                            int sum = postServer.containsKey(resultsMap.get(key)) ? postServer.get(i) : 0;        // index.sum : 0;        //sum = postServer.get(keyCat) == null ? 0 : postServer.get(keyCat);
                            sum += index.sum;
                            postServer.put(resultsMap.get(key), sum);
                        }
                    }
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

