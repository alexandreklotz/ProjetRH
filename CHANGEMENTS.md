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
