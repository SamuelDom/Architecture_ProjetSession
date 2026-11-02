# Architecture_ProjetSession
Projet de session Architecture de logiciel 

## Démarrer l'API

Avec un JDK récent installé, depuis la racine du projet :

```powershell
javac --add-modules jdk.httpserver -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
java --add-modules jdk.httpserver -cp out hero.infrastructure.HeroHttpServer
```

Le serveur écoute sur le port `8080` par défaut. Un autre port peut être passé en argument :

```powershell
java --add-modules jdk.httpserver -cp out hero.infrastructure.HeroHttpServer 8081
```

## Routes

`POST /heroes` crée un héros. Les espèces acceptées sont `HUMAN`, `ELF`, `DWARF` et `ORC`; les classes sont `WARRIOR`, `RANGER`, `MAGE` et `CLERIC`.

```json
{
	"name": "Thorin",
	"species": "DWARF",
	"heroClass": "WARRIOR",
	"strength": 14,
	"dexterity": 12,
	"constitution": 16,
	"intelligence": 8,
	"wisdom": 10,
	"charisma": 13
}
```

La création renvoie `201` avec le héros créé et son identifiant dans l’en-tête `Location`. Un nom déjà utilisé renvoie `409`.

`GET /heroes/{id}` renvoie le héros correspondant (`200`) ou `404` s’il n’existe pas. Les données sont conservées en mémoire et disparaissent à l’arrêt du serveur.
