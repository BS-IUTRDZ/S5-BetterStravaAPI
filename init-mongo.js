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
        ]
    }
)