package services.interfaces;

import dto.request.SlotReq;
import dto.response.SlotRes;

public interface DoctorActivity {

    SlotRes generateSlots(SlotReq req);
    
}
