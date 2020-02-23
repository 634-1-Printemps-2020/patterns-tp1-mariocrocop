package metier;

import outils.Labyrinthe;

import java.util.*;

public class PyRat {

    private int labyWidth;
    private int labyHeight;
    private List<Point> fromages; //Liste de base des fromages
    private Map<Point, List<Point>> labyrinth; //Hash Map => Labyrinth
    private Set<Point> fromagesSet; //Liste des fromages sous form de Hash Set
    private Map<Point, Set<Point>> labyrinthSet; //Hash Map => Labyrinth


    /* Méthode appelée une seule fois permettant d'effectuer des traitements "lourds" afin d'augmenter la performace de la méthode turn. */
    public void preprocessing(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        this.labyWidth = labyWidth;
        this.labyHeight = labyHeight;
        this.fromages = fromages;
        this.labyrinth = laby;
        this.fromagesSet = new HashSet<Point>(fromages); //Création de la copie sous forme de HashSet
        //for(Point frm: fromages){this.fromagesSet.add(frm);}
        this.labyrinthSet = new HashMap<>(); //Création de la copie du Labyrinth sous forme de HashMap
        for(Point key: labyrinth.keySet()){ //Boucle dans le Labyrinth
            Set<Point> values = new HashSet<>(laby.get(key)); //Création d'un Set contenant les clés
            labyrinthSet.put(key, values); //On ajoute le Set dans le nouveau Labyrinth sous forme de HashMap avec un Set
        }
    }

    /* Méthode de test appelant les différentes fonctionnalités à développer.
        @param laby - Map<Point, List<Point>> contenant tout le labyrinthe, c'est-à-dire la liste des Points, et les Points en relation (passages existants)
        @param labyWidth, labyHeight - largeur et hauteur du labyrinthe
        @param position - Point contenant la position actuelle du joueur
        @param fromages - List<Point> contenant la liste de tous les Points contenant un fromage. */
    public void turn(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point pt1 = new Point(2,1);
        Point pt2 = new Point(3,1);
        System.out.println((fromageIci(pt1) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt1);
        System.out.println((fromageIci_EnOrdreConstant(pt2) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt2);
        System.out.println((passagePossible(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println("Liste des points inatteignables depuis la position " + position + " : " + pointsInatteignables(position));
    }

    /* Regarde dans la liste des fromages s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci(Point pos) {
        return this.fromages.contains(pos); //Effectue une boucle en utilisant le equals
    }

    /* Regarde de manière performante (accès en ordre constant) s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci_EnOrdreConstant(Point pos) {
        return this.fromagesSet.contains(pos); //Sous forme de HashSet le contains n'effectue pas de boucle, une fonction hashCode est implémenté dans Point
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a ».
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible(Point de, Point a) {
        if(labyrinth.containsKey(de)){ //Si le labyrith possède la clé "de"
            List<Point> p = labyrinth.get(de); //On get la position De
            return p.contains(a); //On vérifie s'il est relié à la position "a"
        }
        return false;
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a »,
        mais sans devoir parcourir la liste des Points se trouvant dans la Map !
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible_EnOrdreConstant(Point de, Point a) {
        if(this.labyrinthSet.containsKey(de)){ //Si le labyrith possède la clé "de"
            Set<Point> p = labyrinthSet.get(de); //On get la position De
            return p.contains(a); //On vérifie s'il est relié à la position "a"
        }
        return false;
    }

    /* Retourne la liste des points qui ne peuvent pas être atteints depuis la position « pos ».
        @return la liste des points qui ne peuvent pas être atteints depuis la position « pos ». */
    private List<Point> pointsInatteignables(Point pos) {
        List<Point> pointsInatteignable = new ArrayList<>(); //List
        //Boucle Labyrithn
        for(Point key: labyrinthSet.keySet()){
            //System.out.println(key); //Clé
            //System.out.println(labyrinthSet.get(key)); //Valeur
            if (!key.equals(pos)){ //Si la clé ne correspond pas on ajoute à la liste
                pointsInatteignable.addAll(labyrinthSet.get(key));
            }
        }
        return pointsInatteignable;
    }
}