# Game Matcher App (DubHacks 2021)

App hackathon link: https://devpost.com/software/game-matching

App demo: https://youtu.be/BX5i7_KAPy8

## Inspiration
We all agreed that playing games online during the pandemic helped us stay connected to our friends, even when we couldn't see each other in person. However, when it comes to connecting with new people, online lobbies and matching can be hit-or-miss: sometimes you get really fun lobbies that you get along great with, and other times, you just want to leave as fast as you can. We wanted to create a react app that would allow you to match with other players based on your preferences and hobbies, which we felt would make it easier and more rewarding to connect with other players.

## What it does
This app includes a series of forms which allow you to create an account for playing the game, log into said account, save your matching preferences, and match with other players to play the game.

## How we built it
We implemented a MySQL database to store and manage user info (id/usernames, their ages, skill level, and hobbies, as well as their preferences). Then, we created a matching algorithm that would keep track of all queued players (waiting to be matched), read preferences and user info from the database, and return a match that would be the most aligned to both users' preferences. Next, we created a react app with three main components: a main class, which would cycle between the different UIs (creating a username/account, updating preferences/waiting for matching, and playing the actual game), and classes for each of the UI views. Finally, we used a spark server to communicate between the local game and the server, which includes the player matching algorithm and the MySQL database.

## Challenges we ran into
The biggest challenge we ran into was learning how to use react. Prior to this project, we all had little no no experience with react and spark, so we were constantly looking up documentation and references to understand how to implement what we wanted. Another big challenge we faced was actually programming a game that could be played in multiplayer (we didn't manage to finish this by the deadline). By the time we figured out how to create a react app that would work with a spark server, we didn't have enough time to figure out how we could make an interactive game that both players could modify/manipulate instantaneously.

## Accomplishments that we're proud of
One accomplishment we're really proud of was creating an app that actually managed to communicate to the server from multiple users' ends. It was challenging to figure out how all of the pieces worked together to communicate back and forth, so seeing this finally work was really satisfying.

## What we learned
We learned a lot about how servers work, and about how local machines can communicate with each other through a server. We also learned a lot about react and typescript, and how the different components work with each other to create the final app.

## What's next for Game Matching
We want to implement a multiplayer game that can be played after the connection takes place, as that would fulfill the original vision we had in mind for this project. Additionally, we want to make the UI look better (both more intuitive/effective and nicer to look at).
