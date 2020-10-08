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
        throw new UnsupportedOperationException();
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
        }else{ //feil logikk
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
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
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
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            throw new UnsupportedOperationException();
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


