package iut.info3.betterstravaapi.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

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
        Instant date = Instant.parse("2023-11-30T18:35:24.00Z");
        UserEntity entity =
                new UserEntity("John.Doe@gmail.com","John","Doe","mdp");
        entity.setId(1234);
        String expectedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
                + "eyJpZCI6MTIzNCwiZW1haWwiOiJKb2huLkRvZUBnbWFpbC5jb20iLCJkYXRldGltZS1jbGFpbSI6MTcwMTM2OTMyNCwiZXhwIjoxNzAzOTYxMzI0fQ.";

        String[] tabExpected = expectedToken.split("\\.");
        String[] tabUser = userService.generateToken(entity,date).split("\\.");

        String expected = tabExpected[0] + tabExpected[1];
        String real = tabUser[0] + tabUser[1];

        assertEquals(expected,real);

    }

}