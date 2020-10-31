#GET
```sh
git clone --recursive  -j4 https://github.com/SKANFA/kvac-pwn github.com/SKANFA/kvac-pwn
```



## server side dependency.
```sh
git clone https://github.com/aadnk/ProtocolLib github.com/aadnk/ProtocolLib 
cd github.com/aadnk/ProtocolLib
mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
```
after compilation, place the 'target/ProtocolLib.jar' file in the 'plugins' directory on the minecraft server


#BUILD
compile the dependency for the shell:
```sh
mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
```
