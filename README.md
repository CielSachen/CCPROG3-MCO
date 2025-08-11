# Coffee Truck Simulator

## Compilation

```bash
# For MacOS and Linux:
javac ./src/main/java/cielsachen/ccprog3/**/*.java
```

## Usage

```bash
# For MCO1:
java -cp ./src/main/java cielsachen.ccprog3.mco1.Main
# For MCO2:
java -cp ./src/main/java cielsachen.ccprog3.mco2.Main
```

## Documentation

```bash
# For MCO1:
javadoc -cp ./src/main/java cielsachen.ccprog3.mco1 -d ./docs -subpackages cielsachen.ccprog3.mco1.controller cielsachen.ccprog3.mco1.exception cielsachen.ccprog3.mco1.helper cielsachen.ccprog3.mco1.model cielsachen.ccprog3.mco1.model.coffee cielsachen.ccprog3.mco1.service cielsachen.ccprog3.mco1.util
# For MCO2:
javadoc -cp ./src/main/java cielsachen.ccprog3.mco2 -d ./docs -subpackages cielsachen.ccprog3.mco2.controller cielsachen.ccprog3.mco2.exception cielsachen.ccprog3.mco2.model cielsachen.ccprog3.mco2.model.coffee cielsachen.ccprog3.mco2.service cielsachen.ccprog3.mco2.util cielsachen.ccprog3.mco2.validator cielsachen.ccprog3.mco2.view cielsachen.ccprog3.mco2.view.component cielsachen.ccprog3.mco2.view.form
```

## Flattening for Submission

Submission requires all source files compressed in a zip file without folders.

The source files can be flattened using a PowerShell script:

```pwsh
.\Flatten-SourceFiles.ps1
```

```bash
pwsh ./Flatten-SourceFiles.ps1
```
