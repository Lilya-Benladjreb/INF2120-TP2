import java.util.NoSuchElementException;

public class ListeTrieeChainee <T extends Comparable> implements IListeTriee <T>{

    private Maillon <T> elements;
    private int nbElements;
    private Maillon <T> position;

    public ListeTrieeChainee(){
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
        boolean ajouter  = false;

        Maillon <T> nouveau = new Maillon<>(element);
        T info;

        //Traitement de l'élément si celui-ci est null
        if(element == null){
            throw new NullPointerException("L'élement ne peut être nul.");
        }

        //Traitement de l'élément pour l'ajout
        Maillon<T> tmp = elements;

        if(!elementExiste(element)) {
            while (tmp.info() != null) {
                if (tmp.info().compareTo(element) < 0) {
                        nouveau.modifierInfo(tmp.info());
                        nouveau.modifierSuivant(tmp.suivant());
                        position = nouveau;
                }
                if (elements.info().compareTo(element) > 0) {
                        info = nouveau.info();
                        nouveau.modifierInfo((T) element);
                        elements.modifierInfo((T) info);
                }
                tmp = tmp.suivant();
                tmp.modifierSuivant(new Maillon<T>((T) element, tmp.suivant()));
            }
            nbElements ++;
            ajouter = true;
        }

        return ajouter;
    }



    @Override
    public int ajouter(IListeTriee autreListe) throws NullPointerException{
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
        if(position == null){
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
     * @throws ListeVideException si cette liste est vide.
     * @throws NullPointerException si l'element donne est null.
     * @throws NoSuchElementException si l'element donne (non null) n'existe
     *         pas dans cette liste.
     */
    @Override
    public void positionner(T element) throws ListeVideException, NullPointerException, NoSuchElementException {

    }

    /**
     * <p>Deplace la position courante sur l'element se trouvant au debut de cette
     * liste (le premier element devient l'element courant), si cette liste n'est
     * pas vide.</p>
     *
     * @throws ListeVideException si cette liste est vide.
     */
    @Override
    public void debut() throws ListeVideException{
        if(estVide()){
            throw new ListeVideException();
        }else{
            position = elements;; //Assigner la position au debut
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
    public void fin() throws ListeVideException{
        Maillon<T> fin;
        if(estVide()){
            throw new ListeVideException();
        }

        //positionner le maillon fin sur le dernier maillon de la chaine
        fin = elements;
        while(fin.suivant() != null){
            fin = fin.suivant();
        }
    }

    @Override
    public boolean precedent() throws ListeVideException {
        return false;
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
     * @return true si l'operation a ete effectuee, false sinon.
     * @throws ListeVideException si cette liste est vide.
     */
    @Override
    public boolean suivant() throws ListeVideException{
        boolean suivant;

        if(estVide()){
            throw new ListeVideException();
        }else if(position.suivant() == null){
            suivant = false;
        }else{
            position = position.suivant();
            suivant = true;
        }
        return suivant;
    }

    @Override
    public T supprimer() throws ListeVideException {
        return null;
    }

    @Override
    public boolean supprimer(T element) throws ListeVideException, NullPointerException {
        return false;
    }

    @Override
    public IListeTriee supprimer(IListeTriee autreListe) throws NullPointerException {
        return null;
    }

    /**
     * <p>Retourne true si element est present dans cette liste, false sinon. </p>
     * @param element l'element dont on veut tester l'existence.
     * @return true si element est present dans cette liste, false sinon.
     */
    @Override
    public boolean elementExiste(T element) {
        boolean elementExiste = true;

        if(elements == null) {
            elementExiste = false;
        } else if(elements.info().getClass() != element.getClass()){
            elementExiste = false;
        }

        Maillon<T> tmp = elements;

        while(tmp != null) {
            if(tmp.info().compareTo(element) == 0){
                elementExiste =  true;
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
     * @return true si cette liste est vide, false sinon.
     */
    @Override
    public boolean estVide() {
        boolean estVide;
        if ( nbElements == 0){
            estVide = true;
        }else {
            estVide =  false;
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
        while (nbElements != 0){
            Maillon <T> p = elements;
            for (int i = 1; i < nbElements; i ++){
                p.modifierSuivant(p.suivant());
            }
        }

    }

    /**
     * Redéfinition de la méthode toString pour afficher les ListeTriéeChainée.
     *
     * Devrait avoir l'allure suivante:
     * [[4, 5, 7, 10, 11, 23]
     *
     * @return listeTriéeChainée du bon format.
     */
    @Override
    public String toString() throws NullPointerException{
        String s = "[";
        Maillon<T> tmp;

        if(elements == null){
            throw new NullPointerException("Le premier élément de la liste est nul.");
        }

        for (int i = 0 ; i <= nbElements ; i++) {
            s = "[" + elements.info();
            if (elements.suivant() == null) {
                s = s + "]";

            } else {
                s = s + ", ";
                tmp = position.suivant();
                while (tmp != null) {
                    s = s + tmp.info();
                    if (tmp.suivant() != null) {
                        s = s + ", ";
                    }
                    tmp = tmp.suivant();
                }

                s = s + "]";
            }
        }
        return s;
    }
}

