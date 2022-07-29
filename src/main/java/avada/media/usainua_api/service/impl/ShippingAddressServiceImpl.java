package avada.media.usainua_api.service.impl;

import avada.media.usainua_api.model.delivery.ShippingAddress;
import avada.media.usainua_api.repository.ShippingAddressRepo;
import avada.media.usainua_api.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepo shippingAddressRepo;

    @Override
    public void saveShippingAddress(ShippingAddress shippingAddress) {
        shippingAddressRepo.save(shippingAddress);
    }

    @Override
    public void deleteShippingAddressById(Long id) {
        shippingAddressRepo.deleteById(id);
    }

    @Override
    public List<ShippingAddress> getAllShippingAddressByUserId(Long id) {
        return shippingAddressRepo.findByUserId(id);
    }

    @Override
    public ShippingAddress getDefaultShippingAddress() {
        return shippingAddressRepo.findShippingAddressByMainEquals(true);
    }

    @Override
    public ShippingAddress getShippingAddressById(Long id) {
        return shippingAddressRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found Banking Card with id: " + id));
    }

}
