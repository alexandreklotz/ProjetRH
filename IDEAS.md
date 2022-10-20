La classe "Test" qui hérite de la classe "Qcm" n'est pas créée dans la BDD.
On a à la place une colonne "dtype" qui je le pense va nous permettre de déterminer si il s'agit d'un
qcm ou d'un test. Mais je pense que ça va mettre le bordel.
Je dois tester tout ça et voir si je dois faire une réplique de qcm mais sans héritage.

Il va aussi falloir trouver une solution pour les réponses, comment est-ce que je peux gérer un "test" renvoyé
par l'utilisateur et ne récupérer que les réponses sélectionnées par ce dernier pour les comparer ensuite à la réponse
créée dans la BDD et voir si elle est correcte ?

Pour utilisateur, création d'un champ mot de passe ? On peut imaginer deux scénarios :
- Le candidat reçoit un email et accède à la page du test. Avant de commencer, il renseigne son nom, prénom, adresse mail et son mot de passe. L'utilisateur est donc créée et peut passer le test.
- Le recruteur créée un compte pour chaque candidat (peu très très chronopage/laborieux si beaucoup de candidats).
Dans le cas de la première solution, il faudrait spécifier qu'il s'agit d'un candidat non enregistré pour déclencher le processus d'inscription lors du passage du test (je pense).

Pour gérer la classe Test (anciennement Score), j'ai pensé à :
plutôt qu'à créer une classe identique à Qcm via héritage ou duplication, cette classe contiendrait uniquement un ID et une variable 'Score'.
Cette classe serait liée à l'utilisateur et contiendrait uniquement son score à ce qcm. Lors de l'envoi du test par
le candidat une fois fini, un service dédié au score ferait une liste des réponses sélectionnées par ce dernier
en les comparerait à celles du qcm via leur ID en utilisant le booléen isCorrect. (grosso modo)