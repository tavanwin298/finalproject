import java.util.*;
import java.io.*;

// Card interface for abstraction and polymorphism
interface Card {
    void play(Game game);
    String getId();
}

// DecisionCard - player makes a choice
class DecisionCard implements Card {
    private String id;
    private String prompt;
    private Map<String, String> options;

    public DecisionCard(String id, String prompt, Map<String, String> options) {
        this.id = id;
        this.prompt = prompt;
        this.options = options;
    }

    public void play(Game game) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        for (String option : options.keySet()) {
            System.out.println("- " + option);
        }
        String choice = scanner.nextLine();
        String nextId = options.getOrDefault(choice, null);
        if (nextId != null) game.queueCardById(nextId);
        else System.out.println("Invalid choice. Skipping.");
    }

    public String getId() { return id; }
}

// RandomCard - draws next card based on probability
class RandomCard implements Card {
    private String id;
    private Map<String, Double> outcomes;

    public RandomCard(String id, Map<String, Double> outcomes) {
        this.id = id;
        this.outcomes = outcomes;
    }

    public void play(Game game) {
        double r = Math.random();
        double sum = 0.0;
        for (Map.Entry<String, Double> entry : outcomes.entrySet()) {
            sum += entry.getValue();
            if (r <= sum) {
                game.queueCardById(entry.getKey());
                return;
            }
        }
    }

    public String getId() { return id; }
}

// Character class for combat system
class Character {
    String name;
    int attack;
    int defense;
    int hp;

    public Character(String name, int attack, int defense, int hp) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
    }

    public boolean isAlive() {
        return hp > 0;
    }
}

// BattleCard - turn-based combat
class BattleCard implements Card {
    private String id;
    private Character enemy;
    private String winId;
    private String loseId;

    public BattleCard(String id, Character enemy, String winId, String loseId) {
        this.id = id;
        this.enemy = enemy;
        this.winId = winId;
        this.loseId = loseId;
    }

    public void play(Game game) {
        Character player = game.getPlayer();
        System.out.println("Battle Start: " + player.name + " vs " + enemy.name);
        while (player.isAlive() && enemy.isAlive()) {
            enemy.hp -= Math.max(0, player.attack - enemy.defense);
            if (!enemy.isAlive()) break;
            player.hp -= Math.max(0, enemy.attack - player.defense);
        }
        System.out.println(player.isAlive() ? "You won!" : "You lost!");
        game.queueCardById(player.isAlive() ? winId : loseId);
    }

    public String getId() { return id; }
}

// Game class for managing logic
class Game {
    private Queue<Card> deck = new LinkedList<>();
    private Map<String, Card> cardMap = new HashMap<>();
    private Character player;

    public Game() {
        this.player = new Character("Hero", 10, 5, 30);
    }

    public Character getPlayer() { return player; }

    public void addCard(Card card) {
        cardMap.put(card.getId(), card);
    }

    public void queueCardById(String id) {
        Card card = cardMap.get(id);
        if (card != null) deck.add(card);
    }

    public void makeSample() {
        Map<String, String> options = new HashMap<>();
        options.put("Enter the cave", "battle1");
        options.put("Go around", "random1");
        addCard(new DecisionCard("start", "You're at a fork. What do you do?", options));

        Character goblin = new Character("Goblin", 6, 3, 20);
        addCard(new BattleCard("battle1", goblin, "win1", "lose1"));

        Map<String, Double> outcomes = new HashMap<>();
        outcomes.put("win1", 0.5);
        outcomes.put("lose1", 0.5);
        addCard(new RandomCard("random1", outcomes));

        addCard(new DecisionCard("win1", "You won the battle! Continue?", Map.of("Yes", "start")));
        addCard(new DecisionCard("lose1", "You lost... Retry?", Map.of("Retry", "start")));

        queueCardById("start");
    }

    public void play() {
        while (!deck.isEmpty()) {
            Card current = deck.poll();
            current.play(this);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.makeSample();
        game.play();
    }
}
