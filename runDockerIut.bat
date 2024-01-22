@ECHO OFF
D:
rmdir /s /q S5-BetterStrava
mkdir S5-BetterStrava
cd S5-BetterStrava
copy Z:\sae\S5-BetterStravaAPI\.env .env
copy Z:\sae\S5-BetterStravaAPI\docker-compose.yml docker-compose.yml
copy Z:\sae\S5-BetterStravaAPI\initialisation-DB.sql initialisation-DB.sql
copy Z:\sae\S5-BetterStravaAPI\init-mongo.js init-mongo.js
docker-compose up -d
Z:
cd Z:\sae\S5-BetterStravaAPI