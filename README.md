# cloud-native-sensor-platform

## Inhaltsverzeichnis
- [Beschreibung](#beschreibung)
- [User Stories](#user-stories)
- [Architektur](#architektur)
- [Datenbankschema](#datenbankschema)
- [API-Schnittstellen](#api-schnittstellen)
- [Testen](#testen)
- [AWS Anwendungen](#aws-anwendungen)
    - [Fargate](#fargate)
    - [Cognito](#cognito)
    - [RDS](#rds)
- [Vernwendung der Plattform](#verwendung-der-plattform)

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

Um die Messwerte zu speichern wird folgende Tabelle verwendet:

Tabelle sensor-data

| Id (PK) | timestamp | userId | temperature |
|----------|-----------|------------|------------|
|   ...    |     ...   |      ...   |      ...   |

Zudem sind timestamp und userId einzigartig

## API-Schnittstellen

| Bezeichnung | Ressource | Payload |
|-------------|-----------| -------- |
| Lesen von Daten | GET /sensor-data | - | 
| Schreiben von Daten | POST /sensor-data | temperature-value |

## Testen
Um die Anwendung lokal zu testen, müssen die Datenbank und AWS Cognito lokal emuliert werden.

- Zur Emulation der Datenbank eignet sich eine einfache lokale Datenbankinstanz von PostgreSQL in Docker mit der [sql](https://github.com/Maximilian-Knoll04/cloud-native-sensor-platform/blob/main/sensordata.sql) Datei.
- Zur Emulation von AWS Cognito als Identity Provider würde sich ein OIDC-Provider wie Keycloak eignen. Aufgrund von weiterem nötigen Wissen und mangelnder Zeit, kann hier nur eine Kurzbeschreibung stattfinden.

Dazu kommt, dass die Umgebungsvariablen alle lokal in der Entwicklungsumgebung festgelegt werden.

Zum Testen von der Datenbank wird folgendes gemacht:
1. `docker run --name <dbname> -p 5432:5432 -e POSTGRES_PASSWORD=local postgres:17-alpine`
2. Mit der Datenbank verbunden über z.B. HeidiSQL oder in Intellj IDEA mit dem integrierten Datenbanktool und `CREATE DATABASE sensorplattform;` ausgeführt
3. Der Inhalt der .sql-Datei (wie oben) in der Datenbank ausgeführt.

Um Cognito mit Keycloak zu testen und somit eine vollständige DevProdParity im Projekt zu ermöglichen, müsste man:
1. Eine Dockerinstanz von Keycloak lokal ausführen
2. Ein geeignetes Dev-Profile in Spring hinterlegen, dass die Prod-Configuration in der `application.properties` mit geeigneten Werten aus der Umgebung überschreibt.

## AWS Anwendungen

### Fargate

Um die Applikation als Container in einer serverless Umgebung aufzusetzen eignet sich hier AWS Fargate als Anwendung. Durch die Einfache Integration in Github Actions kann die Continuous Delivery gestreamlined werden, sodass man sich auf die Entwicklung der Features kümmern kann.


### Cognito

Zur Benutzerverwaltung und der generellen Verifikationsmethoden über das OIDC Protokoll wird AWS Cognito verwendet. Diese Anwendung ermöglicht es, Anmeldungen, Registrierungen sowie Berechtigungsprüfungen zu vollziehen. Damit wird ein erheblich großer Aufwand annuliert.


### RDS

Um Applikationsdaten zu persistieren und eine hohe Verlässlichkeit zu garantieren, wird AWS RDS verwendet.

Die Anwendung ermöglicht die schnelle und einfache Erstellung von Datenbanken mit einer selbstwählbaren DBMS-Engine. Für dieses Projekt wird PostgreSQL genutzt.

## Verwendung der Plattform

> [!NOTE]
> Um unerwünschte Aktivitäten zu vermeiden, ist keine Registrierung möglich.
> Daher gibt es nur vorgefertigte Accounts.

Über diesen [Link](https://cl-a2b16ea083ef413095bc58593cfe00a0.ecs.eu-central-1.on.aws/) kommt man auf die Startseite der Applikation.

<img width="646" height="238" alt="image" src="https://github.com/user-attachments/assets/2745a405-bb3e-4b41-ae8d-8ce4053ea107" />

Nach dem Betätigen von `Log in` wird man auf die Login Page des eigenen User Pools geleitet um sich anzumelden.

<img width="1276" height="704" alt="image" src="https://github.com/user-attachments/assets/cc8beb9f-656f-4432-9fed-4dcefdcc1f26" />


Nach erfolgreicher Anmeldung landet man auf der Hautpseite.

<img width="511" height="434" alt="image" src="https://github.com/user-attachments/assets/2dfc54b1-f4bd-4e33-82ff-730455f33705" />


Dort kann der Kunde seine Messungen einsehen und neue anlegen.

<img width="345" height="351" alt="image" src="https://github.com/user-attachments/assets/61f8a588-75e9-40a4-8fba-706204fe3968" />
<img width="342" height="390" alt="image" src="https://github.com/user-attachments/assets/0dd65f25-6cf4-41ef-967d-b04b6ac6dcea" />

Mit anschließendem Klick auf `Log out` kommt man wieder auf die Startseite.


