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
    @Override
    public boolean ajouter(T element) throws NullPointerException {
        boolean ajouter = false;

        Maillon<T> nouveau = new Maillon<>(element);
        Maillon<T> precedent = null;
        Maillon<T> tmp = elements;
        T infoTmp;

        //Traitement de l'élément si celui-ci est null
        if (element == null) {
            throw new NullPointerException("L'élement ne peut être nul.");
        }

        //Traitement de l'élément si celui-ci existe deja dans la liste
        if (elementExiste(element)) {
            ajouter = false;
        } else if (tmp == null) {
            //Ajout en début de liste (liste vide)
            elements = nouveau;
            position = nouveau;
            nbElements++;
            ajouter = true;
        }

        //positionner precedent juste avant
        while (tmp != null && tmp.info().compareTo(element) < 0) {
            precedent = tmp;
            tmp = tmp.suivant();
        }

        //Inserer nouveau apres tmp
        nouveau.modifierSuivant(tmp);

        if(precedent != null){
            precedent.modifierSuivant(nouveau);
            nbElements++;
        }

        position = nouveau;
        ajouter = true;

        return ajouter;
    }


    @Override
    public int ajouter(IListeTriee autreListe) throws NullPointerException {
        return 0;
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
    @Override
    public void positionner(T element) throws ListeVideException, NullPointerException, NoSuchElementException {
        Maillon<T> elementDonne = new Maillon<>(element);

        if (estVide()) {
            throw new ListeVideException();
        }else if (element == null){
            throw new NullPointerException("L'élément donné ne peut être nul.");
        }else if (!elementExiste(element)){
            throw new NoSuchElementException("L'élément donné n'existe pas.");
        }else{
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
    @Override
    public void debut() throws ListeVideException {
        if (estVide()) {
            throw new ListeVideException();
        } else {
            position = elements;
            ; //Assigner la position au debut
        }

    }

    /**
     * <p>Deplace la position courante sur l'element se trouvant a la fin de cette
     * liste (son dernier element devient l'element courant), si cette liste n'est
     * pas vide.</p>
     *
     * @throws ListeVideException si cette liste est vide.
     */
    @Override
    public void fin() throws ListeVideException {
        Maillon<T> fin;
        if (estVide()) {
            throw new ListeVideException();
        }

        //positionner le maillon fin sur le dernier maillon de la chaine
        fin = elements;
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
            while(position != null && position.info().compareTo(elements) < 0){
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
            elementSupprime = (T) elements;
            elements = elements.suivant();
            position = elements;
            nbElements--;
        } else {
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
     * @param element l'element a supprimer.
     * @return true si l'element a ete supprime, false sinon.
     * @throws ListeVideException si cette liste est vide avant l'appel.
     * @throws NullPointerException si element est null.
     */
    @Override
    public boolean supprimer(T element) throws ListeVideException, NullPointerException {
        boolean elementSupprime = false;
        Maillon<T> precedent = elements;

        if (estVide()){
            throw new ListeVideException();
        }else if(element == null){
            throw new NullPointerException("L'élément ne peut pas être nul");
        }else if(!elementExiste(element)){
            elementSupprime = false;
        }

        //Supprimer en début de liste
        if(element.compareTo(elements) == 0){
            elements = elements.suivant();
            position = elements;
        }

        while(!precedent.suivant().info().equals(element)){
            precedent = precedent.suivant();
            position = precedent;
        }

        precedent.modifierSuivant(precedent.suivant().suivant());

        return elementSupprime;
    }

    @Override
    public IListeTriee supprimer(IListeTriee autreListe) throws NullPointerException {
        return null;
    }

    /**
     * <p>Retourne true si element est present dans cette liste, false sinon. </p>
     *
     * @param element l'element dont on veut tester l'existence.
     * @return true si element est present dans cette liste, false sinon.
     */
    @Override
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

    @Override
    public int nbrElements() {
        return nbElements;
    }

    /**
     * <p>Retourne true si cette liste est vide, false sinon.</p>
     *
     * @return true si cette liste est vide, false sinon.
     */
    @Override
    public boolean estVide() {
        boolean estVide;
        if (nbElements == 0) {
            estVide = true;
        } else {
            estVide = false;
        }
        return estVide;
    }

    @Override
    public IListeTriee sousListe(T elementDebut, T elementFin) throws
            ListeVideException, NullPointerException, NoSuchElementException {
        return null;
    }

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
        while (nbElements != 0) {
            Maillon<T> p = elements;
            for (int i = 1; i < nbElements; i++) {
                p.modifierSuivant(p.suivant());
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

