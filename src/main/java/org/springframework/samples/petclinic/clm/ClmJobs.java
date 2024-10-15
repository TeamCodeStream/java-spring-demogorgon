package org.springframework.samples.petclinic.clm;

import com.newrelic.api.agent.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClmJobs {

	private final OwnerRepository ownerRepository;
	private static final Logger logger = LoggerFactory.getLogger(ClmJobs.class);

	public ClmJobs(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

    // No CLM expected - needs @Trace annotation
    @Scheduled(fixedRate = 35000)
    protected void doStuff1() {
        logger.info("doStuff1");
        List<PetType> petTypes = ownerRepository.findPetTypes();
        logger.info("doStuff petTypes {}", petTypes);
    }

    // Expected CLM - but might be too fast to get recorded
    @Trace(dispatcher = true)
    @Scheduled(fixedRate = 90000)
    protected void doStuff4() {
        logger.info("doStuff4");
    }

    // Expected CLM
    @Trace(dispatcher = true)
    @Scheduled(fixedRate = 60000)
    protected void doStuff2() {
        logger.info("doStuff2");
        List<PetType> petTypes = ownerRepository.findPetTypes();
		logger.info("doStuff2 petTypes {}", petTypes);
    }

    // Expected CLM
    @Trace(dispatcher = true, metricName = "clmJobs/doStuff3")
    @Scheduled(fixedRate = 70000)
    protected void doStuff3() {
        logger.info("doStuff3");
        List<PetType> petTypes = ownerRepository.findPetTypes();
        logger.info("doStuff3 petTypes {}", petTypes);
    }
}
