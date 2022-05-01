# HowLongToEat-TP-Architecture-distribuee

## Présentation :
How Long To Eat est un projet étudiant réalisé pour notre cours d'architecture distribuée.
L'objectif étant de travailler sur une application SpringBoot avec plusieurs micro-services.

HLTE est un site internet Tripadvisor Like. La principale différence est que notre site se base plus sur le temps d'attente avant de pouvoir être servi. Tous les utilisateurs (enregistrés comme visiteurs) peuvent consulter les notes d'un restaurant et voir le temps d'attente moyen pour chaque jour aux différents horraires d'ouverture du restaurant. Seuls les utilisateurs enregistrés peuvent laisser une note sur un restaurant avec leur temps avant d'être servie.


## Micro-services
Notre application comporte 6 micro-services : 
  - ServiceRegistry
  - ConfigServer
  - Gateway
  - note 
  - restaurant
  - user

## Lancement de l'application

**/!\ L'application utilise une base de donnée Azure SQL avec un pare-feu. Ainsi, si votre adresse IP n'est pas incluse dans ce pare feu, vous ne pourrez pas démarrer les micro-services note/restaurant/user /!\\**

Etant une application java, il vous faut Java d'installé.

Voici la ligne de commande à rentrer sous windows : 
<pre>java -jar "[PATH_TO_HLTE_FOLDER]/[MICROSERVICE_NAME]/build/libs/[MICROSERVICE_NAME]</pre>

Les micro-services ServiceRegistry/ConfigServer sont d'abords à lancer en premier, ensuite l'ordre importe peu. 

Pour le front, le framework "React" est utilisé. 
Pour lancer l'application front, voici la ligne de commande à lancer dans le dossier Front.

<pre> npm run start </pre>

Si React n'est pas installé de base, lancé d'abord cette ligne 

<pre> npm i react </pre>

## Diaporama 

Voici le lien du diaporama utilisé lors de la présentation : 

https://docs.google.com/presentation/d/1jj8r73XwEuO7kOIZH9svB-kzdd9qa89yw5dFoRW8HSw/edit?usp=sharing
