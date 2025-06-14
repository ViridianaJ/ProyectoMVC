package mx.unam.aragon.ico.te.restaurantesmvc.controladores;

import mx.unam.aragon.ico.te.restaurantesmvc.modelos.Restaurante;
import mx.unam.aragon.ico.te.restaurantesmvc.servicios.RestauranteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/restaurante")
public class RestaurantesController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantesController.class);

    @Autowired
    private RestauranteService restauranteService;

    // Página principal
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // Mostrar un restaurante fijo (prueba)
    @GetMapping("/demo")
    public String restauranteDemo(Model model) {
        Restaurante restaurante = new Restaurante(
                1,
                "Tacos El Gordo",
                "Comida mexicana tradicional",
                "CDMX",
                "Mexicana",
                "Lun–Dom 10:00–22:00",
                "https://worldtraveller73.com/wp-content/uploads/2024/10/img_2786.jpeg",
                "555-123-4567",
                "https://tacoselgordo.com",
                4.7
        );
        model.addAttribute("restaurante", restaurante);
        return "restaurante";
    }

    // Formulario para crear restaurante
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("restaurante", new Restaurante());
        return "formRestaurante";
    }

    // Guardar restaurante nuevo
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Restaurante restaurante) {
        logger.info("Guardando restaurante: {}", restaurante);
        restauranteService.guardarRestaurante(restaurante);
        return "redirect:/restaurante/nuevo?exito";
    }

    // 🔍 Buscar por ID
    @GetMapping("/buscar")
    public String buscarPorId(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            try {
                model.addAttribute("restauranteBuscado", restauranteService.getRestaurante(id));
            } catch (EntityNotFoundException e) {
                model.addAttribute("error", "No se encontró un restaurante con ID " + id);
            }
        }
        return "VerRestaurante";
    }

    // 📋 Mostrar todos
    @GetMapping("/buscarTodos")
    public String mostrarTodos(Model model) {
        List<Restaurante> lista = restauranteService.getTodosLosRestaurantes();
        model.addAttribute("todosLosRestaurantes", lista);
        return "VerRestaurante";
    }

    // ✏️ Mostrar todos los restaurantes para editar
    @GetMapping("/editar")
    public String mostrarFormularioEdicion(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Restaurante restaurante = restauranteService.getRestaurante(id);
            model.addAttribute("restaurante", restaurante);
        }
        model.addAttribute("restaurantes", restauranteService.getTodosLosRestaurantes());
        return "EditarRestaurante";
    }

    // 💾 Actualizar restaurante editado
    @PostMapping("/actualizar")
    public String actualizarRestaurante(@ModelAttribute Restaurante restaurante) {
        logger.info("Actualizando restaurante: {}", restaurante);
        restauranteService.guardarRestaurante(restaurante);
        return "redirect:/restaurante/editar?exito";
    }
    // 🗑️ Eliminar restaurante por ID
    @PostMapping("/delete")
    public String eliminarRestaurante(@RequestParam Integer id) {
        boolean eliminado = restauranteService.eliminarRestaurante(id);
        if (eliminado) {
            logger.info("Restaurante con ID {} eliminado correctamente.", id);
            return "redirect:/restaurante/eliminar?eliminado";
        } else {
            logger.warn("No se encontró el restaurante con ID {} para eliminar.", id);
            return "redirect:/restaurante/eliminar?error";
        }
    }

    // 🧾 Vista para mostrar todos los restaurantes a eliminar
    @GetMapping("/eliminar")
    public String mostrarEliminarRestaurantes(Model model) {
        List<Restaurante> lista = restauranteService.getTodosLosRestaurantes();
        model.addAttribute("restaurantes", lista);
        return "EliminarRestaurante";
    }


}

