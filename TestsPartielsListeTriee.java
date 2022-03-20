import java.util.NoSuchElementException;

/**
 * Classe de tests partiels pour tester la classe ListeTriee (TP2 INF2120 H22). 
 * 
 * Ces tests sont partiels, et ne testent donc pas tous les cas pour toutes les 
 * methodes. Vous devez ajouter vos propres tests pour verifier les cas manquants.
 * 
 * Mettre cette classe dans votre projet, et executer la methode main : verifiez
 * que les affichages correspondent a ceux ecrits en commentaires dans le code.
 * 
 * @author Melanie Lord
 * @version Hiver 2022
 */
public class TestsPartielsListeTriee {

   /**
    * Aucune des instructions de cette methode main ne fait planter le programme.
    * Si les tests plantent, c'est qu'il y a une erreur dans votre code.
    *
    * @param args (vide)
    */
   public static void main (String[] args) {

      IListeTriee<Integer> l1 = new ListeTrieeChainee<> ();
      IListeTriee<Integer> l2 = new ListeTrieeChainee<> ();
      boolean b;
      Integer elt;

      try {
         System.out.println (l1.elementCourant());
         System.out.println ("ERREUR, devrait lever "
                 + "une ListeVideException");
      } catch (ListeVideException e) {
         System.out.println ("OK");                     //OK
      } catch (Exception e) {
         System.out.println ("ERREUR, devrait lever "
                 + "une ListeVideException");
      }

      System.out.println (l1.elementExiste (3));        //false
      System.out.println (l1.estVide ());               //true
      System.out.println (l1.nbrElements ());           //0

      l1.ajouter (7);
      l1.ajouter (4);
      l1.ajouter (0);
      l1.ajouter (18);
      l1.ajouter (11);
      l1.ajouter (10);

      System.out.println (l1);                          //[0, 4, 7, 10, 11, 18] (10)
      System.out.println (l1.elementCourant ());        //10

      System.out.println (l1.elementExiste (3));        //false
      System.out.println (l1.elementExiste (18));       //true
      System.out.println (l1.estVide ());               //false
      System.out.println (l1.nbrElements ());           //6

      l2.ajouter (18);
      l2.ajouter (3);
      l2.ajouter (0);
      l2.ajouter (11);
      l2.ajouter (6);

      System.out.println (l2);                          //[0, 3, 6, 11, 18] (6)
      System.out.println (l2.elementCourant ());        //6

      l1.ajouter (l2);

      System.out.println (l1);                          //[0, 3, 4, 6, 7, 10, 11, 18] (0)

      l1.positionner (10);
      System.out.println (l1.elementCourant ());        //0

      l1.positionner (18);
      System.out.println (l1.elementCourant ());        //18

      l1.positionner (7);
      System.out.println (l1.elementCourant ());        //7

      l2.vider ();
      System.out.println (l2);                          //[]

      try {
         l1.positionner (12);
         System.out.println ("ERREUR, devrait lever "
                 + "une NoSuchElementException");
      } catch (NoSuchElementException e) {
         System.out.println ("OK");                     //OK
      } catch (Exception e) {
         System.out.println ("ERREUR, devrait lever "
                 + "une NoSuchElementException");
      }

      System.out.println (l1.elementCourant ());        //7

      b = l1.precedent ();
      System.out.println (l1.elementCourant ());        //6
      System.out.println (b);                           //true

      l1.suivant ();
      l1.suivant ();
      b = l1.suivant ();
      System.out.println (l1.elementCourant ());        //11
      System.out.println (b);                           //true

      l1.debut();
      System.out.println (l1.elementCourant ());        //0

      b = l1.precedent ();
      System.out.println (b);                           //false
      System.out.println (l1.elementCourant ());        //0

      l1.fin ();
      System.out.println (l1.elementCourant ());        //18

      b = l1.suivant ();
      System.out.println (b);                           //false
      System.out.println (l1.elementCourant ());        //18

      l2.ajouter (0);
      l2.ajouter (16);
      l2.ajouter (4);
      l2.ajouter (7);
      l2.ajouter (2);
      l2.ajouter (35);
      l2.ajouter (18);

      System.out.println (l1.elementsCommuns (l2));     //[0, 4, 7, 18] (0)
      System.out.println (l2.elementsCommuns (l1));     //[0, 4, 7, 18] (0)

      l2.vider ();
      l2.ajouter (2);
      l2.ajouter (15);
      l2.ajouter (22);
      l2.ajouter (36);

      System.out.println (l1.elementsCommuns (l2));     //[]

      System.out.println (l1.sousListe (0, 18));        //[0, 3, 4, 6, 7, 10, 11, 18] (0)
      System.out.println (l1.sousListe (3, 12));        //[3, 4, 6, 7, 10, 11] (3)
      System.out.println (l1.sousListe (18, 100));      //[18] (18)
      System.out.println (l1.sousListe (19, 100));      //[]

      try {
         System.out.println (l1.sousListe (10, 6));
         System.out.println ("ERREUR, devrait lever "
                 + "une NoSuchElementException");
      } catch (NoSuchElementException e) {
         System.out.println ("OK");                     //OK
      } catch (Exception e) {
         System.out.println ("ERREUR, devrait lever "
                 + "une NoSuchElementException");
      }

      elt = l1.supprimer ();
      System.out.println (l1);                          //[0, 3, 4, 6, 7, 10, 11] (11)
      System.out.println (elt);                         //18

      l1.debut ();
      elt = l1.supprimer ();
      System.out.println (l1);                          //[3, 4, 6, 7, 10, 11] (3)
      System.out.println (elt);                         //0

      l1.positionner (7);
      elt = l1.supprimer ();
      System.out.println (l1);                          //[3, 4, 6, 10, 11] (6)
      System.out.println (elt);                         //7

      l1.supprimer (6);
      System.out.println (l1);                          //[3, 4, 10, 11] (4)

      l2.vider ();
      l2.ajouter (8);
      l2.ajouter (10);
      l2.ajouter (2);
      l2.ajouter (43);
      l2.ajouter (4);
      l1.supprimer (l2);

      System.out.println (l1);                          //[3, 11] (3)

      l1.supprimer (3);
      l1.supprimer (11);
      System.out.println (l1);                          //[]

      try {
         System.out.println (l1.supprimer (2));
         System.out.println ("ERREUR, devrait lever "
                 + "une ListeVideException");
      } catch (ListeVideException e) {
         System.out.println ("OK");                     //OK
      } catch (Exception e) {
         System.out.println ("ERREUR, devrait lever "
                 + "une ListeVideException");
      }

   } //fin main
/*   public static void main(String[] args) {
      Maillon <Integer> element1 = new Maillon<>(1);
      Maillon <Integer> element2 = new Maillon<>(2);
      Maillon <Integer> element3 = new Maillon<>(3);
      Maillon <Integer> element4 = new Maillon<>(4);

      element1.modifierSuivant(element2);
      element2.modifierSuivant(element3);
      element3.modifierSuivant(element4);

      System.out.println(element1.info());
      System.out.println(element1.suivant().suivant().suivant().suivant());


   }*/

}
