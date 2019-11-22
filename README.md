# Explosive card game
Author - Nick Karaolis

## How to play
Simply type `sbt run` via a terminal and the game will start.
It is all console based input/output; messages appear in the console and player input is done via the console.

## Assumptions made
1. The number of each type of card does not need to be chosen, it has been baked into the code rather than allowing the user to provide the value
2. Since the random shuffle is non deterministic for lists greater than 2, it could in theory return the deck in the same order

## Further additions
1. Add a shuffle card which the player can draw and use to shuffle the deck at any point