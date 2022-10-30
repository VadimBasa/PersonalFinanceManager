import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.Mockito;

public class TestCalculationMax {
    final String textJsonOther = "{\"title\": \"шило\", \"date\": \"2022.02.08\", \"sum\": 600}";
    final String textJsonCetegory = "{\"title\": \"акции\", \"date\": \"2022.02.08\", \"sum\": 500}";
    CalculationMax calculationMax = new CalculationMax();
    Gson gsonTest = new Gson();

    @Mock
    private Main mainServer;

    @BeforeEach
    void setUp() {
        mainServer = Mockito.mock(Main.class);
    }

    @Test
    public void testLoadOtherFromTSV() throws IOException {
        Marcet marcetTest = gsonTest.fromJson(textJsonOther, Marcet.class);
        calculationMax.basket.add(marcetTest);
        Map actualResult = (calculationMax.loadFromTSV(new File("categories.tsv")));
        JSONObject expectedResult = new JSONObject();
        expectedResult.put("categories", "другое");
        expectedResult.put("sum", 600);
        System.out.println(expectedResult.get("categories"));
        System.out.println(actualResult.get("categories"));
        Assertions.assertEquals(expectedResult.get("categories"), actualResult.get("categories"));
    }

    @Test
    public void testLoadCategoryFromTSV() throws IOException {
        Marcet marcetTestCategory = gsonTest.fromJson(textJsonCetegory, Marcet.class);
        calculationMax.basket.add(marcetTestCategory);
        Map actualResult = (calculationMax.loadFromTSV(new File("categories.tsv")));
        JSONObject expectedResult = new JSONObject();
        expectedResult.put("categories", "финансы");
        expectedResult.put("sum", 500);
        System.out.println(expectedResult.get("sum"));
        System.out.println(actualResult.get("sum"));
        Assertions.assertEquals(expectedResult.get("sum"), actualResult.get("sum"));
    }
}
