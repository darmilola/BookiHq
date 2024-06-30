package domain.Models

import domain.Enums.CRUD

data class NetworkState(val state: CRUD, val isDone: Boolean = false, val isErr: Boolean = false)