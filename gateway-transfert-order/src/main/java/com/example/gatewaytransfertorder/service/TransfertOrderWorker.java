package com.example.gatewaytransfertorder.service;


import com.example.gatewaytransfertorder.model.TransferOrder;
import lombok.extern.java.Log;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

/**
 * @author bean.nguyen
 */
@Component
@Log
public class TransfertOrderWorker {
    private BranchApiService branchApiService;
    private MasterApiService masterApiService;
    private RedissonClient redissonClient;

    @Autowired
    public TransfertOrderWorker(BranchApiService branchApiService,
                                MasterApiService masterApiService,
                                RedissonClient redissonClient) {
        this.branchApiService = branchApiService;
        this.masterApiService = masterApiService;
        this.redissonClient = redissonClient;
    }

    /**
     * Receives a message.
     *
     * @param message    Message that receives from queue.
     *
     */
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(final TransferOrder message) {
        boolean canTransfertOrder = branchApiService.canTransferOrder(message);
        //TODO: insert log db.
        if (canTransfertOrder) {
            //TODO: update redis using redisson.
            log.log(Level.INFO, "Update redis db");
            //TODO: sync data to master
            masterApiService.syncToMaster(message);
            //TODO: delete order in branch.
            branchApiService.deleteOrder(message);
        }
//         log.log(Level.INFO, "Message receive: {0}", message);
//        gatewayApiService.send(message);
        /*1. call api to check can transfer
          2. if true, then insert log to serve statictics.
          3. call master to sync data.
          4. update redis.
          4. call api to delete branch db
        * */

    }
}
