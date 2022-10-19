import com.google.gson.Gson;
//import org.junit.jupiter.api.AfterEach;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
//import java.util.HashMap;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;

public class TestCalculationMax {
    final String textJsonOther = "{\"title\": \"шило\", \"date\": \"2022.02.08\", \"sum\": 400}";
    final String textJsonCetegory = "{\"title\": \"акции\", \"date\": \"2022.02.08\", \"sum\": 500}";
    //final Map<String, Integer> categoryTest = new HashMap<>();
    Gson gsonTest = new Gson();

    @Mock
    private Main mainServer;

    @BeforeEach
    void setUp() {
        mainServer = Mockito.mock(Main.class);
        Marcet marcetTest = gsonTest.fromJson(textJsonOther, Marcet.class);
        mainServer.basket.add(marcetTest);
        Marcet marcetTestCategory = gsonTest.fromJson(textJsonCetegory, Marcet.class);
        mainServer.basket.add(marcetTestCategory);
    }

    @Test
    public void testLoadOtherFromTSV() throws IOException {

        Map actualResult = (CalculationMax.loadFromTSV(new File("categories.tsv")));
        JSONObject expectedResult = new JSONObject();
        expectedResult.put("categories", "другое");
        expectedResult.put("sum", 400);
        System.out.println(expectedResult);
        System.out.println(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testLoadCategoryFromTSV() throws IOException {

        Map actualResult = (CalculationMax.loadFromTSV(new File("categories.tsv")));
        JSONObject expectedResult = new JSONObject();
        expectedResult.put("categories", "финансы");
        expectedResult.put("sum", 500);
        System.out.println(expectedResult);
        System.out.println(actualResult);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
