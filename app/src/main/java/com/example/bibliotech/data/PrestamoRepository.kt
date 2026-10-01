import com.example.bibliotech.data.PrestamoDao
import com.example.bibliotech.model.Prestamo

class PrestamoRepository(private val prestamoDao: PrestamoDao) {

    suspend fun insertar(prestamo: Prestamo) {
        prestamoDao.insertar(prestamo)
    }

    suspend fun obtenerPrestamosActivos(): List<Prestamo> {
        return prestamoDao.obtenerPrestamosActivos()
    }

    suspend fun obtenerPrestamoPorId(id: Int): Prestamo? {
        return prestamoDao.obtenerPrestamoPorId(id)
    }

    suspend fun actualizarPrestamo(prestamo: Prestamo) {
        prestamoDao.actualizarPrestamo(prestamo)
    }
}