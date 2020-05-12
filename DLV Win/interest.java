team("Barcelona").
team("Toluca").
team("Lobos BUAP").
team("Real Madrid").
team("America").
team("Chelsea").


playerPlays(leonelMessi,"Barcelona" ).
playerPlays(luisSuarez,"Barcelona" ).
playerPlays(rubensZambueza, "Toluca").
playerPlays(alfredoTalavera, "Toluca").
playerPlays(cristianoRonaldo, "Real Madrid").
playerPlays(keylorNavas, "Real Madrid").
playerPlays(oribePeralta, "America").
playerPlays(michaelArroyo, "America").
playerPlays(rodrigoGodinez, "Lobos BUAP").
playerPlays(davidLuiz, "Chelsea").

client(alan).
client(alfonso).
client(alina).
client(gerardo).


likes(gerardo, leonelMessi).
likes(gerardo, alfredoTalavera).
likes(gerardo, davidLuiz).
likes(alan, oribePeralta).


doesNotLike(CLIENT, TEAM):-
   -likes(CLIENT, PLAYER),
   playerPlays(PLAYER, TEAM).


interest(CLIENT, TEAM):-
   likes(CLIENT, PLAYER),
   playerPlays(PLAYER, TEAM),
   not doesNotLike(CLIENT, TEAM).









