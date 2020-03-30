package model;

import java.util.*;
import java.io.Serializable;

/**
 * Clase que define a un colectivo
 *
 * @author Daniel Prieto Fernández
 *         Silvia Tomey Prieto
 *         Patricia Matos Meza
 *
 * @version 3/03/2020
 */
@SuppressWarnings("unused")
public class Colectivo extends Actor implements Serializable {
    private String descripcion;
    private Usuario representante; //DISEÑO
    private ArrayList<Usuario> listaUsuario = new ArrayList<>();
    private Colectivo padre = null;
    private ArrayList<Colectivo> subcolectivos = new ArrayList<>();
    private List<Notificacion> notificacionesRecibidas = new ArrayList<>();

    public Colectivo(String descripcion, String nombre, Usuario representante) {
        super(nombre);
        this.descripcion = descripcion;
        this.representante = representante;
    }

    /**
     * Función que devuelve la lista de usuarios de un colectivo
     * @return Usuarios en un colectivo
     */
    public ArrayList<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    /**
     * Función que devuelve la lista de subcolectivos
     * @return Subcolectivos del colectivo
     */
    public ArrayList<Colectivo> getSubcolectivos() {
        return subcolectivos;
    }

    /**
     * Funcion que devuelve las notificaciones recibidas
     * @return notificaciones recibidas
     */
    public List<Notificacion>  getNotificacionesRecibidas() {
        return notificacionesRecibidas;
    }

    /**
     * Funcion que anade notificacion de un proyecto a un colectivo
     * @param n notificacion
     */
    public void addNotificacion(Notificacion n){
        this.notificacionesRecibidas.add(n);
    }

    /**
     * Función que devuelve la descripción de un colectivo
     * @return String con la descripción del colectivo
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Función que modifica la descripción de un colectivo
     * @param descripcion del proyecto
     */
    public void setDescripcion(String descripcion) {
        if (descripcion.length() > 500){
            return;
        }
        this.descripcion = descripcion;
    }

    /**
     * Función que devuelve el representante de un colectivo
     * @return Usuario que creo el colectivo
     */
    public Usuario getRepresentante() {
        return representante;
    }

    /**
     * Función que devuelve el colectivo padre si lo tiene
     * @return Colectivo padre del colectivo
     */
    public Colectivo getPadre() {
        return padre;
    }

    /**
     * Función que añade un usuario a un colectivo
     * @param usuario que se unira al colectivo si no estaba anteriormente
     * @return true si se añade el usuario a la lista
     *         false si ya estaba en el colectivo
     */
    public boolean unirse(Usuario usuario) {
        if (inPadre(usuario)) {
            return false;
        } else {
            this.listaUsuario.add(usuario);
            return true;
        }
    }

    /**
     * Función recursiva que compureba si un usuario esta en el colectivo o en alguno de sus padres
     * @param usuario que se comprobará si está en el colectivo o en el padre
     * @return true si esta en el colectivo o en alguno de los padres
     *         false si no se encuentra
     */
    private boolean inPadre(Usuario usuario) {  //DISEÑO (no se si debe ser privado)
        if (this.listaUsuario.contains(usuario) || usuario.equals(this.representante)) {
            return true;
        } else {
            if (this.getPadre() == null) return false;
            return this.padre.inPadre(usuario);
        }
    }

    /**
     * Función que quita a un usuario de la lista de usuarios del colectivo
     * @param usuario que se quiere eliminar de un colectivo
     * @return true si se ha eliminado el usuario de la lista
     * false si no estaba en la lista
     */
    public boolean abandonar(Usuario usuario) {
        if (this.listaUsuario.contains(usuario)) {
            this.listaUsuario.remove(usuario);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Función que añade a un colectivo a los colectivos de los que quiere recibir noticias un usuario
     * @param usuario que recibira las noticias del colectivo
     * @return true si se añade el usuario correctamente
     * false si el usuario no esta en el colectivo o no se puede añadir a la lista
     */
    public boolean subscribirseNoticias(Usuario usuario) {
        if (this.listaUsuario.contains(usuario)) {
            usuario.addSuscritoNoticias(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Función que crea un subcolectivo, deja al colectivo como padre y se inicia uno nuevo
     * @param nombre      del subcolectivo
     * @param descripcion subcolectivo
     * @return true si se crea correctamente
     * false si da algun error
     */
    public boolean crearSubcolectivo(String nombre, String descripcion) {
        Colectivo colectivoHijo = new Colectivo(descripcion, nombre, this.representante);
        colectivoHijo.padre = this;
        this.subcolectivos.add(colectivoHijo);
        return true;
    }

    /**
     * Función que devuelve el % de usuarios que tiene en común con el colectivo
     * @param cole colectivo del que se quiere calcular la afinidad
     * @return Double con el % de afinidad
     */
    public Double calcularAfinidad(Colectivo cole) {    //DISEÑO cambiar tipo
        double cont = 0, nUsuarios = 0;
        for (Usuario usuario: cole.listaUsuario) {
            nUsuarios++;
            if (this.listaUsuario.contains(usuario)) {
                cont++;
            }
        }
        if(nUsuarios == 0){
            return 0.0;
        }
        return cont / nUsuarios * 100;
    }
}