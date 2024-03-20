package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.DeliveryLocation
import domain.Models.PaymentMethod
import domain.Models.ServiceTypeItem
import domain.Models.ServiceTypeSpecialist
import domain.Models.UnsavedAppointment
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

class CartViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _subtotal =  savedStateHandle.getStateFlow("subtotal", 0)
    private var _total =  savedStateHandle.getStateFlow("total", 0)
    private var _deliveryFee =  savedStateHandle.getStateFlow("deliveryFee", 0)
    private var _deliveryLocation = savedStateHandle.getStateFlow("deliveryLocation", DeliveryLocation.HOME_DELIVERY.toPath())
    private var _paymentMethod = savedStateHandle.getStateFlow("paymentMethod", PaymentMethod.CARD_PAYMENT.toPath())


    val subtotal: StateFlow<Int>
        get() = _subtotal

    val total: StateFlow<Int>
        get() = _total

    val deliveryFee: StateFlow<Int>
        get() = _deliveryFee

    val deliveryLocation: StateFlow<String>
        get() = _deliveryLocation

    val paymentMethod: StateFlow<String>
        get() = _paymentMethod

    fun setSubTotal(subtotal: Int) {
        savedStateHandle["subtotal"] = subtotal
    }

    fun setTotal(total: Int) {
        savedStateHandle["total"] = total
    }

    fun setDeliveryFee(deliveryFee: Int) {
        savedStateHandle["deliveryFee"] = deliveryFee
    }

    fun setDeliveryLocation(deliveryLocation: String) {
        savedStateHandle["deliveryLocation"] = deliveryLocation
    }

    fun setPaymentMethod(paymentMethod: String) {
        savedStateHandle["paymentMethod"] = paymentMethod
    }




}