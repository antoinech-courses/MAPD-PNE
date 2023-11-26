# Petri-Network
Par Nathan Claeys et Antoine Cheucle

## Description
Ce projet implémente notre réseau de Pétri avec l'interface PNE. Il implémente les éléments atomiques constitutifs de cette structure : des transitions, des places (avec un certain nombre de jetons), et 4 types d'arcs différents, faisant le lien entre les transitions et les places. Il existe deux arcs basiques : entrant et sortant (d'une place), et deux arcs sortants particuliers (arc zéro et arc videur).

## Environnement
Version d'Eclipse : 2022-06
Java Runtime Environment 11

## Exécution
Exécuter le fichier Main.java du package org.pneditor.editor pour lancer l'interface. Sélectionner le moèdle `cheucleclaeys`.

## Tests
Il est possible de réaliser tous les tests unitaires directement depuis l'interface graphique.

## Code
Le code de l'adaptation du modèle avec des patterns Adapteurs se situe dans le package `org.pneditor.petrinet.adapters.cheucleclaeys`