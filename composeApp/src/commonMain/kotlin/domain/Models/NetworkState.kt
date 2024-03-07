package domain.Models

data class NetworkState(val state: CRUD, val isDone: Boolean = false, val isErr: Boolean = false)