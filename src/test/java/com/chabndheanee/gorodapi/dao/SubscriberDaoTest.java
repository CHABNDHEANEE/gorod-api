package com.chabndheanee.gorodapi.dao;

import com.chabndheanee.gorodapi.exception.ObjectAlreadyExistsException;
import com.chabndheanee.gorodapi.model.Service;
import com.chabndheanee.gorodapi.model.Subscriber;
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
public class SubscriberDaoTest {
    private final SubscriberDao subscriberDao;

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

    private final Subscriber sub1 = createSubscriber(0);
    private final Subscriber sub2 = createSubscriber(1);
    private final Subscriber sub3 = createSubscriber(2);
    private final Subscriber sub4 = createSubscriber(3);
    private final Subscriber sub5 = createSubscriber(4);
    private final Subscriber sub6 = createSubscriber(5);
    private final Subscriber sub7 = createSubscriber(6);
    private final Subscriber sub8 = createSubscriber(7);
    private final Subscriber sub9 = createSubscriber(8);
    private final Subscriber sub10 = createSubscriber(9);
    private final Subscriber sub11 = createSubscriber(10);

    @Test
    public void createSubscriber_WithoutErrorTest() {
        int result = subscriberDao.createSubscriber(sub1);
        assertThat(result, is(0));
    }

    @Test
    public void createSubscriber_WithError() {
        Exception exception = assertThrows(ObjectAlreadyExistsException.class, () -> {
            subscriberDao.createSubscriber(sub1);
            subscriberDao.createSubscriber(sub1);
        });

        assertThat(exception.getMessage(), is("Данная пара л/c-услуга уже существует"));
    }

    @Test
    public void getSubscribers_WithoutSorting_WithoutFiltering_WithoutPages() {
        addSubscribers();
        List<Subscriber> result = subscriberDao.getSubscribers(false, null, 1);

        assertThat(result.size(), is(10));

        checkSubscribers(result);
    }

    @Test
    public void getSubscribers_WithSorting_WithoutFiltering_WithoutPages() {
        addSubscribers();
        final Service testSer = Service.builder()
                .id(3)
                .name("Холодная вода")
                .children(new ArrayList<>())
                .build();

        Subscriber testSub = Subscriber.builder()
                .id(10)
                .account("1111")
                .fio("testSub")
                .service(testSer)
                .build();

        subscriberDao.createSubscriber(testSub);
        List<Subscriber> result = subscriberDao.getSubscribers(true, null, 1);

        assertThat(result.get(0), is(testSub));
    }

    @Test
    public void getSubscribers_WithoutSorting_WithFiltering_WithoutPages() {
        addSubscribers();

        List<Subscriber> result = subscriberDao.getSubscribers(false, "account-id5", 1);

        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(sub6));
    }

    @Test
    public void getSubscribers_WithoutSorting_WithoutFiltering_WithPages() {
        addSubscribers();
        subscriberDao.createSubscriber(sub11);

        List<Subscriber> result = subscriberDao.getSubscribers(false, null, 2);

        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(sub11));
    }

    @Test
    public void searchSubscriber_ExcludeChild() {
        addSubscribers();
        sub11.setService(ser3);
        subscriberDao.createSubscriber(sub11);

        List<Subscriber> result = subscriberDao.searchSubscriber(false, 1, 2);

        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(sub11));
    }

    @Test
    public void searchSubscriber_IncludeChild() {
        addSubscribers();
        sub11.setService(ser3);
        subscriberDao.createSubscriber(sub11);

        List<Subscriber> result = subscriberDao.searchSubscriber(true, 1, 2);

        assertThat(result.size(), is(10));
    }

    private void addSubscribers() {
        subscriberDao.createSubscriber(sub1);
        subscriberDao.createSubscriber(sub2);
        subscriberDao.createSubscriber(sub3);
        subscriberDao.createSubscriber(sub4);
        subscriberDao.createSubscriber(sub5);
        subscriberDao.createSubscriber(sub6);
        subscriberDao.createSubscriber(sub7);
        subscriberDao.createSubscriber(sub8);
        subscriberDao.createSubscriber(sub9);
        subscriberDao.createSubscriber(sub10);
    }

    private void checkSubscribers(List<Subscriber> result) {
        assertThat(result.size(), is(10));
        assertThat(result.get(0), is(sub1));
        assertThat(result.get(1), is(sub2));
        assertThat(result.get(2), is(sub3));
        assertThat(result.get(3), is(sub4));
        assertThat(result.get(4), is(sub5));
        assertThat(result.get(5), is(sub6));
        assertThat(result.get(6), is(sub7));
        assertThat(result.get(7), is(sub8));
        assertThat(result.get(8), is(sub9));
        assertThat(result.get(9), is(sub10));

    }

    private Subscriber createSubscriber(int id) {
        return Subscriber.builder()
                .id(id)
                .fio("fio" + id)
                .account("account-id" + id)
                .service(ser4)
                .build();
    }
}
