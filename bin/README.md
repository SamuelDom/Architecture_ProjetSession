# Jeu de rôle en Java

Application de jeu de rôle en Java, exécutable dans un terminal. Le projet rassemble la création de héros, les classes et espèces, l'inventaire et l'équipement, les créatures, les attaques, le combat et la progression par expérience.

## Prérequis

- JDK 17 ou plus récent
- Maven 3.8 ou plus récent

## Compiler et tester

```powershell
mvn test
```

## Lancer la démonstration

```powershell
mvn package
java -cp target/classes rpg.Main
```

La démonstration prépare une équipe de héros et leur équipement puis lance un combat interactif contre un gobelin. Pendant le combat, choisissez une cible à l'invite du terminal.
