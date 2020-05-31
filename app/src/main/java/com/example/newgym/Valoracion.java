package com.example.newgym;

public class Valoracion {

    private Integer id;
    private String fecha;
    private String nombre;
    private String edad;
    private String genero;
    private Integer privacidad;
    private Integer valoracion;

    //Constructor, utilizado en VerValoraciones
    public Valoracion(){

    }

    //Constructor utilizado en VerValoracion
    public Valoracion(Integer id, String fecha, String nombre, String edad, String genero, int privacidad, int valoracion) {
        this.id = id;
        this.fecha = fecha;
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.privacidad = privacidad;
        this.valoracion = valoracion;
    }


    //Métodos getters
    public Integer getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    public int getPrivacidad() {
        return privacidad;
    }

    public int getValoracion(){
        return valoracion;
    }

    public String getStringValoracion() {
        String s = "";
        if(valoracion == 1){
            s = "Muy elevada";
        }else if(valoracion == 2 ){
            s = "Elevada";
        }else if(valoracion == 3){
            s = "Media";
        }else if(valoracion == 4){
            s = "Poca";
        }else if(valoracion == 5){
            s = "Muy poca";
        }
        return s;
    }


    //Métodos setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setPrivacidad(int privacidad) {
        this.privacidad = privacidad;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }


    @Override
    public String toString() {
        return "Valoracion{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad='" + edad + '\'' +
                ", genero='" + genero + '\'' +
                ", privacidad=" + privacidad +
                ", valoracion=" + valoracion +
                '}';
    }
}
