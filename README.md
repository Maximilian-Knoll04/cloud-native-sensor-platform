# cloud-native-sensor-platform

## Inhaltsverzeichnis
  - [Beschreibung](#beschreibung)
  - [User Stories](#user-stories)
  - [Architektur](#architektur)
  - [Datenbankschema](#datenbankschema)
  - [API-Schnittstellen](#api-schnittstellen)
  - [Funktionsweise](#funktionsweise)
  - [AWS Anwendungen](#aws-anwendungen)
      - [Fargate](#fargate)
      - [Cognito](#cognito)
      - [RDS](#rds)

## Beschreibung
Es geht um eine Web-Platform zur Verwaltung von Sensordaten von Kunden.

Jeder kann sich als Kunde registrieren und bekommt einen Sensor generiert, mit dem der Kunde Temperaturen auffassen kann.

Jeder Sensor kann Temperaturen in Grad Celsius (°C) an eine Logging-Schnittstelle senden.

Die Daten werden zeitlich erfasst und können über eine Statistik-Schnittstelle eingesehen werden.

Der Einfachheit halber wird in diesem Projekt die Menge an Sensoren pro Kunde auf einen Temperatur-Sensor in Celsius begrenzt. Ziel ist nämlich nur das Verstehen und Arbeiten mit AWS Diensten, sowie das Auslagern von Kritischen Anwendungen um effizienter und sicherer zu entwickeln.

Aufgrund der Komplexität der Verwaltung für Sensorverifikation, kann jedoch nur der Kunde über die geeignete Schnittstelle Sensordaten senden.



## User Stories
  - Als Kunde möchte ich mich anmelden
  - Als Kunde möchte ich mich registrieren
  - Als Kunde möchte ich Sensordaten erfassen können
  - Als Kunde möchte ich Sensordaten als Statistiken betrachten
    

## Architektur
<img width="1270" height="624" alt="image" src="https://github.com/user-attachments/assets/3d3e5200-47bc-4af1-943a-7d8a15f7005f" />


## Datenbankschema

Um die Messwerte zu speichern werden folgende Tabellen verwendet:

Tabelle sensors

| sensorId (PK) | userId (PK) |
| --------- | ------------ |
|   ...    |  ...   |


Tabelle sensor-data

| sensorId (PK) | timestamp (PK) | temperature |
|----------|-----------|------------|
|   ...    |     ...   |      ...   |

Es resultiert eine 1:n Beziehung zwischen sensors <-> sensor-data


## API-Schnittstellen

| Bezeichnung | Ressource | Payload |
|-------------|-----------| -------- |
| Lesen von Daten | GET /sensor-data | - | 
| Schreiben von Daten | POST /sensor-data | temperature-value |


## AWS Anwendungen

### Fargate



### Cognito

Zur Benutzerverwaltung und der generellen Auth Methoden wird AWS Cognito verwendet. Diese Anwendung ermöglicht es, Anmeldungen, Registrierungen sowie Richtlinien unter den Komponenten des Gesamtsystems durchzuführen und zu verwalten. Damit wird ein erheblich großer Aufwand annuliert. 

### RDS

