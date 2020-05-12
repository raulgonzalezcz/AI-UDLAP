woman(maria).
woman(mariaDelCarmen).
woman(regina).
woman(amparo).
woman(mariaAmparo).

man(julian).
man(antonio).
man(jesus).
man(gerardo).
man(taro).

%%Julian is Maria´s parent
parent(maria, julian).
parent(regina, julian).
parent(maria, mariaDelCarmen).
parent(regina, mariaDelCarmen).
parent(mariaAmparo, amparo).
parent(jesus, amparo).
parent(gerardo, amparo).
parent(mariaAmparo, antonio).
parent(jesus, antonio).
parent(gerardo, antonio).
parent(taro, gerardo).

father(CHILD, PARENT):-
	parent(CHILD,PARENT),
	man(PARENT).

mother(CHILD, PARENT):-
	parent(CHILD,PARENT),
	woman(PARENT).

grandfather(CHILD, GRANDPARENT):-
	parent(CHILD, PARENT),
	father(PARENT, GRANDPARENT),
	man(GRANDPARENT).

grandmother(CHILD, GRANDPARENT):-
	parent(CHILD, PARENT),
	mother(PARENT, GRANDPARENT),
	woman(GRANDPARENT).

%%%% M1  is the brother of M2
brothers(M1, M2):-
	parent(M1, PARENT),
	parent(M2, PARENT),
	man(M1),
	M1 != M2.

sisters(W1, W2):-
	parent(W1, PARENT),
	parent(W2, PARENT),
	woman(W1),
	W1 != W2.
