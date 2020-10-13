# Dependencies

## server side dep.
```sh
git clone https://github.com/aadnk/ProtocolLib github.com.aadnk.ProtocolLib 
cd github.com.aadnk.ProtocolLib
mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
```
after compilation, place the target/ProtocolLib.jar file in the plugins directory on the minecraft server

## AND
compile the dependency for the shell:
```sh
git clone https://github.com/betterprice-code/lib.orchid github.com.betterprice-code.lib.orchid
cd github.com.KVAC.lib.orchid
mvn clean install
```
