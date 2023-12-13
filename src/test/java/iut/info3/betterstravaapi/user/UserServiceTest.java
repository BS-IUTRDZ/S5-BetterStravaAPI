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

}