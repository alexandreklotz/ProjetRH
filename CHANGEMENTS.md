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