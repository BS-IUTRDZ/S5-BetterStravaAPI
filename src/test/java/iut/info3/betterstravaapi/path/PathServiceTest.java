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
        assertEquals(14.6,pathService.recupDernierParcour(1).getStatistiques().getDistance());
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
    public void testFindPaths() throws ParseException {
        pathService.findParcourByDateAndName("nom","01/01/2023","01/01/2025",1);


        verify(repository).findEntitiesByDateAndName(1672531200000L, 1735689600000L,"nom",1,false);
    }

    @Test
    public void testFindPathsLength() throws ParseException {
        pathService.findParcourByDateAndNameAndDistance("nom","01/01/2023","01/01/2025", 15,15,1);


        verify(repository).findEntitiesByDateAndNameAndDistance(1672531200000L, 1735689600000L,"nom", 15 , 15,1,false);
    }



}
