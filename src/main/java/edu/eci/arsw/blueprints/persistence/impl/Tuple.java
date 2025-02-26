package edu.eci.arsw.blueprints.persistence.impl;

import java.util.Objects;

/**
 * Representa una tupla genérica con dos elementos.
 *
 * @param <T1> el tipo del primer elemento
 * @param <T2> el tipo del segundo elemento
 */
public class Tuple<T1, T2> {

    public final T1 t1;
    public final T2 t2;

    /**
     * Construye una nueva tupla con los elementos especificados.
     *
     * @param o1 el primer elemento
     * @param o2 el segundo elemento
     */
    public Tuple(T1 t1, T2 t2) {
        super();
        this.t1 = t1;
        this.t2 = t2;
    }

    /**
     * Devuelve el primer elemento de la tupla.
     *
     * @return el primer elemento
     */
    public T1 getElem1() {
        return t1;
    }

    /**
     * Devuelve el segundo elemento de la tupla.
     *
     * @return el segundo elemento
     */
    public T2 getElem2() {
        return t2;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.t1);
        hash = 17 * hash + Objects.hashCode(this.t2);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple<?, ?> other = (Tuple<?, ?>) obj;
        if (!Objects.equals(this.t1, other.t1)) {
            return false;
        }
        if (!Objects.equals(this.t2, other.t2)) {
            return false;
        }
        return true;
    }
}
