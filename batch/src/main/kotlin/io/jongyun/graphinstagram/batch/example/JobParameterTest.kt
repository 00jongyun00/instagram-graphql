package io.jongyun.graphinstagram.batch.example

import java.util.*
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class JobParameterTest(
    val jobLauncher: JobLauncher,
    val job: Job
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val jobParameters = JobParametersBuilder()
            .addString("name", "user3")
            .addLong("id", 2L)
            .addDouble("weight", 65.5)
            .addDate("date", Date())
            .toJobParameters()

        jobLauncher.run(job, jobParameters)
    }
}