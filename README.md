# finalproject

Card Clash is a text-based, card-driven adventure game designed to demonstrate fundamental Object-Oriented Programming principles. The game revolves around a deck of cards that trigger events, decisions, or combat, each leading to a branching narrative based on user interaction.

core stuff:

Inheritance: Three card types (DecisionCard, RandomCard, BattleCard) extend from a common interface (Card).
Encapsulation: Card and character data are private and accessed through methods.
Polymorphism & Abstraction: The Card interface defines an abstract play() method that is implemented differently by each card type.
Well-structured Data: Uses queues, hash maps, and lists to manage game flow and card retrieval.

Users:
Players: Engage in a dynamic, branching card adventure.

Purpose:
Demonstrate OOP skills in a fun, interactive format.
Build expandable game logic for future improvements.

Code Summary:

Card Interface
Define play(game) method for all cards
Define getId() to uniquely identify a card

DecisionCard
Prompt the player with options
Take input
Based on input, queue the next card using Game

RandomCard
Use random chance to select one of multiple possible next cards
BattleCard

Simulate a turn-based battle between the player and an enemy

If player wins: queue win card

If player loses: queue lose card

Character
Attributes: name, attack, defense, hp
Method: isAlive() to check health status

Game
Maintain a deck (queue of cards) and discard pile
Store all cards in a hashmap for quick ID lookup
Use queueCardById(id) to move a card into play
makeSample() builds a basic deck
play() loop runs through each card drawn from the queue
