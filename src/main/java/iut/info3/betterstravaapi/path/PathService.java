package iut.info3.betterstravaapi.path;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import iut.info3.betterstravaapi.EnvGetter;
import iut.info3.betterstravaapi.user.UserRepository;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PathService {

    @Autowired
    private PathRepository pathRepository;

    public List<PathEntity> recupPerformances30Jours (int idUser) {
        Calendar calendrier = Calendar.getInstance();
        calendrier.add(Calendar.HOUR,-24*30);
        return  pathRepository.findPathByIdUtilisateurAndArchiveAndDateAfter(idUser,false,calendrier.getTime().getTime() );
    }

    public List<PathEntity> recupPerformancesGlobal (int idUser) {
        return  pathRepository.findPathByIdUtilisateurAndArchive(idUser,false);
    }

    public PathEntity recupDernierParcour (int idUser) {
        return pathRepository.findTopByIdUtilisateurAndArchiveOrderByDateDesc(idUser, false);
    }

}
