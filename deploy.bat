SET activeProfile=https
SET keystoreAbsoluteFilePath=C:\g\api\keys\tp4keystore
SET targetJar=target/api-1.0-SNAPSHOT.jar

call mvn clean install
echo -------------------------------------------------------------------------------------------------------------------
echo /                                                                                                                \
echo /                                                                                                                \
echo /                                                                                                                \
echo /                             Le serveur est disponible sur https://localhost:8443                               \
echo /                                                                                                                \
echo /                                                                                                                \
echo /                                                                                                                \
echo -------------------------------------------------------------------------------------------------------------------
call  java -Dspring.profiles.active=%activeProfile% -Dkeystore.file=file:///%keystoreAbsoluteFilePath% -jar %targetJar%
