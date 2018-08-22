package com.newframe.repositories.dataSlave.order;

import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderHirer;
import com.newframe.utils.query.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderHirerSlave extends BaseRepository<OrderHirer,Long>  {

}
