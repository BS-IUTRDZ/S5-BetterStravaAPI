package iut.info3.betterstravaapi.user;

import iut.info3.betterstravaapi.EnvGetter;
import iut.info3.betterstravaapi.path.PathEntity;
import iut.info3.betterstravaapi.path.Statistiques;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private EnvGetter envGetter;

    @Autowired
    private UserService userService;

    @Test
    public void testCheckPresenceEmailWithEmailAlreadyTaken() {
        assertTrue(userService.checkPresenceEmail("utilisateur@test.com"));
    }

    @Test
    public void testCheckPresenceEmailWithoutEmailAlreadyTaken() {
        assertFalse(userService.checkPresenceEmail("utilisateurnonexistant@test.com"));
    }

    @Test
    public void testFindUserInDataBase() {
        UserEntity rechercher = new UserEntity("utilisateur@test.com","test","utilisateur","9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
        rechercher.setId(1);
        assertEquals(rechercher.toString(),userService.findByEmailAndPassword("utilisateur@test.com","9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08").toString());
    }

    @Test
    public void testCreationTokenValid() {
        when(envGetter.getSentence()).thenReturn("LeSanglier");
        when(envGetter.getExpiration()).thenReturn(123456789L);
        Instant date = Instant.parse("2023-11-30T18:35:24.00Z");
        UserEntity entity =
                new UserEntity("John.Doe@gmail.com","John","Doe","mdp");
        entity.setId(1234);
        String expected = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
                + "eyJpZCI6MTIzNCwiZW1haWwiOiJKb2huLkRvZUBnbWFpbC5jb20iLCJkYXRldGltZS1jbGFpbSI6MTcwMTM2OTMyNCwiZXhwIjoxODI0ODI2MTEzfQ."
                + "SVcbXATQKfSKquE9c90q1cmYhh_iclSqFq--_FNwFwQ";

        String real = userService.generateToken(entity,date);

        assertEquals(expected,real);

    }

    @Test
    public void testValiditerTokenValid(){
        when(envGetter.getSentence()).thenReturn("LeSanglier");
        when(envGetter.getExpiration()).thenReturn(123456789L);

        UserEntity entity =
                new UserEntity("John.Doe@gmail.com","John","Doe","mdp");
        entity.setId(1234);

        String jwt = userService.generateToken(entity,Instant.now());

        assertTrue(userService.isTokenNotExpired(jwt));

    }

    @Test
    public void testValiditerTokenInvalid() {
        when(envGetter.getSentence()).thenReturn("LeSanglier");
        when(envGetter.getExpiration()).thenReturn(0L);

        UserEntity entity =
                new UserEntity("John.Doe@gmail.com","John","Doe","mdp");
        entity.setId(1234);

        String jwt = userService.generateToken(entity,Instant.now().minus(10, ChronoUnit.SECONDS));

        assertFalse(userService.isTokenNotExpired(jwt));

    }

    @Test
    public void testCalculerPerformance() {
        PathEntity path = new PathEntity();
        path.setDate(Calendar.getInstance().getTime().getTime());
        Statistiques stat = new Statistiques();
        stat.setDuree(1500);
        stat.setDistance(15);
        path.setStatistiques(stat);

        PathEntity pathVide = new PathEntity();

        List<PathEntity> liste = new ArrayList<>();
        liste.add(path);
        liste.add(path);
        liste.add(path);
        liste.add(pathVide);

        Map<String,String> expected = new HashMap<>();
        expected.put("nombre_parcours","4");
        expected.put("temps","4500.0");
        expected.put("distance","45.0");

        assertEquals(expected, userService.calculerPerformance(liste));

    }

}