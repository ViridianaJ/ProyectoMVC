package mx.unam.aragon.ico.te.restaurantesmvc.repositorios;

import mx.unam.aragon.ico.te.restaurantesmvc.modelos.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

// Esta interfaz hereda de JpaRepository y maneja objetos de tipo Restaurante.
// Con esto se obtiene acceso a todas las operaciones DML para la tabla correspondiente.
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {

}




