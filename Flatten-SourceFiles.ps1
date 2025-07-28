$pathSep = [System.IO.Path]::DirectorySeparatorChar

$sourceBasePath = (".", "src", "main", "java", "cielsachen", "ccprog3" -join $pathSep)
$sourceDirNames = @("mco1", "mco2")
$sourceFileExt = "*.java"

$outBaseDirName = "dist"
$outBaseDirPath = ".$($pathSep)$($outBaseDirName)"

if (-not(Test-Path -Path $outBaseDirPath)) {
  New-Item -Path "." -Name $outBaseDirName -ItemType "Directory" -Force | Out-Null

  Write-Output -InputObject "Created: $($outBaseDirPath)"

  Write-Output -InputObject ""
}

$sourceDirNames | ForEach-Object -Process {
  $currDirName = $_

  if ($sourceDirNames.IndexOf($_) -ge 1) {
    Write-Output -InputObject ""
  }

  $outDirPath = "$($outBaseDirPath)$($pathSep)$($currDirName)"

  if (-not(Test-Path -Path $outDirPath)) {
    New-Item -Path $outBaseDirPath -Name $currDirName -ItemType "Directory" -Force | Out-Null

    Write-Output -InputObject "Created: $($outDirPath)"

    Write-Output -InputObject ""
  }

  $sourceDirPath = "$($sourceBasePath)$($pathSep)$($currDirName)"

  Write-Output -InputObject "Copying from $($sourceDirPath) to $($outDirPath)"

  Get-ChildItem -Path $sourceDirPath -Include $sourceFileExt -File -Recurse |
  ForEach-Object -Process {
    Copy-Item -Path $_.FullName -Destination $outDirPath

    Write-Output -InputObject "  Copied: $($_.Name)"
  }

  Write-Output -InputObject ""

  Write-Output -InputObject "Fixing source file import statements"

  Get-ChildItem -Path $outDirPath -Include $sourceFileExt -File -Recurse |
  ForEach-Object -Process {
    (Get-Content -Path $_.FullName) | Where-Object { $_ -notmatch "cielsachen.ccprog3.${$currDirName}" } |
    Set-Content -Path $_.FullName


    Write-Output -InputObject "  Updated: $($_.Name)"
  }
}

