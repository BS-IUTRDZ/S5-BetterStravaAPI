package iut.info3.betterstravaapi.path;

import org.junit.jupiter.api.Test;
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
        assertEquals(1,pathService.recupParcours30Jours(1).size());
    }

    @Test
    public void testRecupGlobal(){
        assertEquals(2,pathService.recupParcoursAll(1).size());
    }

}
