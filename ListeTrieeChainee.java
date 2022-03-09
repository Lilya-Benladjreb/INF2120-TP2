public class ListeTrieeChainee implements IListeTriee{
    @Override
    public boolean ajouter(Comparable element) {
        return false;
    }

    @Override
    public int ajouter(IListeTriee autreListe) {
        return 0;
    }

    @Override
    public Comparable elementCourant() {
        return null;
    }

    @Override
    public void positionner(Comparable element) {

    }

    @Override
    public void debut() {

    }

    @Override
    public void fin() {

    }

    @Override
    public boolean precedent() {
        return false;
    }

    @Override
    public boolean suivant() {
        return false;
    }

    @Override
    public Comparable supprimer() {
        return null;
    }

    @Override
    public boolean supprimer(Comparable element) {
        return false;
    }

    @Override
    public IListeTriee supprimer(IListeTriee autreListe) {
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
    public IListeTriee sousListe(Comparable elementDebut, Comparable elementFin) {
        return null;
    }

    @Override
    public IListeTriee elementsCommuns(IListeTriee autreListe) {
        return null;
    }

    @Override
    public void vider() {

    }
}
