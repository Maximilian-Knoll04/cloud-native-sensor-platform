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

Pro Kunde gibt es genau einen Eintrag. Die ID des Kunden wird hier gespeichert, um 

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
| Anlegen von einem neuen Sensor | POST /sensors | - |
| Schreiben von Daten | POST /sensor-data | temperature-value |

## Funktionsweise
<img width="1336" height="513" alt="image" src="https://github.com/user-attachments/assets/4998d343-9da7-4ba2-a3c6-58a4b0fa8546" />

1.) Der Kunde ruft zunächst die Sensorplattform Webseite auf

2.) Da dieser nicht angemeldet ist, wird dieser zur Anmeldung weitergeleitet

3.) Nach erfolgreicher Anmeldung wird der Kunde zur Plattform zurückgeleitet...

4.) ...und erhält seine kundenbezogene Ansicht, wo die Daten des Sensors angezeigt werden

5.) Der Kunde kann per Knopfdruck einen neuen Sensor anlegen...

6.) ...und bekommt Zertifikate zur Verbindung mit der IoT Schnittstelle zurückgeliefert 

7.) Das IoT Gerät kann dann mit den Zertifikaten seine Daten an die Plattform schicken

## AWS Anwendungen

### Fargate



### Cognito

Zur Benutzerverwaltung und der generellen Auth Methoden wird AWS Cognito verwendet. Diese Anwendung ermöglicht es, Anmeldungen, Registrierungen sowie Richtlinien unter den Komponenten des Gesamtsystems durchzuführen und zu verwalten. Damit wird ein erheblich großer Aufwand annuliert. 

### RDS

