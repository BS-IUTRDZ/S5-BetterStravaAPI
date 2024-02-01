@ECHO OFF
D:
cd S5-BetterStrava
docker-compose down --volumes
cd ..
rmdir /s /q S5-BetterStrava
Z:
cd Z:\sae\S5-BetterStravaAPI