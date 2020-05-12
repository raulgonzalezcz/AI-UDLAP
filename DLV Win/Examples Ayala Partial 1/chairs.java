
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% FACTS
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

person(john).
person(yoko).
person(paul).
person(george).
person(ringo).
person(linda).

table(tableOne).
table(tableTwo).
table(tableThree).

numberOfChairsAtTable(tableOne,2).
numberOfChairsAtTable(tableTwo,2).
numberOfChairsAtTable(tableThree,2).

getAlongWell(ringo,george).
getAlongWell(john,ringo).
getAlongWell(john,yoko).
getAlongWell(paul,linda).

-getAlongWell(linda, yoko).



%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% RULES
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%  Disjunctive Rule
% A given person, or sits or does not sit in a given table

sits(PERSON,TABLE) v doesNotSit(PERSON,TABLE) :-
    person(PERSON),
    table(TABLE).




canNotSitTogether(X,Y):-
    -getAlongWell(X,Y).

canNotSitTogether(X,Y):-
    -getAlongWell(Y,X).

%  Constraint
%  It is impossible a world where
%  two persons do not get along well
%  and one sits at a given table
%  and the other sits at the same table

:- canNotSitTogether(PERSON,OTHERPERSON),
    sits(PERSON,TABLE),
    sits(OTHERPERSON,TABLE).



%  Constraint
%  It is impossible a world where
%  for a given table
%  with a number of chairs
%  the number of persons is not less or equal
%  than the number of chairs.
%
% We can not have more persons than chairs at a table.

:- table(TABLE),
   numberOfChairsAtTable(TABLE,NUMBEROFCHAIRS),
   not #count { PERSON: sits(PERSON,TABLE) } <= NUMBEROFCHAIRS.



%  Constraint
%  It is impossible a world where
%  the number of tables where a person sits
%  is not ONE
%
%  We can not have a given person at more than one table

:- person(PERSON),
    not #count{TABLE : sits(PERSON,TABLE)} = 1.



