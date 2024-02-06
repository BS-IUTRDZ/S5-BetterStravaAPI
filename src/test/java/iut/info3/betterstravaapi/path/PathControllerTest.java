package iut.info3.betterstravaapi.path;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.Json;
import iut.info3.betterstravaapi.EnvGetter;
import iut.info3.betterstravaapi.user.UserEntity;
import iut.info3.betterstravaapi.user.UserService;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static iut.info3.betterstravaapi.user.UserControllerTest.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PathController.class)
public class PathControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    PathService pathService;

    @MockBean
    PathRepository pathRepository;

    @MockBean
    UserService userService;

    private PathEntity pathEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        ArrayList<Coordonnees> points = new ArrayList<>();
        points.add(new Coordonnees(48.25,12.25));
        points.add(new Coordonnees(43.85,17.855));

        ArrayList<PointInteret> pointsInteret = new ArrayList<>();
        pointsInteret.add(new PointInteret("test","super",new Coordonnees(78.58,69.54)));

        pathEntity = new PathEntity(
                2,
                "reussi",
                "path success",
                points,
                pointsInteret
        );

        pathEntity.setId(new ObjectId("a1a1a1a1a1a1a1a1a1a1a1a1"));

        userEntity = new UserEntity(
                "test@mail.com",
                "Test",
                "test",
                "motdepasse");
        userEntity.setId(2);
    }

    @Test
    public void testCreatePathSuccess() throws Exception {

        when(userService.getTokenBd(2)).thenReturn("token");
        when(userService.verifierDateExpiration("token")).thenReturn(true);
        when(pathService.recupDernierParcour(2)).thenReturn(pathEntity);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/createPath")
                        .content(asJsonString(pathEntity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void testCreatePathTokenInvalid() throws Exception {

        when(userService.getTokenBd(2)).thenReturn("token");
        when(userService.verifierDateExpiration("token")).thenReturn(false);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/createPath")
                        .content(asJsonString(pathEntity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void testLastPathSuccess() throws Exception {
        JSONObject jsonObject = new JSONObject(
                """
                        {"description": "path success"}
                       """);

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathService.recupDernierParcour(2)).thenReturn(pathEntity);
        when(pathService.getPathInfos(pathEntity)).thenReturn(jsonObject);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/lastPath")
                        .header("token", "token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testLastPathTokenInvalid() throws Exception {
        JSONObject jsonObject = new JSONObject(
                """
                        {"description": "path success"}
                       """);

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathService.recupDernierParcour(2)).thenReturn(pathEntity);
        when(pathService.getPathInfos(pathEntity)).thenReturn(jsonObject);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/lastPath")
                        .header("token", "mauvaisToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLastPathParamInvalid() throws Exception {
        JSONObject jsonObject = new JSONObject(
                """
                        {"description": "path success"}
                       """);

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathService.recupDernierParcour(2)).thenReturn(pathEntity);
        when(pathService.getPathInfos(pathEntity)).thenReturn(jsonObject);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/lastPath")
                        .header("mauvaisToken", "mauvaisToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testModifyDescriptionSuccess() throws Exception {
        JSONObject jsonObject = new JSONObject(
                """
                        {"description": "path modify success",
                        "id": "a1a1a1a1a1a1a1a1a1a1a1a1"}
                       """);

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathRepository.findById(pathEntity.getId())).thenReturn(java.util.Optional.of(pathEntity));

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/modifyDescription")
                        .content(jsonObject.toString())
                        .header("token", "token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals("path modify success", pathEntity.getDescription());
    }

    @Test
    public void testModifyDescriptionTokenInvalid() throws Exception {
        JSONObject jsonObject = new JSONObject(
                """
                        {"description": "path success",
                        "id": "a1a1a1a1a1a1a1a1a1a1a1a1"}
                       """);

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathRepository.findById(pathEntity.getId())).thenReturn(java.util.Optional.of(pathEntity));

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/modifyDescription")
                        .content(jsonObject.toString())
                        .header("token", "mauvaisToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testModifyDescriptionIdInvalid() throws Exception {
        JSONObject jsonObject = new JSONObject(
                """
                        {"description": "path success",
                        "id": "a2a2a2a2a2a2a2a2a2a2a2a2"}
                       """);

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathRepository.findById(pathEntity.getId())).thenReturn(java.util.Optional.of(pathEntity));

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/modifyDescription")
                        .content(jsonObject.toString())
                        .header("token", "token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testModifyDescriptionJsonInvalid() throws Exception {
        String invalidJson = "invalid json body";

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathRepository.findById(pathEntity.getId())).thenReturn(java.util.Optional.of(pathEntity));

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/modifyDescription")
                        .content(invalidJson)
                        .header("token", "token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
