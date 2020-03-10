package model;


import java.io.*;
import java.util.*;


/**
 * Clase que define la aplicación Temis
 *
 * @author Daniel Prieto Fernández
 * Silvia Tomey Prieto
 * Patricia Matos Meza
 * @version 3/03/2020
 */
public class Temis {
    private static Temis pTemis;
    private String usuarioAdmin = "admon";
    private String contrasenaAdmin = "adminadmin";
    private Usuario usuarioConectado = null;
    private boolean representanteLegal = false;
    private boolean adminFlag = false;
    private Map<Integer, Usuario> usuarios = new HashMap<>();
    private Map<String, Colectivo> colectivos = new HashMap<>();
    private Map<Actor, Proyecto> proyectos = new HashMap<>();


    /**
     * Constructor de la clase Temis
     */
    private Temis() {
    }

    /**
     * Devuelve instancia del singleton
     *
     * @return pTemis instancia de Temis
     */
    public static Temis getInstance() {
        if (pTemis == null) {
            pTemis = new Temis();
        }
        return pTemis;
    }

    /**
     * Funcion que guarda en el archivo Temis.txt los usuarios, los colectivos y
     * los proyectos existente en la aplicación.
     *
     * @throws IOException
     */
    public void escribirFichero() throws IOException {
        FileOutputStream fos = new FileOutputStream("Temis.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);


        // Escribe usuarios

        oos.writeObject(this.usuarios);

        // Escribe usuarios

        oos.writeObject(this.colectivos);

        // Escribe proyectos

        oos.writeObject(this.proyectos);

        oos.close();
    }

    /**
     * Funcion que lee del archivo Temis.txt todos los objetos existentes de la aplicación
     *
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void leerFichero() throws IOException {
        FileInputStream fis = new FileInputStream("Temis.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object objLeido;
        Map<Integer, Usuario> usuarioMap;
        Map<String, Colectivo> colectivoMap;
        Map<Actor, Proyecto> proyectoMap;


        try {
            // Leer usuarios

            objLeido = ois.readObject();
            usuarioMap = (Map<Integer, Usuario>) objLeido;
            pTemis.usuarios = usuarioMap;

            // Leer colectivos

            objLeido = ois.readObject();
            colectivoMap = (Map<String, Colectivo>) objLeido;
            pTemis.colectivos = colectivoMap;

            // Leer proyectos

            objLeido = ois.readObject();
            proyectoMap = (Map<Actor, Proyecto>) objLeido;
            pTemis.proyectos = proyectoMap;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ois.close();
    }

    /**
     * Funcion que permite al usuario registrarse
     *
     * @param dni        dni del usuario
     * @param nombre     nombre del usuario
     * @param contrasena contrasena del usuario
     * @return True/False dependiendo del éxito
     */
    public boolean registrarse(String dni, String nombre, String contrasena) {
        Usuario usuario;

        if (this.usuarios.containsKey(dni)) { //comprobar nombre si existe
            return false;
        }
        // usuario = new Usuario(dni, nombre, contrasena);
        return true;
    }

    /**
     * Funcion que le permite al usuario iniciar sesion en la aplicación
     *
     * @param id         nombre o dni del usuario
     * @param contrasena del usuario
     * @return True/False dependiendo del éxito
     */
    public boolean iniciaSesion(String id, String contrasena) {
        if (id == "" || contrasena == "") return false;
        if (id.length() == 9) {
            if (Character.isLetter(id.charAt(8))) {
                if (this.usuarios.containsKey(id)) {
                    this.usuarioConectado = this.usuarios.get(id);
                    return true;
                }
            }
            return false;
        } else {
            try {
                Integer.parseInt(id);
                return false;
            } catch (NumberFormatException e) {
                for (Usuario user : this.usuarios.values()) {
                    if (id.equals(user.getNombre())) {
                        this.usuarioConectado = user;
                        return true;
                    }
                }
                return false;
            }
        }
    }

    /**
     * Funcion que permite al usuario cerrar sesión
     */
    public void cierraSesion() {
        if (this.usuarioConectado != null) {
            this.usuarioConectado.setLogueado(false);
            this.usuarioConectado = null;
        }
    }

    /**
     * Funcion que permite añadir un proyecto a la aplicacion
     * @param p Proyecto a añadir
     */
    public void anadirProyecto(Proyecto p) {
        this.proyectos.put(p.getCreador(), p);
    }

    /**
     * Función que permite añadir un colectivo a la aplicación
     * @param c Colectivo a añadir
     */
    public void anadirColectivo(Colectivo c){
        this.colectivos.put(c.getRepresentante().getDni(),c);
    }


}
