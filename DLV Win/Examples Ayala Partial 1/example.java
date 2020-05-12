%% Deductive database in DLV
% Disjunctive Logic V
% DLV System
% http://www.dlvsystem.com/
%
% DOWNLOAD:
% http://www.dlvsystem.com/dlv/

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% facts
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%predicate arity 0
iAmHungry.

%predicates arity 1
man(john).
man(george).
man(paul).
man(ringo).
man(joseph).
man(richard).
man(dominic).

woman(shakira).
woman(maria).
woman(selena).

%predicates arity 2
likes(dominic, selena).
likes(paul, shakira).
likes(maria, george).
likes(george, maria).
likes(maria, paul).

%negations
-likes(selena, dominic).
-likes(paul, maria).
-likes(shakira, dominic).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% RULES
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

haveABreak:-
    iAmHungry.

dateProposal(X,Y):-
    man(X),
    woman(Y),
    likes(X, Y),
    likes(Y, X).

blindDateProposal(X,Y):-
    man(X),
    woman(Y).


giveAChanceDateProposal(X,Y):-
    man(X),
    woman(Y),
    likes(X, Y),
    -likes(Y, X).

giveAChanceDateProposal(X,Y):-
    man(X),
    woman(Y),
    -likes(X, Y),
    likes(Y, X).



% particular-affirmative

somebodyLikesHim(Y):-
    man(Y),
    woman(X),
    likes(X, Y).




% universal-negative
% based on the absence of evidence
% of the particular-affirmative

nobodyLikesHim(X):-
    man(X),
    not somebodyLikesHim(X).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% About the two forms of negation in DLV
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% the "-" negation means:
% "we have evidence that it is false"%
% the "not" negation means:
% "we have no evidence that it is true"

