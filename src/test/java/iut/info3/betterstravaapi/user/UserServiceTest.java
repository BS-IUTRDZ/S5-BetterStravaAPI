package iut.info3.betterstravaapi.user;

import iut.info3.betterstravaapi.EnvGetter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;

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
        assertEquals(rechercher.toString(),userService.findByEmailAndPassword("utilisateur@test.com","9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08").get(0).toString());
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

}