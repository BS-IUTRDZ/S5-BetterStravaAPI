package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PathServiceTest {

    @Autowired
    private PathService pathService;

    @SpyBean
    private PathRepository repository;

    @Test
    public void testRecupDernierParcour() throws Exception{
        assertEquals(12.5,pathService.recupDernierParcour(1).getDistance());
    }

    @Test
    public void testRecup30Jour(){
        assertEquals(1,pathService.recupParcours30Jours(1).size());
    }

    @Test
    public void testRecupGlobal(){
        assertEquals(2,pathService.recupParcoursAll(1).size());
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

    @Test
    public void testFindPaths() throws ParseException {
        pathService.findParcourByDateAndName("nom","01/01/2023","01/01/2025",1);


        verify(repository).findEntitiesByDateAndName(1672527600000L, 1735686000000L,"nom",1,false);
    }

    @Test
    public void testRecupParcoursParId() {
        PathEntity path = pathService.recupParcoursParId(new ObjectId("65cc80a3261be8e0c30adfaf"));

        assertEquals("balade a salles-la-source",path.getNom());
    }

}
