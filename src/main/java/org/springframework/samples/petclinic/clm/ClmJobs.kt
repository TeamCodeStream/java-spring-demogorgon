package org.springframework.samples.petclinic.clm

import com.newrelic.api.agent.Trace
import org.slf4j.LoggerFactory
import org.springframework.samples.petclinic.owner.OwnerRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class ClmJobs(private val ownerRepository: OwnerRepository) {
    private val logger = LoggerFactory.getLogger(ClmJobs::class.java)

    // No CLM expected - needs @Trace annotation
    @Scheduled(fixedRate = 35000)
    protected fun doStuff1() {
        logger.info("doStuff1")
        val petTypes = ownerRepository.findPetTypes()
        logger.info("doStuff petTypes {}", petTypes)
    }

    // Expected CLM - but might be too fast to get recorded
    @Trace(dispatcher = true)
    @Scheduled(fixedRate = 90000)
    protected fun doStuff4() {
        logger.info("doStuff4")
    }

    // Expected CLM
    @Trace(dispatcher = true)
    @Scheduled(fixedRate = 60000)
    protected fun doStuff2() {
        logger.info("doStuff2")
        val petTypes = ownerRepository.findPetTypes()
        logger.info("doStuff2 petTypes {}", petTypes)
    }

    // Expected CLM
    @Trace(dispatcher = true, metricName = "clmJobs/doStuff3")
    @Scheduled(fixedRate = 70000)
    fun doStuff3() {
        logger.info("doStuff3")
        val petTypes = ownerRepository.findPetTypes()
        logger.info("doStuff3 petTypes {}", petTypes)
    }
}
