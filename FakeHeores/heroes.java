package FakeHeores;
import CaractEtEspece.Caract;
import Créatures.Creature;
import Stats.CapaciteArmure;
import Attaque.Attack;
import Stats.xp;
import CombatSystem.EffectsTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

public class heroes {
    private int id;
    private String nom;
    private Stats.Health pv;
    private Caract caract;
    private CapaciteArmure capaciteArmure;
    private Attack attack;
    private xp xp;
    private List<EffectsTemplate> activeEffects = new ArrayList<>();
    private boolean isStunned;
    // Constructeur
    public heroes(int id, String nom, int pv, Caract caract, CapaciteArmure capaciteArmure) {
        this.id = id;
        this.nom = nom;
        this.pv = new Stats.Health(pv);
        this.caract = caract;
        this.capaciteArmure = capaciteArmure;
        this.attack = new Attack();
        this.xp = new xp();
    }

    public void addEffect(EffectsTemplate effect) {
        activeEffects.add(effect);
        effect.applyEffect(this);
        System.out.println(effect.getName() + " a été appliqué à " + nom + " pour " + effect.getDuration() + " tours.");
    }

    public void effectChecker(){
        Iterator<EffectsTemplate> iterator = activeEffects.iterator();
        while(iterator.hasNext()){
            EffectsTemplate effect = iterator.next();

            effect.onTurnStart(this);
            if(effect.tick()){
                effect.onRemove(this);
                System.out.println("L'effet" + effect.getName() + " est retiré");
                iterator.remove();
            }
        }
    }

    public boolean stunnedStatus(){
        return isStunned;
    }

    public void isStunned(){
        isStunned = true;
    }

    public void isNotStunned(){
        isStunned = false;
    }

    /**
     * Méthode statique pour générer la liste de héros directement depuis la classe
     */
    public static List<heroes> genererListeDeHeros() {
        List<heroes> liste = new ArrayList<>();
        Random random = new Random();
        
        // Banque de noms pour remplir la liste automatiquement
        String[] nomsExemples = {"Arthur", "Lancelot", "Merlin", "Guenièvre"};

        for (int i = 0; i < nomsExemples.length; i++) {
            int idUnique = i + 1;
            String nomHero = nomsExemples[i];
            // Génère un nombre entre 1 et 30
            int pvAleatoire = random.nextInt(30) + 1; 

            liste.add(new heroes(idUnique, nomHero, pvAleatoire, new Caract(10, 10, 10, 10, 10, 10, "Humain", 10, 10, 10, 10, 10, 10), new CapaciteArmure(10)));
        }

        return liste;
    }

    public void takeDamage(int damage) {
        pv.takeDamage(damage);
        if (pv.getCurrentHealth() <= 0) {
            System.out.println(nom + " est mort !");
            return;
        }
        System.out.println(nom + " a subi " + damage + " points de dégâts. PV restants : " + pv);
    }

    public void addXp(int amount) {
        xp.addXp(amount);
    }

    public void actionChoice(ArrayList<Creature> creatures) {
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1. Attaquer");
        System.out.println("2. Défendre");

        // Suppression du try () pour éviter de fermer System.in
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                attack.targetChoice(creatures);
                break;
            case 2:
                addEffect(new CombatSystem.DefenseEffect(1, 2)); // Applique l'effet de défense
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
                actionChoice(creatures);
                break;
        }
    }


    // Getters (Optionnel, utile pour récupérer les données plus tard)
    public int getId() { return id; }
    public String getNom() { return nom; }
    public int getPv() { return pv.getCurrentHealth(); }
    public Caract getCaract() { return caract; }
    public CapaciteArmure getCapaciteArmure() { return capaciteArmure; }
    @Override
    public String toString() {
        return "Héros [ID=" + id + ", Nom=" + nom + ", PV=" + pv.getCurrentHealth() + "]";
    }
}
