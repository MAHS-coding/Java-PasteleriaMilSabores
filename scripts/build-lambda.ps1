<#
Build the lambda module (Windows PowerShell helper).

This script uses the existing Maven wrapper located in the `core-microservice` module
to run a multi-module build that includes the `lambda/process-sqs` module.

Usage (PowerShell):
  .\scripts\build-lambda.ps1

You can optionally set `-UploadToS3` to upload the generated jar to an S3 bucket
when AWS CLI credentials are configured in the environment.
#>

param(
    [switch]$UploadToS3,
    [string]$S3Bucket
)

Write-Host "Building lambda module (lambda/process-sqs) using core-microservice mvnw..."

$wrapper = Join-Path -Path $PSScriptRoot -ChildPath "..\core-microservice\mvnw.cmd"
$wrapper = (Resolve-Path $wrapper).ProviderPath

if (-not (Test-Path $wrapper)) {
    Write-Error "Maven wrapper not found at $wrapper. Ensure you have the project checkout and wrappers under each module."
    exit 1
}

Push-Location (Join-Path $PSScriptRoot "..")
try {
    # Build the lambda module directly by pointing -f to the module's pom.xml
    $lambdaPom = Join-Path $PSScriptRoot "..\lambda\process-sqs\pom.xml"
    $lambdaPom = (Resolve-Path $lambdaPom).ProviderPath
    & $wrapper -f $lambdaPom clean package
    if ($LASTEXITCODE -ne 0) { throw "mvn build failed (exit $LASTEXITCODE)" }

    $jar = Join-Path $PSScriptRoot "..\lambda\process-sqs\target\process-sqs-0.0.1.jar"
    if (-not (Test-Path $jar)) {
        Write-Error "Expected jar not found: $jar"
        exit 1
    }

    Write-Host "Lambda jar built: $jar"

    if ($UploadToS3) {
        if (-not $S3Bucket) {
            Write-Error "UploadToS3 requested but S3Bucket parameter is empty"
            exit 1
        }
        Write-Host "Uploading to s3://$S3Bucket/$(Split-Path $jar -Leaf) ..."
        aws s3 cp $jar "s3://$S3Bucket/$(Split-Path $jar -Leaf)"
        if ($LASTEXITCODE -ne 0) { throw "aws s3 cp failed (exit $LASTEXITCODE)" }
        Write-Host "Uploaded to s3://$S3Bucket/$(Split-Path $jar -Leaf)"
    }
}
finally {
    Pop-Location
}
