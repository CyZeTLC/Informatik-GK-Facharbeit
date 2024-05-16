# RoadSystem

Das Projekt stellt die Simulation eines Straßensystems dar. Dafür wird ein Graph als Datenstruktur genutzt.
Primäres Ziel des Projektes ist anhand der Simulation die Entwicklung von Staus zu simulieren.


## Authors

- [@CyZeTLC](https://www.github.com/CyZeTLC) - tom@cyzetlc.de


## Installation

Um das Projekt zu benutzen, muss der SourceCode heruntergeladen werden und in eine IDE eingefügt werden. Beim erstmaligen Starten des Projektes erstellt sich automatisch eine `config.json` Datei, in welcher alle Konfigurationen getroffen werden können.

### Config

```json
{
  "entitiesAmount": 2,
  "tickSpeed": 1000,
  "carsPercentage": 0.5,
  "mysql": {
    "hostname": "ipv4",
    "port": 3306,
    "database": "database_name",
    "username": "username",
    "password": "password",
    "poolSize": 3
  }
}
```

##### Bedeutung der Konfigurationen:
- `entitiesAmount` -> Anzahl der Fahrzeuge
- `tickSpeed` -> Zeit, die pro Tick gebraucht wird (in Millisekunden)
- `carsPercentage` -> Prozentuale Anzahl an Autos. 0.5 würde bei zwei Fahrzeuge ein Auto und ein Motorrad bedeuten
