Voila comment j'avais imaginé la méthode submitTest pour la soumission des tests par le candidat et la vérification des réponses :
J'ai du modifier la conception de la classe Test et supprimer l'héritage entre Qcm et Test. J'ai du créer des nouvelles relations, lire CHANGEMENTS.md.

Logique :

    public Test submitTest(Test test){

	Double scoreTemp = utilisateur.getGlobalScore();
	
	Optional<Qcm> qcmTest = qcmRepository.findById(test.getQcm().getId());
	if(qcmTest.isEmpty()){
		return erreur;
	}
	List<Reponse> qcmReponses = qcmTest.get().getQuestions().getReponses();
	for(Reponse reponse : qcmReponses){
		boolean isCorrect = reponseRepository.findCorrectAnswerById(reponse.getId());  /////Voir pour passer deux paramètres, QcmId en plus pour sécuriser.
		if(isCorrect == true){
			test.setScore+=reponse.getPoints(); //pas correct mais l'idée est la
			scoreTemp+=reponse.getPoints();
		}
	}
	utilisateur.setGlobalScore(scoreTemp);
    }

Pour cela il me fallait qu'un objet de type Test corresponde au JSON suivant :

    {
	"id" : "xxxxxxx",
	"utilisateur" : {
		"id" : "xxxxxx"
	    },
	"qcm" : {
		"id" : "xxxxxx"
	    },
	"reponses" : {
		"id" : "xxxxxx"
	    }
    }

Le résultat final est assez proche.

---
Mettre en place un mécanisme d'ajout au fur et à mesure de réponses à une question et ensuite d'ajout d'une question au qcm.
Si la question n'a pas deux réponses minimum, elle ne sera pas liée au QCM.

---
Comment gérer la création d'un nouveau test ? On envoie un QCM à partir duquel on créé un test ?
Niveau front, comment gérer ? On récupère la liste de tous les qcm et on sélectionne celui qu'on veut assigner à un candidat pour le tester
et la création du test se fait à ce moment la ? Y réfléchir.

---
Il va falloir trouver une solution pour les réponses, comment est-ce que je peux gérer un "test" renvoyé
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