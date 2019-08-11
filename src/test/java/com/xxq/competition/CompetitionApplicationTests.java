package com.xxq.competition;

import com.xxq.competition.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitionApplicationTests {

    @Autowired
    Person person;
    Logger logger=LoggerFactory.getLogger(CompetitionApplicationTests.class);
    @Test
    public void contextLoads() {
        logger.trace("this is trace log");
        logger.debug("degug trace");
        logger.info("info trace");
        logger.warn("warning trace");
        logger.error("error trace");
        //System.out.println(person);
    }

}
