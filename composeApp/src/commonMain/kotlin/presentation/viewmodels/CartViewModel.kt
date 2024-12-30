package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Enums.DeliveryMethodEnum
import domain.Enums.PaymentMethod
import kotlinx.coroutines.flow.StateFlow

class CartViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _subtotal =  savedStateHandle.getStateFlow("subtotal", 0L)
    private var _total =  savedStateHandle.getStateFlow("total", 0L)
    private var _deliveryFee =  savedStateHandle.getStateFlow("deliveryFee", 0L)


    val subtotal: StateFlow<Long>
        get() = _subtotal

    val total: StateFlow<Long>
        get() = _total

    val deliveryFee: StateFlow<Long>
        get() = _deliveryFee

    fun setSubTotal(subtotal: Long) {
        savedStateHandle["subtotal"] = subtotal
    }

    fun setTotal(total: Long) {
        savedStateHandle["total"] = total
    }

    fun setDeliveryFee(deliveryFee: Long) {
        savedStateHandle["deliveryFee"] = deliveryFee
    }




}