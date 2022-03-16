import java.util.NoSuchElementException;

public class ListeTrieeChainee <T extends Comparable> implements IListeTriee <T>{

    private Maillon <T> elements;
    private int nbElements;
    private Maillon <T> position;

    public ListeTrieeChainee(){
        this.nbElements = 0;
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
    public boolean ajouter(Comparable element) throws NullPointerException {
        Maillon <T> nouveau = new Maillon<T>((T) element);
        Comparable info;

        //Traitement de l'élément si celui-ci est null
        if(element == null){
            throw new NullPointerException("L'élement ne peut être nul.");
        }

        if(elements == null){
            elements = new Maillon<T>((T) element);
            position = elements;
            return true;
        } else if(elementExiste(element)){
            //Traitement de l'élément s'il est déjà présent dans la liste
            return false;
        }else {
            //Traitement de l'élément pour l'ajout
            Maillon<T> tmp = elements;
            position = elements;

            for (int i = 1 ; i < nbElements ; i++ ){
                if(elements != null){
                    while (elements.suivant() != null && elements.info().compareTo(element) < 0){
                        position = elements.suivant();
                    }
                }

                nouveau.modifierSuivant(elements.suivant());
                elements.modifierSuivant(nouveau);

                if(elements.info().compareTo(element) > 0){
                    info = nouveau.info();
                    nouveau.modifierInfo(elements.info());
                    elements.modifierInfo((T) info);
                }
                tmp = tmp.suivant();
                tmp.modifierSuivant(new Maillon<T>((T) element, tmp.suivant()));
            }
            nbElements ++;
            return true;
        }
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
        return (T) position.info();
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
    public void positionner(Comparable element) throws ListeVideException, NullPointerException, NoSuchElementException {

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
        if(estVide() == true){
            throw new ListeVideException();
        }else{
             positionner(0);
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
        if(estVide()){
            throw new ListeVideException();
        }

    }

    @Override
    public boolean precedent() throws ListeVideException {
        return false;
    }

    @Override
    public boolean suivant() throws ListeVideException{
        return false;
    }

    @Override
    public T supprimer() throws ListeVideException {
        return null;
    }

    @Override
    public boolean supprimer(Comparable element) throws ListeVideException, NullPointerException {
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
    public boolean elementExiste(Comparable element) {

        if(elements == null) return false;

        if(elements.info().getClass() != element.getClass()){
            return false;
        }

        Maillon<T> tmp = elements;

        while(tmp != null) {
            if(tmp.info().compareTo(element) == 0){
                return true;
            }
            tmp = tmp.suivant();
        }

        return false;
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
        if ( nbElements == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public IListeTriee sousListe(Comparable elementDebut, Comparable elementFin) throws
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
}
