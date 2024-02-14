package iut.info3.betterstravaapi.path;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.Json;
import iut.info3.betterstravaapi.EnvGetter;
import iut.info3.betterstravaapi.user.UserEntity;
import iut.info3.betterstravaapi.user.UserService;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import iut.info3.betterstravaapi.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static iut.info3.betterstravaapi.user.UserControllerTest.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
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

        Statistiques stats = new Statistiques();

        pathEntity = new PathEntity(
                2,
                "reussi",
                "path success",
                1707732412782L,
                points,
                stats
        );

        pathEntity.setPointsInterets(pointsInteret);
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
        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathService.recupDernierParcour(2)).thenReturn(pathEntity);
        when(userService.isTokenNotExpired("token")).thenReturn(true);



        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/createPath")
                        .header("token", "token")
                        .content(asJsonString(pathEntity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void testCreatePathTokenInvalid() throws Exception {

        when(userService.getTokenBd(2)).thenReturn("token");
        when(pathService.recupDernierParcour(2)).thenReturn(pathEntity);
        when(userService.isTokenNotExpired("token")).thenReturn(false);
        when(userService.isTokenNotExpired("token")).thenReturn(false);


        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/createPath")
                        .header("token", "token")
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
                        .get("/api/path/lastPath")
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
                        .get("/api/path/lastPath")
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
                        .get("/api/path/lastPath")
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

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/path/modifyDescription")
                        .content(invalidJson)
                        .header("token", "token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddPointUnauthorized() throws Exception {

        PathEntity pathEntity = new PathEntity();
        pathEntity.setId(new ObjectId());

        when(pathService.recupDernierParcour(2)).thenReturn(pathEntity);

        JSONObject object = new JSONObject();
        object.put("id", pathEntity.getId().toString());
        object.put("longitude", 12.25);
        object.put("latitude", 48.25);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/addPoint")
                        .content(asJsonString(object))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnauthorized());

    }

    @Test
    public void testAddPoint() throws Exception {

        when(pathService.recupDernierParcour(2)).thenReturn(pathEntity);
        when(pathService.recupParcoursParId(pathEntity.getId())).thenReturn(pathEntity);

        JSONObject object = new JSONObject();
        object.put("id", pathEntity.getId());
        object.put("longitude", 12.25);
        object.put("latitude", 48.25);

        System.out.println(object);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/addPoint")
                        .content(String.valueOf(object))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    public void testResearchPathsOk() throws Exception {
        String email = "utilisateur@test.com";
        String mdp = "test";
        String prenom = "utilisateur";
        String nom = "test";

        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setEmail(email);
        entity.setMotDePasse(mdp);
        entity.setPrenom(prenom);
        entity.setNom(nom);
        when(userService.getTokenBd(2)).thenReturn("token");
        when(userService.isTokenNotExpired(anyString())).thenReturn(true);
        when(userService.findUserByToken(anyString())).thenReturn(entity);

        String dateMin = "01/01/2023";
        String dateMax = "01/01/2025";

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/path/findPath?"
                        + "dateInf=" + dateMin
                        + "&dateSup=" + dateMax
                        + "&nom="
                        + "&distanceMin=" + 0
                        + "&distanceMax=" + 0)
                        .header("token","")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(pathService).findParcourByDateAndName("",dateMin,dateMax,1);


    }

    @Test
    public void testResearchPathsOkWithParcoursLength() throws Exception {
        String email = "utilisateur@test.com";
        String mdp = "test";
        String prenom = "utilisateur";
        String nom = "test";
        int distanceMin = 0;
        int distanceMax = 1;
        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setEmail(email);
        entity.setMotDePasse(mdp);
        entity.setPrenom(prenom);
        entity.setNom(nom);
        when(userService.getTokenBd(2)).thenReturn("token");
        when(userService.isTokenNotExpired(anyString())).thenReturn(true);
        when(userService.findUserByToken(anyString())).thenReturn(entity);

        String dateMin = "01/01/2023";
        String dateMax = "01/01/2025";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/path/findPath?"
                                + "dateInf=" + dateMin
                                + "&dateSup=" + dateMax
                                + "&nom=" + nom
                                + "&distanceMin=" + distanceMin
                                + "&distanceMax=" + distanceMax)
                        .header("token","")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(pathService).findParcourByDateAndNameAndDistance(
                nom, dateMin, dateMax , distanceMin, distanceMax,1);


    }


    @Test
    public void testResearchPathsNotOk() throws Exception {
        String email = "utilisateur@test.com";
        String mdp = "test";
        String prenom = "utilisateur";
        String nom = "test";
        int distanceMin = 0;
        int distanceMax = 1;
        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setEmail(email);
        entity.setMotDePasse(mdp);
        entity.setPrenom(prenom);
        entity.setNom(nom);
        when(userService.getTokenBd(2)).thenReturn("token");
        when(userService.isTokenNotExpired(anyString())).thenReturn(false);
        when(userService.findUserByToken(anyString())).thenReturn(entity);

        String dateMin = "01/01/2023";
        String dateMax = "01/01/2025";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/path/findPath?"
                                + "dateInf=" + dateMin
                                + "&dateSup=" + dateMax
                                + "&nom="
                                + "&distanceMin=" + distanceMin
                                + "&distanceMax=" + distanceMax)
                        .header("token","")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());



    }

    @Test
    public void testArchivingPathSuccess() throws Exception {

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathService.recupParcoursParId(pathEntity.getId())).thenReturn(pathEntity);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/archivingPath")
                        .content(pathEntity.getId().toString())
                        .header("token", "token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertTrue(pathEntity.getArchive());
    }

    @Test
    public void testArchivingPathUnauthorized() throws Exception {

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathService.recupParcoursParId(pathEntity.getId())).thenReturn(pathEntity);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/archivingPath")
                        .content(pathEntity.getId().toString())
                        .header("token", "mauvaisToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testArchivingPathInternalServerError() throws Exception {

        when(userService.findUserByToken("token")).thenReturn(userEntity);
        when(pathService.recupParcoursParId(pathEntity.getId())).thenReturn(pathEntity);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/archivingPath")
                        .content("a2a1a1a1a1a1a1a1a1a1a1a1")
                        .header("token", "token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

}
