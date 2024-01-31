package domain.appointments

interface AppointmentRepository {
    fun get(id: Long)
    fun create()
    fun update(id: Long)
}