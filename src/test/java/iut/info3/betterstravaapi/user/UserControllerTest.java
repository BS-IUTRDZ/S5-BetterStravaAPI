package iut.info3.betterstravaapi.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import iut.info3.betterstravaapi.path.PathEntity;
import iut.info3.betterstravaapi.path.PathService;
import org.apache.catalina.User;
import org.bson.json.JsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    PathService pathService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void testCreateAccountSuccess() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/users/createAccount")
                        .content(asJsonString(new UserEntity(
                                "test@mail.com",
                                "nomTest",
                                "prenomTest",
                                "motDePasse")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
    }

    @Test
    public void testCreateAccountWithJsonError() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/users/createAccount")
                        .content(asJsonString(new UserEntity(
                                "test@mail.com",
                                "nomTest",
                                null,
                                "motDePasse")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateAccountWithEmailAlreadyTake() throws Exception
    {
        when(userService.checkPresenceEmail("utilisateur@test.com")).thenReturn(true);
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/users/createAccount")
                        .content(asJsonString(new UserEntity(
                                "utilisateur@test.com",
                                "nomTest",
                                "prenomTest",
                                "motDePasse")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isConflict());
    }

    @Test
    public void testAuthenticateWithValidUser() throws Exception {
        when(userService.findByEmailAndPassword("utilisateur@test.com",
        "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"))
                .thenReturn(new UserEntity());
        mockMvc.perform(MockMvcRequestBuilders
            .get("/api/users/login?email=utilisateur@test.com&password=test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testAuthenticateWithInvalidUser() throws Exception {
        when(userService.findByEmailAndPassword("utilisateur@test.com",
    "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"))
            .thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/login?email=utilisateur@test.com&password=test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testgetInfo() throws Exception {

        UserEntity user = new UserEntity(
                "test@mail.com",
                "nomTest",
                "prenomTest",
                "motDePasse");
        user.setId(1);

        when(userService.findUserByToken("biche")).thenReturn(user);
        when(pathService.recupDernierParcour(1)).thenReturn(new PathEntity());
        when(pathService.recupPerformancesGlobal(1)).thenReturn(new ArrayList<>());
        when(pathService.recupPerformances30Jours(1)).thenReturn(new ArrayList<>());

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/users/getInfo")
                        .content(asJsonString("{token:biche}"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
