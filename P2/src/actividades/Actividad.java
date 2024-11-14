package actividades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Actividad implements Serializable{
	String tipo;
	String descripcion;
	
	String objetivo;
	
	String nivelDificultad;
	
	double duracion;
	
	List<Actividad> ActividadesPrerrequisito;
	
	Date fechaLim;
	
	List<Actividad> actividadesOpcionales;
	
	String estado;
	
	boolean completado;
	
	boolean obligatoria;
	
	List<Reseña> reseñas;
	
	List<Double> ratings;
	
	double calificacion;
	
	double ratingProm;
	
	public Actividad(String tipo,String descripcion, String objetivo, String nivelDificultad, double duracion,
			 Date fechaLim, boolean obligatoria) {
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		this.nivelDificultad = nivelDificultad;
		this.duracion = duracion;
		this.ActividadesPrerrequisito = new ArrayList<>();
		this.fechaLim = fechaLim;
		this.actividadesOpcionales = new ArrayList<>();
		this.estado = "No enviada";
		this.completado = false;
		this.obligatoria = obligatoria;
		this.reseñas = new ArrayList<>();
		this.ratings = new ArrayList<>();
		this.ratingProm = 0;
		this.calificacion = -1;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getNivelDificultad() {
		return nivelDificultad;
	}

	public void setNivelDificultad(String nivelDificultad) {
		this.nivelDificultad = nivelDificultad;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public List<Actividad> getActividadesPrerrequisito() {
		return ActividadesPrerrequisito;
	}

	public void setActividadesPrerrequisito(List<Actividad> actividadesPrerrequisito) {
		ActividadesPrerrequisito = actividadesPrerrequisito;
	}

	public Date getFechaLim() {
		return fechaLim;
	}

	public void setFechaLim(Date fechaLim) {
		this.fechaLim = fechaLim;
	}

	public List<Actividad> getActividadesOpcionales() {
		return actividadesOpcionales;
	}

	public void setActividadesOpcionales(List<Actividad> actividadesOpcionales) {
		this.actividadesOpcionales = actividadesOpcionales;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	public List<Reseña> getReseñas() {
		return reseñas;
	}

	public void setReseñas(List<Reseña> reseñas) {
		this.reseñas = reseñas;
	}

	public boolean isObligatoria() {
		return obligatoria;
	}

	public void setObligatoria(boolean obligatoria) {
		this.obligatoria = obligatoria;
	}

	public void addReseña(Reseña reseña) {
		this.reseñas.add(reseña);
	}
	
	public abstract void menu();
	
	public void calcularProm() {
		if(this.ratings.size()<0) {
			System.out.println("Todavía no hay ratings");
		}
		else {
		double total = 0;
		for (int i=0; i< this.ratings.size();i++) {
			total += this.ratings.get(i);
			}
		total = total/this.ratings.size();
		this.ratingProm = total;
		}
	}
	
	public void addRating(double rating) {
		this.ratings.add(rating);
		calcularProm();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void addPreReq(Actividad actividad) {
		this.ActividadesPrerrequisito.add(actividad);
	}
	public abstract void completar();

    public abstract void iniciar();

	public double getRatingProm() {
		return ratingProm;
	}

	public void setRatingProm(double ratingProm) {
		this.ratingProm = ratingProm;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}
	public double getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}
}
