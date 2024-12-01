package domain.Enums

enum class DatabaseEnum {
    APP_DATABASE_NAME;
    fun toPath() = when (this) {
        APP_DATABASE_NAME -> "app_database_name"
    }
}