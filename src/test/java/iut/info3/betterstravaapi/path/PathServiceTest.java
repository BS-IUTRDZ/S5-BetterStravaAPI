package iut.info3.betterstravaapi.path;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PathServiceTest {

    @Autowired
    private PathService pathService;

    @Test
    public void testRecupDernierParcour() throws Exception{
        assertEquals(12.5,pathService.recupDernierParcour(1).getDistance());
    }

    @Test
    public void testRecup30Jour(){
        assertEquals(1,pathService.recupPerformances30Jours(1).size());
    }

    @Test
    public void testRecupGlobal(){
        assertEquals(2,pathService.recupPerformancesGlobal(1).size());
    }

    @Test
    public void getPathInfos(){
        PathEntity path = pathService.recupDernierParcour(1);
        JSONObject pathJson = new JSONObject(
                """
                        {
                            "description": "superbe village avec une très jolie cascade meme si la balade est un peu courte",
                            "nom": "balade a salles-la-source",
                            "points": {
                                "point0": {
                                    "latitude": 44.435465,
                                    "longitude": 2.514783
                                },
                                "point1": {
                                    "latitude": 44.436536,
                                    "longitude": 2.514771
                                },
                                "point2": {
                                    "latitude": 44.437898,
                                    "longitude": 2.513335
                                }
                            }
                        }""");
        JSONAssert.assertEquals(pathJson,pathService.getPathInfos(path), false);
    }

}
