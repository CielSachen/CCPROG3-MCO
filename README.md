# JavaJeeps

## Compilation

```bash
# Compiles to ./target/classes/cielsachen/ccprog3/
mvn compile
```

## Usage

```bash
# For MCO1:
java -cp ./target/classes/ cielsachen.ccprog3.mco1.Main
# For MCO2:
java -cp ./target/classes/ cielsachen.ccprog3.mco2.Main
```

## Documentation

```bash
# Generates to ./target/reports/apidocs/
mvn javadoc:javadoc
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
