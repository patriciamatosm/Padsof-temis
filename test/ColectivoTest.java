import org.junit.*;
import model.*;
import static org.junit.Assert.*;

public class ColectivoTest {

    private Colectivo c;
    private Usuario u;

    @Before
    public void setUp () {
        this.u = new Usuario("Daniel", "12345678A", "12345");
        this.c = new Colectivo("Test descripcion", "Test", u);
    }

    @Test
    public void unirRep() {
        //NO deberia poder unirse el representante
        assertFalse(this.c.unirse(u));
    }

    @Test
    public void unirse() {
        Usuario user = new Usuario("Sergio", "00000000A", "00000");
        this.c.unirse(user);
        assertTrue(c.getListaUsuario().contains(user));
        //Que no se puedan unir usuarios repetidos
        assertFalse(c.unirse(user));
    }

    @Test
    public void abandonar() {
        Usuario user = new Usuario("Sergio", "00000000A", "00000");
        assertFalse(c.abandonar(user));
        c.unirse(user);
        assertTrue(c.getListaUsuario().contains(user));
        c.abandonar(user);
        assertFalse(c.getListaUsuario().contains(user));
    }

    @Test
    public void subscribirseNoticias() {
        Usuario user = new Usuario("Sergio", "00000000A", "00000");
        //Si no esta en el colectivo no deberia dejarle subscribirse a noticias
        this.c.subscribirseNoticias(user);
        assertFalse(user.getSuscritoNoticias().contains(this.c));
        //Despues de unirse si
        this.c.unirse(user);
        assertTrue(c.getListaUsuario().contains(user));
        this.c.subscribirseNoticias(user);
        assertTrue(user.getSuscritoNoticias().contains(this.c));
    }

    @Test
    public void crearSubcolectivo() {
        c.crearSubcolectivo("Test1", "Description1");
        assertNotNull(c.getSubcolectivos());
        assertEquals(c, c.getSubcolectivos().get(0).getPadre());
        c.crearSubcolectivo("Test2", "Description2");
        assertNotNull(c.getSubcolectivos().get(1));
        assertEquals(c.getSubcolectivos().get(0).getPadre(), c.getSubcolectivos().get(1).getPadre());
    }

    @Test
    public void afinidad() {
        Colectivo c1 = new Colectivo("Colectivo test 1", "Colectivo 1", u);
        Colectivo c2 = new Colectivo("Colectivo test 2", "Colectivo 2", u);

        Usuario u1 = new Usuario("User 1", "00000001A", "00000");
        Usuario u2 = new Usuario("User 2", "00000002A", "00000");
        Usuario u3 = new Usuario("User 3", "00000003A", "00000");
        Usuario u4 = new Usuario("User 4", "00000004A", "00000");
        Usuario u5 = new Usuario("User 5", "00000005A", "00000");

        assertEquals(0.0, c1.calcularAfinidad(c2), 0);

        c1.unirse(u1);

        assertEquals(0.0, c1.calcularAfinidad(c2), 0);
        assertEquals(0.0, c2.calcularAfinidad(c1), 0);

        c2.unirse(u1);

        assertEquals(100.0, c1.calcularAfinidad(c2), 0);
        assertEquals(100.0, c2.calcularAfinidad(c1), 0);

        c1.unirse(u2);

        assertEquals(100.0, c1.calcularAfinidad(c2), 0);
        assertEquals(50.0, c2.calcularAfinidad(c1), 0);

    }
}
