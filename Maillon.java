
/**
 * Classe Maillon servant a construire une liste chainee. 
 *
 * @author Melanie Lord
 * @version Hiver 2022
 * @param <T> le type de l'info dans le maillon.
 */
public class Maillon<T> {

   private T info;             //information stockee dans ce maillon
   private Maillon<T> suivant; //le maillon suivant de ce maillon 
                               //(pour le chainage).

   /**
    * Cree un nouveau maillon n'ayant pas de maillon suivant.
    *
    * @param info l'information qui sera stockee dans le maillon
    */
   public Maillon(T info) {
      this(info, null);
   }

   /**
    * Cree un nouveau maillon ayant un autre maillon existant comme suivant
    *
    * @param info l'information qui sera stockee dans le maillon
    * @param suivant le maillon qui sera le suivant du maillon cree
    */
   public Maillon(T info, Maillon<T> suivant) {
      this.info = info;
      this.suivant = suivant;
   }

   /**
    * Permet d'obtenir l'info de ce maillon.
    *
    * @return l'info de ce maillon.
    */
   public T info() {
      return info;
   }

   /**
    * Permet d'obtenir le suivant de ce maillon.
    *
    * @return le suivant de ce maillon ou null si aucun suivant.
    */
   public Maillon<T> suivant() {
      return suivant;
   }

   /**
    * Permet de modifier l'information de ce maillon par l'info passee en parametre.
    *
    * @param info la nouvelle information qui sera stockee dans ce maillon.
    */
   public void modifierInfo(T info) {
      this.info = info;
   }

   /**
    * Permet de modifier le suivant de ce maillon par le maillon passe en
    * parametre.
    *
    * @param suivant le maillon qui sera le nouveau suivant de ce maillon.
    */
   public void modifierSuivant(Maillon<T> suivant) {
      this.suivant = suivant;
   }
   
   /**
    * Retourne une representation sous forme de chaine de caractere de ce 
    * maillon (incluant tous les maillons chaines).
    * 
    * @return une representation sous forme de chaine de caractere de ce 
    *         maillon (incluant tous les maillons chaines).
    */
   @Override
   public String toString() {
      String s;
      Maillon<T> tmp;
      
      s = "[" + this.info;
      if (this.suivant() == null) {
         s = s + "]";
         
      } else {
         s = s + ", ";
         tmp = this.suivant;
         while (tmp != null) {
            s = s + tmp.info();
            if (tmp.suivant() != null) {
               s = s + ", ";
            }
            tmp = tmp.suivant();
         }
         
         s = s + "]";
      }
      return s;
   }
}
