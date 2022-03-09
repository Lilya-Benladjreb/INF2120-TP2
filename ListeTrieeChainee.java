import java.util.NoSuchElementException;

public class ListeTrieeChainee implements IListeTriee{
    @Override
    public boolean ajouter(Comparable element) throws NullPointerException {
        return false;
    }

    @Override
    public int ajouter(IListeTriee autreListe) throws NullPointerException{
        return 0;
    }

    @Override
    public Comparable elementCourant() throws ListeVideException {
        return null;
    }

    @Override
    public void positionner(Comparable element) throws ListeVideException, NullPointerException, NoSuchElementException {

    }

    @Override
    public void debut() throws ListeVideException{

    }

    @Override
    public void fin() throws ListeVideException{

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
    public Comparable supprimer() throws ListeVideException {
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

    @Override
    public boolean elementExiste(Comparable element) {
        return false;
    }

    @Override
    public int nbrElements() {
        return 0;
    }

    @Override
    public boolean estVide() {
        return false;
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

    @Override
    public void vider() {

    }
}
