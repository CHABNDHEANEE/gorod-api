package com.chabndheanee.gorodapi.dao;
import com.chabndheanee.gorodapi.exception.ServiceDeleteException;
import com.chabndheanee.gorodapi.model.Service;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ServiceDaoTest {
    private final ServiceDao serviceDao;

    private final Service ser4 = Service.builder()
            .id(3)
            .name("Холодная вода")
            .children(new ArrayList<>())
            .build();
    private final Service ser5 = Service.builder()
            .id(4)
            .name("Горячая вода")
            .children(new ArrayList<>())
            .build();
    private final Service ser3 = Service.builder()
            .id(2)
            .name("Вода")
            .children(new ArrayList<>(Arrays.asList(ser4, ser5)))
            .build();
    private final Service ser2 = Service.builder()
            .id(1)
            .name("Отопление")
            .children(new ArrayList<>())
            .build();
    private final Service ser1 = Service.builder()
            .id(0)
            .name("Жилищно-коммунальные услуги")
            .children(new ArrayList<>(Arrays.asList(ser2, ser3)))
            .build();
    private final Service ser7 = Service.builder()
            .id(6)
            .name("Ясли")
            .children(new ArrayList<>())
            .build();
    private final Service ser8 = Service.builder()
            .id(7)
            .name("Старшая группа")
            .children(new ArrayList<>())
            .build();
    private final Service ser6 = Service.builder()
            .id(5)
            .name("Детский сад")
            .children(new ArrayList<>(Arrays.asList(ser7, ser8)))
            .build();
    private final Service ser9 = Service.builder()
            .id(8)
            .name("Электроэнергия")
            .children(new ArrayList<>())
            .build();

    @Test
    public void getHierarchyTest() {
        List<Service> result = serviceDao.getHierarchy();

        assertThat(result.size(), is(3));
        assertThat(result.get(0), is(ser1));
        assertThat(result.get(0).getChildren().get(0), is(ser2));
        assertThat(result.get(0).getChildren().get(1), is(ser3));
        assertThat(result.get(0).getChildren().get(1).getChildren().get(0), is(ser4));
        assertThat(result.get(0).getChildren().get(1).getChildren().get(1), is(ser5));

        assertThat(result.get(1), is(ser6));
        assertThat(result.get(1).getChildren().get(0), is(ser7));
        assertThat(result.get(1).getChildren().get(1), is(ser8));

        assertThat(result.get(2), is(ser9));
    }

    @Test
    public void deleteService_WithoutForce_WithoutErrors() {
        serviceDao.deleteService(false, 8);

        List<Service> result = serviceDao.getHierarchy();

        assertThat(result.size(), is(2));
    }

    @Test
    public void deleteService_WithForce() {
        serviceDao.deleteService(true, 0);

        List<Service> result = serviceDao.getHierarchy();

        assertThat(result.size(), is(2));
    }

    @Test
    public void deleteService_WithChildService_AndExpectError() {
        Exception exception = assertThrows(ServiceDeleteException.class, () ->
                serviceDao.deleteService(false, 0));

        assertThat(exception.getMessage(), is("Невозможно удаление! Данная услуга содержит дочерние услуги!"));
    }
}
