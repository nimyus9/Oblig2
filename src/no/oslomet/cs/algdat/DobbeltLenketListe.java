package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a,"Tabellen a er Null!");

        for(T t : a) {
            if (t == null) {

            } else if(tom()){
                Node<T> node = new Node<>(t, null, null);
                hode = hale = node;
                antall++;
            }else{
                Node<T> node = new Node<>(t, hale,null);
                hale.neste = node;
                hale = node;
                antall++;
            }
        }
    }

    public Liste<T> subliste(int fra, int til){
        endringer = 0;
        fratilKontroll(antall,fra,til);

        DobbeltLenketListe<T> subliste = new DobbeltLenketListe<>();


        for (int i = fra; i < til ; i++) {
            subliste.leggInn(hent(i));
        }

        return  subliste;
    }

    private void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0){
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }
        if (til > antall){
            throw new IndexOutOfBoundsException ("til(" + til + ") > antall(" + antall + ")");
        }
        if (fra > til)   {
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi,"Verdi kan ikke vaere null");


        if(tom()){
            Node<T> node = new Node<>(verdi, null,null);
            hale = hode = node;
            antall++;
            endringer++;
            return true;
        } else{
            Node<T> node = new Node<>(verdi, hale,null);
            hale.neste = node;
            hale = node;
            antall++;
            endringer++;
            return true;
        }
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi,"Verdi kan ikke vaere Null");

        if(indeks < 0 || indeks > antall) {
            throw new IndexOutOfBoundsException("Indeks er ute av intervallet");
        }

        if(verdi == null){
            throw new NullPointerException("Verdi kan ikke vaere Null");
        }

        Node<T> node;

        if(antall == 0){
            node = new Node<>(verdi,null, null);
            hode = hale = node;
            antall++;
            endringer++;

        } else if(tom() || indeks >=antall){
            leggInn(verdi);
        } else if(indeks == 0){
            node = new Node<>(verdi,null, hode);
            hode.forrige = node;
            hode = node;
            antall++;
            endringer++;
        } else{
            Node<T> gammelNode = finnNode(indeks);
            node = new Node<>(verdi,gammelNode.forrige,gammelNode);
            gammelNode.forrige = node;
            node.forrige.neste = node; //
            antall++;
            endringer++;
        }
    }

    @Override
    public boolean inneholder(T verdi) {
        if(indeksTil(verdi) == -1){
            return false;
        }

        return true;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    // hjelpemetode
    private Node<T> finnNode(int indeks){
        Node<T> returnNode;

        if(indeks < antall/2){
            returnNode = hode;
            for (int i = 0; i < indeks ; i++) {
                returnNode = returnNode.neste;
            }
        } else{
            returnNode = hale;
            for (int i = 1; i < (antall - indeks); i++) {
                returnNode = returnNode.forrige;
            }
        }
        return returnNode;
    }

    @Override
    public int indeksTil(T verdi) {
        for (int i = 0; i < antall ; i++) {
            if(hent(i).equals(verdi)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks,false);
        Objects.requireNonNull(nyverdi,"Verdi kan ikke vaere null");

        Node<T> gammelNode = finnNode(indeks);
        T gammelVerdi = gammelNode.verdi;

        gammelNode.verdi = nyverdi;
        endringer++;
        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        if(verdi == null) {
            return false;
        }

        Node<T> node = hode;

        while (node != null) {
            if (node.verdi.equals(verdi)) {
                break;
            }

            node = node.neste;
        }
        if (node == null) {
            return false;
        }


        if(node == hode){
            hode = hode.neste;
            if (hode != null) {
                hode.forrige = null;
            } else {
                hale = null;
            }
        }else if (node == hale){
            hale = hale.forrige;
            hale.neste = null;
        }else{
            node.forrige.neste = node.neste;
            node.neste.forrige = node.forrige;
        }

        antall--;
        endringer++;

        return true;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks,false);

        if(tom()) {
            return null;
        }

        Node<T> node;
        T verdi;

        if(antall == 1){
            verdi = hode.verdi;
            hode = hale = null;
        } else if(indeks == 0){
            if(antall == 2){
                hode = hale;
                hale.neste = null;
                hode.forrige = null;
            }else {
                node = hode.neste;
                node.forrige = null;
                hode = node;
            }
            verdi = hode.verdi;
        } else if(indeks == antall-1){

            if(antall == 2){
                hale = hode;
                hale.neste = null;
                hode.forrige = null;
            }else{
                node = hale.forrige;
                node.neste = null;
                hale = node;
            }
            verdi = hale.verdi;
        } else {
            node = finnNode(indeks);
            verdi = node.verdi;
            node.neste.forrige = node.forrige;
            node.forrige.neste = node.neste;
        }

        antall--;
        endringer++;
        return verdi;
    }

    @Override
    public void nullstill() {
        while(hode != null){
            fjern(0);
            endringer++;
        }
        antall = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb  = new StringBuilder();
        sb.append("[");

        if(!tom()){
            Node<T> p = hode;
            sb.append(p.verdi);
            p = p.neste;

            while( p != null){
                sb.append(", ").append(p.verdi);
                p = p.neste;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public String omvendtString() {
        StringBuilder sb  = new StringBuilder();
        sb.append("[");

        if(!tom()){
            Node<T> p = hale;
            sb.append(p.verdi);
            p = p.forrige;

            for(; p != null;){
                sb.append(", ").append(p.verdi);
                p = p.forrige;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        DobbeltLenketListeIterator dobbeltLenketListeIterator = new  DobbeltLenketListeIterator(indeks);
        return dobbeltLenketListeIterator;
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            if(iteratorendringer != endringer){
                throw new ConcurrentModificationException("Ulike endringer");
            }

            if(!hasNext()){
                throw new NoSuchElementException("Ikke true");
            }

            fjernOK = true;
            T temp = denne.verdi;
            denne = denne.neste;
            return temp;
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


