### 16/11/2022

*BACKEND*

*Le logging fonctionne, on peut lire dans l'IDE mes messages de log personnalisés. Maintenant il ne me reste plus qu'à voir comment
l'exporter vers un fichier texte.*

*Toutes les fonctionnalités ont été retestées et tout fonctionne. J'aimerais cependant modifier la manière dont les réponses
choisies par l'utilisateur sont analysées pour déterminer si elles sont correctes ou pas. Il faudrait pouvoir récupérer les questions
de chaque qcm puis les réponses de chaque question, comparer les ID de la réponse renvoyée et des réponses de la question,
et répéter l'opération pour chaque question. Il faudra effectuer des modifications de logique dans certaines parties du code.*

*FRONTEND*

- Création des services pour chaque entité.
- Création des entités dans le package models

*J'ai du mal à me remettre dans le peu d'angular que j'ai vu, je sais ce que je dois faire à peu près mais je ne sais pas comment.*

---
### 15/11/2022

*BACKEND*

__Modifications apportées au code :__
- Ajout des dépendances pour ajouter Slf4j au projet pour le logging
- Implémentation du logging dans les classes `ServiceImpl`. *__A TESTER__*
- Concernant le logging, il y'a une erreur de Bean. Je suis actuellement en train de la troubleshoot.
- Petite modification de code dans `QuestionServiceImpl`.

*J'ai un souci de bean pour le Logging. Je vais installer la dernière version d'IDEA comme il refuse de se mettre
à jour sur mon laptop. J'ai aussi bidouillé un peu le front mais pas suffisant. Je me perds un peu dans angular et je
pense que ça va me prendre un peu de temps.*

---
### 14/11/2022

*FRONTEND*

*Création des models pour chaque entité. Vérifier les attributs de chaque objet surtout les attributs faisant références à une autre entité pour assurer les relations
entre eux.*

__Sinon, concernant SonarQube, je l'ai téléchargé et je peux le lancer sur ma VM Debian. Il se lance, je peux m'y connecter cependant il faut que j'arrive à paramétrer
la connexion à la BDD de mon laptop. Cela est simple cependant il faut que je voie comment je peux modifier le fichier de config et si je dois installer des driver/
dépendances sur ma VM (il faut que je dégage windows de mon laptop et que je me mette sous linux, ça ne m'apportera que des bénéfices :)__

---
### 13/11/2022

*FRONTEND*

*Je commence à "bidouiller" angular, j'ai créé un component : homepage. Il servira à afficher une page d'accueil.*

---
### 08/11/2022

*Je n'ai pas commit depuis le 3 car j'ai recherché quelques trucs sur le net concernant sonarqube, le chiffrage
des infos de la BDD et j'ai aussi pris un petit break :) Concernant SonarQube, je pense ou l'installer sur ma
vm debian ou alors directement sur mon laptop mais une version community existe donc je pourrais m'en servir*

__Modifications apportées au code :__
- Création d'une méthode __userSelfRetrieve__ dans `UtilisateurService` qui a été implémentée dans `UtilisateurServiceImpl`. Cette méthode va permettre au front de récupérer l'utilisateur connecté et on pourra afficher ses informations comme ses tickets, nom, etc...
- Création d'un __@GetMapping__ dans `UserEndPointController` qui pointe vers la méthode citée ci-dessus

*Je me penche actuelle sur la création de components angular basiques pour commencer en parallèle des autres choses que je compte/dois faire.*

---
### 03/11/2022

__Modifications apportées au code :__
- Correction d'une erreur dans `UtilisateurServiceImpl` dans les méthodes de MAJ d'un utilisateur.
- Ajout d'un booléen __alreadySubmitted__ dans `Test`. Le booléen est utilisé dans la méthode __submitTest__ pour s'assurer que le test en question a déja été submit, si oui alors on retourne une erreur

*Très petit push aujourd'hui, d'autres modifs vont être faites ce weekend et la semaine prochaine.*

---
### 02/11/2022

__Création du projet Angular dans main/resources.__

---
### 02/11/2022

__Modifications apportées au code :__
- Création d'un nouveau contrôleur `UserEndPointController`. Ce contrôleur contient les liens que les utilisateurs (candidats surtout) utiliseront pour récupérer et soumettre leurs tests. Un reset de mot de passe sera aussi mis en place.
- Création de deux nouvelles méthodes dans `TestService` et `TestServiceImpl` : __RetrieveSingleCandidatTest__ et __RetrieveAllCandidatTest__. La première implémente un contrôle du rôle de l'utilisateur et contrôle si l'utilisateur qui veut accéder à ce test en est bien le candidat assigné. Si le test n'est pas assigné, il pourra y accéder. Un admin ainsi qu'un recruteur peuvent accéder à tous les tests.
- Création d'une troisième nouvelle méthode dans `TestService` et `TestServiceImpl` : __utilisateurTestSelfAssign__. Si un utilisateur se voit envoyé le lien d'un test qui n'est pas assigné à un utilisateur, il pourra se l'assigner à lui-même (ou le front pourra le faire).
- Création d'une méthode dans `UtilisateurService` et `UtilisateurServiceImpl` : __userProfileUpdate__. Cette méthode sera réservée aux utilisateurs pour qu'ils puissent mettre à jour certains éléments de leur profil.
- Deux nouvelles méthodes qui renvoient des booléens ont été crées dans `UtilisateurService` et `UtilisateurServiceImpl` : __isUserExisting__ et __mailAddressInUse__. Elles nous permmettent d'implémenter des check de base pour s'assurer que les éléments renseignés sont contrôlés.
- Création d'une query SQL dans `UtilisateurRepository` : __findIfUsedMailAddress__. Query utilisée par la méthode __mailAddressInUse__ citée ci-dessus.

*Je n'ai pas encore modifié le type des retours car ResponseEntity ne répond pas complètement à mon besoin,
si je paramètre la Response pour renvoyer un objet de type Test par exemple je ne peux pas renvoyer de texte
dans le cas d'une erreur.*

*Pour moi le back est à 90% fini, le reste n'est que du peaufinage et éventuellement des petits checks à rajouter
ou autres fonctionnalités légères notamment pour les utilisateurs.*

---
### 01/11/2022 - 2

__La sécurité est maintenant fonctionnelle. Pour cela, j'ai du corriger une erreur dans `UtilisateurRepository.`__

__Modifications apportées au code :__
- Modification de la Query SQL `findUserWithRoles` dans `UtilisateurRepository` -> jusqu'à présent cette query était censée récupérer l'utilisateur et les roles en fonction du login spécifié sauf qu'au lieu de récupérer le login elle récupérait le mot de passe, ce qui ne collait pas...
- Modifications effectuées dans `SecurityUserDetails` -> Les return n'avaient pas été paramétrés correctement. OK maintenant.

*Pour les autres modifications à effectuer, lire le premier paragraphe du 01/11 ci-dessous.*

---
### 01/11/2022

__Modifications apportées au code :__
- Implémentation de la sécurité.
- Mise en place du chiffrage des mots de passe lors de la création/MAJ d'un utilisateur

*Le code a été push cependant il y'a un problème avec `ApplicationWebSecurityConfig`. La config ne me retrouve pas la __DataSource__ (BDD SQL) et par
conséquent m'empêche d'effectuer toute action sur un lien ayant une gestion des accès. Tout est cependant correct, il doit y avoir une petite subtilité
que j'ai oublié.*

*Une méthode pour récupérer les tests du candidat avec une vérification de l'identité de ce dernier va être mise en place, il ne pourra récupérer que ses tests
et passer ceux qui lui sont assignés. Il faudra aussi voir si il faut gérer le cas de figure ou un utilisateur inexistant accède au lien du test pour le passer
et s'inscrit par la même occasion ce qui l'assignera automatiquement à ce dernier.*

---
### 31/10/2022

__Modifications apportées au code :__
- Suppression du booléan __selectedAnswer__ dans `Reponse`. Il ne sera pas utile/utilisé.
- Modification de la logique du code de la méthode `createQuestion` dans `QuestionServiceImpl`. La réponse qui était créée lors de la création de la question n'était liée à aucune réponse car cette opération était faite avant la finalisation de la création de la question, ce qui faisait que la réponse n'était pas assignée. L'ordre d'exécution a été modifié en accord.
- Implémentation du calcul du score global de l'utilisateur dans la méthode `submitTest` dans `TestServiceImpl`. Des modifications ont été effectuées dans la logique de cette méthode suite à divers problèmes rencontrés.

Peu de modifications listées mais maintenant toutes les méthodes sont fonctionnelles. La majorité de mes modifications ont été réalisées sur les classes/services `Test, Question, Reponse et Utilisateur`.
La prochaine étape est la modification des retours (modifier les return String/void par des ResponseEntity) puis l'implémentation de la sécurité et par conséquent
les liens des requêtes Post seront modifiés si nécessaires pour correspondre à la configuration de sécurité.

Après test, tout est fonctionnel, il n'y aura que des petites retouches à faire comme par exemple lors de la soumission d'un test par un candidat, s'assurer que l'UUID d'une réponse
n'est pas renvoyé deux fois car actuellement le code ne vérifie que si la question renvoyée est juste. Peut-être qu'il serait possible de mettre en place un check
ou l'appli récupère les questions du qcm et vérifie que chaque réponse renvoyée correspond à une question du qcm et qu'elle est liée à la bonne. La vérification
actuelle est très basique mais fonctionne. Après tout, le front se chargera de tout ça dans le sens ou il affichera à l'écran les questions du qcm lié au test et
ses réponses et renverra un JSON au back contenant les réponses sélectionnées par le candidat. Le candidat lui-même ne pourra pas taper directement sur l'API pour
truquer ses tests (cas de figure à prendre en compte ?).

---
### 30/10/2022

__Modifications apportées au code :__
- Création d'un OneToMany de `Qcm` vers `Test`. Un Qcm peut être à l'origine de plusieurs tests mais un test ne peut pointer que vers un seul qcm.
- Suppresion de l'héritage de `Qcm` vers `Test`, plus nécessaire car le Qcm contient l'id d'un Qcm qu'on va utiliser pour récupérer les questions/réponses.
- Création d'une variable de type String appelée __titre__ dans `Test`.
- Création d'un __@ManyToMany__ de `Réponse` vers `Test`. Cette relation nous permettra de stocker les réponses renvoyées par l'utilisateur dans le test.
- Implémentation de la méthode `updateTest` dans `TestServiceImpl`.
- Création de la méthode `submitTest` dans `TestService` et `TestServiceImpl`
- Ajout d'un __@PutMapping__ dans `TestController` pour que le candidat puisse soumettre son test.
- Désactivation du __@PostMapping__ (createReponse) dans `ReponseController` pour créer une question => La création d'une réponse se fera automatiquement lors de la création/mise à jour de la question. Si il y'a moins de deux réponses, vous ne pourrez pas créer/update la question/les réponses.

La majorité des modifications effectuées ont été effectuées dans le cadre de l'implémentation de la méthode __submitTest__. La création d'un Test était OK cependant
la conception de la classe `Test` ne permettait pas de mettre en place un check des réponses sélectionnées.

Je compte modifier le type des return de méthodes, je vais les modifier pour renvoyer des ReponseEntity ce qui me permettra de récupérer le contenu de ces retours
dans le front et de renvoyer un statut HTTP. Je pense mettre en place un logging aussi.

*La création des réponses ne fonctionne plus pour l'instant à cause de la condition sur le nombre de réponses. Des modifications doivent être apportées dans la logiques du service `ReponseService`*

---
### 27/10/2022

*Commit/push avec 3 jours de retard*

__Modifications apportées au code :__
- Création d'une variable de type __Double__ appelée *points* dans `Reponse`. Détermine le nombre de points qu'attribue la question.

Je me suis penché sur le souci du submit d'un test, et j'ai peut-être une solution autre qu'un @ManyToMany qui créerait
d'autres problèmes en même temps qu'il en corrigerait d'autres et j'ai aussi laissé tomber l'idée d'une classe
réplique de question et réponse qui aussi créerait des problèmes.
Je pense qu'un Test n'aurait qu'un Id et pointerai vers le Qcm voulu via son Id et récupèrerait les questions/réponses
pour les envoyer au front. Lors du submit, le JSON comporterait ces éléments en plus de l'Id de l'utilisateur (ce qui
nous servira à calculer son globalScore et à avoir un historique des tests passés) et d'un array des réponses choisies
avec leur Id. On pourrait vérifier si chaque réponse renvoyée est correcte grâce à un foreach et une Query SQL
ou du code tout simple.

---
### 26/10/2022

__Modifications apportées au code :__
- Création d'une Query SQL dans `UtilisateurRepository` pour la mise en place future de la sécurité
- Petites modifications de code dans `ReponseServiceImpl`, `TestServiceImpl`.
- La méthode `createTest` a été modifiée dans `TestService` et partout ou elle a été implémentée. Pour créer un test, on envoie l'UUID de l'utilisateur (dans le cas ou il existe déja) et le qcm choisi puis le service créé un Test à partir du qcm.
- Création d'un nouvel INSERT dans import.sql pour créer un admin par défaut
- Modification d'une erreur dans les mappings du __Post__ et du __Put__ pour créer et update un QCM dans `QcmController`. Au lieu de demander un body, je demandais un PathVariable, ce qui n'est pas possible.

Toutes les requêtes CRUD de base fonctionnent comme voulu sauf pour `Test`.
Pour créer un test, je lui envoie un QCM et l'id de l'utilisateur qui va le passer et récupère les éléments du QCM.
Cependant, les questions, étant liées qu QCM ne peuvent pas être réassignées au Test. Trouver une solution.
Des modifications de la logique dans certains services seront effectuées, notamment pour le trio question/reponse/qcm
pour la liaison d'une question à un qcm. Il faut vérifier que la question a bien deux réponses minimum et le code
actuel ne fonctionne pas. Il faudrait pouvoir mettre en place un mécanisme d'ajout des réponses aux questions au fur et à mesure.
Une idée, récupérer les questions/réponses au fur et à mesure via un mapping GET ? Et créer un PostMapping pour que lors
de l'envoi la réponse soit prise en compte et processée directement. Réfléchir.
__Créer une entité réplique de question, par exemple TestQuestion ? De même pour Reponse ?__
(la méthode de mise à jour de `Test` n'a par conséquent pas été testée.)

---
### 25/10/2022

*Le "return type" des méthodes __delete__ des services sera modifié. Pour l'instant ces méthodes renvoient un string
mais je vais implémenter à terme des ResponseEntity ou une classe qui renverra un objet créé à cet effet.*

__Modifications apportées au code :__
- Dans `UtilisateurController`, j'avais fait un @Autowired sur `UtilisateurServiceImpl` au lieu de l'interface `UtilisateurService`. Ceci a été corrigé.
- Mappings créés dans `TestController`, `ReponseController` et `QuestionController`
- Des modifications dans le code de `QuestionServiceImpl` ont été effectuées.
- La méthode `createTest` doit être testée. On envoie un QCM à partir duquel on créé un test en reprenant ses éléments.

*Je suis en train de réfléchir à comment implémenter la méthode "submit" pour que le candidat puisse envoyer son test mais aussi comment le modifier.
Il faudra aussi gérer l'éventualité ou l'utilisateur est vide et que ce champ sera renseigné lorsque le candidat s'inscrira pour passer ce test.
Une méthode et un mapping dans le controller doivent être créés à cet effet.*

*Il faudra aussi mettre en place une méthode pour que les utilisateurs puissent changer leur mot de passe si nécessaire.*

*Aussi penser à implémenter des vérifications comme par exemple pour update un utilisateur, s'assurer que le login n'est pas pris,
et des méthodes similaires pour les autres classes.*

---
### 24/10/2022

__Modifications apportées au code :__
- Ajout d'un booléen `selectedAnswer` dans `Reponse`. Ce booléen sera peut-être renommé en Français. Il me servira à implémenter la vérification des réponses envoyées par le candidat. Il sera supprimé si inutile ou autre.
- Création du CRUD dans `ReponseService` et implémentation de ce service dans `ReponseServiceImpl`.
- Création du CRUD dans `TestService` et implémentation dans `TestServiceImpl`.
- Implémentation d'une vérification du nombre de réponses d'une question. Si inférieur à deux, une erreur est renvoyée. __Cette fonctionnalité doit être testée__
- Création d'une requête SQL dans `TestRepository` => `findTestByCandidat`. Cette requête renvoie une liste des tests passés par le candidat indiqué.
- Création d'une requête SQL dans `ReponseReposiory` => `findReponsesByQuestion`.
- Création d'une requête SQL dans `QuestionRepository` => `findQuestionByQcmId`.
- Création des controleurs manquants. La logique sera implémentée lors du prochain commit.

Une fois toutes les méthodes CRUD vérifiées et le fonctionnement de certaines méthodes spécifiques, je commencerais
l'implémentation de la sécurité.

---
### 20/10/2022 - 2

__Modifications apportées au code :__
- Modification de la query SQL dans `QcmRepository` => Elle retourne désormais une liste. Elle nous permettra de récupérer les QCM contenant les mots indiqués dans leur titre.
- Modification de la méthode `getQcmByTitle` dans `QcmServiceImpl / QcmService` pour qu'elle retourne une liste (en accord avec la modification de la requête évoquée ci-dessus)
- Suppression de la méthode `getQuestionByQcmName` => Cette méthode ne sera pas utile puisqu'on récupère déja les questions du qcm avec son id, de plus dans le cas ou plusieurs qcm ont un titre similaire ou assez proche cela compliquerait les choses.
- Création des Rest Endpoints dans `QcmController`.

Une fois tous les rest endpoints créées et testés et le fonctionnement du renvoi du test utilisateur (lire ideas.md), je passerais à la gestion des erreurs puis à la sécurité.

---
### 20/10/2022
__Premier commit__
