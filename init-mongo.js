db = db.getSiblingDB('betterstrava');

db.createCollection('parcours');

db.parcours.insertOne(
    {
        nom : "balade a salles-la-source",
        idUtilisateur : 1,
        description : "superbe village avec une très jolie cascade meme si la balade est un peu courte",
        points : [
            {latitude : 44.435465, longitude : 2.514783},
            {latitude : 44.436536, longitude : 2.514771},
            {latitude : 44.437898, longitude : 2.513335},
        ],
        pointsInterets : [
            {
                nom : "cascade",
                description : "jolie eau",
                coordonnees : {latitude : 44.437005, longitude : 2.514477}
            }
        ],
        archive : false,
        statistiques : {
            duree : 1524,
            vitesseMoyenne: 15.5,
            distance: 14.6,
            denivPos: 250,
            denivNeg: 33
        },
        date : Date.now(),
    }

);

db.parcours.insertOne(
    {
        nom : "vieux",
        idUtilisateur : 1,
        description : "trop vieux",
        points : [
            {latitude : 44.435465, longitude : 2.514783},
            {latitude : 44.436536, longitude : 2.514771},
            {latitude : 44.437898, longitude : 2.513335},
        ],
        pointsInterets : [
            {
                nom : "cascade",
                description : "jolie eau",
                coordonnees : {latitude : 44.437005, longitude : 2.514477}
            }
        ],
        archive : false,
        statistiques : {
            duree : 1524,
            vitesseMoyenne: 15.5,
            distance: 14.6,
            denivPos: 250,
            denivNeg: 33
        },
        date : Date.UTC(2023,1,10,10,10,10,10),
    }
);