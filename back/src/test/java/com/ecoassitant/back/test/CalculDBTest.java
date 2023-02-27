package com.ecoassitant.back.test;

import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.repository.CalculRepository;
import com.ecoassitant.back.service.CalculService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CalculDBTest {
    @InjectMocks
    private CalculService calculService;
    @Mock
    CalculRepository repo;
    @Autowired
    TestEntityManager testEm;
    @Test
    void calculTest1(){
        var list = List.of(
             new CalculEntity()
        );

        BDDMockito.doReturn(list).when(repo).findByNbCalcul(1);
    }
}
