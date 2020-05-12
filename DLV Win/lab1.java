%Recomendation

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% facts
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

movie("Interview With The Vampire").
movie("Mission Impossible").
movie("Spiderman").
movie("Focus").
movie("Interstellar").
movie("The wolf of wall street").

actorActress(tomCruise,"Interview With The Vampire").
actorActress(tomCruise,"Mission Impossible").
actorActress(bradPitt,"Interview With The Vampire").
actorActress(kirstenDunst,"Interview With The Vampire").
actorActress(kirstenDunst,"Spiderman").
actorActress(willSmith,"Focus").
actorActress(margotRobbie,"Focus").
actorActress(matthewMcConaughey,"Interstellar").
actorActress(margotRobbie,"The wolf of wall street").
actorActress(leonardoDiCaprio,"The wolf of wall street").

client(alan).
client(alfonso).
client(alina).
client(gerardo).

likes(gerardo,"The wolf of wall street"). %He visited the Web Site 2 or more times
likes(alan,willSmith).
likes(gerardo, margotRobbie).
likes(gerardo, tomCruise).
-likes(gerardo, bradPitt).

doesNotLike(CLIENT, MOVIE):-
	-likes(CLIENT, ACTORACTRESS),
	actorActress(ACTORACTRESS, MOVIE).

interest(CLIENT, MOVIE):-
	likes(CLIENT, ACTORACTRESS),
	not doesNotLike(CLIENT, MOVIE),
	actorActress(ACTORACTRESS, MOVIE). 
