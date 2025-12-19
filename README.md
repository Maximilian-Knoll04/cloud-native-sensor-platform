# cloud-native-sensor-platform

## Inhaltsverzeichnis
  - [Beschreibung](#beschreibung)
  - [User Stories](#user-stories)
  - [Architektur](#architektur)
  - [Funktionsweise](#funktionsweise)
  - [AWS Anwendungen](#aws-anwendungen)
      - [DynamoDB](#dynamodb)
      - [Lambda](#lambda)
      - [API Gateway](#api-gateway)
      - [IoT Core](#iot-core)
      - [Cognito](#cognito)
      - [Amplify](#amplify)


## 19.12.2025 Überdenkung
Um eine Mischung aus einer Spring Boot Komponente und dem Cloud Provider AWS zu machen, wird die Geschäftslogik (API Gateway und Lambda) in die Spring Boot Komponente verlagert.
Zudem wird das IoT Core Service rausgenommen, um die Arbeitslast zu mindern. IoT Geräte werden somit in der Datenbank verwaltet.
Zu guter letzt wird auch die DynamoDB durch eine relationale Datenbank ersetzt, um mit der vertrauten SQL Technologie zu arbeiten und den Einarbeitungsaufwand in NoSQL außen vor zu lassen.

## Neue überdachte Architektur
<img width="1356" height="571" alt="image" src="https://github.com/user-attachments/assets/9647e180-d946-4bd8-84f7-3fb9b700d35a" />


## Neues Datenbankschema

Tabelle sensors

| sensorId | userId (PK) |
| --------- | ------------ |
|   ...    |  ...   |


Tabelle sensor-data

| sensorId (PK) | timestamp (PK) | temperature |
|----------|-----------|------------|
|   ...    |     ...   |      ...   |

> [!NOTE]
> Bei den User Stories heißt es, dass jegliche Typen von Daten gespeichert werden können.
> Hierbei wird sich jedoch der Einfachheit halber auf Temperaturen in °C (Celsius) beschränkt.


## Erweiterung der API-Schnittstellen

Zu allen Schnittstellen muss im Header der JWT Token mitgeliefert werden, um den User und seine Taten zu verifizieren. 

Zu den vorhandenen 2 ersten Schnittstellen kommen weitere hinzu:

| Bezeichnung | Ressource | Payload |
|-------------|-----------| -------- |
| Lesen von Daten | GET /sensor-data | - | 
| Anlegen von einem neuen Sensor | POST /sensors | - |
| Schreiben von Daten | POST /sensor-data | temperature-value |

> [!NOTE]
> Wegen der Einfachheit wird anhand des JWT Tokens die userId ermittelt, anhand der die sensorId ermittelt wird. Daher ist die Mitlieferung der sensorId in der Payload überflüssig.
> Zudem schlägt die Erstellung eines neuen Sensors fehl, wenn schon ein Eintrag für eine userId in der sensors Tabelle existiert.

## Dockerisierung
Um die Spring Boot Anwendung in der Cloud als Container laufen zu lassen, wird AWS ECS (Elastic Container Service) verwendet. 


## Beschreibung
Es geht um eine Web-Platform zur Verwaltung von Sensordaten von Kunden.
Jeder kann sich als Kunde registrieren und eigene Sensoren registrieren.
Jeder Sensor kann einen messbaren Datentyp und einen dazugehörigen Wert an eine Logging-Schnittstelle senden.
Die Daten werden zeitlich erfasst und können über eine Statistik-Schnittstelle eingesehen werden.

Der Einfachheit halber wird in diesem Projekt die Menge an Sensoren pro Kunde auf einen Temperatur-Sensor in Celsius begrenzt. Ziel ist nämlich nur das Verstehen und Arbeiten mit AWS Diensten.   

## User Stories
  - Als Kunde möchte ich mich anmelden, um Statistiken zu betrachten und meinen Sensor zu verwalten
  - Als Kunde möchte ich mich registrieren
  - Als Kunde möchte ich einen Sensor anlegen
  - Als Kunde möchte ich Sensordaten erfassen können
  - Als Kunde möchte ich Sensordaten als Statistiken betrachten
## Architektur
<img width="1336" height="513" alt="image" src="https://github.com/user-attachments/assets/d5fb753e-282e-4803-82db-e73daaf73f80" />

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
### DynamoDB
Zum persistieren der Sensordaten ist eine Datenbank notwendig. Hierzu wird die NoSQL Datenbank "DynamoDB" von AWS genommen. Diese eignet sich gut für horizontale Skalierung und Verfügbarkeit. Zudem ist diese schnell eingerichtet. Eine weiterer Entscheidungsgrund gegenüber einer relationalen Datenbank wie RDS von AWS ist, dass die Konsistenz der Daten keine Rolle spielt, oder zumindest die gespeicherten Daten nicht kritisch sind sondern lediglich Logs sind.

Hier das Schema, an das sich gehalten wird:

> [!NOTE]
> customerId und timestamp bilden den Composition Key. Vergleichsweise ist es ein zusammengesetzter Primärschlüssel.

| Attribute | Key-Type | Example |
|-----| ---- | --- |
|customerId| Primary Key (Partition) | 27358
|timestamp| Secondary Key (Sort) | 2022-07-13T09:23:45
|temperature| - | 24.6



### Lambda
In diesem Projekt werden lediglich 3 Funktionen benötigt. Diese sind:
  - Das Erstellen eines Sensors
  - Der Abruf der Sensordaten
  - Das Schreiben neuer Sensordaten

Daher eignet sich eine FaaS-Lösung (Function-as-a-Service), in der man lediglich die benötigten Funktionen implementieren muss.
Diese kommunizieren dann intern über die AWS SDK mit den anderen Anwendungen wie DynamoDB oder IoT Core.


### API Gateway
Um HTTP-Schnittstellen nach außen in das weltweite Internet verfügbar zu machen, wird API Gateway verwendet. Es bietet vor allem die Möglichkeit Lambda Funktionen als Handler für bereitgestellte Schnittstellen anzuhängen und die empfangenen Daten mitzuleiten. Zudem kann auch die Nutzung der Schnittstellen auf authentifizierte Benutzer beschränt werden um anonyme oder nicht-Benutzer zu blockieren. Damit wird dafür gesorgt, dass sich die Lambda Funktionen keine Authentifizierung vornehmen müssen.

Momentan sind folgende Schnittstellen angedacht:

| Bezeichnung | Ressource |
|-------------|-----------|
| Lesen von Daten | GET /sensor-data |
| Anlegen von einem neuen Sensor | POST /sensors |

### IoT Core
Damit Temperatursensoren ihre Daten an die Plattform senden können, benötigen diese Zertifikate, da diese über andere Authentifizierungsmechanismen verifiziert werden können. Hierfür stellt die Anwendung IoT Core von AWS die Möglichkeit bereit, Geräte (sogenannte "Things") anzulegen. Diese können sowohl per Hand als auch über die AWS API erstellt werden. Damit ist die Erstellung und Rücklieferung der Zertifikate über AWS Lambda automatisierbar und für die Plattform geeignet.

> [!WARNING]
> Ein Problem bei der Ausstellung der Zertifikate ist, dass sich diese nur einmalig ausstellen lassen. Das heißt bei Verlust der Zertifikate muss ein neues "Thing" erstellt werden. In diesem Projekt wird sich aber nur auf die einmalige Erstellung eines "Things" beschränkt, was beim Ausprobieren berücksichtigt werden muss

### Cognito

Zur Benutzerverwaltung und der generellen Auth Methoden wird AWS Cognito verwendet. Diese Anwendung ermöglicht es, Anmeldungen, Registrierungen sowie Richtlinien unter den Komponenten des Gesamtsystems durchzuführen und zu verwalten. Damit wird ein erheblich großer Aufwand annuliert. 

### Amplify

Um die Webseite für die Plattform bereitzustellen wird AWS Amplify verwendet. Es gibt einem die Möglichkeit, die Webseite über eine Versionsverwaltung zu verwalten. Der größte Knackpunkt liegt jedoch in der Bereitstellung von Javascript-Bibliotheken, die sich in das Gesamtsystem von AWS integrieren lassen. Die daraus bestehende SDK (Software Development Kit) gibt dem Frontend die Möglichkeit, ganz einfach mit den AWS Komponenten zu kommunizieren. Damit wird einem die Arbeit zur Erstellung eigener geeigneter Kommunikationsmechanismen mit AWS (wie etwa Cognito für Auth oder API Gateway für REST Calls) genommen.

## Umsetzung
Folgend wird erklärt, wie die Plattform auf AWS umgesetzt wird mit einer Schritt-für-Schritt Anleitung

### Anlegen einer DynamoDB-Tabelle

### Anlegen von Lambda-Funktionen

### Anlegen von API Gateway-Schnittstellen

### Anlegen des Cognito-Benutzerpools

### Anlegen der Amplify Webseite

### Anlegen der IoT Core Weiterleitung an Lambda
