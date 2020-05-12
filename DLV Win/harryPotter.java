%Facts
person(harry).
person(ron).
person(hermione).
person(hagrid).
person(draco).
person(voldemort).
person(ginny).
person(snape).
person(dobby).
person(dumbledore).

getAlongWell(harry, ron).
getAlongWell(harry, hermione).
getAlongWell(draco, voldemort).
getAlongWell(draco, snape).

-getAlongWell(harry, voldemort).
-getAlongWell(harry, draco).
-getAlongWell(dobby, voldemort).
-getAlongWell(draco, hermione).
-getAlongWell(draco, ron).
-getAlongWell(voldemort, hermione).
-getAlongWell(voldemort, ron).

table(tableOne).
table(tableTwo).

numberOfChairsAtTable(tableOne, 5).
numberOfChairsAtTable(tableTwo, 5).

%Disjunctive Rule
%A given peron, or sits or doesn´t sit in a given table (OR)
sits(PERSON, TABLE) v
doesNotSit(PERSON, TABLE):-
	person(PERSON),
	table(TABLE).

canNotSitTogether(P1, P2):-
	-getAlongWell(P1, P2).

canNotSitTogether(P1, P2):-
	-getAlongWell(P2, P1).

%Constraint: Its imposible a world where two persos dont get along well and one sits at a given table
%and the other sits at the same table

:- canNotSitTogether(P1, P2),
	sits(P1, TABLE),
	sits(P2, TABLE).

:-table(TABLE),
numberOfChairsAtTable(TABLE, NUMBERCHARIS), not #count{ PERSON: sits(PERSON, TABLE)} <= NUMBERCHARIS.

:-person(PERSON), not #count {TABLE: sits(PERSON, TABLE) } =1.