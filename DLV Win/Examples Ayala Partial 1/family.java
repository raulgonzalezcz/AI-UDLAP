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


% Y is the father of X
father(X,Y):-
   parent(X,Y),
   man(Y).

% Y is the grandfather of X
grandFather(X,Y):-
   parent(X, ANYBODY),
   parent(ANYBODY, Y),
   man(Y).






















