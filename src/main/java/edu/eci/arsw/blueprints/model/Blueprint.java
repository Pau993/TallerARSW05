package edu.eci.arsw.blueprints.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Representa un plano con un autor, un nombre y una lista de puntos.
 */
public class Blueprint {

    private String author;
    private List<Point> points;
    private String name;

    public Blueprint() {
    }
    
    /**
     * Construye un nuevo Blueprint con el autor, nombre y puntos especificados.
     *
     * @param author el autor del plano
     * @param name el nombre del plano
     * @param pnts los puntos del plano
     */
    public Blueprint(String author, String name, Point[] pnts) {
        this.author = author;
        this.name = name;
        this.points = new ArrayList<>(Arrays.asList(pnts));
    }

    /**
     * Devuelve el nombre del plano.
     *
     * @return el nombre del plano
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el autor del plano.
     *
     * @return el autor del plano
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Devuelve la lista de puntos en el plano.
     *
     * @return la lista de puntos
     */
    public List<Point> getPoints(){
        return points;
    }

    /**
     * Añade un punto al plano.
     *
     * @param p el punto a añadir
     */
    public void addPoint(Point p) {
        points.add(p);
    }

    @Override
    public String toString() {
        return "Blueprint{" + "author=" + author + ", name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Blueprint other = (Blueprint) obj;
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (points.size() != other.points.size()) {
            return false;
        }
        for (int i = 0; i < this.points.size(); i++) {
            if (!this.points.get(i).equals(other.points.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void setPoints(List<Point> points) { // Agrega este setter
        this.points = points;
    }
}
