package io.jongyun.graphinstagram.batch.example

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class JobRunner(
    private val jobLauncher: JobLauncher,
    private val job: Job
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        // job 에 대해 parameters 가 동일하면 jobInstance 는 동일한 객체를 return 하기 때문에 batch 에 실패한다.
        val jobParameters = JobParametersBuilder()
            .addString("name", "user2")
            .toJobParameters()

        jobLauncher.run(job, jobParameters)
    }
}