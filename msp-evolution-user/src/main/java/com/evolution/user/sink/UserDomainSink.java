//package com.evolution.user.sink;
//
//import com.evolution.user.core.UserDomain;
//import com.evolution.user.query.model.UserModel;
//import com.evolution.user.query.repository.UserModelRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//
//@EnableBinding(Sink.class)
//public class UserDomainSink {
//
//    private final static Logger logger = LoggerFactory.getLogger(UserDomainSink.class);
//
//    private final UserModelRepository userModelRepository;
//
//    @Autowired
//    public UserDomainSink(UserModelRepository userModelRepository) {
//        this.userModelRepository = userModelRepository;
//    }
//
//    @StreamListener(Sink.INPUT)
//    public void sink(UserDomain domain) {
//        logger.info("Catch domain:" + domain);
//
//        UserModel model = UserModel.builder()
//                .key(domain.getKey())
//                .eventNumber(domain.getEventNumber())
//                .username(domain.getUsername())
//                .password(domain.getPassword())
//                .firstName(domain.getFirstName())
//                .lastName(domain.getLastName())
//                .nickname(domain.getNickname())
//                .build();
//        userModelRepository.save(model);
//    }
//}
