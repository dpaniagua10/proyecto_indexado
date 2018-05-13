package gt.edu.umg.fileindexado;

/*Daniel Paniagua 7690-17-6640 programacion 1 "B"
 * programa para registro de alumnos de una escuela
*/
import java.io.*;
import java.util.*;

public class ArchivoIndexado {

	static final String datos = "C:/Users/Rosy Perez/Desktop/java/datos_reg.txt";
	static final String index = "C:/Users/Rosy Perez/Desktop/java/index_reg.txt";
	Scanner scan = new Scanner(System.in);
	static File fdat = new File(datos);
	static File find = new File(index);
	static long pos;
	static long cc;
	
	List<Alumno> lista = new ArrayList<Alumno>();
	
	public static void main(String[] args) {
	
		ArchivoIndexado principal = new ArchivoIndexado();
		principal.menu();
	}
	
	public void menu() {
		
		byte opcion;
		long carne;
		
		do {
			System.out.println("\n\tBienvenido!!!!");
			System.out.println("\tElije una opcion \n");
			System.out.println("1.\tVerificar Archivo\n");
			System.out.println("2.\tCrear Archivo\n");
			System.out.println("3.\tRegistrar Alumno\n");
			System.out.println("4.\tBuscar Alumno\n");
			System.out.println("5.\tListar Alumnos\n");
			System.out.println("6.\tModificar Alumno\n");
			System.out.println("7.\tSalir\n");
			
			opcion=scan.nextByte();
			
			switch(opcion) {
				case 1:
					if(validarArchivo()) {
						System.out.println("\n\tEl archivo ya existe");
					}else {
						System.out.println("\n\tEl archivo no existe");
					}
					break;
				case 2: 
					crearArchivo();
					break;
				case 3:	// registrar alumno
					scan.nextLine();
					registrarAlumno();
					if(buscarCarne(cc)) {
						System.out.println("Carne ya registrado");
					}else {
						guardarAlumno();
					}
					break;
				case 4: 
					System.out.println("Ingrese carne a buscar");
					cc= scan.nextLong();
					if(buscarCarne(cc)) {	
						mostrarAlumno();
					}else{
						System.out.println("Carne no registrado!!!\n");
					}
					break;
				case 5:
					listarAlumno();
					break;
				case 6:
					System.out.println("Ingrese carne a buscar");
					carne = scan.nextLong();
					if(buscarCarne(carne)) {	
						modificarAlumno();
						guardaModificacion();
					}else{
						System.out.println("Carne no registrado!!!\n");
					}
					break;
				case 7:
					System.out.println("\n Vuelve pronto");
					break;
				default:
					System.out.println("Opcion no valida");		
			}
		}while(opcion!=7);
		
	}
	
	public boolean validarArchivo() {
		
		File file = new File(datos);
		return file.exists();
	}
	
	public void crearArchivo() {
		
		if(!validarArchivo()) {
			try {
				fdat.createNewFile();
				find.createNewFile();
				System.out.println("\tArchivo creado exitosamente \n");
				System.out.println(fdat.getAbsolutePath()); 
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}else {
			System.out.println("\tArchivo ya existe \n");
			System.out.println(fdat.getAbsolutePath());
			
		}
	}
	
	public void registrarAlumno() {
		
		Alumno x = new Alumno();
		lista.clear();
		
		String n;
		short g; 
		byte e; 
		float nn; 
		boolean es;
		System.out.println("Nombre del alumno: ");
		n = scan.nextLine();
		System.out.println("Carne: ");
		cc = scan.nextLong();
		System.out.println("Grado: ");
		g = scan.nextShort();
		System.out.println("Edad: ");
		e = scan.nextByte();
		System.out.println("Nota: ");
		nn = scan.nextFloat();
		if(nn>=61) {
			es = true;
			System.out.println("Aprobado");
		}else {
			es = false;
			System.out.println("No Aprobado");
		}
		
		x.setNombre(n);
		x.setCarne(cc);
		x.setGrado(g);
		x.setEdad(e);
		x.setNota(nn);
		x.setEstado(es);
	
		lista.add(x);
	}
	
	public void guardarAlumno() {
		
		pos = 0;

		try {
			RandomAccessFile file = new RandomAccessFile(fdat,"rw");
			for (Alumno x : lista) {
				file.seek(file.length());
				pos = file.getFilePointer();
				file.writeUTF(x.getNombre());
				file.writeLong(x.getCarne());
				file.writeShort(x.getGrado());
				file.writeByte(x.getEdad());
				file.writeFloat(x.getNota());
				file.writeBoolean(x.getEstado());
			}
			file.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			RandomAccessFile ifile = new RandomAccessFile(find,"rw");
			for(Alumno x : lista) {
				ifile.seek(ifile.length());
				ifile.writeLong(x.getCarne());
				ifile.writeLong(pos);
			}
			ifile.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocurrio el siguiente error: "+e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		
		
		
	}
	
	public boolean buscarCarne(long carne){
		
		long ntmp;
		boolean finfile = false;
		
		try {
			RandomAccessFile file = new RandomAccessFile(find,"rw");	
			do {
				try {
					ntmp = file.readLong();
					pos = file.readLong();
					if(carne==ntmp){
						//System.out.println(ntmp);
						//System.out.println(pos);
						finfile = true;
						return true;
					}
				}catch(EOFException e) {
					finfile = true;
				}
			}while(!finfile);
			file.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return false;
				
	}
	
	public void modificarAlumno() {
		
		String n;
		short g; 
		byte e; 
		float nn; 
		boolean es;
		
		try {
			
			RandomAccessFile file = new RandomAccessFile(fdat,"rw");
			lista.clear();
			file.seek(pos);
			n = file.readUTF();
			cc = file.readLong();
			g = file.readShort();
			e = file.readByte(); 
			System.out.println("\nAlumno: "+n);
			System.out.println("Carne: "+cc);
			System.out.println("Grado: "+g);
			System.out.println("Edad: "+e);
			System.out.println("Nota: "+file.readFloat());
			System.out.println("\nIngrese nueva nota: ");
			nn = scan.nextFloat();
			if(nn>=61) {
				es = true;
				System.out.println("Aprobado");
			}else {
				es = false;
				System.out.println("No Aprobado");
			}
			file.close();
			Alumno x = new Alumno(n,cc,g,e,nn,es);
			lista.add(x);
			//System.out.println(es);
		} catch (IOException ei) {
			System.out.println(ei.getMessage());
		}
		
	}
	
	public void mostrarAlumno() {
		
		try {
			RandomAccessFile file = new RandomAccessFile(fdat,"rw");
			file.seek(pos);
			System.out.println("\nAlumno: "+file.readUTF());
			System.out.println("Carne: "+file.readLong());
			System.out.println("Grado: "+file.readShort());
			System.out.println("Edad: "+file.readByte());
			System.out.println("Nota: "+file.readFloat());
			String condicion;
			if(file.readBoolean()) {
				condicion = "Aprobado";
			}else {
				condicion = "No Apobado";
			}
			System.out.println("Estado: "+condicion);
			file.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void listarAlumno() {
		
		boolean finfile = false;
		try {
			RandomAccessFile file = new RandomAccessFile(fdat,"rw");
			do {
				try {
					System.out.println("\nAlumno: "+file.readUTF());
					System.out.println("Carne: "+file.readLong());
					System.out.println("Grado: "+file.readShort());
					System.out.println("Edad: "+file.readByte());
					System.out.println("Nota: "+file.readFloat());
					String condicion;
					if(file.readBoolean()) {
						condicion = "Aprobado";
					}else {
						condicion = "No Apobado";
					}
					System.out.println("Estado: "+condicion);
				}catch(EOFException e) {
					finfile = true;
				}
			}while(!finfile);
			file.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	public void guardaModificacion() {
		
		try {
			RandomAccessFile file = new RandomAccessFile(fdat,"rw");
			for (Alumno x : lista) {
				file.seek(pos);
				//pos = file.getFilePointer();
				file.writeUTF(x.getNombre());
				file.writeLong(x.getCarne());
				file.writeShort(x.getGrado());
				file.writeByte(x.getEdad());
				file.writeFloat(x.getNota());
				file.writeBoolean(x.getEstado());
			}
			file.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
