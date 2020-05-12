movie("Interview With The Vampire").
movie("Mission Impossible").
movie("Spiderman").
movie("Focus").
movie("Interstellar").
movie("The wolf of wall street").



actorActress(tomCruise,"Mission Impossible" ).
actorActress(tomCruise,"Interview With The Vampire" ).
actorActress(bradPitt, "Interview With The Vampire").
actorActress(kirstenDunst, "Interview With The Vampire").
actorActress(kirstenDunst, "Spiderman").
actorActress(willSmith, "Focus").
actorActress(margotRobbie, "Focus").
actorActress(margotRobbie, "The wolf of wall street").
actorActress(mattewMcConaughew, "Interstellar").
actorActress(leonardoDiCaprio, "The wolf of wall street").

client(alan).
client(alfonso).
client(alina).
client(gerardo).


likes(gerardo, margotRobbie).
likes(gerardo, tomCruise).
likes(gerardo, bradPitt).
likes(alan, willSmith).


doesNotLike(CLIENT, MOVIE):-
   -likes(CLIENT, ACTORACTRESS),
   actorActress(ACTORACTRESS, MOVIE).


interest(CLIENT, MOVIE):-
   likes(CLIENT, ACTORACTRESS),
   actorActress(ACTORACTRESS, MOVIE),
   not doesNotLike(CLIENT, MOVIE).









