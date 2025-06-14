package mx.unam.aragon.ico.te.restaurantesmvc.servicios;

import jakarta.persistence.EntityNotFoundException;
import mx.unam.aragon.ico.te.restaurantesmvc.modelos.Restaurante;
import mx.unam.aragon.ico.te.restaurantesmvc.repositorios.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;


    public boolean guardarRestaurante(Restaurante restaurante) {
        Restaurante resultado = restauranteRepository.save(restaurante);
        return resultado != null;
    }


    public Restaurante getRestaurante(Integer id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante con ID " + id + " no encontrado"));
    }

    // Read -> Obtener todos los restaurantes
    public List<Restaurante> getTodosLosRestaurantes() {
        return restauranteRepository.findAll();
    }

    // Update -> Modificar restaurante existente
    public boolean actualizarRestaurante(Restaurante restaurante) {
        if (restauranteRepository.existsById(restaurante.getId())) {
            restauranteRepository.save(restaurante);
            return true;
        }
        return false;
    }

    // Delete -> Eliminar restaurante por ID
    public boolean eliminarRestaurante(Integer id) {
        if (restauranteRepository.existsById(id)) {
            restauranteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


