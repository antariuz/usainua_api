package avada.media.usainua_api.service;

import avada.media.usainua_api.model.delivery.ShippingAddress;

import java.util.List;

public interface ShippingAddressService {

    void saveShippingAddress(ShippingAddress shippingAddress);

    void deleteShippingAddressById(Long id);

    List<ShippingAddress> getAllShippingAddressByUserId(Long id);

    ShippingAddress getDefaultShippingAddress();

    ShippingAddress getShippingAddressById(Long id);

}
