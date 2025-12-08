# cloud-native-sensor-platform

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



