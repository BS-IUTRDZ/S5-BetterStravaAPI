package iut.info3.betterstravaapi.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

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
        List<UserEntity> list = new ArrayList<>();
        list.add(new UserEntity());
        when(userService.findByEmailAndPassword("utilisateur@test.com",
        "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"))
                .thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
            .get("/api/users/login?email=utilisateur@test.com&password=test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testAuthenticateWithInvalidUser() throws Exception {
        List<UserEntity> list = new ArrayList<>();
        when(userService.findByEmailAndPassword("utilisateur@test.com",
    "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"))
            .thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/login?email=utilisateur@test.com&password=test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
