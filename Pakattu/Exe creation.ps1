
# Exe name
$_name = "CVLuonti"
# Folder containing Jar file
$_input = "$HOME\downloads\cvexe"
# Jar file name with extension required
$_mainJAR = "CVLuonti.jar"
# Defines file extension on windows exe/msi, Linux deb etc 
$_type = "exe"
# Where exe file is created
$_dest = "$HOME\Downloads"
# Local path to icon
$_icon = "$HOME\Downloads\cvexe\favicon.ico"

$_uuid = "df3253c1-3ce8-4a0d-8d44-4b2e2491edbf"


<#
Uses JPackage to create the exe file
--app-version with --win-upgrade-uuid should allow to override existing version using Universally Unique Identifier
--win-per-user-install => install the exe only for x user
--menu => after installing the exe it allows windows search to find CVLuonti
--Verbose prints what this code is doing into Powershell

6.10/26
- Creation of PDF btn function should work
- Previewing the pdf on the other hand won't work.
Thinking it something to do with preview scripts paths as version 118.0 of
CValkusivu creating the pdf it self returned same output as preview currently



Currently after executing the exe it creates CVLuonti folder under $HOME
and creates shortcut to the user desktop,
used clean Oracle VMBox windows 11 install to do the testings



7.10/26 
Line 479 From CVLuopdf 
String[] strArray = str.split("" + a);
Updated line
String[] strArray = str.split("" + a, -1);

Tested on Oracle VMBOX with "Windows11_Client_x64_fi-fi_26300_9457.iso" and everything was working correctly on fast debugging trinyg to get preview working

#>

# Path to wanted java version containing jpackage.exe
& "C:\Program Files\Java\latest\jdk-21\bin\jpackage.exe" `
    --name $_name `
    --input "$_input" `
    --main-jar $_mainJAR `
    --app-version "1.0.1" `
    --win-upgrade-uuid $_uuid `
    --type $_type `
    -d "$_dest" `
    --icon "$_icon" `
    --win-per-user-install `
    --win-shortcut `
    --win-menu `
    --verbose
    