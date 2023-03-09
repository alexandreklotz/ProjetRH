### 09/03/2023

__*FRONTEND*__

__Modifications apportées au code :__
- Ajout de __.subscribe()__ dans chaque appel de type `delete` dans `qcm.service`, `reponse.service`, `question.service`
- Mise en place de redirections lors de la suppression d'éléments (question, réponse, qcm) et de refresh lors de la création d'autres (réponses dans question)

*D'autres modifications doivent être faites telles que la mise à jour d'un utilisateur si son mot de passe est vide donc inchangé,
self-modification d'un utilisateur et nettoyer/clarifier certains bouts de code.*

---
### 08/03/2023

__*BACKEND*__

__Modifications apportées au code :__
- Réactivation du post mapping `createReponse` dans `ReponseController`
- Ajout de l'id des questions dans la liste des questions d'un qcm, corrige un problème ou l'id de la question n'était pas récupéré dans le front

__*FRONTEND*__

__Modifications apportées au code :__
- Modification de `QcmInterface` et `QuestionInterface` => Un Qcm peut être null dans une Question et vice versa
- Modification des models `Qcm` et `Question` en fonction de la modification indiquée ci-dessus
- Modification des formulaires de modification `single-qcm` et `single-question` => La modification fonctionne. Il ne me reste qu'à finaliser quelques bricoles pour `single-question` et c'est bon
- Modification de `QuestionService` => Création de la requête `updateQuestion`
- Modification de `ReponseService` => Création des requêtes `createNewReponseInQuestion` et `deleteQuestion`

---
### 1/03/2023 - 6/03/2023

__*FRONTEND*__

__Modifications apportées au code :__
- Le bug de la mise à jour des mots de passe lors de la modification d'un utilisateur a été partiellement corrigée. Il faut maintenant gérer le cas lorsqu'on ne renvoie pas de mot de passe.

*Beaucoup de bricolage, peu de choses concrètes à ajouter dans le changelog*

---
### 28/02/2O23

__*FRONTEND*__

*Pas de changelog depuis un bon moment, j'ai fait pas mal de modifications/essais sans rien noter, ça aurait été trop lourd
et créé beaucoup de superflu.*

__Modifications apportées au code :__
- Création de deux "DTO" (pas vraiment des DTO) : `question-dto` et `reponse-dto`.
- Modification de `qcm-interface`, `question-interface`.
- Modification de la logique et des formulaires pour les deux composants suivants : `create-question-form` et `create-qcm-form`

*La création d'un QCM et d'une Question avec plusieurs réponses est maintenant possible ! Il y'a juste de la logique à corriger/
nettoyer dans certains composants sinon c'est fonctionnel.*


---
### 15/02/2023

*Bagarre encore et toujours avec les formulaires, je pense pourtant être tout près...*

---
### 14/02/2023

__*FRONTEND*__

*On persiste sur les formulaires et je pense toucher au but (tardivement certes). J'ai réussi à avoir un JSON
comme je veux cependant le formulaire ne renvoie pas les valeurs des 'formArray' qui sont des sous formulaires,
comme par exemple dans une question il y'a un array de réponses, il n'arrive pas à ajouter les variables souhaitées
dans l'array réponses.*

*J'ai aussi des bug d'affichage dans les HTML de ces mêmes formulaires, lorsque je créé une réponse dans une question,
au lieu d'avoir le texte de cette dernière comme souhaité j'ai juste [Object] [object]. Cela est du à comment je gère
l'array des réponses, je vais tout de suite les récupérer dans le formulaire. Cela n'est pas ma priorité immédiate,
je cherche à pouvoir créer des objets avant tout.*

---
### 13/02/2023

__*FRONTEND*__

__Modifications apportées au code :__
*Modification des formulaires et de leur vue, correction d'éléments dans des services et création d'éléments comme
qcm-interface*


__*BACKEND*__

__Modifications apportées au code :__
- Modification de l'URL du __@GetMapping__ dans `TestController` => auparavant `moderateur/test/{testId}`, maintenant `moderateur/test/id/{testId}`

---
### 09/02/2023

__*FRONTEND*__

__Modifications apportées au code :__
- Beaucoup de modifications apportées dans diverses pages HTML suite à des oublis de classe dans les balises <input>.
- Ajout d'un lien dans `Utilisateur-Service` : `getCandidatById`. Pointe vers un nouveau lien du back pour récupérer un candidat avec son id.


__*BACKEND*__

__Modifications apportées au code :__
- Création d'une __@Query__ dans `UtilisateurRepository` => `findCandidatById`.
- Création de la méthode `getCandidatById` dans `UtilisateurService` et implémentation dans `UtilisateurServiceImpl` qui utilise la query mentionnée ci-dessus.


---
### 08/02/2023

__*FRONTEND*__

__Modifications apportées au code :__
- Création du component `utilisateur-self-update` : permet à l'utilisateur de modifier ses informations


*Pour abréger ce changelog, j'ai continué la mise en place des composants propres aux admins/recruteurs pour
la création/gestion/suppression d'éléments. Il y'a aussi eu de la mise en page et des nouvelles méthodes ont été créées
pour assurer les fonctions de ces composants.*


__*BACKEND*__

__Modifications apportées au code :__
- Création d'une __@Query__ SQL dans `QuestionRepository` pour récupérer les questions étant liées à un qcm ou à rien du tout mais aucune question liée à un test
- Création d'un __@GetMapping__ dans `QuestionController` qui tape sur la Query mentionnée ci-dessus

---
### 07/02/2023

__*FRONTEND*__

__Modifications apportées au code :__
- Création d'un nouveau component : `unauthorized.component.ts` => affichera une page qui indique que l'utilisateur n'est pas autorisé à accéder la page à laquelle il souhaite accéder
- Modification de `auth.guard.ts` pour rediriger les requêtes non autorisées vers `unauthorized`
- Suppression du bouton `Supprimer` dans les listes accessibles par l'administrateur (`utilisateur-liste`, `qcm-liste`, etc)
- Modification du formulaire de modification d'un utilisateur pour gérer les rôles (`single-utilisateur`)
- Le formulaire de modification d'un qcm `single-qcm` est presque terminé, il me faut juste finaliser la gestion des questions dans le formulaire/typescript
- Le component `question-liste` récupère les questions étant assignées à un qcm ou à rien du tout mais ne récupère pas les questions liées à un test


__*BACKEND*__

__Modifications apportées au code :__
- Création d'une nouvelle Query dans `QuestionRepository` pour récupérer les questions n'étant assignées à aucun test (mais qui peuvent être assignées qu'à un qcm ou rien du tout) : `getNoTestQuestion()`

---
### 06/02/2023

__*BACKEND*__

__Modifications apportées au code :__
- Ajout de __l'id du rôle de l'utilisateur__ dans la __JsonView__ de l'utilisateur


__*FRONTEND*__

__Modifications apportées au code :__
- Le formulaire de visualisation/modification `single-utilisateur` récupère l'utilisateur et nous permet de le modifier.

---
### 01/02/2023

*Encore beaucoup de bagarre avec les formulaires... Une usine à gaz*

__*BACKEND*__

__Modifications apportées au code :__
- Création de `RolesController` => Ne contient qu'un seul __@GetMapping__ qui est utilisé par le front pour récupérer la liste de tous les rôles pour la création des utilisateurs


__*FRONTEND*__

__Modifications apportées au code :__
- Finalisation du formulaire `create-utilisateur-form` => Un admin peut maintenant créer un utilisateur et lui attribuer un rôle
- Sur la vue admin, dans la liste des tests, le bouton __Créer__ redirige maintenant l'admin vers le formulaire de création d'un test
- Des interfaces ont été créées pour chaque classe : `question`, `test`, `reponse` plus ceux cités ci-dessous. Certaines interfaces pourraient ne pas être utilisées
- Refactor de `UtilisateurInterface` vers `CandidatInterface` (Lire ligne ci-dessous)
- Création de `UtilisateurInterface` qui nous permet de normaliser un JSON qui sera envoyé au back pour création d'un utilisateur
- Création d'une route dans `app.routing.ts` pour créer des utilisateurs => __http://.../creer/utilisateur__
- Ajout des boutons `Créer` et `Supprimer` dans `test-liste` et `qcm-liste` dans le panel admin.

*Les formulaires pour créer une question et un qcm sont en cours. Je regarde comment je peux faire.
Il y'a encore deux/trois petites fonctionnalités comme la barre de pourcentage à "animer" sur le dashboard
candidat, la liste des derniers tests/candidats créés sur le panel recruteur (voir si il faudra créer une variable
date dans le back)*

---
### 25-31/01/2023

*Beaucoup de recherches, de faire pour défaire, lecture de doc, etc...*

__*FRONTEND*__

__Modifications apportées au code :__
- Modification du code pour `create-test-form.component.ts` et `create-test-form.component.html` => Au lieu de créer un formulaire dynamique et de récupérer ses valeurs, on récupère juste les valeurs renseignées dans le formulaire qu'on stocke dans une variable qui par la suite est renseignée dans le JSON qui est envoyé au back. __ET CA MARCHE !__
- Ajout de la variable `points` dans `Question.model` => Oubliée lors de la création
- Suppression de la variable `points` dans `reponse.model.ts` => Une réponse n'a pas de points dans ce logiciel
- Création de `QuestionInterface` qui servira à envoyer une question dans un JSON au back pour la création.

---
### 25/01/2023

__*FRONTEND*__

__Modifications apportées au code :__
- Le formulaire de création de test fonctionne cependant pour l'instant je n'arrive pas à récupérer l'id du candidat sélectionné
- Création de `test-interface`.

*J'ai passé beaucoup de temps à bricoler les formulaires, j'ai tenté de modifier le formulaire pour créer des qcm mais ça se complique.
Il va en être de même pour le formulaire de création de questions.*

---
### 24/01/2023

__*FRONTEND*__

__Modifications apportées au code :__
- Création d'une interface : `candidat-interface.ts` => Nous permet de formaliser un modèle qui sera repris pour créer un JSON
- Création d'une __PostRequest__ dans `utilisateur.service.ts` => `createCandidat`. Cette méthode nous permet de créer des candidats
- Modification de `create-candidat-form.ts` => ajout d'un `FormBuilder` dans lequel j'implémente un `FormGroup` qui correspond à un formulaire sur lequel pointe le HTML. Il récupère les données de chaque champ et complète ce dernier qui est ensuite envoyé au back.
- La création de candidats fonctionne !

*La même modification va être apportée à tous les formulaires.*

---
### 18/01/2023

__*BACKEND*__

__Modifications apportées au code :__
- Création d'une __@Query__ dans `UtilisateurRepository` => `findUtilisateursWithRole`. La méthode `getAllCandidats` dans `UtilisateurServiceImpl` utilise cette query pour uniquement renvoyer les candidats au front.


__*FRONTEND*__

__Modifications apportées au code :__
- Création de plusieurs components :
  - `single-qcm` => servira à afficher un seul qcm
  - `single-question` => Servira à afficher une seule question
  - `single-candidat` => idem que précédemment mais pour candidat
- Création d'une requête POST dans `QuestionService` pour créer des questions (pas fonctionnelle, à implémenter dans le formulaire)
- Création d'une requête GET dans `QuestionService` pour récupérer les questions non assignées dans le formulaire de création d'un Qcm.
- Modification de certaines routes car la manière dont je gère les UUID pose problème. Voir si j'arrive à régler le problème complètement.
- Pour la liste des Qcm et des Tests accessibles à partir du panel recruteur (et qui le seront aussi à partir du panel admin), les lignes sont cliquables et nous redirigent vers une page contenant les détails du qcm/test.
- Idem que ci-dessus pour la liste des candidats

*Les formulaires de création pour `Question et Qcm` vont être un poil compliqués, il faut que j'arrive à gérer l'ajout de plusieurs réponses pour les questions
et de plusieurs questions pour les qcm. Il faut que je puisse avoir une liste des questions déja sélectionnées dans le formulaire.
Cela me demande donc de mettre en place des formulaires dynamiques.*

*Une fois les formulaires dynamiques faits, je pourrais me concentrer sur l'envoi des requêtes et passer à l'étape finale qui sera
l'affichage du Test lors du passage de ce dernier sur l'écran du candidat. Comment gérer l'affichage en fonction du temps imparti
indiqué dans la question, etc...*

---
### 17/01/2023

__*BACKEND*__

__Modifications apportées au code :__
- Finalisation de la modification de la méthode `submitTest` dans `TestServiceImpl` => Le score est maintenant calculé correctement.

__*FRONTEND*__

__Modifications apportées au code :__
- Création d'un nouveau formulaire pour créer les questions => `create-question-form` dans *_components/_forms* et ajout de la route dans le app routing
- Modification de la requête __GET__ dans `QcmService`. Elle est maintenant async


*Pour faire un formulaire de création de question propre, il me faut un formulaire dynamique. Il faudra que j'implémente ça pour
le formulaire de création utilisateur/candidat en essayant de faire en sorte de n'avoir qu'un seul formulaire/component pour
ces deux fonctions. Une fois chaque formulaire créé, je rajouterais les requêtes manquantes dans les services et je ferais des
JSON dynamiques qui seront envoyés au back.*

---
### 16/01/2023

__*BACKEND*__

__Modifications apportées au code :__
- Création d'une __@Query__ SQL dans `QuestionRepository` => `getUnassignedQuestions`. Cette query renvoie une liste des questions n'étant pas assignées à un test ou un qcm.
- Création d'un __@GetMapping__ dans `QuestionController` qui permet au recruteur/admin d'utiliser la requête mentionnée ci-dessus pour assigner une question non assignée à un qcm.

---
### 11/01/2023

__*BACKEND*__

__Modifications apportées au code :__
- La méthode `submitTest` dans `TestServiceImpl` ne fonctionnait pas comme souhaité suite aux changements dans les classes, etc. Elle a été corrigée
- Création d'une méthode `getNumberOfCorrectReponses` dans `ReponseService` => Sert à renvoyer un Integer égal au nombre de réponses justes pour la question indiquée. Nous sert à caculer le nombre de points de chaque réponse.

*Il y'a des problèmes avec le calcul du score total du test dans `submitTest`. Il retourne un score négatif à chaque fois et itère
en double sur la map (apparemment) à vérifier.*

---
### 10/01/2023

__*BACKEND*__

*Pas de changement, beaucoup de recherches pour régler certaines problématiques et certains changements mineurs.*

---
### 09/01/2023

__*BACKEND*__

__Modifications apportées au code :__
- Modification de la méthode `setUtilisateurGlobalScore` dans `TestScoreServiceImpl` => La méthode calcule le pourcentage de chaque test pour calculer le pourcentage total de réussite de l'utilisateur peu importe la note maximale du test
- Modification de la méthode `submitTest` dans `TestServiceImpl` => Adaptée aux modifications apportées aux classes `Test`, `Qcm` et `Question`.
- Modification de la méthode `deleteTest` dans `TestServiceImpl` => Adaptée aux modifications apportées citées ci-dessus

---
### 05/01/2023

__*BACKEND*__

__Modifications apportées au code :__
- Suppression du package `bean`
- Suppression des relations suivantes :
  - __@OneToMany__ de `Test` vers `Reponses`
  - __@ManyToOne__ de `Test` vers  `Qcm`
- Création des relations suivantes :
  - __@ManyToMany__ de `Question` vers `Test`
- Création d'une nouvelle méthode : `duplicateQuestionsFromQcm` dans `QuestionService` => Récupère les questions du qcm, et créé de nouvelles identiques qui seront assignées au test indiqué

*La méthode submit test va devoir être modifiée. Plutôt que de récupérer les questions/réponses du qcm lié, les vérifier
et les mettre dans une map pour ensuite comparer tout ça avec celles du test, on vérifiera directement les questions/réponses du
test de base (qui est assigné à l'utilisateur dans sa forme de base, donc non soumis et vierge) pour vérifier que tout est identique
puis on se servira du booléen `isSelectedAnswer` qui a été créé dans `Reponse` pour le reste.*

---
### 04/01/2O23

__*FRONTEND*__

__Modifications apportées au code :__
- Modification du HTML/CSS dans `recruteur-panel` et `utilisateur-list`
- Modification du HTML/CSS pour le formulaire `create-candidat-formulaire`
- Création d'un nouveau component dans `_components/_forms` : `create-test-form`.

*Beaucoup de "bricolage" et beaucoup d'idées. Prend pas mal de temps*


---
### 03/01/2023

__*FRONTEND*__

__Modifications apportées au code :__
- Création de deux nouveau components qui seront des formulaires de création : `create-candidat-form` et `create-utilisateur-form`
- Création d'un panel propre aux recruteurs `recruteur-panel`.

*Principalement du bricolage en HTML/CSS sur le dashboard candidat, recruteur panel et create-candidat-form.*

---
### 30/12/2022

__*FRONTEND*__

__Modifications apportées au code :__
- Ajout d'une méthode `headerDisplay` dans `header` => récupère le rôle de l'utilisateur et affiche des éléments dans le header en fonction de ce dernier
- Suppression du component `candidat-tests-liste` et suppression du lien vers cette vue dans le header, on affichera tout sur une seule page pour le candidat
- Création d'un panel administrateur avec des *card*, component : `admin-panel` dans le dossier `admin`
- Modification de `TestService` => La méthode `getAllTest` est maintenant une méthode async
- Suppression de `utilisateur.component.ts` et `test.component.ts` => Maintenant inutiles
- Ajout d'images dans `/assets/images` => Pour l'instant uniquement les images du panel admin sont dans ce dossier
- `test-list.component` affiche un tableau contenant tous les tests avec quelques informations les concernant

*Beaucoup de HTML/CSS. La searchbar dans le header doit être rendue fonctionnelle et centrée, de même que les cards dans le panel admin (il faudra aussi le faire
dans le panel recruteur vu que la page sera identique).*

*Les pages affichant des tableaux comme pour tous les utilisateurs ou tous les tests seront modifiés pour ouvrir une page affichant l'élément sur lequel on
a cliqué.*

---
### 29/12/2022

__*FRONTEND*__

__Modifications apportées au code :__
- Refactor de `sidebar` => maintenant nommé `header`
- Modification du CSS/HTML de `header` => Ajout de liens et modification du graphisme
- Modification du CSS/HTML de `login` => On a maintenant une petite fenêtre de login centrée avec un peu de style
- Création d'un component `candidat-tests-liste` qui va récupérer tous les tests du candidat et les afficher dans un tableau
- Modification de la méthode `userRetrieveAssignedTests` dans `UserEndPointService` => C'est maintenant une méthode async
- Modification de la méthode `getAllUtilisateur` dans `UtilisateurService` => Maintenant async
- Création d'un tableau qui contient tous les utilisateurs. Component : `utilisateur-list.component.ts`

*Toujours pas énormément de progrès. Beaucoup bataillé avec bootstrap et quelques bugs. J'ai quelques erreurs de code à corriger dans le back mais elles sont
minimes donc rien de grave. Je progresse plus lentement que prévu. Le header n'est pas super beau à mon goût, la fenêtre de login est pas mal mais je suis pas
très orienté design lol. La dashboard candidat est relativement vide, je me demande si je ne vais pas tout regrouper dans la dashboard et faire des containers
avec un pour les tests non passés, tests passés, tests récemment assignés, plutôt que de le séparer (ou alors permettre à l'utilisateur de "ticker" une box
pour filter les tests affichés tout en gardant une liste des nouveaux tests assignés).*

---
### 28/12/2022

__*FRONTEND*__

__Modifications apportées au code :__
- Création d'un nouveau dossier `_forms` dans `_components` : ce dossier contiendra les components pour les formulaires de création/modification
- Création d'un nouveau component : `utilisateur-form` dans `_forms`
- Création d'un nouveau component : `single-utilisateur` dans `_components`. Ce component nous servira à afficher un seul utilisateur avec toutes ses informations.
- Modification du HTML/CSS dans le component `utilisateur-list`. Ajout de colonnes.
- Modification du HTML/CSS dans le component `dashboard`. Début de la mise en page.
- Modification du HTML/CSS dans le component `sidebar`.

*Pas énormément de progrès ce jour. Je ne trouve aucune documentation qui répond à mes questions sur bootstrap. Bien évidemment il y'a la doc de bootstrap sur leur
site mais elle ne m'aide pas. Impossible de faire une sidebar comme je veux ni même de disposer des éléments comme je le souhaite sur la page dashboard.*

---
### 27/12/2022

__*BACKEND*__

__Modifications apportées au code :__
- Suppression de la méthode `updateUtilisateurGlobalScoreAfterDelete` dans `TestScoreService / TestScoreServiceImpl`-> Elle est inutile et seule la méthode `setUtilisateurGlobalScore` est nécessaire/utilisée.
- Le calcul des points d'un test se faisait en faisant l'addition des points de chaque réponse correcte ou en enlevant les points d'une réponse incorrecte. La propriété `Points` a été basculée dans `Question` et la méthode `submitTest` dans `TestServiceImpl` a été modifiée en fonction.
- Modification de la méthode `submitTest` dans `TestServiceImpl` => Les points étant maintenant propres à la question et non à la réponse, on divise les points de la question par le nombre de réponses qu'elle possède. Si elle a par exemple 4 réponses et qu'elle vaut 4 points, chaque réponse vaudra un point. Si la réponse est correcte, on ajoute un point, si elle est incorrecte ou enlève un point.
- Modification du code de la méthode `deleteTest` => On itère dans chaque réponse liée au test qu'on veut supprimer, on récupère la liste de tests de cette réponse et on supprime le test a supprimer de cette dernière. J'ai du adopter cette approche car sinon ça posait problème dans la BDD.


__*FRONTEND*__

__Modifications apportées au code :__
- Modification de la requête HTTP dans `UserEndPointService` -> Quand un candidat voulait accéder à sa dashboard, les données n'étaient renvoyées qu'une fois sur deux. Le code a été modifié pour procéder à une exécution séquentielle de la requête en précisant `await` avant cette dernière. La méthode `userSelfRetrieve` dans ce même service est maintenant une méthode `async`. Toutes les requêtes HTTP seront async.
- Ajout de bootstrap dans le projet

*J'attaque la mise en forme du dashboard candidat histoire d'avoir une vue.*


---
### 21/12/2022

__*FRONTEND*__

__Modifications apportées au code :__
- Création d'un service dédié aux candidats : `UserEndPointService` => Il va effectuer les requêtes POST/GET sur les endpoints propres aux candidats
- Création d'un dossier `_restricted` dans `_services`. Ce dossier contient les services réservés aux recruteurs/admin
- Suppression de `dashboard.model.ts` => inutile
- Création du component `sidebar` et suppression du component `logout`. Le button pour logout sera intégré dans la sidebar
- Création du dossier `_components` qui contiendra tous les components de l'app

*Grosse réorganisation du code en cours. J'ai supprimé beaucoup de component pour me concentrer sur un ou deux component maximum
à la fois. J'ai créé deux "dummy" components qui seront bien entendu de vrais components à terme qui sont test/test-list et
utilisateur/utilisateur-list. Je me focus d'abord sur la dashboard, voir comment je peux faire la disposition de cette dernière
et une fois que j'ai une vue correcte avec une sidebar comme je le souhaite j'attaque les components en profondeur.*


__*BACKEND*__

__Modifications apportées au code :__
- Création d'une nouvelle variable de type __String__ dans `Utilisateur` => `userDescription`

*TODO : Il va falloir corriger la variable `globalScore` de `Utilisateur` pour n'afficher que 2 chiffres après la virgule.*

---
### 20/12/2022

__*BACKEND*__

__Pour le 2e push de ce jour, modification rapide de la méthode `setUtilisateurGlobalScore`, le paramètre `testScore` n'était pas
nécessaire et la méthode ainsi que `submitTest` ont été modifiés en fonction.__

*La manière dont je calculais le globalScore des utilisateurs n'était pas correcte. Un service a été créé à cet effet et la
logique de calcul a été corrigée.*

__Modifications apportées au code :__
- Modification du antMatcher `/recruteur` dans `ApplicationWebSecurityConfig` => modifié en tant que `/moderateur`
- Suppression du __@GetMapping__ de test `recruteur/test` dans `LoginController`
- La majorité des __@RequestMapping__ sauf le lien `admin/utilisateur/create, ` pour modifier tous les utilisateurs ont été modifiés et sont accessibles par les recruteurs. Ces liens commencent désormais par `moderateur/` au lieu de `admin/`
- Création de __@RequestMapping__ pour créer/modifier et supprimer __UNIQUEMENT__ des candidats. Ces liens seront accessibles aux admins et recruteurs mais ont été principalement créés pour les recruteurs
- Création d'un service `TestScoreService` qui est implémenté dans `TestScoreServiceImpl`. Ce service sert à mettre à jour le score de l'utilisateur qui soumet un test et il sert aussi à mettre à jour le score de l'utilisateur lors de la suppression d'un test qu'il a déja passé et dont la note est comptée dans sa moyenne. Les méthodes sont :
  - `setUtilisateurGlobalScore` : Il récupère l'utilisateur qui a passé le test et le score du test, remet sa moyenne à 0, fait le total de tous les tests passés le nouveau inclus et divise ce total par le nombre de tests passés. Il set ce total à l'utilisateur dans sa variable `globalScore`.
  - `updateUtilisateurGlobalScoreAfterDelete` : Il récupère l'id du test supprimé et l'id de l'utilisateur lié à ce test. Il récupère ces deux objets, récupère la liste des tests de l'utilisateur et supprime le test à supprimer de cette liste. Il additionne ensuite le score des tests restants et les divise par le nombre de tests. Il set le total à l'utilisateur dans sa variable `globalScore`.
- Création d'une __@Query__ dans `RolesRepository` => `findRoleByName`. Cette query nous permet de récupérer un rôle en indiquant sa dénomination. Cette query est utilisée par `UtilisateurServiceImpl` dans la méthode `createCandidat` pour s'assurer que l'utilisateur créé par le recruteur aura le rôle candidat __par défaut__.


__*FRONTEND*__

__Modifications apportées au code :__
- Modification de la logique dans `auth.guard.ts` => Le guard arrive maintenant à itérer sur un array de rôles et à rediriger l'utilisateur. Le filtrage des rôles est maintenant fonctionnel

*Je vais attaquer la réorganisation du front. Je vais séparer l'appli en trois dossiers, admin, candidat et recruteur.
Je vais peut-être créer un service propre aux candidats avec seulement les liens vers les endpoints qu'ils peuvent utiliser,
et en créer pour les recruteurs et admins histoire de bien séparer (voir si utile/cohérent dans le cas des services).
Pour les pages, séparer l'appli me permettrait d'organiser correctement mon projet à moins qu'il soit possible d'utiliser
des paramètres de angular dans le HTML pour cacher des champs si une condition n'est pas remplie. Il y'a des choses sur
lesquelles je dois encore me documenter. En tout cas mon ostacle de l'authentification est de l'histoire ancienne, en
prenant du recul c'était en fait tout simple...*

---
### 16-17-18-19/12/2022

__*FRONTEND*__

*Le filtrage de l'accès aux pages en fonction des rôles fonctionne cependant je rencontre un problème, lorsque je spécifie plusieurs
rôles dans la route sous forme de tableau, par exemple : date : { allowedRoles : ["CANDIDAT", "RECRUTEUR"]}, le `auth.guard` 
n'arrive pas à comparer le rôle de l'utilisateur avec chacun des rôles du tableau. J'ai pensé à faire un forEach mais inutile
sur typescript, j'ai aussi essayé d'itérer sur chacun des éléments avec une boucle for mais sans succès. Par contre, le filtrage
marche parfaitement si il n'y a qu'un rôle qui peut accéder à la route car il n'est pas sous forme de tableau.
Il y'a deux solutions pour palier à ce problème : Créer un guard par rôle, lourd et peu pratique mais fonctionnel et
je pourrais être plus strict au niveau des rôles ou trouver une solution pour le tableau de rôles dans la route 
en trouvant un moyen pour comparer chaque string de l'array.*

*En gros, j'ai beaucoup fait pour défaire. Très frustrant car du coup j'avance peu alors que c'est quelque chose de très simple.*

__Modifications apportées au code :__
- Création de plusieurs méthodes dans `token.service.ts` => `getUserRoleFromToken` qui récupère le rôle de l'utilisateur dans le token et le renvoie, et `redirectUserBasedOnRole` qui redirige les utilisateurs en fonction de leur rôle (lire le commentaire en italique avant la liste)

---
### 15/12/2022

__*BACKEND*__

__Modifications apportées au code :__
- Ajout d'un __antMatcher__ dans `ApplicationWebSecurityConfig` => `/recruteur`. Des mappings vont être modifiés, au lieu de commencer par `/admin` ils commenceront par `/recruteur`. Cela me permettra de gérer les rôles plus facilement.
- Création d'un __GetMapping__ dans `LoginController` => `/recruteur/test`. Il sera utilisé afin de vérifier la gestion des rôles du front. Seul un recruteur ou un admin peuvent accéder à cet url, pas un utilisateur normal.


__*FRONTEND*__

__Modifications apportées au code :__
- Création de `token.interceptor.ts` => Cet élément intercepte les requêtes sortantes du front allant vers le back, la clone et intègre dans ce clone le token de l'utilisateur. Si le token est expiré, il le supprime.
- Refactor de `homepage` en `dashboard`.

---
### 14/12/2022

__*BACKEND*__

__Modifications apportées au code :__
- Modification de `LoginController` : modification du retour de `/authentification` => on retourne maintenant une map pour pouvoir renvoyer le token dans un formulaire JSON. La propriété __@RequestMapping(produces = "application/json")__ a été ajoutée dans ce contrôleur dans ce but.

__*FRONTEND*__

__Modifications apportées au code :__
- Création de deux services : `auth` et `token`. `auth` nous permet de nous authentifier sur le back en récupérant le token et en allant le stocker dans `token` qui lui est injecté avec `auth.guard.ts` pour filtrer les requêtes
- Création de `_helpers` et de `auth.guard.ts` => nous permet de filtrer les demandes d'accès aux différentes URL du front en vérifiant le token et si l'utilisateur est identifié. `app-routing.module.ts` a été modifié pour que ce mécanisme puisse fonctionner.
- Les répertoires contenant des services/interfaces ou autres mécanismes essentiels au fonctionnement du front été renommés comme suivant : `_nomdudossier`. Cela nous permet de différencier les directory pour des éléments visuels et les directory des mécanismes logiciels
- Mise en place du logout.

*Il faut que je gère les rôles, c'est-à-dire que par exemple le auth.guard.ts ne permet qu'aux utilisateurs authentifiés d'accéder aux pages autres que la page de login
cependant il ne filtre pas l'accès aux pages réservées au recruteur/admin. Cela viendra par la suite.*

*Je vais devoir restructurer toute l'application et séparer le côté admin, le côté recruteur et le coté candidat.
Cela implique peut-être de créer des services propres à chaque type d'utilisateur pour bien fermer/dédier certaines fonctions*

---
### 13/12/2022

__*BACKEND*__

*Mise en place des token. La config de sécurité a à nouveau été modifiée. Les token sont bien générés*

__Modifications apportées au code :__
- Création de `JwtUtil` et `JwtRequestFilter` => Mise en place des token
- La configuration de sécurité `ApplicationWebSecurityConfig` a été modifiée en fonction.
- Des erreurs ont été corrigées dans `SecurityUserDetailsService`
- Création d'un `@PostMapping` dans `LoginController` => `/authentification`. Nous permet de nous authentifier et de nous voir attribués un token.

*Il ne me reste plus qu'à mettre en place le stockage du token dans le front et voir comment récupérer les éléments liés à l'utilisateur connecté.*

---
### 12/12/2022

__*FRONTEND*__

*J'ai beaucoup défait pour refaire, d'où la nature de mes modifications.*

__LE LOGIN FONCTIONNE APRES MODIFICATION DE LA CONFIG DE SECURITE. Il ne me reste qu'à récupérer un Id de session lors du login
pour que les pages soient 100% fonctionnelles__

__Modifications apportées au code :__
- Suppression du component `login`.
- Suppression de `authservice` et de `httpinterceptorservice`.
- Création d'un nouveau component `login`.

__*BACKEND*__

__Modifications apportées au code :__
- Modification de la configuration de sécurité `ApplicationWebSecurityConfig`
- Création d'un filtre pour les requêtes `SessionFilter`

---
### 06/12/2022

*Progrès très lent car malade. En train de me bagarrer avec le soucis de la page de login angular qui serait causé par la sécurité capricieuse et vague de spring security.*

---
### 30/11/2022

__*BACKEND*__

*Les modifications apportées notamment le Bean `AuthenticationBean` me servent à tester. Au fur et à mesure que j'avancerai et que
je deviendrais familier avec la liaison Angular/Spring Security j'adapterai le code en fonction.*

__Modifications apportées au code :__
- Création d'un package `Bean` qui va contenir un bean pour l'authentification : `AuthenticationBean`
- Modification de `LoginController` : création d'un __@GetMapping__ `/logintest`.
- Légères modifications dans `ApplicationWebSecurityConfig`.


__*FRONTEND__*

*Comme indiqué ci-dessus, les modifications apportées me servent à tester le login.*

__Modifications apportées au code :__
- Création d'un component `login` : il contient le HTML de la page de login (stade primitif actuellement) et la logique dans le fichier .ts
- Création d'un component `logout` : il est pour l'instant vide mais il servira comme son nom l'indique à gérer les logout
- Création d'un service `HttpinterceptorService` : Il sert à intercepter la requête HTTP pour le login et génère un header d'authorization __Basic__
- Les méthodes suivantes ont été créées dans `AuthService` :
  - `handleLogin` : elle utilise la méthode `login` de `authservice` pour nous permettre de nous authentifier
  - `createBasicToken` qui va créer un token pour l'authentification Basic à partir du username/password
  - `registerSuccessfulLogin` qui va stocker un "item" pour la session
  - `logout` qui pour l'instant n'est pas implémentée
  - `isUserLoggingIn` qui nous sert à savoir si l'utilisateur est en train de se connecter
  - `getLoggedInUserName` qui nous sert à récupérer le login de l'utilisateur loggé
- Modification de `app.component.html` : changement de la balise, on utilise maintenant la balise <router-outlet>.

*Je suis bien évidemment un tuto pour implémenter tout ça, mon interprétation du code n'est peut-être pas la plus précise.*
*Il y'a des console.log à certains endroits du code qui me permettent de savoir ou le code "rentre" ou pas.*

---
### 29/11/2022

__*BACKEND*__

__Modifications apportées au code :__
- La méthode `submitTest` fonctionne parfaitement.
- Création d'un nouveau controller dans un nouveau package `login` pour mettre en place la page de login Angular : `LoginController`

__*FRONTEND*__

__Modifications apportées au code :__
- Création d'un component pour la page de login : `login.component.ts`
- Création d'un service pour l'authentification : `auth.service.ts`

---
### 28/11/2022

*La méthode `submitTest` a été en grande partie corrigée mais une nouvelle erreur subsiste, les réponses sont comptées en double.
Le code sera commit quand même mais cette méthode devra être totalement corrigée. Les problèmes mentionnés ci-dessous sont dus
à la manière dont j'ai géré la dernière boucle for each.*

__Modifications apportées au code :__
- Modification de la méthode `submitTest` -> Les réponses n'étaient pas comptabilisées car le code ne rentrait pas dans un "If". Le code a été corrigé.
- La méthode `submitTest` n'enregistre pas les réponses renvoyées avec le test.

---
### 24/11/2022

__Modifications apportées au code :__
- Correction de la méthode `keys` dans `MapFilterService` -> Elle ne fonctionnait pas comme nécessaire dans le cadre de la méthode `submitTest` dans `TestServiceImpl`.
- Export des logs dans un fichier externe
- Ajout de JsonViews dans `Reponse` et `Test` pour afficher les id des tests liés dans chaque réponse et les id de chaque réponse liée dans chaque test

*J'ai un léger souci avec la méthode `submitTest`. Il y'a un if qui ne s'éxecute pas qui par conséquent fait que la map des questions/réponses
du test est vide. Par conséquent aucune réponse ne se retrouve liée au test. Le statement est sûrement incorrect.*

*Le code ne sera pas commit ce soir vu que la méthode `submitTest n'est pas fonctionnelle.*

---
### 23/11/2022

__*BACKEND*__

__Modifications apportées au code :__
- Suppresion du __@ManyToMany__ entre `Test` et `Question`. Cette relation sera inutile.
- Création d'un service `MapFilterService`. Il me sert à effectuer un __.stream()__ sur les maps dans la méthode `submitTest` dans `TestServiceImpl` pour comparer le nombre de réponses des questions de chaque côté (Test/Qcm)
- La méthode `submitTest` dans `TestServiceImpl` a été __complètement modifiée__ :
  - Elle créé une Map pour les questions/réponses du qcm : une map contient une clé et des valeurs. Chaque map aura pour clé la réponse avec pour valeurs la question qui leur est liée. Pour Qcm, on vérifie que la question existe et si oui on récupère ses questions.
  - Elle créé une Map pour les questions/réponses du test (identique à ci-dessus) sauf que pour le test on vérifie que les réponses existent en plus des questions (on récupère les questions à travers les réponses pour test). On vérifie ensuite que la question existe dans la Map du Qcm, puis on récupère ses réponses. On vérifie, via un for each que chaque réponse est liée à la question spécifiée initialement.
  - Ensuite, elle vérifie que la liste des réponses renvoyée dans le test contient la réponse de cette instance du for each et qu'elle n'est pas présente dans la map du test. Si c'est le cas, elle ajoute la réponse en tant que clé et la question utilisée initialement pour récupérer tous les éléments en tant que value.
  - Finalement, on fait un for each sur chaque question présente dans la map du qcm, on récupère le nombre de réponses liées afin d'implémenter des checks de base :
    - Le nombre de réponses ne peut pas être nul
    - Le nombre de réponses renvoyées ne peut pas être supérieur au nombre de réponses liées à la question dans le qcm
  - On fait ensuite appel à la fonction `keys` de `mapFilterService` pour récupérer les questions renvoyées par l'utilisateur pour la question correspondante à celle du for each. On enregistre chaque réponse de la question dans un Set et on itère sur ce dernier. On vérifie si les réponses sont justes ou fausses et on calcule le score.


__*FRONTEND*__
 
*Afin de pouvoir tester mes component de base, il faut que j'implémente une page de login en plus d'une fonction de login.
Je dois aussi implémenter dans ce cas une fonction de logout du côté du back.*

---
### 22/11/2022

__*BACKEND*__

__Modifications apportées au code :__
- Création d'un __@ManyToMany__ entre `Test` et `Question`.
- Modification de la Query SQL `findTestByCandidat` dans `TestRepository` : la query ne pointait pas vers la colonne ID de la table Utilisateur.

*J'ai commencé à taper du code à la volée pour modifier la méthode `submitTest` dans `TestServiceImpl` pour implémenter tout les checks
nécessaires pour éviter des réponses envoyées en double ou des réponses ne correspondant pas à la question.*

---
### 21/11/2022

__*BACKEND*__

__Modifications apportées au code :__
- Modification du retour de la méthode `retrieveAllCandidatTest` dans `TestServiceImpl`. Plutôt que d'utiliser une Query SQL on récupère tout simplement les tests de l'utilisateurs par le getter.

*Je réfléchis à un correctif pour la soumission des tests par le candidat pour le check des questions/réponses.
Je pense que je vais devoir modifier le modèle `Test` en créant un __@ManyToMany__ de `Test` vers `Question`
et peut-être effectuer les vérifications à travers cette relation.*

__*FRONTEND*__

__Modifications apportées au code :__
- Création de `homepage.model.ts` => Ce component nous servira à récupérer les attributs de base de l'utilisateur et les afficher sur une page d'accueil
- Ajout de code HTML dans les fichiers `xxx.component.html / xxx-list.component.html` pour afficher les éléments souhaités des objets de chaque classe. Je vais en faire de même dans les composants `single-xxx.component.ts` qui eux serviront à afficher un seul élément sur la page.
- Création de `homepage.service.ts`. Ce service pour l'instant ne contient que les URL pour récupérer des éléments (comme tous les autres services pour le moment)

*Les URL POST/PUT/DELETE seront créés une fois que les GET seront fonctionnels et que les components seront 100% OK.
Les GET vont me permettre dans un premier temps de gérer le visuel.*

---
### 20/11/2022

__*FRONTEND*__

__Modifications apportées au code:__
- Création de plusieurs components :
  - `Single-XXX.component` : servira à afficher un seul objet d'une classe sur une page à part, par exemple pour Utilisateur ce component servira à afficher sur la page utilisateur/{userid} ce seul utilisateur avec des éléments bien définis
  - `XXX-component` : servira à afficher un seul objet d'une classe dans une liste, par exemple un utlisateur dans une liste d'utilisateurs avec des éléments définis
  - `XXX-list.component` : servira à afficher une liste d'objets d'une classe.
- Création de `app-routing.module`. Les routes ont été créées mais devront sûrement être modifiées.
- Les requêtes __GET__ ont été créées dans chaque service de chaque entité pour récupérer les objets de chacun. Cela me permettra de mettre en place mes components et les vues de base dans un premier temps. Les requêtes put/post/delete viendront par la suite.
- Création d'observables pour récupérer les listes via les requêtes __GET__ dans chaque component `xxx-list`.

---
### 17/11/2022

__*FRONTEND*__

__Modifications apportées au code :__
- Création d'un nouveau component : `qcmlist`. Il servira à récupérer tout les qcm à partir du service et à les afficher dans le component

*J'ai fait quelques bricoles comme compléter les models et en allant ajouter des imports dans `QcmComponent`.
Je vais créer les components pour chaque model et m'assurer que tout est ok dans un premier temps, puis je mettrais
le routing en place à travers mes services pour récupérer les éléments du backend.*

---
### 16/11/2022

__*BACKEND*__

*Le logging fonctionne, on peut lire dans l'IDE mes messages de log personnalisés. Maintenant il ne me reste plus qu'à voir comment
l'exporter vers un fichier texte.*

*Toutes les fonctionnalités ont été retestées et tout fonctionne. J'aimerais cependant modifier la manière dont les réponses
choisies par l'utilisateur sont analysées pour déterminer si elles sont correctes ou pas. Il faudrait pouvoir récupérer les questions
de chaque qcm puis les réponses de chaque question, comparer les ID de la réponse renvoyée et des réponses de la question,
et répéter l'opération pour chaque question. Il faudra effectuer des modifications de logique dans certaines parties du code.*

__*FRONTEND*__

- Création des services pour chaque entité.
- Création des entités dans le package models

*J'ai du mal à me remettre dans le peu d'angular que j'ai vu, je sais ce que je dois faire à peu près mais je ne sais pas comment.*

---
### 15/11/2022

__*BACKEND*__

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

__*FRONTEND*__

*Création des models pour chaque entité. Vérifier les attributs de chaque objet surtout les attributs faisant références à une autre entité pour assurer les relations
entre eux.*

__Sinon, concernant SonarQube, je l'ai téléchargé et je peux le lancer sur ma VM Debian. Il se lance, je peux m'y connecter cependant il faut que j'arrive à paramétrer
la connexion à la BDD de mon laptop. Cela est simple cependant il faut que je voie comment je peux modifier le fichier de config et si je dois installer des driver/
dépendances sur ma VM (il faut que je dégage windows de mon laptop et que je me mette sous linux, ça ne m'apportera que des bénéfices :)__

---
### 13/11/2022

__*FRONTEND*__

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
