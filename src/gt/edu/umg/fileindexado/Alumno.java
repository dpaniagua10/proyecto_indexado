package gt.edu.umg.fileindexado;

public class Alumno {

	private String nombre;
	private long carne;
	private short grado;
	private byte edad;
	private float nota;
	private boolean estado;
	
	public Alumno() {
		//constructor sin argumentos 
	}
	
	public Alumno(String n, long c, short g, byte e, float nn, boolean es) {
	
		nombre = n;
		carne = c;
		grado = g;
		edad = e;
		nota = nn;
		estado = es;
	}
	
	public String getNombre() { //devuelve es el nombre
		return nombre;
	}
	public void setNombre(String nombre) { // 
		this.nombre = nombre; 
	}
	public long getCarne() {
		return carne;
	}
	public void setCarne(long carne) {
		this.carne = carne;
	}
	public short getGrado() {
		return grado;
	}
	public void setGrado(short grado) {
		this.grado = grado;
	}
	public byte getEdad() {
		return edad;
	}
	public void setEdad(byte edad) {
		this.edad = edad;
	}
	public float getNota() {
		return nota;
	}
	public void setNota(float nota) {
		this.nota = nota;
	}
	public boolean getEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
}
