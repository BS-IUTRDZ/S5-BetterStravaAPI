| nom | Description | Type | Contraintes | Règle de composition |
| --- | ----------- |------|-------------|----------------------|
| Users_nom | Nom de l'utilisateur | String | nombre de caractères < 20 | null |
|Users_prenom | Prenom de l'utilisateur | String | nombre de caractères < 20 | null |
|Users_email | Email associer au compte | String | nombre de caractères < 100 | Format : %@%.%
| Users_id | id de l'utilisateur dans la base de données | Integer | > 0 | Générer automatiquement par la base de données.
| Parcours_titre | Titre désignant le parcour | String | nombre de caractères < 50 | null |
| Parcours_Description | Description du parcour | String | nombre de caractères < 1000 | null |
| Parcours_nbKilometre | Nombre de kilometre parcouru durant le parcour | Integer | arrondi à 1e10-2 |
| Parcours_Duree | Temp passer a réaliser le parcour | format : XXHXX et XXs | Enregistrement de l'heure au début et a la fin du parcour |
| Synthèse_DenivelerPos | Nombre de mètre monté durant un parcour | Integer | null | null |
|Synthèse_DenivelerNeg | Nombre de mètre descendu durant un parcour | Integer | null | null |
| Synthèse_Vitesse | Vitesse moyenne durant un parcour | Integer | null | Format : XX m/s ou XX m/m ou XX k/h |
| Point_Lattitude | Lattitude d'un jsonPoint durant un parcour | Integer | null | null |
| Point_Longitude | Longitude d'un jsonPoint durant un parcour | Integer | null | null |
| Point_Altitude | Altitude d'un jsonPoint durant un parcour | Integer |
| null | null |
| PointInteret_Lattitude | Lattitude d'un jsonPoint d'interet durant un parcour | Integer | null | null |
| PointInteret_Longitude | Longitude d'un jsonPoint d'interet durant un parcour | Integer | null | null |
| PointInteret_Altitude | Altitude d'un jsonPoint d'interet durant un parcour | Integer |
| null | null |
| PointInteret_Description | Description associer a un jsonPoint d'interet | String | nb caractère < 1000 | null |
| PointInteret_Titre | Titre associer a un jsonPoint d'interet | String | nb caractère < 20 | null |
| PointInteret_Photos | Photos facultative prise par l'utilisateur l'ors de l'enregistrement d'un jsonPoint d'intérêt | String |null |
