package iut.info3.betterstravaapi.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testCreationTokenValid() {
        UserEntity entity =
                new UserEntity("John.Doe@gmail.com","John","Doe","mdp");
        entity.setId(1234);
        String expectedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
                + "eyJpZCI6MTIzNCwiZW1haWwiOiJKb2huLkRvZUBnbWFpbC5jb20iLCJkYXRldGltZS1jbGFpbSI6MTcwMjQ4MzQzMSwiZXhwIjoxNzA1MDc1NDMxfQ." +
                "ptepiOaary3v_kOlx0syVEEmYZ7a1B2bl1UBQK9SuL8";
        String[] tabExpected = expectedToken.split("\\.");
        String[] tabUser = userService.generateToken(entity).split("\\.");

        assertEquals(tabExpected[0],tabUser[0]);
        assertEquals(tabExpected[2],tabUser[2]);

    }

}