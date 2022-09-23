# Mini-projet Programmation Objet 2022-2023

BLABLAVLA

Il va s'agir de développer un "magasin" en plusieurs étapes.
1. implémenter un magasin "générique" de manière guidée
2. implémenter un ensemble de produits à vendre
3. développer une mini-interface utilisateur utilisant vos produits et le magasin "générique"

## Consignes de départ

1. Vous travaillerez en binôme.

2. Vous indiquerez la composition de votre binome sur le document partagé https://docs.google.com/spreadsheets/d/1yqhINtPWueN8wlFXbnVdup5VuXMXf1f2z29AE96d4nU/edit?usp=sharing

3. Vous demanderez l'accès au sous-groupe gitlab https://gitlab.univ-nantes.fr/iut.lpmiar.po/iut.lpmiar.po.2022 ; cliquez sur "Request Access" 

4. Une fois votre demande acceptée, créez dans le sous-groupe un dépôt nommé comme indiqué dans le document partagé

5. Placez le code de votre projet dans le dépôt git créé et utilisez le pour votre développement en binôme

6. A la fin du projet, je récupérerai votre code via un clone de votre dépôt git uniquement ; **la date du clone final vous sera précisée utlérieurement**

7. Vous placerez un `README.md` à la racine de votre dépôt dans le lequel vous rappelerez vos noms, prénoms et groupe, ainsi que toutes les 
informations nécessaires à la compilation de votre projet (version minimale du SDK, librairies nécessaires, consignes d'utilisation, etc.)


## Le magasin "générique"

Dans `src/` il ya un paquetage `magasin` qui contient une classe `Magasin` à compléter ainsi que 
plusieurs interfaces décrivant les grandes fonctionnalités attendues :
1. gestion des clients dans `iClientele`
2. gestion de stock dans `iStock`
3. gestion des paniers-clients dans `IPanier`

Dans `test/` plusieurs classes de test vous permettront de valider votre implémentation.


## Des produits à vendre

Dans le paquetage `mesproduits` du dossier `src/` Vous définirez un ensemble de produits à vendre
selon votre inspiration. 

_Exemple :_ cela peut-être des sous-marins, des cartes pokemons ou des fruits et légumes.

l'objectif pédagogique est que vous montriez  votre compréhension des concepts tels que l'héritage, la redéfinition, etc. ainsi que des 
fonctionnalités java avancées.

_Exemple :_ on pourrait alimenter la base produits à partir de requetes vers une API, la lecture d'un .CSV, etc.

Vous définirez des cas de tests pour valider vos produits dans `test/mesproduits`.


## Une mini-application

Vous développerez dans `src/monapplication` une mini-application en mode console 
permettant de "jouer" avec votre magasin de produits à vendre. 
Il devra y avoir des données déjà définies, des commandes, etc. afin de faciliter le test de votre application
