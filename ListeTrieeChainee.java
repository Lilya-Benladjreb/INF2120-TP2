import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ListeTrieeChainee<T extends Comparable> implements IListeTriee<T> {

    private Maillon<T> elements;
    private int nbElements;
    private Maillon<T> position;

    public ListeTrieeChainee() {
        this.elements = null;
        this.nbElements = 0;
        this.position = null;
    }


    /**
     * <p>Ajoute l'element donne dans cette liste (si possible), en respectant
     * l'ordre croissant des elements. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si element est null, l'ajout n'est pas effectue, et la methode leve une
     * exception NullPointerException.</li>
     *
     * <li>Si element n'est pas null et qu'il existe deja dans cette liste, l'ajout
     * n'est pas effectue  (doublons interdits), et la methode retourne false
     * pour le signaler.</li>
     *
     * <li>Si l'ajout est effectue, la methode retourne true, et l'element courant
     * devient l'element ajoute (la position courante est deplacee sur l'element
     * ajoute).</li>
     * </ul>
     *
     * @param element l'element a ajouter dans cette liste.
     * @return true si l'ajout est effectue, false sinon.
     * @throws NullPointerException si element est null.
     */
    //Methode Fonctionnelle
    @Override
    public boolean ajouter(T element) throws NullPointerException {
        boolean ajouter = false;

        Maillon<T> nouveau = new Maillon<>(element);
        T infoTmp;

        //Traitement de l'élément si celui-ci est null
        if (element == null) {
            throw new NullPointerException("L'élement donné ne peut être nul, merci!");
        }

        //Ajout de l'element dans la liste
        if (!elementExiste(element)) {
            nbElements++;
            position = nouveau;
            ajouter = true;

            if (elements == null) {
                //Ajout en début de liste (liste vide)
                elements = nouveau;
            } else {
                //Ajout dans une liste non-vide
                Maillon<T> precedent = null;
                Maillon<T> suivant = elements;

                boolean positionTrouvee = false;
                while (suivant != null && !positionTrouvee) {
                    //Trouver avant qui mettre le nouvel élément
                    if (suivant.info().compareTo(element) > 0) {
                        positionTrouvee = true;
                    }
                    //Initier un precedent
                    if (!positionTrouvee) {
                        precedent = suivant;
                        suivant = suivant.suivant();
                    }

                }

                if (precedent != null) {
                    // il y a un précédent avant l'élément, l'enregistrer comme suivant
                    precedent.modifierSuivant(nouveau);
                } else {
                    // il n'y a aucun précédent, l'élément est le premier
                    elements = nouveau;
                }

                if (suivant != null) {
                    // l'élément n'est pas le dernier, enregistrer son suivant.
                    nouveau.modifierSuivant(suivant);
                }
            }
        }

        return ajouter;
    }

    /**
     * <p>Ajoute les elements de autreListe dans cette liste, en conservant l'ordre
     * croissant. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si autreListe est null, cette liste n'est pas modifiee, et la methode
     * leve une exception NullPointerException.</li>
     *
     * <li>Lorsqu'un element de autreListe existe deja dans cette liste,
     * celui-ci n'est pas ajoute (doublons interdits).</li>
     *
     * <li>Apres l'appel de cette methode, si cette liste n'est pas vide,
     * la position courante est placee sur le premier element de cette liste
     * (le premier element devient l'element courant).</li>
     *
     * <li>Apres l'appel de cette methode, les elements de autreListe n'ont pas
     * ete modifies, mais la position courante peut avoir ete modifiee.</li>
     * </ul>
     * <pre>
     * Exemple : soit
     *             cette liste = [4, 5, 7, 10, 11, 23],
     *             et autreListe = [3, 6, 7, 9],
     *
     *           apres l'appel ajouter(autreListe) :
     *           cette liste = [3, 4, 5, 6, 7, 9, 10, 11, 23] (elt courant = 3).
     * </pre>
     *
     * @param autreListe la liste contenant les elements a ajouter dans cette liste.
     * @return le nombre d'elements ajoutes dans cette liste (0 si aucun).
     * @throws NullPointerException si autreListe est null.
     */
    @Override
    public int ajouter(IListeTriee<T> autreListe) throws NullPointerException {
        int nbElementsAjoutes = 0;

        if (autreListe == null) {
            throw new NullPointerException("La liste que vous désirez ajouter ne peut être nulle, merci!");
        } else {
            //Positionner au debut de l'autre liste et ajouter son élément à la liste actuelle
            autreListe.debut();
            this.ajouter(autreListe.elementCourant());
            nbElementsAjoutes++;
            //Continuer le même processus pour les autres éléments.
            while (autreListe.suivant()) {
                this.ajouter(autreListe.elementCourant());
                nbElementsAjoutes++;
            }
            //Placer le maillon Position au début de la liste
            this.debut();
        }
        return nbElementsAjoutes;
    }

    /**
     * <p>Permet de consulter l'element qui se trouve a la position courante de
     * cette liste (l'element courant), si celle-ci n'est pas vide. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si cette liste est vide, la methode leve une exception
     * ListeVideException.</li>
     * <li>Cette methode ne modifie pas cette liste (incluant sa position
     * courante).</li>
     * </ul>
     *
     * @return l'element a la position courante (element courant), dans cette liste.
     * @throws ListeVideException si cette liste est vide.
     */
    //Methode Fonctionnelle
    @Override
    public T elementCourant() throws ListeVideException {
        if (position == null || estVide()) {
            throw new ListeVideException();
        }
        return position.info();
    }

    /**
     * <p>Deplace la position courante sur l'element donne, si celui-ci existe
     * dans cette liste. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si la liste est vide, la position courante de cette liste demeure inchangee,
     * et la methode leve une exception ListeVideException.</li>
     *
     * <li>Si la liste n'est pas vide, mais que l'element donne est null, la position
     * courante demeure inchangee, et la methode leve une exception
     * NullPointerException.</li>
     *
     * <li>Si la liste n'est pas vide, mais que l'element (non null) donne n'existe
     * pas dans cette liste, la position courante demeure inchangee, et la
     * methode leve une exception NoSuchElementException.</li>
     * </ul>
     *
     * @param element l'element sur lequel deplacer la position courante.
     * @throws ListeVideException     si cette liste est vide.
     * @throws NullPointerException   si l'element donne est null.
     * @throws NoSuchElementException si l'element donne (non null) n'existe
     *                                pas dans cette liste.
     */
    //Methode Fonctionnelle
    @Override
    public void positionner(T element) throws ListeVideException, NullPointerException, NoSuchElementException {
        Maillon<T> elementDonne = new Maillon<>(element);

        if (estVide()) {
            throw new ListeVideException();
        } else if (element == null) {
            throw new NullPointerException("L'élément donné ne peut être nul, merci!");
        } else if (!elementExiste(element)) {
            throw new NoSuchElementException("L'élément donné n'existe pas! ");
        } else {
            position = elementDonne;
        }

    }


    /**
     * <p>Deplace la position courante sur l'element se trouvant au debut de cette
     * liste (le premier element devient l'element courant), si cette liste n'est
     * pas vide.</p>
     *
     * @throws ListeVideException si cette liste est vide.
     */
    //Methode Fonctionnelle
    @Override
    public void debut() throws ListeVideException {
        if (estVide()) {
            throw new ListeVideException();
        } else {
            position = elements;
            //Assigner la position au debut
        }

    }

    /**
     * <p>Deplace la position courante sur l'element se trouvant a la fin de cette
     * liste (son dernier element devient l'element courant), si cette liste n'est
     * pas vide.</p>
     *
     * @throws ListeVideException si cette liste est vide.
     */
    //Methode Fonctionnelle
    @Override
    public void fin() throws ListeVideException {
        Maillon<T> fin;
        if (estVide()) {
            throw new ListeVideException();
        }

        //positionner le maillon fin sur le dernier maillon de la chaine
        fin = position;
        while (fin.suivant() != null) {
            fin = fin.suivant();
        }
        position = fin;
    }

    /**
     * <p>Deplace la position courante sur l'element qui precede l'element courant
     * dans cette liste. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si cette liste est vide, aucune modification n'est effectuee, et la methode
     * leve une exception ListeVideException.</li>
     *
     * <li>S'il n'y a pas de precedent parce que l'element courant est l'element
     * en debut de liste (avant l'appel de cette methode), la position
     * courante demeure inchangee, et la methode retourne false pour signaler
     * qu'on est au debut de la liste.</li>
     *
     * <li>S'il y a un precedent, la methode retourne true pour signaler que
     * l'operation a bien ete effectuee.</li>
     * </ul>
     *
     * @return true si l'operation a ete effectuee, false sinon.
     * @throws ListeVideException si cette liste est vide.
     */
    //Methode (Should) Fonctionnelle
    @Override
    public boolean precedent() throws ListeVideException {
        boolean precedentEffectuee = false;
        Maillon<T> precedent = null;
        Maillon<T> position = elements;

        if (estVide()) {
            throw new ListeVideException();
        } else if (position.equals(elements)) { // si l'élément courant est le 1er element de la liste
            precedentEffectuee = false;
        } else { //Si l'élément n'est pas en début de liste
            while (position != null && position.info().compareTo(elements) < 0) {
                precedent = position;
                position = position.suivant();
            }
            precedent.modifierSuivant(position);
            precedentEffectuee = true;
        }
        return precedentEffectuee;
    }

    /**
     * <p>Deplace la position courante sur l'element qui suit (vient apres)
     * l'element courant dans cette liste. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si la liste est vide, aucune modification n'est effectuee, et la methode
     * leve une exception ListeVideException.</li>
     *
     * <li>S'il n'y a pas de suivant parce que l'element courant est l'element
     * en fin de liste (avant l'appel de cette methode), la position
     * courante demeure inchangee, et la methode retourne false pour signaler
     * qu'on est a la fin de la liste.</li>
     *
     * <li>S'il y a un suivant, la methode retourne true pour signaler que l'operation
     * a bien ete effectuee.</li>
     * </ul>
     *
     * @return true si l'operation a ete effectuee, false sinon.
     * @throws ListeVideException si cette liste est vide.
     */
    @Override
    public boolean suivant() throws ListeVideException {
        boolean suivant;

        if (estVide()) {
            throw new ListeVideException();
        } else if (position.suivant() == null) {
            suivant = false;
        } else {
            position = position.suivant();
            suivant = true;
        }
        return suivant;
    }

    /**
     * <p>Supprime l'element courant de cette liste, et retourne l'element supprime.
     * </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si cette liste est vide avant la suppression, celle-ci n'est pas modifiee
     * et la methode leve une exception ListeVideException.</li>
     * <li>Si la suppression est effectuee, et que cette liste n'est pas vide apres
     * la suppression :
     *       <ul>
     *       <li>Si l'element supprime etait le premier element de cette liste, la
     *       position courante est placee sur le (nouveau) premier element de cette liste.</li>
     *
     *       <li>Sinon, la position courante est placee sur l'element qui precedait
     *       l'element supprime dans cette liste.</li>
     *       </ul>
     * </li>
     *
     * </ul>
     *
     * @return l'element supprime de cette liste.
     * @throws ListeVideException si cette liste est vide avant l'appel.
     */
    @Override
    public T supprimer() throws ListeVideException {
        T elementSupprime;
        if (estVide()) {
            throw new ListeVideException();
        } else if (elements.suivant() == null) {
            //Supprimer au début de la liste
            elementSupprime = elements.info();
            elements = elements.suivant();
            position = elements;
            nbElements--;
        } else {
            //Supprimer dans une liste non-vide
            Maillon<T> p = elements;

            elementSupprime = p.suivant().info();
            p.modifierSuivant(p.suivant().suivant());
            --nbElements;

        }
        return elementSupprime;
    }

    /**
     * <p>Supprime l'element donne de cette liste. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si cette liste est vide avant la suppression, celle-ce n'est pas modifiee
     * et la methode leve une exception ListeVideException.</li>
     *
     * <li>Si cette liste n'est pas vide, et que l'element donne est null, la methode
     * leve une exception NullPointerException.</li>
     *
     * <li>Si element (non null) n'est pas dans cette liste, la suppression n'est
     * pas effectuee, la position courante n'est pas modifiee, et la methode
     * retourne false pour signaler que l'operation n'a pas ete effectuee.</li>
     *
     * <li>Si la suppression est effectuee et que cette liste n'est pas vide apres la
     * suppression :
     *
     *       <ul><li>Si l'element supprime etait le premier element de cette liste, la
     *       position courante est placee sur le (nouveau) premier element de cette
     *       liste.</li>
     *
     *       <li>Sinon, la position courante est placee sur l'element qui precedait
     *       l'element supprime dans cette liste.</li>
     *       </ul></li>
     * </ul>
     *
     * <pre>
     * Exemple 1 : Soit cette liste = [1, 3, 6, 7, 11, 23, 42, 100]
     *             Apres l'appel de supprimer(7)
     *             cette liste = [1, 3, 6, 11, 23, 42, 100] et element courant = 6.
     *
     * Exemple 2 : Soit cette liste = [1, 3, 6, 7, 11, 23, 42, 100]
     *             Apres l'appel de supprimer(100)
     *             cette liste = [1, 3, 6, 11, 23, 42] et element courant = 42.
     *
     * Exemple 3 : Soit cette liste = [1, 3, 6, 11, 23, 42]
     *             Apres l'appel de supprimer(1)
     *             cette liste = [3, 6, 11, 23, 42] et element courant = 3.
     *
     * Exemple 4 : Soit cette liste = [1]
     *             Apres l'appel de supprimer(1)
     *             cette liste = [] (liste vide) et aucun element courant.
     * </pre>
     *
     * @param element l'element a supprimer.
     * @return true si l'element a ete supprime, false sinon.
     * @throws ListeVideException   si cette liste est vide avant l'appel.
     * @throws NullPointerException si element est null.
     */
    @Override
    public boolean supprimer(T element) throws ListeVideException, NullPointerException {
        boolean elementSupprime = false;
        Maillon<T> precedent = elements;

        if (estVide()) {
            throw new ListeVideException();
        } else if (element == null) {
            throw new NullPointerException("L'élément ne peut pas être nul, merci!");
        } else if (!elementExiste(element)) {
            elementSupprime = false;
        }

        //Supprimer en début de liste
        if (element.compareTo(elements.info()) == 0) {
            elements = elements.suivant();
            position = elements;
        }

        while (!precedent.suivant().info().equals(element)) {
            precedent = precedent.suivant();
            position = precedent;
        }

        precedent.modifierSuivant(precedent.suivant().suivant());

        return elementSupprime;
    }


    /**
     * <p>Supprime de cette liste, les elements contenus dans autreListe, lorsque
     * possible. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si autreListe est null, la methode leve une exception
     * NullPointerException.</li>
     *
     * <li>Lorsqu'un element de autreListe n'existe pas dans cette liste,
     * celui-ci n'est (evidemment) pas supprime de cette liste.</li>
     *
     * <li>Si cette liste n'est pas vide apres l'appel de cette methode, sa
     * position courante est placee sur son premier element (le plus petit).</li>
     *
     * <li>Si la liste retournee n'est pas vide, sa position courante est placee
     * sur son premier element (le plus petit).</li>
     *
     * <li>Apres l'appel de cette methode, les elements de autreListe n'ont pas
     * ete modifies, mais la position courante peut avoir ete modifiee.</li>
     * </ul>
     *
     * @param autreListe la liste contenant les elements qu'on veut supprimer
     *                   de cette liste.
     * @return une liste de tous les elements qui ont ete supprimes. Si
     * aucun element n'a ete supprime, la liste retournee est vide.
     * @throws NullPointerException si autreListe est null.
     */
    @Override
    public IListeTriee supprimer(IListeTriee autreListe) throws NullPointerException {
        IListeTriee<T> elementsSupprimes = null;

        if(autreListe == null){
            throw new NullPointerException("La liste à supprimer ne peut être nulle, merci!");
        }else{
            //Positionner le 1er élément de l'autre listre pour la suppression.
            autreListe.debut();
            elementsSupprimes = this.supprimer((IListeTriee) autreListe.elementCourant());
            //Continuer le même processus pour les autres éléments.
            while (autreListe.suivant()) {
                elementsSupprimes = this.supprimer((IListeTriee<T>) autreListe.elementCourant());
            }
            //Placer le maillon Position au début de la liste
            this.debut();
        }
        return elementsSupprimes;
    }

    /**
     * <p>Retourne true si element est present dans cette liste, false sinon. </p>
     *
     * @param element l'element dont on veut tester l'existence.
     * @return true si element est present dans cette liste, false sinon.
     */
    @Override
    //Methode Fonctionnelle
    public boolean elementExiste(T element) {
        boolean elementExiste = false;

        if (elements == null) {
            elementExiste = false;
        } else if (elements.info().getClass() != element.getClass()) {
            elementExiste = false;
        }

        Maillon<T> tmp = elements;

        while (tmp != null) {
            if (tmp.info().compareTo(element) == 0) {
                elementExiste = true;
            }
            tmp = tmp.suivant();
        }

        return elementExiste;
    }

    /**
     * <p>Retourne le nombre d'elements dans cette liste. </p>
     *
     * @return le nombre d'elements dans cette liste.
     */
    @Override
    //Methode Fonctionnelle
    public int nbrElements() {
        return nbElements;
    }

    /**
     * <p>Retourne true si cette liste est vide, false sinon.</p>
     *
     * @return true si cette liste est vide, false sinon.
     */
    @Override
    //Methode Fonctionnelle
    public boolean estVide() {
        boolean estVide = false;
        if (nbElements == 0) {
            estVide = true;
        } else {
            estVide = false;
        }
        return estVide;
    }

    /**
     * <p>Retourne une nouvelle liste qui contient tous les elements de cette liste
     * compris entre elementDebut et elementFin inclusivement. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si cette liste est vide, la methode leve une exception
     * ListeVideException.</li>
     *
     * <li>Si cette liste n'est pas vide, et que elementDebut ou elementFin est null,
     * la methode leve une exception NullPointerException.</li>
     *
     * <li>Si cette liste n'est pas vide et que elementDebut (non null) est
     * strictement plus grand que elementFin (non null), la methode leve une
     * exception NoSuchElementException.</li>
     *
     * <li>Si la sous-liste retournee n'est pas vide, sa position courante est placee
     * sur son premier element (le plus petit).</li>
     *
     * <li>Cette methode ne modifie pas cette liste (incluant sa position courante).</li>
     * </ul>
     *
     * <pre>
     * Exemples : Soit cette liste = [2, 3, 6, 9, 11, 14, 16].
     *              sousListe(2, 11) retourne [2, 3, 6, 9, 11] (elem courant = 2)
     *              sousListe(6, 6) retourne [6] (elem courant = 6)
     *              sousListe(7, 7) retourne [] (liste vide)
     *              sousListe(4, 15) retourne [6, 9, 11, 14] (elem courant = 6 )
     *              sousListe(17, 32) retourne [] (liste vide)
     *              sousListe(4, 5) retourne [] (liste vide)
     * </pre>
     *
     * @param elementDebut l'element qui est la borne inferieure incluse de la
     *                     sous-liste a retourner.
     * @param elementFin   l'element qui est la borne superieure incluse de la
     *                     sous-liste a retourner.
     * @return une sous-liste contenant les elements de cette liste allant de
     * elementDebut a elementFin (inclusivement).
     * @throws ListeVideException     si cette liste est vide.
     * @throws NullPointerException   si elementDebut ou elementFin est null.
     * @throws NoSuchElementException si elementDebut est plus grand que elementFin.
     */
    @Override
    public IListeTriee sousListe(T elementDebut, T elementFin) throws
            ListeVideException, NullPointerException, NoSuchElementException {
        return null;
    }

    /**
     * <p>Retourne une liste des elements qui se trouvent a la fois dans cette liste
     * et dans autreListe. </p>
     *
     * <u>Precisions</u> :
     * <ul>
     * <li>Si autreListe est null, la methode retourne une exception
     * NullPointerException.</li>
     *
     * <li>Cette liste demeure inchangee (incluant sa position courante).</li>
     *
     * <li>Les elements de autreListe demeurent inchanges, mais sa position courante
     * peut avoir ete modifiee.</li>
     *
     * <li>Si la liste retournee n'est pas vide, sa position courante est placee
     * sur son premier element.</li>
     * </ul>
     *
     * @param autreListe la liste a comparer avec cette liste pour trouver les
     *                   elements communs de ces deux listes.
     * @return une liste des elements communs entre cette liste et autreListe.
     * @throws NullPointerException si autreListe est null.
     */
    @Override
    public IListeTriee elementsCommuns(IListeTriee autreListe) throws NullPointerException {
        return null;
    }

    /**
     * <p>Retire tous les elements de cette liste. Apres l'appel de cette
     * methode, la liste est vide.</p>
     */
    @Override
    public void vider() {
        if(nbElements != 0) {
            Maillon<T> p = elements;
            while (p.suivant() != null){
                p.modifierSuivant(elements.suivant());
            }
        }

    }

    /**
     * <p>
     * Retourne une representation de cette liste sous forme d'une chaine de
     * caracteres, selon le format montre ci-dessous.
     * </p>
     * <pre>
     * Format de la chaine retournee :
     *
     *    "[]"                                   (si cette liste est vide)
     *    "[E1, E2, ..., En] (element courant)"  (si cette liste n'est pas vide)
     *
     * Exemple : Soit cette liste = [2, 3, 7, 9, 12, 25, 36, 42] dont l'element
     *           courant est 9. L'appel de toString sur cette liste retournera
     *           la chaine "[2, 3, 7, 9, 12, 25, 36, 42] (9)"
     * </pre>
     *
     * @return une representation de cette liste sous forme d'une chaine de
     * caracteres.
     */
    @Override
    public String toString() {
        String s;

        if (nbElements == 0) {
            s = "[]";
        } else {
            s = elements.toString();
            s = s + " (" + position.info() + ")";
        }
        return s;
    }
}

